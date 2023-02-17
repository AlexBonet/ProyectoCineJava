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
import es.alexbonet.tetsingrealm.model.Venta;

public class VentaRVAdapter extends RecyclerView.Adapter<VentaRVAdapter.ViewHolder> {
    private LayoutInflater inflater;
    private View.OnClickListener onClickListener;
    private List<Venta> list;

    public VentaRVAdapter(Context context, List<Venta> list){
        this.list = list;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @NonNull
    @Override
    public VentaRVAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.venta_view,parent,false);
        view.setOnClickListener(onClickListener);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VentaRVAdapter.ViewHolder holder, int position) {
        Venta v = list.get(position);
        holder.num_venta.setText(""+ v.getNum_venta());
        holder.vendedor.setText(""+ v.getNombre_empleado());
        holder.fecha.setText(""+ v.getHora());
        holder.precio.setText(""+ v.getImporte());
    }

    public void setOnClickListener(View.OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView num_venta;
        TextView vendedor;
        TextView fecha;
        TextView precio;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            num_venta = itemView.findViewById(R.id.vvNum);
            vendedor = itemView.findViewById(R.id.vvVendedor);
            fecha = itemView.findViewById(R.id.vvFecha);
            precio = itemView.findViewById(R.id.vvPrecio);
        }
    }
}
