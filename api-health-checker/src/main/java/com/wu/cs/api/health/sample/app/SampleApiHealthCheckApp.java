package com.wu.cs.api.health.sample.app;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ApplicationEventMulticaster;
import org.springframework.context.event.SimpleApplicationEventMulticaster;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import com.wu.cs.api.health.sample.app.endpoint.event.ApisCalledMeasureListener;
import com.wu.cs.api.health.sample.app.endpoint.event.JvmStartedMeasureListener;

/**
 * Spring boot sample application to show how use the API health check component
 * @author carlosdlr
 *
 */
@SpringBootApplication
@Configuration
public class SampleApiHealthCheckApp {
  /**
   * Startup method to run the spring boot service
   * @param args arguments that can be passed to the service through console
   */
  public static void main(String[] args) {
    Class<?>[] confClassesArray = {SampleApiHealthCheckApp.class, JvmStartedMeasureListener.class, ApisCalledMeasureListener.class};
    SpringApplication.run(confClassesArray, args).start();
  }

  @Bean
  EntityManagerFactory entityManagerFactory() {
    EntityManagerFactory emf = Persistence.createEntityManagerFactory("example-unit");
    return emf;
  }

  @Bean
  public PlatformTransactionManager transactionManager() {
    JpaTransactionManager txManager = new JpaTransactionManager();
    txManager.setEntityManagerFactory(entityManagerFactory());
    return txManager;
  }
  
  /**
   * configuration to allow to the listeners work in asynchronous way
   * @return
   */
  @Bean(name = "applicationEventMulticaster")
  public ApplicationEventMulticaster simpleApplicationEventMulticaster() {
      SimpleApplicationEventMulticaster eventMulticaster 
        = new SimpleApplicationEventMulticaster();
       
      eventMulticaster.setTaskExecutor(new SimpleAsyncTaskExecutor());
      return eventMulticaster;
  }
  
}
