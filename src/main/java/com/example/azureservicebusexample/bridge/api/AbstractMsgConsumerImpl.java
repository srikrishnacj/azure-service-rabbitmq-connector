package com.example.azureservicebusexample.bridge.api;

import com.example.azureservicebusexample.exception.ExceptionTranslator;
import io.micrometer.core.instrument.Metrics;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StopWatch;

import java.time.Duration;
import java.util.Objects;

@Slf4j
public abstract class AbstractMsgConsumerImpl implements MsgConsumer {
    private final MsgConsumerStatus status = new MsgConsumerStatus();
    protected final ExceptionTranslator exceptionTranslator;
    private MsgProducer producer;

    public AbstractMsgConsumerImpl(ExceptionTranslator exceptionTranslator) {
        this.exceptionTranslator = exceptionTranslator;
    }

    @Override
    public String getName() {
        return "consumer";
    }

    @Override
    public MsgConsumerStatus status() {
        return this.status;
    }

    @Override
    public void producer(MsgProducer producer) {
        this.producer = producer;
    }

    @Override
    public void start() {
        try {
            log.info("starting consumer");
            if (this.producer == null) {
                Objects.requireNonNull(producer, "Must specify a producer to consume msg");
            }
            this.startInternal();
            this.status.setRunning(true);
            this.status.setLastKnownException(null);
        } catch (Exception exception) {
            log.error("unable to start consumer", exception);
            this.stop(exception);
        }
    }

    @Override
    public void stop() {
        this.stop(null);
    }

    public void stop(Exception exception) {
        this.status.setRunning(false);
        if (exception != null) {
            log.info("stopping consumer: {}", exception.getMessage());
            this.status.setLastKnownException(exception.getMessage());
        } else {
            log.info("stopping consumer");
        }
        this.stopInternal();
    }

    protected boolean onMessage(String messageId, byte[] message) {
        try {
            StopWatch stopWatch = new StopWatch();
            stopWatch.start();
            log.info("received a msg: {}", messageId);
            if (this.producer.status().isRunning() == false) {
                RuntimeException exception = new RuntimeException("Stopping consumer due to Producer is not running");
                this.stop(exception);
                return false;
            }
            boolean isSent = this.producer.send(messageId, message);
            stopWatch.stop();
            Metrics.globalRegistry.timer("servicebus.consumer").record(Duration.ofMillis(stopWatch.getTotalTimeMillis()));
            log.info("time take to process consumed msg {}", stopWatch.getTotalTimeMillis());
            return isSent;
        } catch (Exception ex) {
            RuntimeException exception = exceptionTranslator.translate(ex);
            log.error("error while consuming msg", exception);
            this.stop(exception);
        }
        return false;
    }

    protected abstract void startInternal() throws Exception;

    protected abstract void stopInternal();
}
