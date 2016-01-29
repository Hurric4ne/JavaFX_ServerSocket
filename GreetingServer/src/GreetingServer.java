// File Name GreetingServer.java

import java.net.*;
import java.util.Scanner;
import java.io.*;

public class GreetingServer extends Thread {
	private ServerSocket serverSocket;

	public GreetingServer(int port) throws IOException {
		serverSocket = new ServerSocket();
		serverSocket.setSoTimeout(10000);
		serverSocket.bind(new InetSocketAddress("127.0.0.2", 9000));
	}
	
	public void run() {
		try {
			System.out.println("Waiting for client on port " + serverSocket.getLocalPort() + "...");
			Socket server = serverSocket.accept();
			try {
				BufferedReader br = new BufferedReader(new FileReader("hallo.txt"));
				/*
				for (String line = br.readLine(); line != null; line = br.readLine()) {
					System.out.println(line);
				}*/
				
				PrintWriter out = new PrintWriter(server.getOutputStream(), true);
				String line = "";
				while ((line = br.readLine()) != null) {
					out.println(line);
				}
				
				br.close();
			} 
			catch (FileNotFoundException e) {
				System.err.println("File not found!");	
			}
			server.close();
		} catch (SocketTimeoutException s) {
			System.out.println("Socket timed out!");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) throws IOException {
		int port = 9000;
		try {
			Thread t = new GreetingServer(port);
			t.start();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}