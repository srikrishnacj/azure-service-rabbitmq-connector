package com.example.azureservicebusexample.samples.servicebus;

import com.example.azureservicebusexample.bridge.api.MsgConsumer;
import com.example.azureservicebusexample.bridge.api.NoopMsgProducerImpl;
import com.example.azureservicebusexample.bridge.servicebus.ServiceBusMsgConfig;
import com.example.azureservicebusexample.bridge.servicebus.ServiceBusMsgConsumerImpl;
import com.example.azureservicebusexample.config.properties.ServiceBusConsumerProperties;

public class ServiceBusMsgConsumerMultiThreadMain extends ServiceBusCommonMain{
    public static void main(String args[]) {
        ServiceBusMsgConsumerMultiThreadMain main = new ServiceBusMsgConsumerMultiThreadMain();
        main.run();
    }

    public void run() {
        this.configMicroMeter();

        ServiceBusMsgConfig config = new ServiceBusMsgConfig();
        config.setConnectionString("Endpoint=sb://dfafdsfsdfd.servicebus.windows.net/;SharedAccessKeyName=RootManageSharedAccessKey;SharedAccessKey=kzP23rBYRSpD/Uix/cvQLTIumhsEKDmvTTFYDtzECjg=");
        config.setQueue("test");

        this.setupClient(1, 100, config);
    }

    private void setupClient(int count, int msgPerProducer, ServiceBusMsgConfig config) {
        for (int ii = 0; ii < count; ii++) {
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    MsgConsumer consumer = new ServiceBusMsgConsumerImpl(config, new ServiceBusConsumerProperties());
                    NoopMsgProducerImpl producer = new NoopMsgProducerImpl();
                    producer.start();
                    consumer.producer(producer);
                    consumer.start();
                }
            });
            thread.start();
        }
    }
}
