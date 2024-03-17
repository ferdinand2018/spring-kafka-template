package com.springkafkaexample.demo.aop;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
@RequiredArgsConstructor
public class InboundRequestsAdvice {

    /**
     * Advice для логирования входящих сообщений в очереди.
     */
    @Around(
            value = "execution(* com.springkafkaexample.demo.message.InputBooksConsumer.readMessages(..)) && " +
                    "args(consumerRecord,..)",
            argNames = "joinPoint,consumerRecord"
    )
    public Object aroundCardsRawQueueConsumerAdvice(
            ProceedingJoinPoint joinPoint,
            ConsumerRecord<String, Object> consumerRecord) throws Throwable {

        log.info("Reading a message from topic: {}", consumerRecord.topic());

        try {
            return joinPoint.proceed();
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

}
