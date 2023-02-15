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
import es.alexbonet.tetsingrealm.controller.SalaController;
import es.alexbonet.tetsingrealm.db.DataBase;
import es.alexbonet.tetsingrealm.model.Sesion;
import io.realm.Realm;

public class SesionRVAdapter extends RecyclerView.Adapter<SesionRVAdapter.ViewHolder>{
    private final SalaController sl = new SalaController();
    private Realm connect;
    private LayoutInflater inflater;
    private View.OnClickListener onClickListener;
    private List<Sesion> sesionList;

    public SesionRVAdapter(Context context, List<Sesion> sesionList){
        this.sesionList = sesionList;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @NonNull
    @Override
    public SesionRVAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.sesion_view,parent,false);
        view.setOnClickListener(onClickListener);
        return new SesionRVAdapter.ViewHolder(view);
    }

    @Override //TODO PETA; NO FICA LA HORA
    public void onBindViewHolder(@NonNull SesionRVAdapter.ViewHolder holder, int position) {
        connect = DataBase.getInstance().conectar(this.inflater.getContext());
        Sesion s = sesionList.get(position);
        holder.hora.setText(s.getHora_empieza());
        holder.numsala.setText("Sala: " + s.getNum_sala());
        holder.tipsala.setText(sl.getSala(connect,s.getNum_sala()).getTipo_sala());
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

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            hora = itemView.findViewById(R.id.svHora);
            numsala = itemView.findViewById(R.id.svNumSala);
            tipsala = itemView.findViewById(R.id.svTipoSala);
        }
    }
}
