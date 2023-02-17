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
import es.alexbonet.tetsingrealm.model.Butaca;

public class CompraRVAdapter extends RecyclerView.Adapter<CompraRVAdapter.ViewHolder>{
    private Controller c = new Controller();
    private LayoutInflater inflater;
    private View.OnClickListener onClickListener;
    private List<Butaca> butacas;
    private int precio;

    public CompraRVAdapter(Context context, List<Butaca> butacas, int precio){
        this.butacas = butacas;
        this.precio = precio;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @NonNull
    @Override
    public CompraRVAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.compra_view,parent,false);
        view.setOnClickListener(onClickListener);
        return new CompraRVAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CompraRVAdapter.ViewHolder holder, int position) {
        Butaca b = butacas.get(position);
        holder.fila.setText("" + b.getFila());
        holder.butaca.setText("" + b.getColunna());
        holder.precio.setText(precio + "€");
    }

    @Override
    public int getItemCount() {
        return butacas.size();
    }

    public void setOnClickListener(View.OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView fila;
        TextView butaca;
        TextView precio;
        View v;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            fila = itemView.findViewById(R.id.cvFila);
            butaca = itemView.findViewById(R.id.cvButaca);
            precio = itemView.findViewById(R.id.cvPrecio);
            this.v = itemView;
        }
    }
}
