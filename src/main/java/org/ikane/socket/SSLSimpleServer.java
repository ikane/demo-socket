package org.ikane.socket;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

import javax.net.ServerSocketFactory;
import javax.net.ssl.SSLServerSocketFactory;

public class SSLSimpleServer extends Thread {
	
	private Socket socket;

	public SSLSimpleServer(Socket socket) {
		super();
		this.socket = socket;
	}

	@Override
	public void run() {
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
			PrintWriter pw = new PrintWriter(this.socket.getOutputStream());
			
			String data = br.readLine();
			System.out.println("Data received: " + data);
			pw.println(data);
			pw.close();
			this.socket.close();
		} catch (Exception e) {
		      // Client disconnected
			e.printStackTrace();
	    }
	}
	
	
	public static void main(String[] args) throws Exception {
		ServerSocketFactory ssf = SSLServerSocketFactory.getDefault();
		ServerSocket ss = ssf.createServerSocket(9096);
		
		System.out.println("Ready...");
		while(true) {
			new SSLSimpleServer(ss.accept()).start();
		}
	}
}
