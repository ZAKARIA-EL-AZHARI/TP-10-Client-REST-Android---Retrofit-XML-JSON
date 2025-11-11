package ma.projet.restclient.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import ma.projet.R;
import ma.projet.restclient.entities.Compte;

public class CompteAdapter extends RecyclerView.Adapter<CompteAdapter.CompteViewHolder> {

    public interface OnItemClickListener {
        void onUpdateClick(Compte compte);
        void onDeleteClick(Compte compte);
    }

    private final List<Compte> comptes = new ArrayList<>();
    private final OnItemClickListener listener;

    public CompteAdapter(OnItemClickListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public CompteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_compte, parent, false);
        return new CompteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CompteViewHolder holder, int position) {
        Compte compte = comptes.get(position);
        holder.bind(compte, listener);
    }

    @Override
    public int getItemCount() {
        return comptes.size();
    }

    public void updateData(List<Compte> newComptes) {
        this.comptes.clear();
        if (newComptes != null) {
            this.comptes.addAll(newComptes);
        }
        notifyDataSetChanged();
    }

    static class CompteViewHolder extends RecyclerView.ViewHolder {
        private final TextView tvId, tvSolde, tvType, tvDate;
        private final View btnDelete, btnUpdate;

        public CompteViewHolder(@NonNull View itemView) {
            super(itemView);
            tvId = itemView.findViewById(R.id.tvId);
            tvSolde = itemView.findViewById(R.id.tvSolde);
            tvType = itemView.findViewById(R.id.tvType);
            tvDate = itemView.findViewById(R.id.tvDate);
            btnDelete = itemView.findViewById(R.id.btnDelete);
            btnUpdate = itemView.findViewById(R.id.btnEdit);
        }

        public void bind(final Compte compte, final OnItemClickListener listener) {
            tvId.setText(String.format(Locale.getDefault(), "ID: %d", compte.getId()));
            tvSolde.setText(String.format(Locale.getDefault(), "Solde: %.2f", compte.getSolde()));
            tvType.setText(String.format("Type: %s", compte.getType()));
            tvDate.setText(String.format("Date: %s", compte.getDateCreation()));

            btnUpdate.setOnClickListener(v -> listener.onUpdateClick(compte));
            btnDelete.setOnClickListener(v -> listener.onDeleteClick(compte));
        }
    }
}
