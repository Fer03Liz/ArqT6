package com.taller3arqui.demo.config;

import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.ws.config.annotation.EnableWs;
import org.springframework.ws.transport.http.MessageDispatcherServlet;
import org.springframework.ws.wsdl.wsdl11.DefaultWsdl11Definition;
import org.springframework.xml.xsd.SimpleXsdSchema;
import org.springframework.xml.xsd.XsdSchema;

@EnableWs
@Configuration
public class WebServiceConfig {

    @Bean
    public ServletRegistrationBean<MessageDispatcherServlet> messageDispatcherServlet(ApplicationContext ctx) {
        MessageDispatcherServlet servlet = new MessageDispatcherServlet();
        servlet.setApplicationContext(ctx);
        servlet.setTransformWsdlLocations(true);
        return new ServletRegistrationBean<>(servlet, "/ws/*"); // todo lo SOAP responde en /ws/*
    }

    @Bean(name = "pagosSOAP")
    public DefaultWsdl11Definition defaultWsdl11Definition(XsdSchema pagosSchema) {
        DefaultWsdl11Definition definition = new DefaultWsdl11Definition();
        definition.setPortTypeName("PagosPort");
        definition.setLocationUri("/ws");
        definition.setTargetNamespace("http://taller3arqui.com/pagos");
        definition.setSchema(pagosSchema);
        return definition;
    }

    @Bean
    public XsdSchema pagosSchema() {
        return new SimpleXsdSchema(new ClassPathResource("pagos.xsd"));
    }
}
