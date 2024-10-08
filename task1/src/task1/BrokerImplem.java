package task1;

import java.util.HashMap;

public class BrokerImplem extends Broker {

    BrokerManager bm;
    HashMap<Integer, RendezVous> rdv = new HashMap<Integer, RendezVous>();

    BrokerImplem(String name) {
        super(name);
        bm = BrokerManager.getSelf();
        bm.addBroker(this);
    }

    @Override
    Channel accept(int port) {
        // ajout du syncronized et verif sur port
        RendezVous tmprdv = null;
        synchronized (rdv) {
            tmprdv = rdv.get(port);
            if (tmprdv != null) {
                throw new IllegalStateException("Port " + port + " already accepting");
            }
            tmprdv = new RendezVous();
            rdv.put(port, tmprdv);
            rdv.notifyAll();
        }
        Channel ch;
        ch = tmprdv.accept(this, port);
        return ch;
    }

    @Override
    Channel connect(String name, int port) {
        BrokerImplem b = bm.getBroker(name);
        if (b == null) {
            return null;
        }
        return b.connect(this, port);
    }

    private Channel connect(BrokerImplem b, int port) {
        RendezVous tmprdv = null;
        synchronized (rdv) {
            tmprdv = rdv.get(port);
            while (tmprdv == null) {
                try {
                    rdv.wait();
                } catch (InterruptedException e) {
                }
                tmprdv = rdv.get(port);
            }
            rdv.remove(port);
        }
        return tmprdv.connect(b, port);
    };
}
