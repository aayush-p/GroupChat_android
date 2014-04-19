package chat.server.sockets;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class ClientSocketInfo {

	Socket clientSocket;
	BufferedReader in;
	PrintWriter out;
	InputStream temp;

	public ClientSocketInfo(Socket c) {
		try {
			clientSocket = c;
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

	public Socket getSocket(){
		return clientSocket;
	}

	public String sReader() throws IOException {
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