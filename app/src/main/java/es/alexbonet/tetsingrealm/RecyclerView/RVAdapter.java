package es.alexbonet.tetsingrealm.RecyclerView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import es.alexbonet.tetsingrealm.R;
import es.alexbonet.tetsingrealm.model.Film;

public class RVAdapter extends RecyclerView.Adapter<RVAdapter.ViewHolder>{
    private LayoutInflater inflater;
    private View.OnClickListener onClickListener;
    private List<Film> pelisCartrelera;

    public RVAdapter(Context context, List<Film> pelisCartrelera){
        this.pelisCartrelera = pelisCartrelera;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @NonNull
    @Override
    public RVAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.film_view_cartelera,parent,false);
        view.setOnClickListener(onClickListener);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RVAdapter.ViewHolder holder, int position) {
        Film f = pelisCartrelera.get(position);
    }

    @Override
    public int getItemCount() {
        return pelisCartrelera.size();
    }

    public void setOnClickListener(View.OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView titulo;
        TextView genero;
        ImageView img;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            titulo = itemView.findViewById(R.id.rvMainTitulo);
            genero = itemView.findViewById(R.id.rvMainGenero);
            img = itemView.findViewById(R.id.rvMainImg);
        }
    }
}
