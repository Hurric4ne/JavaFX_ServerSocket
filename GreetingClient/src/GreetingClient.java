// File Name GreetingClient.java

import java.net.*;
import java.io.*;

public class GreetingClient {
	public static void main(String[] args) {
		String serverName = "127.0.0.2";
		int port = 9000;
		try {
			System.out.println("Connecting to " + serverName + " on port " + port);
			Socket client = new Socket(serverName, port);
			client.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}