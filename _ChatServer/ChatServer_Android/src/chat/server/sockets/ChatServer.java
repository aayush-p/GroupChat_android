package chat.server.sockets;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;

class ChatServer{

    static int             size;
    static ServerSocket    serverSocket;
//    static boolean         active;
    public static LinkedList<ClientSocketInfo> clientSocketList;
    public static LinkedList<String> messages;

    public static void main(String[] arsg){
//    public ChatServer() throws Exception {
//        active = true;
    	clientSocketList = new LinkedList<ClientSocketInfo>();
    	messages = new LinkedList<String>();
    	
    	try {
			serverSocket = new ServerSocket(27012);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	//start a thread which sends broadcasts..
    	Thread br = new BroadcastThread();
    	br.start();
    	
        listen();
    }

    static void listen() {
        while ( true ) {
			try {
				Socket clientSocket;
				clientSocket = serverSocket.accept();
				System.out.println("accepted.");
				
				ClientSocketInfo newClientSocInfo = new ClientSocketInfo(clientSocket);
				clientSocketList.add(newClientSocInfo);
				System.out.println(clientSocketList.size());

				Thread cl = new ClientThread(newClientSocInfo);
				cl.start();

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
    }
}

class ClientThread extends Thread {

//	BufferedReader in;
//	Socket clientSocket;
	ClientSocketInfo curSocInfo;

    ClientThread( ClientSocketInfo clientSocketInfo ) throws IOException{
		this.curSocInfo = clientSocketInfo;
//    	in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
    }

    public void run() {
        
        try {
        	String name = curSocInfo.sReader();
        	System.out.println("new thread.. " + name);
			ChatServer.messages.add(name + " joined..");

			String s;
        	while ( (s = curSocInfo.sReader()) != null ) {
				System.out.println(s);
				ChatServer.messages.add(name + ": " + s);				
			}
			curSocInfo.out.close();
			ChatServer.clientSocketList.remove(this.curSocInfo);
			System.out.println("removing cient.. total size " + ChatServer.clientSocketList.size());
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
//			e.printStackTrace();
			System.out.println("IO exception in thread... closing this connection");
			
			curSocInfo.out.close();
			ChatServer.clientSocketList.remove(this.curSocInfo);
			System.out.println("removing cient.. total size " + ChatServer.clientSocketList.size());
		}
    }

}

class BroadcastThread extends Thread {

    public void run() {
        int msgLength = 0;
        Boolean flag = true;
        
        while(flag){
	        if(ChatServer.messages != null){
	        	flag = false;
	        }
    	}
        
        while (true){
        	try {
				Thread.sleep(5);
				
				if (ChatServer.messages.size() != msgLength ){
					String m = ChatServer.messages.get(msgLength);
					msgLength++;
					
					for(ClientSocketInfo s: ChatServer.clientSocketList){
						s.sWriter(m);
					}
				}
				
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
        
    }

}
