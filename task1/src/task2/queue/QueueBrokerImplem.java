package task2.queue;

import task1.BrokerImplem;
import task1.Channel;

public class QueueBrokerImplem extends QueueBroker {

    BrokerImplem broker;

    QueueBrokerImplem(BrokerImplem broker) {
        super(broker);
        this.broker = broker;
    }

    @Override
    String name() {
        return this.broker.name;
    }

    @Override
    MessageQueue accept(int port) {
        Channel ch = broker.accept(port);
        return new MessageQueueImplem(ch);
    }

    @Override
    MessageQueue connect(String name, int port) {
        Channel ch = broker.connect(name, port);
        return new MessageQueueImplem(ch);
    }

}
