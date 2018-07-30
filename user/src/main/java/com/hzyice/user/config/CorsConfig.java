//package com.hzyice.user.config;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.stereotype.Component;
//import org.springframework.web.cors.CorsConfiguration;
//import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
//import org.springframework.web.filter.CorsFilter;
//
//import javax.servlet.Filter;
//
///*zuul 解决跨域问题*/
//
//@Component
//public class CorsConfig {
//
//    @Bean
//    public Filter corsFilter() {
//        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//        CorsConfiguration config = new CorsConfiguration();
//        config.setAllowCredentials(true);
//        config.addAllowedOrigin("*");
//        config.addAllowedHeader("*");
//        config.addAllowedMethod("*");
//        config.addExposedHeader("x-auth-token");
//        config.addExposedHeader("x-total-count");
//        source.registerCorsConfiguration("/**", config);
//        return new CorsFilter(source);
//    }
//
//   /* @Override
//    protected void configure(HttpSecurity httpSecurity) throws Exception {
//        httpSecurity.addFilterBefore(corsFilter(), ChannelProcessingFilter.class);
//    }*/
//
//
//}
