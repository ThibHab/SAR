package task1;

public class ChannelImplem extends Channel{

    int port;
    CircularBuffer in,out;
    ChannelImplem rch; //remote channel
    boolean disconnected;
    boolean dangling;
    String rname;

    protected ChannelImplem(Broker broker, int port){
        this.port = port;
        this.in = new CircularBuffer(64);
    }

    void connect(ChannelImplem rch, String name){
        this.rch=rch;
        rch.rch=this;
        this.out=rch.in;
        rch.out=this.in;
        rname=name;
    }

    @Override
    public int read(byte[] bytes, int offset, int length) throws DisconnectedException {
        if (disconnected) {
            throw new DisconnectedException();
        }
        int nbyte=0;
        try {
            while (nbyte == 0) {
                if(in.empty()){
                    synchronized(in){
                        while (in.empty()) {
                            if (disconnected || dangling) {
                                throw new DisconnectedException();
                            }
                            try {
                                in.wait();
                            } catch (InterruptedException e) {}
                        }
                    }
                }
                while (nbyte < length && !in.empty()) {
                    byte val = in.pull();
                    bytes[offset+nbyte]=val;
                    nbyte++;
                }
                if (nbyte != 0) {
                    synchronized(in){
                        in.notify();
                    }
                }
            }
        } catch (DisconnectedException e) {
            if (!disconnected) {
                disconnected=true;
                synchronized(out){
                    out.notifyAll();
                }
            }
            throw e;
        }
        return nbyte;
    }

    @Override
    public int write(byte[] bytes, int offset, int length) throws DisconnectedException {
        if(disconnected){
            throw new DisconnectedException();
        }
        int nbyte = 0;
        while (nbyte == 0) {
            if(out.full()){
                synchronized(out){
                    while (out.full()) {
                        if (disconnected) {
                            throw new DisconnectedException();
                        }
                        if (dangling) {
                            return length;
                        }
                        try {
                            out.wait();
                        } catch (InterruptedException e){}
                    }
                }
            }
            while (nbyte<length && !out.full()) {
                byte val =bytes[offset+nbyte];
                out.push(val);
                nbyte++;
            }
            if (nbyte != 0) {
                synchronized(out){
                    out.notify();
                }
            }
        }
        return nbyte;
    }

    @Override
    public void disconnect() {
        synchronized(this){
            if (disconnected) {return;}
            disconnected=true;
            rch.dangling=true;
        }
        synchronized(out){
            out.notifyAll();
        }
        synchronized(in){
            in.notifyAll();
        }
    }

    @Override
    public boolean disconnected() {
        return disconnected;
    }

}
