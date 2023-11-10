package kz.project.navigation.ui.tickets;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import kz.project.navigation.R;
import kz.project.navigation.ui.events.ItemClick;

public class TicketsAdapter extends RecyclerView.Adapter<TicketsAdapter.ViewHolder> {
    private Context context;
    private List<Ticket> eventsItemList;
    private ItemClick itemClick;

    public TicketsAdapter(Context context, List<Ticket> eventsItemList, ItemClick itemClick) {
        this.context = context;
        this.eventsItemList = eventsItemList;
        this.itemClick = itemClick;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_open,parent,false);
        return new ViewHolder(view);
    }

    // "image": "R.drawable.plug_boschi.png"

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.name.setText(eventsItemList.get(position).getName());
        holder.seat.setText(eventsItemList.get(position).getSeat());
    }

    @Override
    public int getItemCount() {
        return eventsItemList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView name, seat;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            seat = itemView.findViewById(R.id.seat);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int pos = getAdapterPosition();
                    if (pos != RecyclerView.NO_POSITION) {
                        itemClick.onItemClick(pos);
                    }
                }
            });
        }
    }
}
