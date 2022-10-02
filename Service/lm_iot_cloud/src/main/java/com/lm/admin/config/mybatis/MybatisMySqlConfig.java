package com.lm.admin.config.mybatis;

import com.alibaba.druid.pool.DruidDataSource;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.ILoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;

@Configuration
@MapperScan(basePackages = MybatisMySqlConfig.PACKAGE, sqlSessionFactoryRef = "mysqlSqlSessionFactory")
public class MybatisMySqlConfig {
    // 精确到 cluster 目录，以便跟其他数据源隔离
    static final String PACKAGE = "com.lm.admin.mapper.mysql";
    static final String MAPPER_LOCATION = "classpath*:mapper/mysql/*.xml";
    @Value("${spring.datasource.mysql.url}")
    private String url;

    @Value("${spring.datasource.mysql.username}")
    private String user;

    @Value("${spring.datasource.mysql.password}")
    private String password;

    @Value("${spring.datasource.mysql.driver-class-name}")
    private String driverClass;

    @Bean(name = "mysqlDataSource")
    public DataSource clusterDataSource() {
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setDriverClassName(driverClass);
        dataSource.setUrl(url);
        dataSource.setUsername(user);
        dataSource.setPassword(password);
        dataSource.setDbType("com.alibaba.druid.pool.DruidDataSource");
        return dataSource;
    }

    @Bean(name = "mysqlTransactionManager")
    public DataSourceTransactionManager clusterTransactionManager() {
        return new DataSourceTransactionManager(clusterDataSource());
    }
    // 等等在试试mp https://blog.csdn.net/qq_41389354/article/details/112008695
    @Bean(name = "mysqlSqlSessionFactory")
    public SqlSessionFactory clusterSqlSessionFactory(@Qualifier("mysqlDataSource") DataSource clusterDataSource)
            throws Exception {
        final SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
        sessionFactory.setDataSource(clusterDataSource);
        // mapper xml扫描
        sessionFactory.setMapperLocations(new PathMatchingResourcePatternResolver()
                .getResources(MybatisMySqlConfig.MAPPER_LOCATION));
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
