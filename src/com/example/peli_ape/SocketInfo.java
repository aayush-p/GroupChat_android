package com.example.peli_ape;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class SocketInfo {

	Socket clientSocket;
	BufferedReader in;
	PrintWriter out;
	InputStream temp;

	public static SocketInfo one = new SocketInfo();

	private SocketInfo() {
		try {
			clientSocket = new Socket("24.185.7.208", 27012);
			temp = clientSocket.getInputStream();
			in = new BufferedReader(new InputStreamReader(temp));
			out = new PrintWriter(clientSocket.getOutputStream(), true);
			
			//out.println("test2");
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//Toast.makeText(this, "mmm", Toast.LENGTH_SHORT).show();
	};

	public static SocketInfo getSocketInfo() throws UnknownHostException, IOException {
//		if (one == null) {
//			one = new SocketInfo();
//		}
		return one;
	}

	public static SocketInfo getSocketInfo(int x) throws UnknownHostException, IOException {
		//if (one != null) {
		//	closeSocket();
		//}
		one = new SocketInfo();
		return one;
	}
	
	public static void closeSocket() throws UnknownHostException, IOException {
		getSocketInfo().clientSocket.shutdownInput();
		getSocketInfo().clientSocket.shutdownOutput();		
		getSocketInfo().clientSocket.close();
		one = null;		
	}
	
//	public Socket getSocket(){
//		return clientSocket;
//	}
	public String sReader() throws IOException{
//		if (temp.available() < 1){
//			return null;
//		}
		String s = this.in.readLine();
		//this.temp.
		return s;
		//return in;
	}
	public void sWriter(String s){
		out.println(s);
		//return out;
	}
}