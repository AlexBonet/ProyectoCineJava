package es.alexbonet.tetsingrealm.RecyclerView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import es.alexbonet.tetsingrealm.R;
import es.alexbonet.tetsingrealm.db.Controller;
import es.alexbonet.tetsingrealm.db.DataBase;
import es.alexbonet.tetsingrealm.model.Sesion;
import io.realm.Realm;

public class GeSionRVAdapter extends RecyclerView.Adapter<GeSionRVAdapter.ViewHolder>{
    private final Controller c = new Controller();
    private Realm connect;
    private LayoutInflater inflater;
    private View.OnClickListener onClickListener;
    private List<Sesion> sesionList;

    public GeSionRVAdapter(Context context, List<Sesion> sesionList){
        this.sesionList = sesionList;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @NonNull
    @Override
    public GeSionRVAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.gestor_sesion_view,parent,false);
        view.setOnClickListener(onClickListener);
        return new GeSionRVAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GeSionRVAdapter.ViewHolder holder, int position) {
        connect = DataBase.getInstance().conectar(this.inflater.getContext());
        Sesion s = sesionList.get(position);
        holder.hora.setText(s.getHora_empieza());
        holder.numsala.setText("Sala: " + s.getNum_sala());
        holder.tipsala.setText(c.getSala(connect,s.getNum_sala()).getTipo_sala());
        holder.titul.setText(s.getTitulo_peli());
    }

    @Override
    public int getItemCount() {
        return sesionList.size();
    }
    public void setOnClickListener(View.OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView hora;
        TextView numsala;
        TextView tipsala;
        TextView titul;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            hora = itemView.findViewById(R.id.gsHora);
            numsala = itemView.findViewById(R.id.gsSala);
            tipsala = itemView.findViewById(R.id.gsTipo);
            titul = itemView.findViewById(R.id.gsTitulo);
        }
    }
}