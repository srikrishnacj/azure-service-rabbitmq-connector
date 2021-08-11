package com.example.azureservicebusexample.bridge.api;

import com.example.azureservicebusexample.exception.ExceptionTranslator;
import io.micrometer.core.instrument.Metrics;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StopWatch;

import java.time.Duration;

@Slf4j
public abstract class AbstractMsgProducerImpl implements MsgProducer {
    private final MsgProducerStatus status = new MsgProducerStatus();
    private final ExceptionTranslator exceptionTranslator;

    protected AbstractMsgProducerImpl(ExceptionTranslator exceptionTranslator) {
        this.exceptionTranslator = exceptionTranslator;
    }

    @Override
    public String getName() {
        return "producer";
    }

    @Override
    public MsgProducerStatus status() {
        return status;
    }

    @Override
    public void start() {
        try {
            log.info("starting producer");
            this.startInternal();
            this.status.setRunning(true);
            this.status.setLastKnownException(null);
        } catch (Exception exception) {
            log.error("unable to start producer", exception);
            this.stop(exception);
        }
    }

    private void stop(Exception exception) {
        if (exception != null) {
            log.info("stopping producer: {}", exception.getMessage());
            this.status.setLastKnownException(exception.getMessage());
        } else {
            log.info("stopping producer");
        }
        this.status.setRunning(false);
        this.stopInternal();
    }

    @Override
    public void stop() {
        this.stop(null);
    }

    public boolean send(String messageId, byte[] message) {
        if (this.isChannelAvailable() == false) {
            log.error("Error while sending msg: {}. No connection established", messageId);
            return false;
        }

        log.debug("Sending Msg: {}", messageId);
        try {
            StopWatch stopWatch = new StopWatch();
            stopWatch.start();
            this.sendInternal(messageId, message);
            log.info("Sent Msg: {}", messageId);
            stopWatch.stop();
            Metrics.globalRegistry.timer("servicebus.producer").record(Duration.ofMillis(stopWatch.getTotalTimeMillis()));
            return true;
        } catch (Exception e) {
            RuntimeException exception = exceptionTranslator.translate(e);
            log.error("error while sending msg", exception);
            this.stop(exception);
            return false;
        }
    }

    protected abstract void startInternal() throws Exception;

    protected abstract void stopInternal();

    public abstract void sendInternal(String messageId, byte[] message) throws Exception;

    public abstract boolean isChannelAvailable();
}
