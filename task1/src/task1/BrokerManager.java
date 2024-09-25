package task1;

import java.util.concurrent.ConcurrentHashMap;

public class BrokerManager {

    ConcurrentHashMap<String,Broker> brokers;

    BrokerManager(){
        brokers = new ConcurrentHashMap<String,Broker>();
    }
    
    public Broker getBroker(String name) {
        return brokers.get(name);
    }
    
}
