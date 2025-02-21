package dwp.agrilog.controladores;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Import;

import dwp.agrilog.configuracion.DwpConfig;

@SpringBootApplication(scanBasePackages = "dwp.agrilog", exclude = SecurityAutoConfiguration.class)
@Import(DwpConfig.class)
@ServletComponentScan
public class InicioSpring extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(InicioSpring.class);
    }
    
    public static void main(String[] args) {
        SpringApplication.run(InicioSpring.class, args);
    }
    
}
