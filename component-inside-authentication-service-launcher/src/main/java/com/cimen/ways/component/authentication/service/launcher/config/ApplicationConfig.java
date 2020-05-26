
package com.cimen.ways.component.authentication.service.launcher.config;

import com.alibaba.druid.pool.DruidDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;

@Component
@MapperScan(basePackages = "com.cimen.ways.component.gateway.dal")
public class ApplicationConfig {

    private String url = "jdbc:mysql://10.0.2.8:3306/ways?characterEncoding=utf-8";
    @Value("${ways.jdbc.username}")
    private String userName;
    @Value("${ways.jdbc.password}")
    private String password;
    @Value("${druid.initialSize}")
    private int initialSize;
    @Value("${druid.logAbandoned}")
    private boolean logAbandoned;
    @Value("${druid.maxActive}")
    private int maxActive;
    @Value("${druid.maxWait}")
    private int maxWait;
    @Value("${druid.minEvictableIdleTimeMillis}")
    private int minEvictableIdleTimeMillis;
    @Value("${druid.minIdle}")
    private int minIdle;
    @Value("${druid.removeAbandoned}")
    private boolean removeAbandoned;
    @Value("${druid.removeAbandonedTimeout}")
    private int removeAbandonedTimeout;
    @Value("${druid.timeBetweenEvictionRunsMillis}")
    private int timeBetweenEvictionRunsMillis;

    @Value("${mybatis.mapper.location}")
    private String mapperLocation ;


    @Value("${mybatis.config.location}")
    private String configLocation;


    @Bean
    public DataSource getDataSource() {
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setUrl(url);
        dataSource.setDriverClassName("com.mysql.jdbc.Driver");
        dataSource.setUsername(this.userName);
        dataSource.setPassword(this.password);
        dataSource.setInitialSize(this.initialSize);
        dataSource.setLogAbandoned(this.logAbandoned);
        dataSource.setMinIdle(this.minIdle);
        dataSource.setMinEvictableIdleTimeMillis(this.minEvictableIdleTimeMillis);
        dataSource.setMaxActive(this.maxActive);
        dataSource.setMaxWait(this.maxWait);
        dataSource.setRemoveAbandoned(this.removeAbandoned);
        dataSource.setRemoveAbandonedTimeout(this.removeAbandonedTimeout);
        dataSource.setTimeBetweenEvictionRunsMillis(this.timeBetweenEvictionRunsMillis);
        return dataSource;
    }

    @Bean
    public SqlSessionFactory sqlSessionFactory(DataSource dataSource) throws Exception {
        SqlSessionFactoryBean sessionFactoryBean = new SqlSessionFactoryBean();
        sessionFactoryBean.setDataSource(dataSource);

        // 配置mapper的扫描，找到所有的mapper.xml映射文件
        Resource[] resources = new PathMatchingResourcePatternResolver().getResources(mapperLocation);
        sessionFactoryBean.setMapperLocations(resources);

        // 加载全局的配置文件
        sessionFactoryBean.setConfigLocation(new DefaultResourceLoader().getResource(configLocation));
        return sessionFactoryBean.getObject();
    }

    @Bean
    public SqlSessionTemplate sqlSessionTemplate(SqlSessionFactory sqlSessionFactory) {
        return new SqlSessionTemplate(sqlSessionFactory);
    }

    @Bean
    public DataSourceTransactionManager dataSourceTransactionManager(DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }
}
