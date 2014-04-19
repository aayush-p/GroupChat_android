package com.example.peli_ape;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.LinkedList;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

public class ChatWindow extends ActionBarActivity {

	LinkedList<String> messages;
	int listLength;
	UpdateNewMessages backgroundUpdation;

//	@Override
//	public void onBackPressed() {
//		// TODO Auto-generated method stub
//		
////		backgroundUpdation.cancel(true);
//		
//		super.onBackPressed();
//	}
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.chat_window);

		messages = new LinkedList<String>();
		listLength = 0;

		backgroundUpdation = new UpdateNewMessages(this);
		backgroundUpdation.execute();

	}

	public void postMessage(View view) {

		EditText input = (EditText) findViewById(R.id.editText1);
		String message = input.getText().toString();
		
		//Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
		
		try {
			SocketInfo clientSocketInfo = SocketInfo.getSocketInfo();
			//clientSocketInfo.sWriter("test3");
			clientSocketInfo.sWriter(message);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
//		new PostMessage(this).execute(message);
		//new PostMessage().execute(message);

		input.setText("");

	}



	private class UpdateNewMessages extends AsyncTask<Void, Void, Void> {

		Context c;
		
		public UpdateNewMessages(Context c){
			this.c = c.getApplicationContext();
		}
		
		@Override
		protected Void doInBackground(Void... values) {

			try {
				
				SocketInfo clientSocketInfo = SocketInfo.getSocketInfo();
				//BufferedReader in = clientSocketInfo.getReader();

				// get info from server and display
				while (true) {
					//Thread.sleep(5000);

					String newMessage = clientSocketInfo.sReader();
					//String newMessage = in.readLine();
					if (newMessage != null){

						if (listLength > 20) {
							messages.removeFirst();
						}
						messages.add(newMessage);
						listLength++;
						publishProgress();
					}
					Thread.sleep(10);
				}

			} catch (UnknownHostException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
		}

		@Override
		protected void onProgressUpdate(Void... values){
			super.onProgressUpdate(values);
			
			ListView msgView = (ListView) findViewById(R.id.listView);
			 
//			StringBuilder sb = new StringBuilder();
//			for( String temp: messages){
//				sb.append(temp).append('\n');
//			}
//			
//			nameView.setText(sb.toString());
			
			//String s[] = {""};

			//ArrayAdapter<String> a = new ArrayAdapter<String>(c, R.layout.list, objects) 
			
			msgView.setAdapter(new ArrayAdapter<String>(c, R.layout.list, messages));
			msgView.setSelection(listLength);
		}
		
		@Override
		protected void onCancelled(Void result) {
			try {
				SocketInfo.closeSocket();
				
			} catch (UnknownHostException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}

}
