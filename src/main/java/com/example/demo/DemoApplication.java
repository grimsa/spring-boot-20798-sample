package com.example.demo;

import org.eclipse.persistence.config.PersistenceUnitProperties;
import org.eclipse.persistence.logging.SessionLog;
import org.eclipse.persistence.platform.database.H2Platform;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableLoadTimeWeaving;
import org.springframework.context.annotation.Import;
import org.springframework.core.env.Environment;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.EclipseLinkJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.Map;

@SpringBootApplication
@EnableTransactionManagement
@Import(DemoApplication.LoadTimeWeavingConfiguration.class)
public class DemoApplication {
    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

    @Bean
    public PlatformTransactionManager transactionManager(EntityManagerFactory entityManagerFactory) {
        return new JpaTransactionManager(entityManagerFactory);
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(
            JpaVendorAdapter jpaVendorAdapter,
            Map<String, String> jpaPropertiesMap,
            DataSource dataSource
    ) {
        LocalContainerEntityManagerFactoryBean entityManagerFactory = new LocalContainerEntityManagerFactoryBean();
        entityManagerFactory.setPersistenceUnitName("primary-persistence-unit");
        entityManagerFactory.setDataSource(dataSource);
        entityManagerFactory.setPackagesToScan("com.example");
        entityManagerFactory.setJpaVendorAdapter(jpaVendorAdapter);
        entityManagerFactory.setJpaPropertyMap(jpaPropertiesMap);
        return entityManagerFactory;
    }

    @Bean
    public DataSource dataSource(Environment env) {
        return DataSourceBuilder.create()
                .url(env.getRequiredProperty("spring.datasource.url"))
                .username(env.getRequiredProperty("spring.datasource.username"))
                .password(env.getRequiredProperty("spring.datasource.password"))
                .build();
    }

    @Bean
    JpaVendorAdapter eclipseLinkJpaVendorAdapter(Environment env) {
        EclipseLinkJpaVendorAdapter jpaVendorAdapter = new EclipseLinkJpaVendorAdapter();
        jpaVendorAdapter.setDatabasePlatform(H2Platform.class.getName());
        jpaVendorAdapter.setShowSql(false);
        jpaVendorAdapter.setGenerateDdl(false);
        return jpaVendorAdapter;
    }

    @Bean
    Map<String, String> jpaPropertiesMap(Environment env) {
        return Map.of(
                PersistenceUnitProperties.CATEGORY_LOGGING_LEVEL_ + SessionLog.WEAVER, SessionLog.FINEST_LABEL,
                PersistenceUnitProperties.WEAVING, "true"
        );
    }

    @Configuration
    @EnableLoadTimeWeaving(aspectjWeaving = EnableLoadTimeWeaving.AspectJWeaving.DISABLED)
    public static class LoadTimeWeavingConfiguration {

        /**
         * This removes an internal Spring Boot bean that interferes with EclipseLink load-time weaving<br>
         * Hopefully just a temporary workaround until they fix the issue.
         *
         * @see <a href="https://github.com/spring-projects/spring-boot/issues/20798">Description of the problem</a>
         */
        // FIXME app should not start with this disabled
//        @Bean
//        static BeanDefinitionRegistryPostProcessor offendingValidatorRemovingBeanDefinitionRegistryPostProcessor() {
//            return new BeanDefinitionRegistryPostProcessor() {
//                @Override
//                public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) {
//                }
//
//                @Override
//                public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) {
//                    registry.removeBeanDefinition("org.springframework.boot.context.properties.ConfigurationPropertiesBeanDefinitionValidator");
//                }
//            };
//        }
    }
}
