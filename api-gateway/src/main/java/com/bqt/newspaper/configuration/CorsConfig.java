package com.bqt.newspaper.configuration;



import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsWebFilter;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;

import java.util.List;


@Configuration
public class CorsConfig extends CorsConfiguration {
    @Bean
    public CorsWebFilter corsFilter()
    {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials( true );
        config.setAllowedOrigins( List.of( "http://localhost:4200" ) );
        config.setAllowedMethods( List.of( "GET", "POST", "PUT", "DELETE") );
//        "origin", "content-type", "accept", "authorization", "cookie"
        config.setAllowedHeaders( List.of("*" ) );

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration( "/**", config );

        return new CorsWebFilter( source );
    }
}
