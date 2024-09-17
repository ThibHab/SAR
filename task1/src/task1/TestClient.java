package task1;

public class TestClient extends Task {

    TestClient(Broker b, Runnable r) {
        super(b, r);
        this.start();
    }


    public void run(){
        Channel channel = this.getBroker().connect("echoServer", 123);
        assert(channel!=null);

        byte[] buffer = new byte[256];
        for(int i=0;i<256;i++){
            buffer[i]=(byte)i;
        }
        int nwrite=channel.write(buffer, 0, buffer.length);
        assert(nwrite==buffer.length);

        byte[] verifbuffer = new byte[256];
        int offset=0;
        int n=0;
        while (n>=0) {
            n=channel.read(buffer, offset, verifbuffer.length);
            offset+=n;
        }

        for(int i=0;i<256;i++){
            assert(buffer[i]==verifbuffer[i]);
        }

        channel.disconnect();
        assert(channel.disconnected());

    }

}
