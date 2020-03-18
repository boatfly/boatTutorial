# SpringCloud Config 自动刷新机制

## 引入依赖
cofig server端和client端均加入bus依赖。
```
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-bus-amqp</artifactId>
</dependency>
```

## config server端配置
bootstrap.yml
```
spring:
  rabbitmq:
    host: 47.100.240.217
    port: 5673
    username: $account
    password: $password

# 暴露bus刷新配置的端点
management:
  endpoints:
    web:
      exposure:
        include: "bus-refresh"
```

## config client端配置
1.bootstrap.yml
```
management:
  endpoints:
    web:
      exposure:
        include: "*"
spring:
  rabbitmq:
    host: 47.100.240.217
    port: 5673
    username: $account
    password: $password
```
2.在需要读取配置属性的class加注解 @RefreshScope

## 触发
当更新config中配置属性时，post方式调用restful接口：
```
http://$configserverip:$configport/actuator/bus-refresh
```
即可全部自动更新`所有加了注解@RefreshScope的服务类`。