package br.com.ufpb.pasii;

import java.io.IOException;
import java.security.KeyStore;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.apache.http.HttpVersion;
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
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ListView;

public class ClubesView extends Activity{

	private static final int FAIL_JSON = 0;
	private static final int FAIL = 1;
	private ListView noteListView;
	private ProgressDialog pd;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.clube_view);

		pd = new ProgressDialog(this);
		pd.setMessage(getString(R.string.load_clubes));
		pd.show();

		new LoadClubesAsyncTask().execute();
	}


	private Handler handler = new Handler() {

		public void handleMessage(android.os.Message msg) {
			AlertDialog.Builder alert = new AlertDialog.Builder(ClubesView.this);
			if ((pd != null) && (pd.isShowing()))
				pd.dismiss();
			switch (msg.what) {	
			case FAIL_JSON:				
				alert.setTitle("Aviso");
				alert.setMessage("Erro de conexão");
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
				alert.setMessage("Nenhum clube cadastrado");
				alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {

					public void onClick(DialogInterface d, int arg1) {
						d.dismiss();
						finish();
					}
				});					
				alert.show();

				break;
			}
		};

	};



	private class LoadClubesAsyncTask extends AsyncTask<Void, Void, String> {

		@Override
		protected String doInBackground(Void... params) {
			String url = Constants.URL_CLUBE;
			return RequisicoesHttp.getRequest(url);
		}

		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);
			pd.dismiss();

			if (result != null) {
				List<Clube> clubes = new ArrayList<Clube>();
				Clube clube = null;
				try {
					JSONObject jsonResult = new JSONObject(result);
					JSONArray jsonArray = jsonResult.getJSONArray("clube");
					for (int i = 0; i < jsonArray.length(); i++) {
						JSONObject jsonObject = jsonArray.getJSONObject(i);
						
						clube = new Clube();
						//JSON Estava vindo com problema, por isso fiz vários try catch
						try{
							clube.setId(jsonObject.getInt("clube"));
						}catch(JSONException e){
							clube.setId(-1);
						}
						try{
							clube.setEscudo(jsonObject.getString("escudo"));
						}catch(JSONException e){
							clube.setEscudo("");
						}
						try{
							clube.setNome(jsonObject.getString("nome"));
						}catch(JSONException e){
							clube.setNome("");
						}
//						clube = new Clube(jsonObject.getInt("clube"), 
//								jsonObject.getString("escudo"),
//										jsonObject.getString("nome"));
						
						clubes.add(clube);
					}

					ClubeListAdapter adapter = new ClubeListAdapter(ClubesView.this, R.layout.clube_list, clubes);
					noteListView = (ListView) findViewById(R.id.lv_clubes);
					noteListView.setAdapter(adapter);

				} catch (JSONException e) {
					handler.sendEmptyMessage(FAIL_JSON);
				}
			} else {
				handler.sendEmptyMessage(FAIL);
			}
		}

	}




}
