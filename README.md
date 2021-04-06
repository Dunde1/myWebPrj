# myWebPrj
my spring boot web project

> application.yml
  spring:
    datasource:
      url: jdbc:mariadb://localhost:3306/database
      driver-class-name: org.mariadb.jdbc.Driver
      username: name
      password: password
    jpa:
      open-in-view: false
      generate-ddl: true
      show-sql: true
      hibernate:
        ddl-auto: update
