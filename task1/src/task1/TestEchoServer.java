package task1;

public class TestEchoServer extends Task{

    TestEchoServer(Broker b, Runnable r) {
        super(b, r);
        this.setDaemon(true);
        this.start();
    }

    public void run(){
        while(true){
            Channel channel = this.getBroker().accept(123);
            new TestEchoServerWorker(channel);
        }
    } 

}