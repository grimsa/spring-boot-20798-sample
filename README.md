# Purpose

This is a small application that can be used to reproduce [Spring Boot #20798](https://github.com/spring-projects/spring-boot/issues/20798) issue.

# Getting Started

To run locally:
- Run `gradle prepareInstrumentation`
- Run `com.example.demo.DemoApplication` with the following VM args: `-javaagent:build/docker/spring-instrument.jar`

Running should produce a similar result:
```
 :: Spring Boot ::        (v2.2.6.RELEASE)

2020-04-18 11:07:38.159  INFO 5304 --- [           main] com.example.demo.DemoApplication         : Starting DemoApplication on DESKTOP-99AEB40 with PID 5304 (C:\DEV\code\boot-20798-demo\out\production\classes started by Fel in C:\DEV\code\boot-20798-demo)
2020-04-18 11:07:38.164  INFO 5304 --- [           main] com.example.demo.DemoApplication         : No active profile set, falling back to default profiles: default
2020-04-18 11:07:38.844  INFO 5304 --- [           main] .s.d.r.c.RepositoryConfigurationDelegate : Bootstrapping Spring Data JPA repositories in DEFAULT mode.
2020-04-18 11:07:38.914  INFO 5304 --- [           main] .s.d.r.c.RepositoryConfigurationDelegate : Finished Spring Data repository scanning in 60ms. Found 1 JPA repository interfaces.
[EL Finer]: weaver: 2020-04-18 11:07:39.482--ServerSession(655713354)--Class [com.example.demo.domain.entities.Project] registered to be processed by weaver.
[EL Finer]: weaver: 2020-04-18 11:07:39.49--ServerSession(655713354)--Class [com.example.demo.domain.entities.SpecificUser] registered to be processed by weaver.
[EL Finer]: weaver: 2020-04-18 11:07:39.49--ServerSession(655713354)--Class [com.example.demo.domain.entities.PersistentDomainObjectWithMetaData] registered to be processed by weaver.
[EL Finer]: weaver: 2020-04-18 11:07:39.49--ServerSession(655713354)--Class [com.example.demo.domain.entities.BaseUser] registered to be processed by weaver.
2020-04-18 11:07:39.505  INFO 5304 --- [           main] j.LocalContainerEntityManagerFactoryBean : Initialized JPA EntityManagerFactory for persistence unit 'primary-persistence-unit'
2020-04-18 11:07:39.721 DEBUG 5304 --- [           main] o.s.o.j.p.ClassFileTransformerAdapter    : Transformer of class [org.eclipse.persistence.internal.jpa.weaving.PersistenceWeaver] transformed class [com/example/demo/domain/entities/Project]; bytes in=614; bytes out=1836
[EL Info]: 2020-04-18 11:07:39.93--ServerSession(655713354)--EclipseLink, version: Eclipse Persistence Services - 2.7.6.v20200131-b7c997804f
2020-04-18 11:07:39.934  INFO 5304 --- [           main] com.zaxxer.hikari.HikariDataSource       : HikariPool-1 - Starting...
2020-04-18 11:07:40.124  INFO 5304 --- [           main] com.zaxxer.hikari.HikariDataSource       : HikariPool-1 - Start completed.
[EL Info]: connection: 2020-04-18 11:07:40.161--ServerSession(655713354)--/file:/C:/DEV/code/boot-20798-demo/out/production/classes/_primary-persistence-unit logout successful
[EL Severe]: ejb: 2020-04-18 11:07:40.164--ServerSession(655713354)--java.lang.NoSuchMethodError: 'void com.example.demo.domain.entities.PersistentDomainObjectWithMetaData.<init>(org.eclipse.persistence.internal.descriptors.PersistenceObject)'
2020-04-18 11:07:40.165  WARN 5304 --- [           main] s.c.a.AnnotationConfigApplicationContext : Exception encountered during context initialization - cancelling refresh attempt: org.springframework.beans.factory.BeanCreationException: Error creating bean with name 'projectRepository': Cannot resolve reference to bean 'jpaMappingContext' while setting bean property 'mappingContext'; nested exception is org.springframework.beans.factory.BeanCreationException: Error creating bean with name 'jpaMappingContext': Invocation of init method failed; nested exception is javax.persistence.PersistenceException: Exception [EclipseLink-28019] (Eclipse Persistence Services - 2.7.6.v20200131-b7c997804f): org.eclipse.persistence.exceptions.EntityManagerSetupException
Exception Description: Deployment of PersistenceUnit [primary-persistence-unit] failed. Close all factories for this PersistenceUnit.
Internal Exception: java.lang.NoSuchMethodError: 'void com.example.demo.domain.entities.PersistentDomainObjectWithMetaData.<init>(org.eclipse.persistence.internal.descriptors.PersistenceObject)'
2020-04-18 11:07:40.166  INFO 5304 --- [           main] j.LocalContainerEntityManagerFactoryBean : Closing JPA EntityManagerFactory for persistence unit 'primary-persistence-unit'
2020-04-18 11:07:40.166  INFO 5304 --- [           main] com.zaxxer.hikari.HikariDataSource       : HikariPool-1 - Shutdown initiated...
2020-04-18 11:07:40.169  INFO 5304 --- [           main] com.zaxxer.hikari.HikariDataSource       : HikariPool-1 - Shutdown completed.
2020-04-18 11:07:40.177  INFO 5304 --- [           main] ConditionEvaluationReportLoggingListener : 

Error starting ApplicationContext. To display the conditions report re-run your application with 'debug' enabled.
2020-04-18 11:07:40.180 ERROR 5304 --- [           main] o.s.b.d.LoggingFailureAnalysisReporter   : 

***************************
APPLICATION FAILED TO START
***************************

Description:

An attempt was made to call a method that does not exist. The attempt was made from the following location:

    com.example.demo.domain.entities.Project.<init>(Project.java)

The following method did not exist:

    'void com.example.demo.domain.entities.PersistentDomainObjectWithMetaData.<init>(org.eclipse.persistence.internal.descriptors.PersistenceObject)'

```

There are two changes that make it run successfully (one of them is enough):

* A) Go to `build.gradle` and downgrade Spring Boot to 2.1
* B) Leave Spring Boot version as-is (2.2), but go to `com.example.demo.DemoApplication.LoadTimeWeavingConfiguration` and enable workaround

