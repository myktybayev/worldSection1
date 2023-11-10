package kz.project.navigation.ui.events;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import kz.project.navigation.R;


public class EventsAdapter extends RecyclerView.Adapter<EventsAdapter.ViewHolder> {
    private Context context;
    private List<EventsItem> eventsItemList;
    private ItemClick itemClick;

    public EventsAdapter(Context context, List<EventsItem> eventsItemList, ItemClick itemClick) {
        this.context = context;
        this.eventsItemList = eventsItemList;
        this.itemClick = itemClick;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_event,parent,false);
        return new ViewHolder(view);
    }

    // "image": "R.drawable.plug_boschi.png"

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.title.setText(eventsItemList.get(position).getTitle());
        holder.desc.setText(eventsItemList.get(position).getDesc());

        holder.status.setText(eventsItemList.get(position).isRead() ? "Read" : "Unread");

        String imagefile = eventsItemList.get(position).getImages().get(0);
        String resName = imagefile.split("\\.")[2];

        holder.image.setImageResource(
                context.getResources().getIdentifier(
                        resName,
                        "drawable", context.getPackageName()));

//        holder.image.setImageResource(eventsItemList.get(position).getImages().get(0));
//        Image image = new Image("");
    }

    @Override
    public int getItemCount() {
        return eventsItemList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView title, desc, status;
        ImageView image;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
            desc = itemView.findViewById(R.id.desc);
            status = itemView.findViewById(R.id.status);
            image = itemView.findViewById(R.id.image);

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
