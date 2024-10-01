package task1;

public class Test {

	static final String SERVER_NAME = "server";
	static final String CLIENT_NAME = "client";
	static final int PORT = 123;

	public static void main(String[] args) {
		BrokerImplem server = new BrokerImplem(SERVER_NAME);
		BrokerImplem client = new BrokerImplem(CLIENT_NAME);

		Task serverTask = new Task(server, () -> {
			while (true) {
				Channel channel = server.accept(PORT);
				byte[] buffer = new byte[256];
				try {
					int n = 0;
					while (n >= 0) {
						n = channel.read(buffer, 0, buffer.length);
						if (n > 0) {
							channel.write(buffer, 0, n);
						}
					}
				} catch (DisconnectedException e) {
					channel.disconnect();
					System.out.println("Server disconnected Exception");
				}

			}
		});
		serverTask.start();

		Task[] clientTasks = new Task[5];
		for (int j = 0; j < 5; j++) {
			clientTasks[j] = new Task(client, () -> {
				try {
					Channel channel = client.connect(SERVER_NAME, PORT);
				byte[] buffer = new byte[256];
				for (int i = 0; i < 256; i++) {
					buffer[i] = (byte) i;
				}
				int nwrite = channel.write(buffer, 0, buffer.length);
				assert (nwrite == buffer.length);
	
				byte[] verifbuffer = new byte[256];
				int offset = 0;
				int n = 0;
				while (n >= 0) {
					n = channel.read(verifbuffer, offset, verifbuffer.length);
					offset += n;
				}
	
				for (int i = 0; i < 256; i++) {
					assert (buffer[i] == verifbuffer[i]);
				}
	
				channel.disconnect();
				assert (channel.disconnected());
				} catch (DisconnectedException e) {
					System.out.println("Client disconnected Exception");
				}
				System.out.println("Client task done");
				
			});
			clientTasks[j].start();
		}

		for (int i = 0; i < 5; i++) {
			try {
				clientTasks[i].join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		serverTask.interrupt();

	}

}
