package com.taller3arqui.demo.initdata;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

import com.taller3arqui.demo.entidades.Producto;
import com.taller3arqui.demo.repositorios.ProductoRepository;

@Configuration
public class DataLoader {

    @Bean
    CommandLineRunner initDatabase(ProductoRepository productoRepository) {
        return args -> {
            if (productoRepository.count() == 0) { // solo si está vacío
                try (BufferedReader reader = new BufferedReader(
                        new InputStreamReader(
                                getClass().getResourceAsStream("/data_inventario.txt"),
                                StandardCharsets.UTF_8))) {

                    String line;
                    while ((line = reader.readLine()) != null) {
                        String[] parts = line.split("\\|");
                        if (parts.length == 5) {
                            Producto p = new Producto();
                            p.setTituloProductos(parts[0]);
                            p.setDescripcion(parts[1]);
                            p.setCantidad(Integer.parseInt(parts[2]));
                            p.setCategoria(parts[3]);
                            p.setPrecio(Double.parseDouble(parts[4]));
                            productoRepository.save(p);
                            productoRepository.save(p);
                            System.out.println("✅ Insertado: " + p.getTituloProductos());

                        }
                    }
                }
            }
        };
    }
}
