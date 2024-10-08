package task2.queue;

import task1.Channel;
import task1.DisconnectedException;

public class MessageQueueImplem extends MessageQueue {

    Channel channel;

    MessageQueueImplem(Channel channel) {
        this.channel = channel;
    }

    @Override
    void send(byte[] bytes, int offset, int length) throws DisconnectedException {
        channel.write(intToByte(length), 0, 4);

        //ack
        byte[] ack = new byte[4];
        int n=0;
        while (n<4){
            n+=channel.read(ack, n, 4-n);       
        }
        if(byteToInt(ack)==length){
            int nwrite=0;
            while (nwrite<length){
                nwrite+=channel.write(bytes, offset+nwrite, length-nwrite);
                
            }
        }

    }

    @Override
    byte[] receive() throws DisconnectedException {
        byte[] length = new byte[4];
        int n=0;
        while (n<4){
            n+=channel.read(length, n, 4-n);
            
        }

        
        int len = byteToInt(length);
        channel.write((intToByte(len)), 0, 4);//ack

        byte[] bytes = new byte[len];
        n=0;
        while(n<len){
            n+=channel.read(bytes, n, len-n);
        }
        return bytes;
    }


    @Override
    void close() {
        channel.disconnect();; 
    }

    @Override
    boolean closed() {
        return channel.disconnected();
    }

    private int byteToInt(byte[] bytes) {
        int result = 0;
        for (int i = 0; i < 4; i++) {
            result |= (bytes[i] & 0xFF) << (i * 8);
        }
        return result;
    }

    private byte[] intToByte(int value) {
        byte[] result = new byte[4];
        for (int i = 0; i < 4; i++) {
            result[i] = (byte) (value >> (i * 8));
        }
        return result;
    }

}
