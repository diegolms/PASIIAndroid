package br.com.ufpb.pasii.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import br.com.ufpb.pasii.R;
import br.com.ufpb.pasii.model.Clube;


public class ClubeListAdapter extends ArrayAdapter<Clube> {

	private ArrayList<Clube> clubes;

	public ClubeListAdapter(Context context, int textViewResourceId, List<Clube> clubes) {
		super(context, textViewResourceId, clubes);
		this.clubes = (ArrayList<Clube>) clubes;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LinearLayout v = (LinearLayout)convertView;
        if (v == null) {
            LayoutInflater vi = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = (LinearLayout)vi.inflate(R.layout.clube_list, null);
        }
        Clube clube = clubes.get(position);
        if (clube != null) {

            TextView tv_id_clube = (TextView) v.findViewById(R.id.tv_id_clube);
            if (tv_id_clube != null){
            	tv_id_clube.setText("Clube " +clube.getId());
            }

            TextView tv_nome_clube = (TextView) v.findViewById(R.id.tv_nome_clube);
            if (tv_nome_clube != null){
            	tv_nome_clube.setText("Nome: " +clube.getNome());
            }
            
            TextView tv_escudo_clube = (TextView) v.findViewById(R.id.tv_escudo_clube);
            if (tv_escudo_clube != null){
            	tv_escudo_clube.setText("Escudo: " +clube.getEscudo());
            }
        }
        return v;
	}
}
