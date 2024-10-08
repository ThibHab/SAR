package task2.queue;

import task1.BrokerImplem;
import task1.DisconnectedException;

public class EchoQueueTask {

    static final String SERVER_NAME = "server";
    static final String CLIENT_NAME = "client";
    static final int PORT = 123;
    static final BrokerImplem server = new BrokerImplem(SERVER_NAME);
    static final BrokerImplem client = new BrokerImplem(CLIENT_NAME);
    static final QueueBroker serverQueue = new QueueBrokerImplem(server);
    static final QueueBroker clientQueue = new QueueBrokerImplem(client);

    public static void main(String[] args) {
        new Task(serverQueue, () -> {
            while (true) {
                try {
                    MessageQueue msgqueue = serverQueue.accept(PORT);
                    try {
                        while (!msgqueue.closed()) {
                            byte[] buffer;
                            buffer = msgqueue.receive();
                            msgqueue.send(buffer, 0, buffer.length);
                        }
                    } catch (DisconnectedException e) {
                        msgqueue.close();
                        System.out.println("Server disconnected Exception");
                    }
                } catch (IllegalStateException e) {
                    System.out.println("Port already accepting");
                }

            }
        }).start();
        test();
    }

    static void test() {
        try {
            MessageQueue mshQueue = clientQueue.connect(SERVER_NAME, PORT);
            assert (mshQueue != null);
            for (int i = 0; i < 10; i++) {
                String test_str = "Hello World Message test " + i;
                byte[] buffer = test_str.getBytes();
                mshQueue.send(buffer, 0, buffer.length);
                System.out.println("Client sent message: " + test_str);
                byte[] verifbuffer = mshQueue.receive();

                assert (buffer.length == verifbuffer.length);
                for (int j = 0; j < buffer.length; j++) {
                    assert (buffer[j] == verifbuffer[j]);
                }
                System.out.println("Client received message: " + test_str);

            }
            mshQueue.close();
            assert (mshQueue.closed());

        } catch (DisconnectedException e) {
            System.out.println("Client disconnected Exception");
        }
        System.out.println("Client task done");

        
    }

}
