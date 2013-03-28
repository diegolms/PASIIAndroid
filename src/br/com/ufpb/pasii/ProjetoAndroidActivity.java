package br.com.ufpb.pasii;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

public class ProjetoAndroidActivity extends Activity implements OnClickListener{
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        findViewById(R.id.bt_clubes).setOnClickListener(this);
        findViewById(R.id.bt_inserir_clubes).setOnClickListener(this);
    }

	public void onClick(View v) {
		Intent i;
		switch(v.getId()){
		case R.id.bt_clubes:
			i = new Intent(ProjetoAndroidActivity.this, ClubesView.class);
			startActivity(i);
			break;
		case R.id.bt_inserir_clubes:
			i = new Intent(ProjetoAndroidActivity.this, InserirClubeActivity.class);
			startActivity(i);
			break;
		}
		
	}
}