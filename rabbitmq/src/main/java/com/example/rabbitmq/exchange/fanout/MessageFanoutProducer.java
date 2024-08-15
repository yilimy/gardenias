package com.example.rabbitmq.exchange.fanout;

import com.example.rabbitmq.RabbitMQServiceAbs;
import com.rabbitmq.client.MessageProperties;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Stream;

/**
 * @author caimeng
 * @date 2024/8/8 16:41
 */
@Slf4j
public class MessageFanoutProducer extends RabbitMQServiceAbs {

    public static void main(String[] args) {
        new MessageFanoutProducer().firstPublish();
    }

    /**
     * 控制台 Exchange 中出现了 yootk.exchange.fanout
     * 数据在exchange中，而不是队列中
     */
    public void firstPublish() {
        initWithHostAndExchange();
        produce();
        destroy();
    }

    @SneakyThrows
    protected void produce() {
        // 获取当前的发送时间
        long start = System.currentTimeMillis();
        CompletableFuture<Void>[] completableFutures = Stream.iterate(0, n -> n + 1)
                .limit(100)
                .map(n -> CompletableFuture.runAsync(() -> {
                    String msg = "【沐言科技 - " + n +"】[fanout] www.yootk.com";
                    try {
                        /*
                         * 1. 使用exchange
                         * 2. 不使用channel就不用设置 routingKey
                         * 3. 设置持久化
                         */
                        channel.basicPublish(EXCHANGE_NAME, "", MessageProperties.PERSISTENT_TEXT_PLAIN, msg.getBytes(StandardCharsets.UTF_8));
                    } catch (IOException e) {
                        log.error("发布失败: " + msg, e);
                    }
                })).<CompletableFuture<Void>>toArray(CompletableFuture[]::new);
        CompletableFuture.allOf(completableFutures).join();
        // 获取结束的发送时间
        long end = System.currentTimeMillis();
        System.out.println("【消息发送完毕】消息发送的耗时时间" + (end - start) + "ms");
    }
}