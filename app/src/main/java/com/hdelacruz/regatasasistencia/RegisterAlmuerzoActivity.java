package com.hdelacruz.regatasasistencia;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.hdelacruz.regatasasistencia.models.Entrada;
import com.hdelacruz.regatasasistencia.services.ApiServiceEntrada;
import com.hdelacruz.regatasasistencia.services.ApiServiceGeneratorE;
import com.muddzdev.styleabletoastlibrary.StyleableToast;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterAlmuerzoActivity extends AppCompatActivity {

    private static String TAG = RegisterEntraActivity.class.getSimpleName();

    private ImageView imagenPreview;
    private TextView fechaTexte;
    private TextView horatexte;
    private EditText dniTexte;
    private TextView codigoTexte;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_almuerzo);
        imagenPreview = findViewById(R.id.imagen_preview);
        fechaTexte = findViewById(R.id.fecha_texte);
        horatexte = findViewById(R.id.hora_texte);
        dniTexte = findViewById(R.id.dniTexte);
        codigoTexte = findViewById(R.id.codigo_texte);


        Thread t = new Thread(){
            @Override
            public void run(){
                try{
                    while (!isInterrupted()){
                        Thread.sleep(1000);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                TextView tdate = (TextView) findViewById(R.id.fecha_texte);
                                long date = System.currentTimeMillis();
                                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                                String dateString = sdf.format(date);
                                tdate.setText(dateString);
                            }
                        });
                    }
                }catch (InterruptedException e){
                }
            }
        };
        t.start();

        Thread h = new Thread(){
            @Override
            public void run(){
                try{
                    while (!isInterrupted()){
                        Thread.sleep(1000);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                TextView tdate = (TextView) findViewById(R.id.hora_texte);
                                long date = System.currentTimeMillis();
                                SimpleDateFormat sdf = new SimpleDateFormat("hh:mm:ss a");
                                String dateString = sdf.format(date);
                                tdate.setText(dateString);
                            }
                        });
                    }
                }catch (InterruptedException e){

                }
            }
        };
        h.start();
    }

    private static final int REQUEST_CAMERA = 100;

    //-------------------------------------------



    //-------------------------------------------


    public void takePicture(View view) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, REQUEST_CAMERA);



    }
    private Bitmap bitmap;


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {




        if (requestCode == REQUEST_CAMERA) {
            if (resultCode == RESULT_OK) {
                bitmap = (Bitmap) data.getExtras().get("data");
                bitmap = scaleBitmapDown(bitmap, 800);  // Redimensionar
                imagenPreview.setImageBitmap(bitmap);
            }
        }
    }

    public void callRegisterE(View view){

        String codigo_e = codigoTexte.getText().toString();
        String dni_e = dniTexte.getText().toString();
        String fecha_e = fechaTexte.getText().toString();
        String hora_e = horatexte.getText().toString();

        if (dni_e.isEmpty()) {
            StyleableToast.makeText(this,"DNI OBLIGATORIO",R.style.exampleToast).show();
            Toast toast = new Toast(getApplicationContext());
            toast.setGravity(Gravity.CENTER,5,5);
            return;
        }

        ApiServiceEntrada service = ApiServiceGeneratorE.createService(ApiServiceEntrada.class);

        Call<Entrada> call;
        if(bitmap == null){

            if (imagenPreview.getAdjustViewBounds()) {
                StyleableToast.makeText(this,"IMAGEN OBLIGATORIO",R.style.exampleToast).show();
                Toast toast = new Toast(getApplicationContext());
                toast.setGravity(Gravity.CENTER,5,5);
                return;
            }
            call = service.createEntrada(codigo_e, dni_e, fecha_e, hora_e);

        }else {

            // De bitmap a ByteArray
            // De bitmap a ByteArray
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
            byte[] byteArray = stream.toByteArray();

            // ByteArray a MultiPart
            RequestBody requestFile = RequestBody.create(MediaType.parse("image/jpeg"), byteArray);
            MultipartBody.Part imagenPart = MultipartBody.Part.createFormData("imagen", "photo.jpg", requestFile);


            // Paramestros a Part
            RequestBody codigo_ePart = RequestBody.create(MultipartBody.FORM, codigo_e);
            RequestBody dni_ePart = RequestBody.create(MultipartBody.FORM, dni_e);
            RequestBody fecha_ePart = RequestBody.create(MultipartBody.FORM, fecha_e);
            RequestBody hora_ePart = RequestBody.create(MultipartBody.FORM, hora_e);

            call = service.createEntrada(codigo_ePart, dni_ePart,fecha_ePart, hora_ePart,imagenPart);
        }
        call.enqueue(new Callback<Entrada>() {
            @Override
            public void onResponse(@NonNull retrofit2.Call<Entrada> call, @NonNull Response<Entrada> response) {
                try {
                    if(response.isSuccessful()) {

                        Entrada entrada = response.body();

                        Log.d(TAG, "entrada: " + entrada);

                        LayoutInflater inflater = getLayoutInflater();
                        View layout = inflater.inflate(R.layout.toast_layout, (ViewGroup)findViewById(R.id.toast_root));

                        Toast toast = new Toast(getApplicationContext());
                        toast.setGravity(Gravity.CENTER,0,0);
                        toast.setDuration(Toast.LENGTH_SHORT);
                        toast.setView(layout);
                        //Toast.makeText(RegisterEntraActivity.this, "Desayuno Registrado", Toast.LENGTH_SHORT).show();
                        setResult(RESULT_OK);
                        toast.show();

                        finish();

                    }else {
                        throw new Exception(ApiServiceGeneratorE.parseError(response).getMessage());
                    }
                } catch (Throwable t) {
                    Log.e(TAG, "onThrowable: " + t.getMessage(), t);
                    Toast.makeText(RegisterAlmuerzoActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void onFailure(@NonNull retrofit2.Call<Entrada> call, @NonNull Throwable t) {
                Log.e(TAG, "onFailure: " + t.getMessage(), t);
                Toast.makeText(RegisterAlmuerzoActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
    private Bitmap scaleBitmapDown(Bitmap bitmap, int maxDimension) {

        int originalWidth = bitmap.getWidth();
        int originalHeight = bitmap.getHeight();
        int resizedWidth = maxDimension;
        int resizedHeight = maxDimension;

        if (originalHeight > originalWidth) {
            resizedHeight = maxDimension;
            resizedWidth = (int) (resizedHeight * (float) originalWidth / (float) originalHeight);
        } else if (originalWidth > originalHeight) {
            resizedWidth = maxDimension;
            resizedHeight = (int) (resizedWidth * (float) originalHeight / (float) originalWidth);
        } else if (originalHeight == originalWidth) {
            resizedHeight = maxDimension;
            resizedWidth = maxDimension;
        }
        return Bitmap.createScaledBitmap(bitmap, resizedWidth, resizedHeight, false);
    }
}

