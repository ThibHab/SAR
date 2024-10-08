package task1;

public class Task extends Thread {

	Broker broker;

	protected Task(Broker b, Runnable r) {
		super(r);
		this.broker = b;
	}

	static Broker getBroker() {
		Thread t = Thread.currentThread();
		if (t instanceof Task) {
			return ((Task) t).broker;
		}
		return null;
	}
}