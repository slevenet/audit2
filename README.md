# audit2

1. Развернуть keycloak, создать новый проект, сконфигурировать пользователей
2.Проверить наличие следующих зависимостей в файле pom.xml:


        <dependencyManagement>
        <dependencies>
            <dependency>
                <groupId>org.keycloak.bom</groupId>
                <artifactId>keycloak-adapter-bom</artifactId>
                <version>${keycloak-adapter-bom.version}</version>
                <type>pom</type>
                <scope>import</scope>
            </dependency>
        </dependencies>
    </dependencyManagement>
    
    
  И сами зависимости :  
    
    
    <dependencies>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-aop</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-security</artifactId>
        </dependency>
        <dependency>
            <groupId>org.keycloak</groupId>
            <artifactId>keycloak-spring-boot-starter</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-data-jpa</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-thymeleaf</artifactId>
        </dependency>
        <dependency>
            <groupId>org.hsqldb</groupId>
            <artifactId>hsqldb</artifactId>
            <scope>runtime</scope>
        </dependency>
    </dependencies>
    
    В случае необходимости, заменить hsqldb на нужную субд
    
   3. Добавить конфигурацию в properties для keycloak (см. пример в репозитории)
   
   Все должно работать :)
   
   Имеется 2 основных аудита: AuditEntityService, AuditControllerService , которые расширяют функционал абстрактного AuditService.
   AuditEntityService Логирует события с помощью @EntityListeners. Эту аннотацию следует поставить над сущностями, аудит операций которых нам требуется.
   Сам Listner - CustomerListener. Стоит обратить внимание, он не в контексте спринга.
   AuditControllerService - работает на основе аспектов и под аудит попадает все, что помечено аннотацией @Controller.
   LogAuditFileHandler - Разделяет запись аудита по разным файлам. Именя файлов можно задать через properties:
   
   
   audit.controller.file = controller_audit.log
  
   audit.entity.file = entity_audit.log
   
   Думаю, стоит добавить логирование через обертку slf4j, используя аппендеры. 
   
   
   За хранение пользователей отвечает keycloak. События получения пользователя будут залогированы только через контроллер.
