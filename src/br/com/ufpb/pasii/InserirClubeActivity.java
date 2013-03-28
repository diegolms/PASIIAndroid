package br.com.ufpb.pasii;

import java.io.IOException;
import java.security.KeyStore;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.apache.http.HttpVersion;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.HTTP;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import br.com.ufpb.pasii.adapter.ClubeListAdapter;
import br.com.ufpb.pasii.core.Constants;
import br.com.ufpb.pasii.http.RequisicoesHttp;
import br.com.ufpb.pasii.model.Clube;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.EditText;
import android.widget.ListView;

public class InserirClubeActivity extends Activity implements OnClickListener{

	private static final int OK = 0;
	private static final int FAIL = 1;

	private ListView noteListView;
	private ProgressDialog pd;
	private EditText etNome,etEscudo;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.novo_clube);

		etNome = (EditText) findViewById(R.id.et_nome);
		etEscudo = (EditText) findViewById(R.id.et_escudo);
		findViewById(R.id.bt_enviar).setOnClickListener(this);

	}

	public void onClick(View v) {
		switch(v.getId()){

		case R.id.bt_enviar:
			pd = new ProgressDialog(this);
			pd.setMessage(getString(R.string.inserindo_clubes));
			pd.show();
			new InsertClubesAsyncTask().execute();
			break;
		}

	}


	private Handler handler = new Handler() {

		public void handleMessage(android.os.Message msg) {
			AlertDialog.Builder alert = new AlertDialog.Builder(InserirClubeActivity.this);
			if ((pd != null) && (pd.isShowing()))
				pd.dismiss();
			switch (msg.what) {
			case OK:	
				alert.setMessage("Clube inserido com sucesso");
				alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {

					public void onClick(DialogInterface d, int arg1) {
						d.dismiss();
						finish();
					}
				});		
				alert.show();
				break;
			case FAIL:				
				alert.setTitle("Aviso");
				alert.setMessage("Preencha todos os campos");
				alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {

					public void onClick(DialogInterface d, int arg1) {
						d.dismiss();
						
					}
				});					
				alert.show();

				break;
			}
		};

	};



	private class InsertClubesAsyncTask extends AsyncTask<Void, Void, String> {

		@Override
		protected String doInBackground(Void... params) {

			String nome = etNome.getText().toString();
			String escudo = etEscudo.getText().toString();

			if(nome.equals("") || escudo.equals("")){
				return null;
			}

			JSONObject json = new JSONObject();
			try {
				json.put("nome", nome);
				json.put("escudo", escudo);
			} catch (JSONException e) {
				e.printStackTrace();
			}

			return RequisicoesHttp.postRequest(Constants.URL_CLUBE, json);


		}

		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);
			if (result != null) {
				if(result.equalsIgnoreCase("Sucesso")){
					handler.sendEmptyMessage(OK);
				}
			} else {
				handler.sendEmptyMessage(FAIL);
			}
		}
	}
}
