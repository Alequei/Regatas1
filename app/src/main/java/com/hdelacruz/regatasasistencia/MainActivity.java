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

    int horastimada1,horaestimada2;
    int diferenciaHD,diferenciaHA,diferenciaHC;
    int diferenciaMD,diferenciMA,diferenciaMC;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Calendar c1 = Calendar.getInstance();
        final int horaActual;

        horaActual =c1.get(Calendar.HOUR_OF_DAY);
        horastimada1=c1.get(Calendar.MINUTE);
        horaestimada2=c1.get(Calendar.SECOND);
        diferenciaHD = 31-horaActual;
        diferenciaHA =36-horaActual;
        diferenciaHC=19-horaActual;
        diferenciaMD=60 - horastimada1;
        registerEButton = findViewById(R.id.ingreso_Butt);
        registerEButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(horaActual>7 && horaActual<10){
                    goSalida();

                } else if(horaActual>11){
                    Toast.makeText(MainActivity.this,"Desayuno Inavilitado"+"  Falta  "+diferenciaHD+"h :"
                            +diferenciaMD+" minutos", Toast.LENGTH_SHORT).show();
                }
            }
        });

        registerSButton = findViewById(R.id.salida_Butt);
        registerSButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (horaActual>=12 && horaActual<=16){
                    goRegister();
                }else if (horaActual<=17){
                    Toast.makeText(MainActivity.this,"Almuerzo inavilitado"+"  Falta  "+diferenciaHA+"h :"
                            +diferenciaMD+" minutos", Toast.LENGTH_SHORT).show();
                }

            }
        });

        registerCButton = findViewById(R.id.Cena_Butt);
        registerCButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (horaActual>=19 && horaActual>=21){
                    ceRegister();
                }else if(horaActual<22){
                    Toast.makeText(MainActivity.this,"Cena inavilitado"+"  Falta  "+diferenciaHC+"h :"
                            +diferenciaMD+"  minutos", Toast.LENGTH_SHORT).show();
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


