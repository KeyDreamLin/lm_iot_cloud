package com.lm.admin.config;

import com.lm.admin.filter.TestLm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * web配置
 * @author Lm
 * @date 2022/11/22 9:25
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Autowired
    private TestLm testLm;

    /**
     * 配置 允许跨域
     * @param registry
     */
    public void addCorsMappings(CorsRegistry registry){
        registry.addMapping("/**")
//                .allowedOrigins("http://xxx.com") // 允许跨域的域名
                .allowedOriginPatterns("*") // 允许所有域
                .allowedMethods("POST","GET","PUT","OPTIONS","DELETE")
//                .allowedMethods("*") // 允许任何方法 post set等等等等
                .allowedHeaders("*") //允许任何请求头
                .allowCredentials(true) // 允许任何证书、cookie
                .maxAge(3600L); // mexAge(3600L)表示在3600秒内，不需要再发送预校验请求，可以缓存该结果
    }

    /**
     * 配置拦截器
     * @param registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(testLm).excludePathPatterns("/api/user/login/**","/api/common/captcha");

    }

    /***
     * 设置静态资源映射
     * @param registry
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
//        registry.addResourceHandler("/login-pc-demo/**").addResourceLocations("classpath:/login-pc-demo/");
    }
}
