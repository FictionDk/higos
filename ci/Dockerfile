FROM java:8-jdk-alpine

#基本环境变量设置
ARG TZ=Asia/Shanghai
ENV SERVER_PORT=9900 \
    PROFILES_ACTIVE=dev \
    JAR_FILE=higos-0.0.1.jar

#链接到容器内部时区
RUN ln -snf /usr/share/zoneinfo/${TZ} /etc/localtime && echo ${TZ} > /etc/timezone

#拷贝应用程序至镜像内部
COPY target/${JAR_FILE} /app.jar

#日志文件目录
VOLUME ["/logs"]

#对外暴露端口
EXPOSE ${SERVER_PORT}

CMD [ "/usr/bin/java" ,"-jar", "/app.jar","--spring.profiles.active=${PROFILES_ACTIVE}"]
