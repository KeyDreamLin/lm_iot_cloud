package com.lm.admin.config.json;

import com.alibaba.fastjson2.JSONReader;
import com.alibaba.fastjson2.JSONWriter;
import com.alibaba.fastjson2.support.config.FastJsonConfig;
import com.alibaba.fastjson2.support.spring.http.converter.FastJsonHttpMessageConverter;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Configuration
public class FastJsonConfiguration {

    @Bean
    public HttpMessageConverters customConverters() {
        List<HttpMessageConverter<?>> messageConverters = new ArrayList<>();

        FastJsonHttpMessageConverter converter = new FastJsonHttpMessageConverter();
        //自定义配置...
        FastJsonConfig config = new FastJsonConfig();
        config.setDateFormat("yyyy-MM-dd HH:mm:ss:ms");

        config.setReaderFeatures(
                JSONReader.Feature.FieldBased,              // 反序列化
                JSONReader.Feature.SupportArrayToBean,      // 支持数据映射的方式
                JSONReader.Feature.InitStringFieldAsEmpty,  // 初始化String字段为空字符串""
                JSONReader.Feature.AllowUnQuotedFieldNames  // 不支持不带双引号的key
        );

        config.setWriterFeatures(
                JSONWriter.Feature.PrettyFormat,                // 格式化输出
                JSONWriter.Feature.WriteNulls,                  // 序列化输出空值字段
                JSONWriter.Feature.NullAsDefaultValue,          // 将空置输出为缺省值，Number类型的null都输出为0，String类型的null输出为""，数组和Collection类型的输出为[]
                JSONWriter.Feature.BrowserCompatible            // 在大范围超过JavaScript支持的整数，输出为字符串格式
        );

        converter.setFastJsonConfig(config);
        converter.setDefaultCharset(StandardCharsets.UTF_8);
        converter.setSupportedMediaTypes(Collections.singletonList(MediaType.APPLICATION_JSON));
        messageConverters.add(0, converter);

        return new HttpMessageConverters(true, messageConverters);

    }
}

