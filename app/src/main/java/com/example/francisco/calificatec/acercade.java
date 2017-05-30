package com.example.francisco.calificatec;

/**
 * Created by FRANCISCO on 30/03/2017.
 */
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class acercade extends AppCompatActivity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acercade);

    }
    public void lanzar_info_c(View view){
        Intent i = new Intent(acercade.this,Info_calificacion.class);
        startActivity(i);
    }


}
