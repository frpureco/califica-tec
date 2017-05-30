package com.example.francisco.calificatec;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.NotificationCompat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class Login extends AppCompatActivity implements View.OnClickListener{
    EditText usr,pas;
    Button in;
    private final static int ID=1;
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        usr = ((EditText) findViewById(R.id.edtx_usr));
        pas = ((EditText) findViewById(R.id.edtx_pass));
        in = (Button) findViewById(R.id.btnId);
        in.setOnClickListener(this);
        //Toast.makeText(getApplicationContext(),usr.getText().toString(),Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onClick(View v) {
        Thread tr= new Thread(){
            public void run() {
                final String resultado=enviarDatosGet(usr.getText().toString(),pas.getText().toString());
                runOnUiThread(new Runnable() {
                    public void run() {
                        int r= obtenerDatosJson(resultado);
                        if (r>0){
                            Intent i= new Intent(getApplicationContext(),menu.class);
                            i.putExtra("cod",usr.getText().toString());

                            String text="Ingresaste correctamente "+usr.getText().toString();
                            String title="Inicio de sesion Correcto";
                            NotificationCompat.Builder builder= basicBuilder(title,text,null);

                            Notification notification= builder.build();
                            NotificationManager notificationManager= (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                            notificationManager.notify(ID,notification);
                            startActivity(i);

                        }else {
                            Toast.makeText(getApplicationContext(),"usuario incorrecto",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        };
            tr.start();
    }

    //metodo para crear el notification Builder.
    private NotificationCompat.Builder basicBuilder(String title,String text,Intent intent){
        NotificationCompat.Builder builder= new NotificationCompat.Builder(this);

        builder.setContentText(text).setContentTitle(title)
        .setSmallIcon(R.drawable.ic_edit);//este icono puede cambiar. solo colocas en la carpeta drawable la imagen del icono
        return builder;
    }

    public String enviarDatosGet(String usu,String pass){
        URL url=null;
        String linea="";
        int respuesta=0;
        StringBuilder resul=null;

        try {
            url= new URL("http://192.168.43.239/moviles/valida.php?usu="+usu+"&pas="+pass);
            Log.e("la url",url.toString());
            HttpURLConnection conection = (HttpURLConnection) url.openConnection();
            respuesta= conection.getResponseCode();
            resul= new StringBuilder();
            if (respuesta==HttpURLConnection.HTTP_OK){

                InputStream in= new BufferedInputStream(conection.getInputStream());
                BufferedReader reader= new BufferedReader(new InputStreamReader(in));
                Log.e("LA LINEA",reader.toString());
                while ((linea=reader.readLine())!=null){

                    resul.append(linea);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        Log.e("ESTE ES MI MENSAJE",resul.toString());

        return resul.toString();
    }

    public  int obtenerDatosJson( String response){
        int res=0;
        try {
            JSONArray json= new JSONArray(response);
            if (json.length()>0)
                res=1;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return  res;
    }
}
