package com.example.peli_ape;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.LinkedList;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class ChatWindow extends ActionBarActivity {

	LinkedList<String> messages;
	int listLength;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.chat_window);

		messages = new LinkedList<String>();
		listLength = 0;

		new UpdateNewMessages().execute();

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


/*
	private class PostMessage extends AsyncTask<String, Void, Void> {
//		Context context;
//		
//		public PostMessage(Context context) {
//			this.context = context.getApplicationContext();
//		}

		@Override
		protected Void doInBackground(String... message) {
            //Toast.makeText(context, "message sending ", Toast.LENGTH_SHORT).show();
			try {
				//publishProgress();

				SocketInfo clientSocketInfo = SocketInfo.getSocketInfo();
				//PrintWriter out = clientSocketInfo.getWriter();
				clientSocketInfo.sWriter("test3");
				for (String n : message) {
		              //out.println(n);
		              clientSocketInfo.sWriter(n);
		              //Toast.makeText(context, "message sent: " + n, Toast.LENGTH_SHORT).show();
				}
			} catch (UnknownHostException e) {
				// TODO Auto-generated catch block
				//Toast.makeText(context, "message sendingvukytxdkyx ", Toast.LENGTH_SHORT).show();
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
		}

		@Override
		protected void onPostExecute(Void a) {
			super.onPostExecute(a);
			
			EditText input = (EditText) findViewById(R.id.editText1);
			input.setText("sent");
		}
		
		@Override
		protected void onProgressUpdate(Void... values){
			super.onProgressUpdate(values);
			EditText input = (EditText) findViewById(R.id.editText1);
			input.setText("sendingggg");
		}
	}
*/

	private class UpdateNewMessages extends AsyncTask<Void, Void, Void> {

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

						if (listLength > 10) {
							messages.removeFirst();
						}
						messages.add(newMessage);
						listLength++;
						publishProgress();
					}
					Thread.sleep(100);
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
			
			TextView nameView = (TextView)findViewById(R.id.textView1);
			StringBuilder sb = new StringBuilder();
			for( String temp: messages){
				sb.append(temp).append('\n');
			}
			
			nameView.setText(sb.toString());

		}
	}

}
