package com.example.azureservicebusexample.samples.servicebus;

import com.example.azureservicebusexample.bridge.api.MsgProducer;
import com.example.azureservicebusexample.bridge.servicebus.ServiceBusMsgConfig;
import com.example.azureservicebusexample.bridge.servicebus.ServiceBusMsgProducerImpl;
import com.example.azureservicebusexample.samples.MessageGenerator;
import com.example.azureservicebusexample.samples.SampleMessage;


public class ServiceBusProducerMultiThreadMain extends ServiceBusCommonMain{
    public static void main(String args[]) {
        ServiceBusProducerMultiThreadMain main = new ServiceBusProducerMultiThreadMain();
        main.run();
    }

    private void run(){
        this.configMicroMeter();

        ServiceBusMsgConfig config = new ServiceBusMsgConfig();
        config.setConnectionString("Endpoint=sb://dfafdsfsdfd.servicebus.windows.net/;SharedAccessKeyName=RootManageSharedAccessKey;SharedAccessKey=kzP23rBYRSpD/Uix/cvQLTIumhsEKDmvTTFYDtzECjg=");
        config.setQueue("test");

        setupClient(100, 100, config);
    }

    private void setupClient(int count, int msgPerProducer, ServiceBusMsgConfig config) {
        for (int ii = 0; ii < count; ii++) {
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    MessageGenerator generator = new MessageGenerator();
                    MsgProducer msgProducer = new ServiceBusMsgProducerImpl(config);
                    msgProducer.start();
                    for (int ii = 0; ii < msgPerProducer; ii++) {
                        sendMsg(msgProducer, generator);
                    }
                }
            });
            thread.start();
        }
    }

    private void sendMsg(MsgProducer producer, MessageGenerator generator) {
        SampleMessage msg = generator.generate();
        producer.send(msg.getId(), msg.data());
    }
}
