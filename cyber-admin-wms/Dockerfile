FROM anapsix/alpine-java:8_server-jre_unlimited

MAINTAINER Lion Li

RUN mkdir -p /cyber/server/logs \
    /cyber/server/temp \
    /cyber/skywalking/agent

WORKDIR /cyber/server

ENV SERVER_PORT=8080

EXPOSE ${SERVER_PORT}

ADD ./target/cyber-admin.jar ./app.jar

ENTRYPOINT ["java", \
            "-Djava.security.egd=file:/dev/./urandom", \
            "-Dserver.port=${SERVER_PORT}", \
            # 应用名称 如果想区分集群节点监控 改成不同的名称即可
#            "-Dskywalking.agent.service_name=cyber-server", \
#            "-javaagent:/cyber/skywalking/agent/skywalking-agent.jar", \
            "-jar", "app.jar"]
