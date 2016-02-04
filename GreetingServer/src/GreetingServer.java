// File Name GreetingServer.java

import java.net.*;
import java.io.*;

public class GreetingServer extends Thread {
	private static ServerSocket serverSocket;
	//parameterloser Konstruktoraufruf, um bind() zu ermöglichen
	public GreetingServer(int port) throws IOException {
		serverSocket = new ServerSocket();
		serverSocket.setSoTimeout(10000);	//Servertimeout nach 10 Sekunden ohne Verbndungsversuch
		serverSocket.bind(new InetSocketAddress("127.0.0.2", 9001));
	}

	public void run() {		//run() Methode des Threads
		while (true) {
			try {
				System.out.println("Waiting for client on port " + serverSocket.getLocalPort() + "...");
				Socket server = serverSocket.accept();	//Verbinden des Servers mit dem Client
				try {
					BufferedReader br = new BufferedReader(new FileReader("hallo.txt"));	//zeilenweises Lesen
					PrintWriter out = new PrintWriter(server.getOutputStream(),	true);		//der Textdatei
					String line = "";
					while ((line = br.readLine()) != null) {
						out.println(line);
					}
					br.close();
				} catch (FileNotFoundException e) {
					System.err.println("File not found!");
					break;
				}
				server.close();	//schließen der Serververbindung
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
			Thread t = new GreetingServer(port);	//erstellen und starten
			t.start();								//eines Threads mit dem Port 9001
		} catch (IOException e) {
			System.err.println("Failed to start Server-Thread!");
			e.printStackTrace();
		}
	}
}