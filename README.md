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

### csrf
1. 跨站请求伪造`cross-site request forgery`
2. 前端请求数据时,csrf()不关闭,后端返回_csrf.token;
3. 前端请求后续请求添加_csrf`<input type="hidden" th:value="${_csrf.token}" name="_csrf" th:if="${_csrf}">`

## Oauth2

### 基础定义
1. 客户端
2. 授权服务器
3. 资源服务器
4. 客户凭证(client Credentials): 客户端的clientId和密码用于认证客户
5. 令牌(tokens): 服务器接收用户请求后,颁发的访问令牌
6. 作用域(scope): 客户请求访问令牌时,由资源者额外细分的权限(permission)

### 令牌类型
1. 授权码, 授权码授权类型,用户交换获取访问令牌和刷新令牌
2. 访问令牌
3. 刷新令牌
4. BearerToken:
5. Proof of Possession(PoP) Token:

### 授权模式
1. http://127.0.0.1:9900/oauth/authorize?response_type=code&client_id=ctrlbus&redirect_uri=http://ctrlbus.matchine.dev.uplasma.com
2. curl -X POST -d "grant_type=authorization_code&code=FeXbAC&client_id=ctrlbus&client_secret=showmethecode&redirect_uri=http://ctrlbus.matchine.dev.uplasma.com" -u "ctrlbus:showmethecode" http://localhost:9900/oauth/token

### 简化授权模式
1. Token Auth认证

### 密码模式
1. curl -X POST -d "username=admin&password=123456&grant_type=password" -u "ctrlbus:showmethecode" http://localhost:9900/oauth/token

### 客户端模式

### Spring security 授权服务器
1. Authorize Endpoint(/oauth/authorize),颁发授权码
2. Token Endpoint(/oauth/token),获取令牌
3. Introspection Endpoint(/oauth2/introspect),校验token合法性
4. Revocation Endpoint(/oauth2/revoke),撤销token授权

### Spring security授权模式 认证流程
1. [用户]访问资源时,被`Oauth2ClientContextFilter`捕获再重定向到[认证服务器]
2. [用户]转到[认证服务器]首先通过`Authorize Endpoint`进行授权,并最终通过`AuthorizationServerTokenServices`生成授权码返回
3. [用户]将认证码交给[客户端],由[客户端]通过授权码到[认证服务器]通过`Token Endpoint`调用`AuthorizationSeverTokenServices`生成token
4. [客户端]持token访问[资源服务器]
5. [资源服务器]通过`Oauth2AuthenticationManager`调用`ResourceServerTokenServices`校验token

## JWT

1. 首部(header),负载(Payload),签名(signature)
2. Header-基本信息 = `{"alg":签名算法,"typ":类型}` Base64
3. Payload-存放有效信息; Base64
4. signature-签证签名

# 问题

1. PasswordEncoder 除了作用于用户登录时,当使用`/oauth/token`核查`client_id/client_secret`时,也会调用.涉及的表有`oauth_client_details`和`user`,及对应的密码需要统一
