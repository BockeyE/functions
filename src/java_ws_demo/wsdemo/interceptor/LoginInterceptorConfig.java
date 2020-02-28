//package java_ws_demo.wsdemo.interceptor;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.cors.CorsConfiguration;
//import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
//import org.springframework.web.filter.CorsFilter;
//import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
//
//@Configuration
//public class LoginInterceptorConfig implements WebMvcConfigurer {
//
//    @Override
//    public void addInterceptors(InterceptorRegistry registry) {
//        registry.addInterceptor(loggerInterceptor())
//                .addPathPatterns("/**")
//                .excludePathPatterns(
////                        "/**",
//                        "/index",
//                        "/v1/user/login",
//                        "/v1/user/logout",
//                        "/v1/user/forgetChange",
//                        "/v1/user/askforVerify",
//                        "/v1/file/**",
//                        "/error/**");
//    }
//
////    @Override
////    public void addCorsMappings(CorsRegistry registry) {
////        System.out.println("我是跨域拦截器注册");
////        registry.addMapping("/**")
////                .allowedOrigins(allowedOrigin)
////                .allowedMethods("*")
////                .maxAge(33600)
////                .allowCredentials(true);
////    }
//
//    @Bean
//    public CorsFilter corsFilter() {
//        CorsConfiguration config = new CorsConfiguration();
//        config.addAllowedOrigin("*");
//        config.setAllowCredentials(true);
//        config.addAllowedMethod("*");
//        config.addAllowedHeader("*");
//        UrlBasedCorsConfigurationSource configSource = new UrlBasedCorsConfigurationSource();
//        configSource.registerCorsConfiguration("/**", config);
//        return new CorsFilter(configSource);
//    }
//
//
//    @Bean
//    LoginInterceptor loggerInterceptor() {
//        return new LoginInterceptor();
//    }
//}
