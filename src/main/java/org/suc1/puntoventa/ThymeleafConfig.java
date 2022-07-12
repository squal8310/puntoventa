package org.suc1.puntoventa;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import nz.net.ultraq.thymeleaf.LayoutDialect;

@Configuration
public class ThymeleafConfig {
    
	@Bean
    public LayoutDialect layoutDialect() {
        return new LayoutDialect();
    }

}