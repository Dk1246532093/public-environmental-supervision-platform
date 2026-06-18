package com.cqupt.nepmserver;
//前后端分离--》跨域配置
import org.springframework.context.annotation.Configuration; //配置类注解
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer; // web配置接口
import org.springframework.web.servlet.config.annotation.CorsRegistry; //跨域配置
@Configuration  //配置类注解，spring会自动加载
public class WebMvcConfig  implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") //配置跨域路径
                .allowedOriginPatterns("*") //允许所有域名访问
                .allowCredentials(true) //是否发送cookie信息
                .allowedMethods("GET", "POST", "PUT", "DELETE") //允许的方法
                .maxAge(3600); //预检请求的缓存时间（秒）
    }

}
