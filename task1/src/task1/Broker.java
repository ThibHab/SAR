package task1;

abstract class Broker {
	String name;
	Broker(String name){this.name=name;}
	abstract Channel accept(int port);
	abstract Channel connect(String name, int port);
	}
