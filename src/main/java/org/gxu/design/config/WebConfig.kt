package org.gxu.design.config

import lombok.extern.slf4j.Slf4j
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.UrlBasedCorsConfigurationSource
import org.springframework.web.filter.CorsFilter
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
@ConfigurationProperties(prefix = "myproject.imgupload")
open class WebConfig :WebMvcConfigurer {
    lateinit var path1:String
    lateinit var path2:String
    lateinit var path3:String
    override fun addResourceHandlers(registry: ResourceHandlerRegistry) {
        registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/admin/")
        registry.addResourceHandler("/img/lost/**").addResourceLocations(path1)
        registry.addResourceHandler("/img/found/**").addResourceLocations(path2)
        registry.addResourceHandler("/img/header/**").addResourceLocations(path3)
        registry.addResourceHandler("/assets/**").addResourceLocations("classpath:/static/assets/")

    }

   
}