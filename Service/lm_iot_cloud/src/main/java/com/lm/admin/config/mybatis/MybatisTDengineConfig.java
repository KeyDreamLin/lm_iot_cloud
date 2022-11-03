package com.lm.admin.config.mybatis;

import com.alibaba.druid.pool.DruidDataSource;
import org.apache.ibatis.logging.stdout.StdOutImpl;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.mybatis.spring.boot.autoconfigure.MybatisProperties;
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
        dataSource.setValidationQuery("select server_status();");
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

        //此处创建一个Configuration 注意包不要引错了
        org.apache.ibatis.session.Configuration configuration=new org.apache.ibatis.session.Configuration();
        //配置日志实现
        configuration.setLogImpl(StdOutImpl.class);
        //此处可以添加其他mybatis配置 例如转驼峰命名
        configuration.setMapUnderscoreToCamelCase(true);

        // sessionFactory工厂装载上面配置的Configuration
        sessionFactory.setConfiguration(configuration);
        return sessionFactory.getObject();
    }


}
