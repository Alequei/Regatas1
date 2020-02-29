package com.hdelacruz.regatasasistencia.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.hdelacruz.regatasasistencia.R;
import com.hdelacruz.regatasasistencia.models.Entrada;
import com.hdelacruz.regatasasistencia.services.ApiServiceEntrada;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;
import java.util.List;

public class EntradaAdapter extends RecyclerView.Adapter<EntradaAdapter.ViewHolder> {

    private static final String TAG = EntradaAdapter.class.getSimpleName();

    private List<Entrada>entradas;

    public EntradaAdapter(){
        this.entradas = new ArrayList<>();
    }

    public  void setEntradas(List<Entrada>entradas){
        this.entradas = entradas;
    }

    class  ViewHolder extends RecyclerView.ViewHolder{
        ImageView fotoImage;
        TextView fecha;
        TextView DNI;

        ViewHolder(View itemView){
            super(itemView);
            fotoImage = itemView.findViewById(R.id.imagen_preview);
            fecha = itemView.findViewById(R.id.hora_texte);
            DNI = itemView.findViewById(R.id.dniTexte);
        }

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_entrada, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        final Context context = viewHolder.itemView.getContext();

        Entrada entrada = this.entradas.get(position);
        viewHolder.fecha.setText(entrada.getFecha_e());
        viewHolder.DNI.setText(entrada.getDni_e());

        String url = ApiServiceEntrada.API_BASE_URL + "/info/images/" + entrada.getImagen();
        Picasso.with(context).load(url).into(viewHolder.fotoImage);
    }

    @Override
    public int getItemCount() {
        return this.entradas.size();
    }
}
