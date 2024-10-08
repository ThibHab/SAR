package task2.queue;

public class Task extends task1.Task {

    QueueBroker queueBroker;

    Task(QueueBroker b, Runnable r){
        super(b.broker, r);
        this.queueBroker = b;
    }
    static QueueBroker getQueueBroker(){
        Thread t = Thread.currentThread();
		if (t instanceof Task) {
			return ((Task) t).queueBroker;
		}
		return null;
    }
    static Task getTask(){
        Thread t = Thread.currentThread();
        if (t instanceof Task) {
            return (Task) t;
        }
        return null;
    }
}
