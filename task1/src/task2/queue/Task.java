package task2.queue;

import task1.Broker;

public abstract class Task {
    Task(Broker b, Runnable r){}
    Task(QueueBroker b, Runnable r){}
    abstract Broker getBroker();
    abstract QueueBroker getQueueBroker();
    static Task getTask(){return null;}
}
