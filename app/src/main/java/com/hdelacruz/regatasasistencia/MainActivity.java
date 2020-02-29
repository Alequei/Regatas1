package com.hdelacruz.regatasasistencia;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    private Button registerEButton;
    private Button registerSButton;
    private Button registerCButton;
    String dateString;
    Integer hora1;
    String hora2;
    String hora3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Calendar c1 = Calendar.getInstance();
        final int horaActual;
        horaActual =c1.get(Calendar.HOUR_OF_DAY);
        registerSButton = findViewById(R.id.salida_Butt);
        registerSButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(horaActual<12){
                    goSalida();

                } else{
                    Toast.makeText(MainActivity.this,"Se avilitara a partir de las 8 Am hasta las 12 Pm", Toast.LENGTH_SHORT).show();
                }
            }
        });

        registerEButton = findViewById(R.id.ingreso_Butt);
        registerEButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (horaActual>12){
                    goRegister();
                }else{
                    Toast.makeText(MainActivity.this,"Se avilitara a partir de las 12 Am hasta las 5 Pm", Toast.LENGTH_SHORT).show();
                }

            }
        });

        registerCButton = findViewById(R.id.Cena_Butt);
        registerCButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (horaActual>19){
                    ceRegister();
                }else{
                    Toast.makeText(MainActivity.this,"Se avilitara a partir de las 7 Am hasta las 10 Pm", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }





    private static final int REQUEST_REGISTER_FORM = 100;

    private void goSalida(){
        Intent intent = new Intent(this, RegisterSaliActivity.class);
        startActivity(intent);
    }

    private void goRegister(){
        Intent intent = new Intent(this, RegisterEntraActivity.class);
        startActivity(intent);
    }
    private void ceRegister(){
        Intent intent = new Intent(this,RegisterAlmuerzoActivity.class);
        startActivity(intent);
    }

    public void callRegisterE(View view){
        startActivityForResult(new Intent(this, RegisterEntraActivity.class), REQUEST_REGISTER_FORM);
    }

    public void callRegisterS(View view){
        startActivityForResult(new Intent(this, RegisterSaliActivity.class), REQUEST_REGISTER_FORM);
    }

    public void callRegisterC(View view){
        startActivityForResult(new Intent(this, RegisterAlmuerzoActivity.class), REQUEST_REGISTER_FORM);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == REQUEST_REGISTER_FORM) {
            return;
        }
    }



    }


