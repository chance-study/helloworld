package org.chance.distributelock.embedded;

import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.SocketUtils;
import redis.embedded.RedisServer;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.io.IOException;
import java.net.ServerSocket;

/**
 * EmbeddedRedis
 *
 * @author GengChao
 * @email chao_geng@sui.com
 * @date 2019/9/23
 */
@Component
public class EmbeddedRedis {

    private RedisServer server;

    @PostConstruct
    public void start() throws IOException {
        // find free port
//        final ServerSocket serverSocket = new ServerSocket(0);
//        final Integer port = serverSocket.getLocalPort();
//        serverSocket.close();

        server = RedisServer.builder()
                .setting("bind 127.0.0.1")
                .port(6378)
//                .port(SocketUtils.findAvailableTcpPort())
//                .slaveOf("locahost", 6378)
//                .setting("daemonize no")
//                .setting("appendonly no")
                .setting("maxheap 128M")
                .build();

        server.start();

//        final LettuceConnectionFactory connectionFactory = new LettuceConnectionFactory(new RedisStandaloneConfiguration("localhost", port));
//        connectionFactory.setDatabase(0);
//        connectionFactory.afterPropertiesSet();

        // set the property so that RedisAutoConfiguration picks up the right port
        System.setProperty("spring.redis.port", String.valueOf(6378));
    }

    @PreDestroy
    public void stop() {
        server.stop();
        System.clearProperty("spring.redis.port");
    }

}
