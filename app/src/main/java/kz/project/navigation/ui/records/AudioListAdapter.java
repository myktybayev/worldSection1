package kz.project.navigation.ui.records;

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
import kz.project.navigation.ui.tickets.Ticket;

public class AudioListAdapter extends RecyclerView.Adapter<AudioListAdapter.ViewHolder> {
    private Context context;
    private List<String> audioList;
    private ItemClick itemClick;

    public AudioListAdapter(Context context, List<String> audioList, ItemClick itemClick) {
        this.context = context;
        this.audioList = audioList;
        this.itemClick = itemClick;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_audio,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.audioName.setText("audio "+(position+1));
    }

    @Override
    public int getItemCount() {
        return audioList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView audioName;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            audioName = itemView.findViewById(R.id.audioName);

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
