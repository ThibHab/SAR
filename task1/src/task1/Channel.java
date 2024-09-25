package task1;

abstract class Channel {
	abstract int read(byte[] bytes, int offset, int length) throws DisconnectedException;
	abstract int write(byte[] bytes, int offset, int length) throws DisconnectedException;
	abstract void disconnect();
	abstract boolean disconnected();
	}
