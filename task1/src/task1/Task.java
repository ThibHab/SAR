package task1;

class Task extends Thread {

	Broker broker;

	Task(Broker b, Runnable r) {
		super(r);
		this.broker = b;
	}

	Broker getBroker() {
		Thread t = Thread.currentThread();
		if (t instanceof Task) {
			return ((Task) t).broker;
		}
		return null;
	}
}