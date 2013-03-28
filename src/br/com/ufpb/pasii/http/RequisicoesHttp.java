package br.com.ufpb.pasii.http;

import java.io.IOException;
import java.security.KeyStore;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import br.com.ufpb.pasii.core.Constants;

import android.util.Log;

public class RequisicoesHttp {

	
	public static String getRequest(String url) {
		String retorno = null; 
		try {
			HttpClient client = new DefaultHttpClient();  
			HttpGet get = new HttpGet(url);
			HttpResponse responseGet = client.execute(get);  
			HttpEntity resEntityGet = responseGet.getEntity();  
			if (resEntityGet != null) {  
				retorno = EntityUtils.toString(resEntityGet);
			}
		} catch (ClientProtocolException e) {
			Log.d(Constants.TAG, e.getMessage());
		} catch (IOException e) {
			Log.d(Constants.TAG, e.getMessage());
		} catch (Exception e) {
			Log.d(Constants.TAG, e.getMessage());
		}
		return retorno;
	}

	

	public static String postRequest(String url, JSONObject json) {
		try {
			DefaultHttpClient httpClient = new DefaultHttpClient();
			ResponseHandler <String> resonseHandler = new BasicResponseHandler();
			HttpPost postMethod = new HttpPost("http://bolaoshow.herokuapp.com/service/clubes");			
			postMethod.setHeader( "Content-Type", "application/json" );
			postMethod.setEntity(new ByteArrayEntity(json.toString().getBytes("UTF8")));
			String response = httpClient.execute(postMethod,resonseHandler);
			return response;
		} catch (ClientProtocolException e) {
			Log.d(Constants.TAG, e.getMessage());
		} catch (IOException e) {
			Log.d(Constants.TAG, e.getMessage());
		} catch (Exception e) {
			Log.d(Constants.TAG, e.getMessage());
		}
		return null;
	}
	


}
