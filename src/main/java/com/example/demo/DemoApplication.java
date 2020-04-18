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
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.EclipseLinkJpaVendorAdapter;

import javax.sql.DataSource;
import java.util.Map;

@SpringBootApplication
@Import(DemoApplication.LoadTimeWeavingConfiguration.class)
public class DemoApplication {
    private static final String ECLIPSELINK_WEAVER_LOGGING_LEVEL = SessionLog.FINER_LABEL;

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(DataSource dataSource) {
        LocalContainerEntityManagerFactoryBean entityManagerFactory = new LocalContainerEntityManagerFactoryBean();
        entityManagerFactory.setPersistenceUnitName("primary-persistence-unit");
        entityManagerFactory.setDataSource(dataSource);
        entityManagerFactory.setPackagesToScan("com.example.demo");
        entityManagerFactory.setJpaVendorAdapter(eclipseLinkJpaVendorAdapter());
        entityManagerFactory.setJpaPropertyMap(Map.of(
                PersistenceUnitProperties.CATEGORY_LOGGING_LEVEL_ + SessionLog.WEAVER, ECLIPSELINK_WEAVER_LOGGING_LEVEL,
                PersistenceUnitProperties.WEAVING, "true"
        ));
        return entityManagerFactory;
    }

    private JpaVendorAdapter eclipseLinkJpaVendorAdapter() {
        EclipseLinkJpaVendorAdapter jpaVendorAdapter = new EclipseLinkJpaVendorAdapter();
        jpaVendorAdapter.setDatabasePlatform(H2Platform.class.getName());
        jpaVendorAdapter.setShowSql(false);
        jpaVendorAdapter.setGenerateDdl(false);
        return jpaVendorAdapter;
    }

    @Bean
    public DataSource dataSource(Environment env) {
        return DataSourceBuilder.create()
                .url(env.getRequiredProperty("spring.datasource.url"))
                .username(env.getRequiredProperty("spring.datasource.username"))
                .password(env.getRequiredProperty("spring.datasource.password"))
                .build();
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
