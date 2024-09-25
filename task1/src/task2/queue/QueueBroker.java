package task2.queue;

import task1.Broker;

public abstract class QueueBroker {
    QueueBroker(Broker broker){}
    abstract String name();
    abstract MessageQueue accept(int port);
    abstract MessageQueue connect(String name, int port);
}
