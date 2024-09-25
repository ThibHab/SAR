package task1;

import java.util.HashMap;

public class BrokerManager {


    private static BrokerManager self;
    static BrokerManager getSelf(){
        return self;
    }
    static{ self=new BrokerManager();}

    HashMap<String,BrokerImplem> brokers;

    BrokerManager(){
        brokers = new HashMap<String,BrokerImplem>();
    }
    
    public synchronized BrokerImplem getBroker(String name) {
        return brokers.get(name);
    }

    public synchronized void addBroker(BrokerImplem broker){
        String name = broker.name;
        BrokerImplem b = brokers.get(name);
        if (b!=null) {
            throw new IllegalStateException(name+" alredy exists");
        }
        brokers.put(name, broker);
    }
    
}
