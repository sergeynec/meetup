package ru.necs.da.spring;

import static java.util.EnumSet.of;
import static org.hibernate.tool.schema.TargetType.DATABASE;
import static org.springframework.jdbc.datasource.DataSourceUtils.getConnection;
import java.util.Properties;
import javax.annotation.PostConstruct;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.tool.hbm2ddl.SchemaExport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import ru.necs.da.model.ValueEntity;

@Configuration
@Import(H2Config.class)
@EnableTransactionManagement
@EnableJpaRepositories("ru.necs.da.repository")
public class DaConfig {

    @Autowired
    private DataSource dataSource;

    @Bean
    @Autowired
    public EntityManagerFactory entityManagerFactory(final Properties hibernateProperties) {
        final LocalContainerEntityManagerFactoryBean factoryBean = new LocalContainerEntityManagerFactoryBean();
        factoryBean.setDataSource(dataSource);
        factoryBean.setPersistenceUnitName("Meetup");
        factoryBean.setPackagesToScan("ru.necs.da.model");
        factoryBean.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        factoryBean.setJpaProperties(hibernateProperties);
        factoryBean.afterPropertiesSet();

        return factoryBean.getObject();
    }

    @Bean
    @Autowired
    public PlatformTransactionManager transactionManager(final EntityManagerFactory entityManagerFactory) {
        final JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManagerFactory);

        return transactionManager;
    }

    @PostConstruct
    public void init() {
        final MetadataSources metadataSources = new MetadataSources(
                new StandardServiceRegistryBuilder()
                        .applySetting("hibernate.dialect", "org.hibernate.dialect.H2Dialect")
                        .applySetting("javax.persistence.schema-generation-connection", getConnection(dataSource))
                        .applySetting("javax.persistence.schema-generation.database.action", "drop-and-create")
                        .build()
        );
        metadataSources.addAnnotatedClass(ValueEntity.class);

        new SchemaExport().create(of(DATABASE), metadataSources.buildMetadata());
    }
}
