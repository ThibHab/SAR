package task2.queue;

import task1.DisconnectedException;

public abstract class MessageQueue {
    abstract void send(byte[] bytes, int offset, int length) throws DisconnectedException;
    abstract byte[] receive() throws DisconnectedException;
    abstract void close();
    abstract boolean closed();
}
