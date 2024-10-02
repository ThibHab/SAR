package task1;

public class Test {

	static final String SERVER_NAME = "server";
	static final String CLIENT_NAME = "client";
	static final int PORT = 123;
	static final BrokerImplem server = new BrokerImplem(SERVER_NAME);
	static final BrokerImplem client = new BrokerImplem(CLIENT_NAME);

	public static void main(String[] args) {
		

		Task serverTask = new Task(server, () -> {
			while (true) {
				Channel channel = server.accept(PORT);
				byte[] buffer = new byte[256];
				try {
					int n = 0;
					while (n >= 0) {
						n = channel.read(buffer, 0, buffer.length);
						int nw=channel.write(buffer, 0, n);
						assert(n==nw);
					}
				} catch (DisconnectedException e) {
					channel.disconnect();
					System.out.println("Server disconnected Exception");
				}

			}
		});
		serverTask.start();

		Task clientTasks = new Task(client, () -> {
		try {
			Channel channel = client.connect(SERVER_NAME, PORT);
			byte[] buffer = new byte[256];
			for (int i = 0; i < 256; i++) {
				buffer[i] = ((byte) i);
			}
			int n=0;
			byte[] verifbuffer = new byte[256];
			int offset = 0;
			while(offset<buffer.length) {
				n=channel.write(buffer, 0, buffer.length-offset);
				offset += channel.read(verifbuffer, offset, n);
			}

			for (int i = 0; i < 256; i++) {
				//probleme a partir de 128
				assert (buffer[i] == verifbuffer[i]);
			}
	
			channel.disconnect();
			assert (channel.disconnected());
			} catch (DisconnectedException e) {
				System.out.println("Client disconnected Exception");
			}
			System.out.println("Client task done");
				
		});
		clientTasks.start();
		System.out.print("Client and Server started");
		try {
			clientTasks.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		serverTask.interrupt();

	}

}
