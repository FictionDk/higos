# Higos(海高斯,2020-08)xxxx统一用户管理平台

介绍
---

> 基于`Spring security`的`oauth2`+`jwt`,解决单点登录问题

版本依赖
---
模块 | 版本
--- | ---
springboot | 2.2.9.RELEASE
docker | Docker version 19.03.1-ce
mysql | 5.7.31

## Spring security
1. 安全框架,利用spring Ioc/DI和AOP,提供声明式安全访问控制功能
2. 核心功能
    - 认证,用户是否能登录
    - 授权,用户是否由权限操作资源
   
### 登录
1. `UserDetailService UserDetails loadByUsername(String username);`
2. `UserDetailService`和`UserDetail`均为接口,其中`UserDetail`Spring security有自己的实现类`User`
3. `PasswordEncoder`接口定义了密码解析,默认建议使用`BCryptPasswordEncoder`

### 请求认证
1. `UsernamePasswordAuthenticationFilter.attemptAuthentication`
2. `antMatchers(ant 表达式)`

### 访问控制
```java
class ExpressionUrlAuthorizationConfigurer{
    static final String permitAll = "permitAll";
    private static final String denyAll = "denyAll";
    private static final String anonymous = "anonymous";
    private static final String authenticated = "authenticated";
    private static final String fullyAuthenticated = "fullyAuthenticated";
    private static final String rememberMe = "rememberMe";
}
```
最终都是基于access方法
```java
class ExpressionUrlAuthorizationConfigurer {
   public ExpressionUrlAuthorizationConfigurer<H>.ExpressionInterceptUrlRegistry access(String attribute) {
      if (this.not) {
         attribute = "!" + attribute;
      }

      ExpressionUrlAuthorizationConfigurer.this.interceptUrl(this.requestMatchers, SecurityConfig.createList(new String[]{attribute}));
      return ExpressionUrlAuthorizationConfigurer.this.REGISTRY;
   } 
}
```


