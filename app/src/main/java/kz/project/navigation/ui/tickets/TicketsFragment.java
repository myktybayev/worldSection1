package kz.project.navigation.ui.tickets;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import kz.project.navigation.MainActivity;
import kz.project.navigation.R;
import kz.project.navigation.ui.events.ItemClick;

public class TicketsFragment extends Fragment implements ItemClick {
    List<Ticket> ticketList = new ArrayList<>();
    List<Ticket> ticketList2 = new ArrayList<>();
    TicketsAdapter ticketsAdapter1, ticketsAdapter2;
    Button btnCreate;
    View root;
    RecyclerView openRecycler, cloeRecycler;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        root =  inflater.inflate(R.layout.fragment_tickets, container, false);
//        ticketList.add(new Ticket("imagePath", "name", "time", "seat", "ceremonyType"));
        fromJsonToClass();
        openRecycler = root.findViewById(R.id.openRecycler);
        cloeRecycler = root.findViewById(R.id.cloeRecycler);

        ticketsAdapter1 = new TicketsAdapter(getContext(), ticketList, this::onItemClick);
        openRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        openRecycler.setAdapter(ticketsAdapter1);

        ticketsAdapter2 = new TicketsAdapter(getContext(), ticketList2, this::onItemClick);
        cloeRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        cloeRecycler.setAdapter(ticketsAdapter2);

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(openRecycler);

        ItemTouchHelper itemTouchHelper2 = new ItemTouchHelper(simpleCallback2);
        itemTouchHelper2.attachToRecyclerView(cloeRecycler);

        btnCreate = root.findViewById(R.id.btn_create);
        btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getActivity(), MainActivity.class);
                intent.putExtra("fragmentName", "addNewTicketFragment");
                startActivity(intent);
            }
        });

        return root;
    }

    @Override
    public void onItemClick(int pos) {
        Intent intent = new Intent(getActivity(), MainActivity.class);
        intent.putExtra("fragmentName", "ticketDetailFragment");
        intent.putExtra("ticket", ticketList.get(pos));
        startActivity(intent);
    }

    public String loadJSONFromAsset() {
        String json = null;
        try {
            InputStream is = getActivity().getAssets().open("events.json");
            int size = is.available();
            byte[] buffer = new byte[size];

            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void fromJsonToClass() {
        try {
            JSONObject obj = new JSONObject(loadJSONFromAsset());
            JSONArray m_jArry = obj.getJSONArray("tickets");

            for (int i = 0; i < m_jArry.length(); i++) {
                JSONObject jo_inside = m_jArry.getJSONObject(i);

                String imagePath = jo_inside.getString("imagePath");
                String name = jo_inside.getString("name");
                String time = jo_inside.getString("time");
                String seat = jo_inside.getString("seat");
                String ceremonyType = jo_inside.getString("ceremonyType");

                ticketList.add(new Ticket(imagePath, name, time, seat, ceremonyType));
                ticketList2.add(new Ticket(imagePath, name, time, seat, ceremonyType));

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(3,ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {

        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            int fromPosition = viewHolder.getAdapterPosition();
            int toPosition = target.getAdapterPosition();

            if (fromPosition < toPosition) {
                for (int i = fromPosition; i < toPosition; i++) {
                    Collections.swap(ticketList, i, i + 1);
                }
            } else {
                for (int i = fromPosition; i > toPosition; i--) {
                    Collections.swap(ticketList, i, i - 1);
                }
            }

            ticketsAdapter1.notifyItemMoved(toPosition, fromPosition);

            return true;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            int position = viewHolder.getAdapterPosition();
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            switch (direction){
                case ItemTouchHelper.LEFT:
                case ItemTouchHelper.RIGHT:
                    if (ticketList.isEmpty()) {
                        return;
                    }

                    ticketList.remove(position);

                    break;
            }

            ticketsAdapter1.notifyDataSetChanged();
        }
    };

    ItemTouchHelper.SimpleCallback simpleCallback2 = new ItemTouchHelper.SimpleCallback(3,ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {

        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            int fromPosition = viewHolder.getAdapterPosition();
            int toPosition = target.getAdapterPosition();

            if (fromPosition < toPosition) {
                for (int i = fromPosition; i < toPosition; i++) {
                    Collections.swap(ticketList2, i, i + 1);
                }
            } else {
                for (int i = fromPosition; i > toPosition; i--) {
                    Collections.swap(ticketList2, i, i - 1);
                }
            }

            ticketsAdapter2.notifyItemMoved(fromPosition, toPosition);

            return true;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            int position = viewHolder.getAdapterPosition();
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            switch (direction){
                case ItemTouchHelper.LEFT:
                case ItemTouchHelper.RIGHT:
                    if (ticketList2.isEmpty()) {
                        return;
                    }

                    ticketList2.remove(position);

                    break;
            }

            ticketsAdapter2.notifyDataSetChanged();
        }
    };
}