Deleting `ProjectRepository` can also make the app start successfully, but it does not solve the underlying problem - still only one class gets transformed.

# Expectation

All the classes registered to be weaved, i.e.

```
[EL Finer]: weaver: 2020-04-18 11:07:39.482--ServerSession(655713354)--Class [com.example.demo.domain.entities.Project] registered to be processed by weaver.
[EL Finer]: weaver: 2020-04-18 11:07:39.49--ServerSession(655713354)--Class [com.example.demo.domain.entities.SpecificUser] registered to be processed by weaver.
[EL Finer]: weaver: 2020-04-18 11:07:39.49--ServerSession(655713354)--Class [com.example.demo.domain.entities.PersistentDomainObjectWithMetaData] registered to be processed by weaver.
[EL Finer]: weaver: 2020-04-18 11:07:39.49--ServerSession(655713354)--Class [com.example.demo.domain.entities.BaseUser] registered to be processed by weaver.
```
are actually transformed, e.g.:
```
2020-04-18 11:18:52.933 DEBUG 8212 --- [           main] o.s.o.j.p.ClassFileTransformerAdapter    : Transformer of class [org.eclipse.persistence.internal.jpa.weaving.PersistenceWeaver] transformed class [com/example/demo/domain/entities/BaseUser]; bytes in=662; bytes out=1884
2020-04-18 11:18:52.935 DEBUG 8212 --- [           main] o.s.o.j.p.ClassFileTransformerAdapter    : Transformer of class [org.eclipse.persistence.internal.jpa.weaving.PersistenceWeaver] transformed class [com/example/demo/domain/entities/PersistentDomainObjectWithMetaData]; bytes in=854; bytes out=6103
2020-04-18 11:18:52.936 DEBUG 8212 --- [           main] o.s.o.j.p.ClassFileTransformerAdapter    : Transformer of class [org.eclipse.persistence.internal.jpa.weaving.PersistenceWeaver] transformed class [com/example/demo/domain/entities/SpecificUser]; bytes in=543; bytes out=1765
2020-04-18 11:18:52.937 DEBUG 8212 --- [           main] o.s.o.j.p.ClassFileTransformerAdapter    : Transformer of class [org.eclipse.persistence.internal.jpa.weaving.PersistenceWeaver] transformed class [com/example/demo/domain/entities/Project]; bytes in=614; bytes out=1836
```
and application starts successfully.

Notice that only one class is actually transformed in the failure scenario. 

# Troubleshooting the failing configuration

Set a conditional breakpoint at `org.springframework.boot.context.properties.ConfigurationPropertiesBindConstructorProvider.findConstructorBindingAnnotatedConstructor` line 62 with condition `type.getSimpleName().equals("ProjectManager")`.

Once it is hit, evaluate the following expressions and notice they all evaluate to `null`:

```
getClass().getClassLoader().findLoadedClass("com.example.demo.domain.entities.BaseUser")
getClass().getClassLoader().findLoadedClass("com.example.demo.domain.entities.PersistentDomainObjectWithMetaData")
getClass().getClassLoader().findLoadedClass("com.example.demo.domain.entities.Project")
getClass().getClassLoader().findLoadedClass("com.example.demo.domain.entities.SpecificUser")
```

Then let `type.getDeclaredConstructors()` call to be executed.

After that evaluate the expressions above again and notice the results are now different:
```
BaseUser                            - loaded
PersistentDomainObjectWithMetaData  - loaded
Project                             - NOT loaded
SpecificUser                        - loaded
```

Because these 3 classes are now loaded, they are excluded from weaving later.

Why do those 3 classes get loaded, but **not** the `Project`? It seems this has something to do with `ProjectManager#doSomething` declaring method parameter as `BaseUser`, even though `Project#creator` returns `SpecificUser`.

# Runtime env

This is likely not dependent on JDK version, the one I used:
```
openjdk 14 2020-03-17
OpenJDK Runtime Environment AdoptOpenJDK (build 14+36)
OpenJDK 64-Bit Server VM AdoptOpenJDK (build 14+36, mixed mode, sharing)
```
