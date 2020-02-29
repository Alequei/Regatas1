package com.hdelacruz.regatasasistencia.services;

import com.hdelacruz.regatasasistencia.models.Entrada;

import java.lang.annotation.Documented;
import java.util.List;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface ApiServiceEntrada {

    String API_BASE_URL = "http://192.168.43.38:8080"; @GET("/entrada")
    Call<List<Entrada>> findAll();

    @FormUrlEncoded
    @POST("/entrada")
    Call<Entrada> createEntrada(@Field("codigo_e") String codigo_e,
                                  @Field("dni_e") String dni_e,
                                  @Field("fecha_e") String fecha_e,
                                  @Field("hora_e") String hora_e);

    @Multipart
    @POST("/entrada")
    Call<Entrada> createEntrada(@Part("codigo_e") RequestBody codigo_e,
                          @Part("dni_e")RequestBody dni_e,
                          @Part("fecha_e") RequestBody fecha_e,
                          @Part("hora_e") RequestBody hora_e,
                          @Part MultipartBody.Part imagen);


}
