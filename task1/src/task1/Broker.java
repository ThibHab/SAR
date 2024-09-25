package task1;

import java.util.concurrent.ConcurrentHashMap;

public class Broker {
	
	static BrokerManager bm;
	ConcurrentHashMap<Integer,RendezVous> rdv = new ConcurrentHashMap<Integer,RendezVous>();

	Broker(String name) {
		if (bm==null){
			bm = new BrokerManager();
		}
		
		
	}

	Channel accept(int port){
		RendezVous tmprdv=new RendezVous();
		rdv.put(port,tmprdv);
		notifyAll();
		try {
			tmprdv.accept();
		} catch (InterruptedException e) {
			return null;
		}
		return new Channel();
	}

	Channel connect(String name, int port) throws InterruptedException{
		Broker server=bm.getBroker(name);
		while (server.rdv == null && !server.rdv.containsKey(port)) {
			wait();	
		}
		RendezVous tmprdv = server.rdv.get(port);
		try {
			tmprdv.connect();
		} catch (InterruptedException e) {
			return null;
		}
		return new Channel();
	};
}
