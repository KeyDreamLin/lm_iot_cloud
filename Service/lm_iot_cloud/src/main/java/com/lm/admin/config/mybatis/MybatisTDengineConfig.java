package com.lm.admin.config.mybatis;

import com.alibaba.druid.pool.DruidDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;

@Configuration
@MapperScan(basePackages = MybatisTDengineConfig.PACKAGE, sqlSessionFactoryRef = "tdengineSqlSessionFactory")
public class MybatisTDengineConfig {
    // 精确到 cluster 目录，以便跟其他数据源隔离
    static final String PACKAGE = "com.lm.admin.mapper.tdengine";
    static final String MAPPER_LOCATION = "classpath*:mapper/tdengine/*.xml";
    @Value("${spring.datasource.tdengine.url}")
    private String url;

    @Value("${spring.datasource.tdengine.username}")
    private String user;

    @Value("${spring.datasource.tdengine.password}")
    private String password;

    @Value("${spring.datasource.tdengine.driver-class-name}")
    private String driverClass;

    @Bean(name = "tdengineDataSource")
    public DataSource clusterDataSource() {
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setDriverClassName(driverClass);
        dataSource.setUrl(url);
        dataSource.setUsername(user);
        dataSource.setPassword(password);
//        dataSource.setDbType("com.alibaba.druid.pool.DruidDataSource");
        return dataSource;
    }

    @Bean(name = "tdengineTransactionManager")
    public DataSourceTransactionManager clusterTransactionManager() {
        return new DataSourceTransactionManager(clusterDataSource());
    }
    @Bean(name = "tdengineSqlSessionFactory")
    public SqlSessionFactory clusterSqlSessionFactory(@Qualifier("tdengineDataSource") DataSource clusterDataSource)
            throws Exception {
        final SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
        sessionFactory.setDataSource(clusterDataSource);
        // mapper xml扫描
        sessionFactory.setMapperLocations(new PathMatchingResourcePatternResolver()
                .getResources(MybatisTDengineConfig.MAPPER_LOCATION));
        try {
            // https://blog.csdn.net/yangshengwei230612/article/details/122583057
            //开启驼峰命名转换 abc_efg-->abcEfg
            sessionFactory.getObject().getConfiguration().setMapUnderscoreToCamelCase(true);
            return sessionFactory.getObject();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
