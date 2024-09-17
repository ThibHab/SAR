package task1;

public class TestEchoServerWorker extends Thread {
    Channel mchannel;
    byte[] buffer;

    TestEchoServerWorker(Channel channel) {
        mchannel = channel;
        buffer = new byte[256];
        this.setDaemon(true);
        this.start();
    }

    public void run() {
        while (true) {
            int n=0;
            while (n >= 0) {
                n = mchannel.read(buffer, 0, buffer.length);
                if (n > 0) {
                    mchannel.write(buffer, 0, n);
                }
            }
        }
    }
}