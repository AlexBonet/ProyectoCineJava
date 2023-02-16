package es.alexbonet.tetsingrealm.RecyclerView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

import es.alexbonet.tetsingrealm.R;
import es.alexbonet.tetsingrealm.db.Controller;
import es.alexbonet.tetsingrealm.model.Film;
import es.alexbonet.tetsingrealm.model.UserType;
import io.realm.Realm;

public class FilmsRVAdapter extends RecyclerView.Adapter<FilmsRVAdapter.ViewHolder>{
    private Controller c = new Controller();
    private LayoutInflater inflater;
    private View.OnClickListener onClickListener;
    private List<Film> pelisCartrelera;
    private Realm connect;
    private String userName;

    public FilmsRVAdapter(Context context, List<Film> pelisCartrelera, Realm connect, String userName){
        this.connect = connect;
        this.userName = userName;
        this.pelisCartrelera = pelisCartrelera;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @NonNull
    @Override
    public FilmsRVAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.film_view_cartelera,parent,false);
        view.setOnClickListener(onClickListener);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FilmsRVAdapter.ViewHolder holder, int position) {
        Film f = pelisCartrelera.get(position);
        holder.titulo.setText(f.getTitulo());
        holder.genero.setText("Genero: " + f.getGenero());
        holder.edad.setText("Edad recomendad: +" + f.getEdad_min());
        Picasso.get().load(f.getUrlImage()).into(holder.img);

        if (f.isEn_cartelera()){
            holder.cartele.setImageResource(R.drawable.ic_baseline_local_movies_24_green);
        } else {
            holder.cartele.setImageResource(R.drawable.ic_baseline_local_movies_24_red);
        }

        if (userName != null){
            if (c.getUser(connect,userName).getTipo().equals(UserType.CLIENTE.getString())){
                holder.cartele.setVisibility(View.INVISIBLE);
            }
        }

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
        TextView edad;
        ImageView img;
        ImageView cartele;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            titulo = itemView.findViewById(R.id.rvMainTitulo);
            genero = itemView.findViewById(R.id.rvMainGenero);
            edad = itemView.findViewById(R.id.rvMainEdadmin);
            img = itemView.findViewById(R.id.rvMainImg);
            cartele = itemView.findViewById(R.id.fvImgCartelera);
        }
    }
}
