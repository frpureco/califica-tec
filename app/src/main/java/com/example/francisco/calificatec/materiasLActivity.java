package com.example.francisco.calificatec;

import android.app.ProgressDialog;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.github.snowdream.android.widget.SmartImageView;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.Arrays;

import cz.msebera.android.httpclient.Header;

public class materiasLActivity extends AppCompatActivity {
    private ListView listView;

    private ArrayAdapter<String> adapter;
    ArrayList unidad= new ArrayList();
    ArrayList ruta=new ArrayList();
    ArrayList todo= new ArrayList();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_materias_l);
        listView=(ListView)findViewById(R.id.ListView);
        descargarimagen();
    }

    private void descargarimagen() {
        unidad.clear();
        ruta.clear();

        final ProgressDialog progressDialog= new ProgressDialog(materiasLActivity.this);
        progressDialog.setMessage("Cargando Datos...");
        progressDialog.show();

        AsyncHttpClient client= new AsyncHttpClient();
        client.get("http://192.168.43.239/moviles/Calif_movil.php", new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                if (statusCode==200){
                    Log.e("Hay algo","Entro!!!!!!!!!!");
                    progressDialog.dismiss();
                    try {
                        JSONArray jsonArray=new JSONArray(new String(responseBody));
                        for (int i=0;i<jsonArray.length();i++){
                            String cade;
                            Log.e("tamaño", Integer.toString(jsonArray.length()));
                            Log.e("Hay algo aqui", jsonArray.getJSONObject(i).getString("no_unidad"));
                            Log.e("Hay algo aqui", jsonArray.getJSONObject(i).getString("ruta"));
                            cade=jsonArray.getJSONObject(i).getString("no_unidad")+"  "+
                                    jsonArray.getJSONObject(i).getString("ruta");
                            unidad.add(jsonArray.getJSONObject(i).getString("no_unidad"));
                            ruta.add(jsonArray.getJSONObject(i).getString("ruta"));
                            todo.add(cade);

                        }

                        adapter=new ArrayAdapter<String>(getApplicationContext(),R.layout.support_simple_spinner_dropdown_item,todo);
                        listView.setAdapter(adapter);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            }
        });
    }

    private class ImageAdapter extends BaseAdapter{

        Context ctx;
        LayoutInflater layoutInflater;
        SmartImageView smartImageView;
        TextView tvunidad,tvruta;

        public ImageAdapter(Context applicationContext) {
            this.ctx=applicationContext;
            layoutInflater=(LayoutInflater) ctx.getSystemService(LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public int getCount() {
            return 0;
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewGroup viewGroup= (ViewGroup) layoutInflater.inflate(R.layout.activity_materias_l_item,null);
            tvunidad= (TextView) viewGroup.findViewById(R.id.tvunidad);
            tvruta=(TextView) viewGroup.findViewById(R.id.tvRuta);


            Log.e("va a poner",unidad.get(position).toString());
            tvunidad.setText(unidad.get(position).toString());
            tvruta.setText(ruta.get(position).toString());

            return viewGroup;
        }
    }
}
