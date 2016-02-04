// File Name GreetingServer.java

import java.net.*;
import java.io.*;

public class GreetingServer extends Thread {
	private static ServerSocket serverSocket;

	public GreetingServer(int port) throws IOException {
		serverSocket = new ServerSocket();
		serverSocket.setSoTimeout(10000);
		serverSocket.bind(new InetSocketAddress("127.0.0.2", 9001));
	}

	public void run() {
		while (true) {
			try {
				System.out.println("Waiting for client on port " + serverSocket.getLocalPort() + "...");
				Socket server = serverSocket.accept();
				try {
					BufferedReader br = new BufferedReader(new FileReader("hallo.txt"));
					PrintWriter out = new PrintWriter(server.getOutputStream(),	true);
					String line = "";
					while ((line = br.readLine()) != null) {
						out.println(line);
					}
					br.close();
				} catch (FileNotFoundException e) {
					System.err.println("File not found!");
					break;
				}
				server.close();
			} catch (SocketTimeoutException s) {
				System.out.println("Socket timed out!");
				break;
			} catch (IOException e) {
				e.printStackTrace();
				break;
			}
		}
	}

	public static void main(String[] args) throws IOException {
		int port = 9001;
		try {
			Thread t = new GreetingServer(port);
			t.start();
		} catch (IOException e) {
			System.err.println("Failed to start Server-Thread!");
			e.printStackTrace();
		}
	}
}