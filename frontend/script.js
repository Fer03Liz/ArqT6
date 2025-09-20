    // Configuración de la API - Se conecta al backend en Docker
    const API_URL = 'http://localhost:8080/api/productos';
    let editandoId = null;
    let todosLosProductos = [];

    // Elementos del DOM
    const form = document.getElementById('productoForm');
    const statusDiv = document.getElementById('status');
    const loadingDiv = document.getElementById('loading');
    const productosContainer = document.getElementById('productosContainer');
    const refreshBtn = document.getElementById('refreshBtn');
    const cancelBtn = document.getElementById('cancelBtn');
    const searchInput = document.getElementById('searchInput');
    const conexionStatus = document.getElementById('conexion-status');
    const conexionText = document.getElementById('conexion-text');

    // Mostrar status de mensaje
    function showStatus(message, isError = false) {
        statusDiv.textContent = message;
        statusDiv.className = `status ${isError ? 'error' : 'success'}`;
        statusDiv.style.display = 'block';
        setTimeout(() => {
            statusDiv.style.display = 'none';
        }, 4000);
    }

    // Actualizar status de conexión
    function updateConexionStatus(isOnline) {
        if (isOnline) {
            conexionStatus.className = 'conexion-status online';
            conexionText.textContent = '🟢 Conectado';
        } else {
            conexionStatus.className = 'conexion-status offline';
            conexionText.textContent = '🔴 Sin conexión';
        }
    }

    // Cargar productos desde la API
    async function cargarProductos() {
        try {
            loadingDiv.style.display = 'block';
            refreshBtn.disabled = true;
            
            const response = await fetch(API_URL);
            
            if (!response.ok) {
                throw new Error(`Error HTTP: ${response.status}`);
            }
            
            const productos = await response.json();
            todosLosProductos = productos;
            mostrarProductos(productos);
            updateConexionStatus(true);
            
        } catch (error) {
            console.error('Error al cargar productos:', error);
            updateConexionStatus(false);
            showStatus('Error al cargar productos. ¿Está ejecutándose el servidor backend?', true);
            productosContainer.innerHTML = '<div class="no-productos">❌ No se pudieron cargar los productos</div>';
        } finally {
            loadingDiv.style.display = 'none';
            refreshBtn.disabled = false;
        }
    }

    // Mostrar productos en la interfaz
    function mostrarProductos(productos) {
    
        if (productos.length === 0) {
            productosContainer.innerHTML = '<div class="no-productos">📦 No hay productos registrados</div>';
            return;
        }
        
        productosContainer.innerHTML = productos.map(producto => {
            const stockClass = producto.cantidad <= 5 ? (producto.cantidad === 0 ? 'Agotado' : 'Poco') : '';
            const stockText = producto.cantidad === 0 ? 'Agotado' : producto.cantidad;
            
            return `
                <div class="producto-card">
                    <div class="stock ${stockClass}">${stockText}</div>
                    <h4>${producto.tituloProductos || 'Producto sin nombre'}</h4>
                    <p class="precio">$${Number(producto.precio).toFixed(2)}</p>
                    <p><strong>Descripción:</strong> ${producto.descripcion || 'Sin descripción'}</p>
                    <p><strong>Categoría:</strong> ${producto.categoria || 'Sin categoría'}</p>
                </div>
            `;
        }).join('');
    }


    // Filtrar productos por búsqueda
    function filtrarProductos() {
        const termino = searchInput.value.toLowerCase();
        const productosFiltrados = todosLosProductos.filter(producto =>
            (producto.tituloProductos && producto.tituloProductos.toLowerCase().includes(termino)) ||
            (producto.descripcion && producto.descripcion.toLowerCase().includes(termino)) ||
            (producto.categoria && producto.categoria.toLowerCase().includes(termino))
        );
        mostrarProductos(productosFiltrados);
    }


    // Manejar envío del formulario
    form.addEventListener('submit', async (e) => {
        e.preventDefault();
        
        const submitBtn = form.querySelector('button[type="submit"]');
        const originalText = submitBtn.textContent;
        submitBtn.disabled = true;
        submitBtn.textContent = '⏳ Guardando...';
        
        const producto = {
            tituloProductos: document.getElementById('nombre').value.trim(), 
            descripcion: document.getElementById('descripcion').value.trim(),
            precio: parseFloat(document.getElementById('precio').value),
            cantidad: parseInt(document.getElementById('stock').value), 
            categoria: document.getElementById('categoria')?.value.trim() || ''
        };
        
        // Validaciones básicas
        if (!producto.tituloProductos) {
            showStatus('El nombre del producto es requerido', true);
            submitBtn.disabled = false;
            submitBtn.textContent = originalText;
            return;
        }
        
        if (producto.precio < 0 || producto.cantidad < 0) {
            showStatus('El precio y stock no pueden ser negativos', true);
            submitBtn.disabled = false;
            submitBtn.textContent = originalText;
            return;
        }
        
        try {
            let response;
            if (editandoId) {
                response = await fetch(`${API_URL}/${editandoId}`, {
                    method: 'PUT',
                    headers: { 'Content-Type': 'application/json' },
                    body: JSON.stringify(producto)
                });
            } else {
                response = await fetch(API_URL, {
                    method: 'POST',
                    headers: { 'Content-Type': 'application/json' },
                    body: JSON.stringify(producto)
                });
            }
            
            if (response.ok) {
                showStatus(editandoId ? '✅ Producto actualizado correctamente' : '✅ Producto creado correctamente');
                form.reset();
                cancelarEdicion();
                cargarProductos();
                updateConexionStatus(true);
            } else {
                const errorData = await response.text();
                throw new Error(`Error HTTP ${response.status}: ${errorData}`);
            }
        } catch (error) {
            console.error('Error:', error);
            updateConexionStatus(false);
            showStatus(`Error al guardar el producto: ${error.message}`, true);
        } finally {
            submitBtn.disabled = false;
            submitBtn.textContent = originalText;
        }
    });

    // Editar producto - No implementado
    async function editarProducto(id) {
        try {
            const response = await fetch(`${API_URL}/${id}`);
            
            if (!response.ok) {
                throw new Error(`Error HTTP: ${response.status}`);
            }
            
            const producto = await response.json();
            
            document.getElementById('productoId').value = producto.id;
            document.getElementById('nombre').value = producto.nombre;
            document.getElementById('descripcion').value = producto.descripcion || '';
            document.getElementById('precio').value = producto.precio;
            document.getElementById('stock').value = producto.stock;
            
            editandoId = id;
            cancelBtn.style.display = 'inline-block';
            
            const submitBtn = form.querySelector('button[type="submit"]');
            submitBtn.textContent = '💾 Actualizar Producto';
            
            document.querySelector('.form-section').scrollIntoView({ 
                behavior: 'smooth',
                block: 'start'
            });
            
            updateConexionStatus(true);
            showStatus(`Editando producto: ${producto.nombre}`);
            
        } catch (error) {
            console.error('Error al cargar producto:', error);
            updateConexionStatus(false);
            showStatus('Error al cargar producto para editar', true);
        }
    }

    // No implementado
    async function eliminarProducto(id) {
        const producto = todosLosProductos.find(p => p.id === id);
        const nombreProducto = producto ? producto.nombre : 'este producto';
        
        if (confirm(`¿Estás seguro de que quieres eliminar "${nombreProducto}"?`)) {
            try {
                const response = await fetch(`${API_URL}/${id}`, {
                    method: 'DELETE'
                });
                
                if (response.ok) {
                    showStatus(`✅ Producto "${nombreProducto}" eliminado correctamente`);
                    cargarProductos();
                    updateConexionStatus(true);
                } else {
                    throw new Error(`Error HTTP: ${response.status}`);
                }
            } catch (error) {
                console.error('Error:', error);
                updateConexionStatus(false);
                showStatus(`Error al eliminar el producto: ${error.message}`, true);
            }
        }
    }

    // Cancelar edición
    function cancelarEdicion() {
        editandoId = null;
        cancelBtn.style.display = 'none';
        form.reset();
        
        // Restaurar texto del botón
        const submitBtn = form.querySelector('button[type="submit"]');
        submitBtn.textContent = '💾 Guardar Producto';
    }

    // Verificar conexión con la API
    async function verificarConexion() {
        try {
            const response = await fetch(`${API_URL}/test`, {
                method: 'GET',
                timeout: 5000
            });
            
            if (response.ok) {
                updateConexionStatus(true);
                return true;
            } else {
                throw new Error(`HTTP ${response.status}`);
            }
        } catch (error) {
            console.error('Error de conexión:', error);
            updateConexionStatus(false);
            return false;
        }
    }

    // Event listeners
    refreshBtn.addEventListener('click', cargarProductos);
    cancelBtn.addEventListener('click', cancelarEdicion);
    searchInput.addEventListener("input", filtrarProductos);

    // Verificar conexión periódicamente
    setInterval(verificarConexion, 30000); // cada 30 segundos

    // Inicialización
    document.addEventListener('DOMContentLoaded', async () => {
        const conexionOk = await verificarConexion();
        
        if (conexionOk) {
            await cargarProductos();
        } else {
            showStatus('No se puede conectar con la API. Verificando conexión...', true);
            
            // Reintentar conexión cada 5 segundos hasta establecerla
            const intervalId = setInterval(async () => {
                if (await verificarConexion()) {
                    clearInterval(intervalId);
                    showStatus('Conexión restablecida. Cargando productos...');
                    cargarProductos();
                }
            }, 5000);
        }
    });