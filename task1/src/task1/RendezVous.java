package task1;

public class RendezVous {

    ChannelImplem ac;
    ChannelImplem cc;
    Broker ab;
    Broker cb;

    private void waits(){
        while (ac == null || cc == null) {
            try {
                wait();
            } catch (InterruptedException e) {
                // TODO: handle exception
            }
        }
    }

    public synchronized Channel accept(Broker b, int port) {
        this.ab = b;
        ac = new ChannelImplem(ab, port);
        if (cc!= null) {
            ac.connect(cc,ab.name);
            notify();
        }else{
            waits();
        }
        return ac;
    }

    public synchronized Channel connect(Broker b, int port) {
        this.cb = b;
        cc = new ChannelImplem(cb, port);
        if (ac != null){
            ac.connect(cc,cb.name);
            notify();
        }else{
            waits();
        }
        return cc;
    }

}
