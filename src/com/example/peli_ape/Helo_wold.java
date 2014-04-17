package com.example.peli_ape;

import java.io.IOException;
import java.net.UnknownHostException;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

public class Helo_wold extends ActionBarActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		//super.onCreate(savedInstanceState);
		super.onCreate(null);
		setContentView(R.layout.main);

		if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();
		}
	}

	public void onClick(View view) {
		EditText input = (EditText) findViewById(R.id.main_input);
		String string = input.getText().toString();
//		Toast.makeText(this, string, Toast.LENGTH_SHORT).show();

		Toast.makeText(this, "connecting..", Toast.LENGTH_SHORT).show();

		new ConnectToServer(this).execute(string);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.helo_wold, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_helo_wold,
					container, false);
			return rootView;
		}
	}

	private class ConnectToServer extends AsyncTask<String, Void, Void> {

		Context context;
		
		private ConnectToServer(Context context){
			this.context = context.getApplicationContext();
		}
		
		@Override
		protected Void doInBackground(String... string) {
			try {
//				Socket clientSocket = new Socket("10.0.2.2", 2222);
//				BufferedReader in1 = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
//				PrintWriter out1 = new PrintWriter(clientSocket.getOutputStream(), true);
//				out1.println("test");
//				
				
				//Toast.makeText(context, "message sending...", Toast.LENGTH_SHORT).show();
				SocketInfo clientSocketInfo = SocketInfo.getSocketInfo(2);
				//PrintWriter out = clientSocketInfo.sWriter(n);
				 for (String n : string) {
					 clientSocketInfo.sWriter(n);
		          }

			} catch (UnknownHostException e) {
				// TODO Auto-generated catch block
				//Toast.makeText(this, "connection prob- UnknownHostException",
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
			
			Intent intent = new Intent(context, ChatWindow.class);
			intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			startActivity(intent);
		}

	}

}
