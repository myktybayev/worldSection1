package kz.project.navigation.ui.events;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import kz.project.navigation.R;

public class EventsFragment extends Fragment implements ItemClick {

    TextView textView;
    EventsAdapter eventsAdapter;
    List<EventsItem> eventsItemList = new ArrayList<>();
    List<EventsItem> eventsItemListAll = new ArrayList<>();
    List<EventsItem> eventsItemListUnread = new ArrayList<>();
    List<EventsItem> eventsItemListRead = new ArrayList<>();
    List<String> images;
    View view;
    RecyclerView recyclerView;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view =  inflater.inflate(R.layout.fragment_events, container, false);

        TextView menu1 = view.findViewById(R.id.menu1);
        TextView menu2 = view.findViewById(R.id.menu2);
        TextView menu3 = view.findViewById(R.id.menu3);
        recyclerView = view.findViewById(R.id.recyclerView);


        fromJsonToClass();

        for (EventsItem eventsItem : eventsItemListAll) {

            if (eventsItem.isRead) {
                eventsItemListRead.add(eventsItem);
            } else {
                eventsItemListUnread.add(eventsItem);
            }
        }


        eventsItemList = eventsItemListAll;

        menu1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeAdapter(eventsItemListAll);
            }
        });
        menu2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeAdapter(eventsItemListUnread);
            }
        });
        menu3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeAdapter(eventsItemListRead);
            }
        });

        eventsAdapter = new EventsAdapter(getContext(), eventsItemList, this::onItemClick);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(eventsAdapter);

        return view;
    }

    public void changeAdapter(List<EventsItem> list){
        eventsAdapter = new EventsAdapter(getContext(), list, this::onItemClick);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(eventsAdapter);
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

    public void fromJsonToClass() {
        try {
            JSONObject obj = new JSONObject(loadJSONFromAsset());
            JSONArray m_jArry = obj.getJSONArray("events");
            ArrayList<HashMap<String, String>> formList = new ArrayList<>();
            HashMap<String, String> m_li;

            for (int i = 0; i < m_jArry.length(); i++) {
                JSONObject jo_inside = m_jArry.getJSONObject(i);

                String title = jo_inside.getString("title");
                String description = jo_inside.getString("description");
                boolean isRead = jo_inside.getBoolean("isRead");
                int viewCounts = jo_inside.getInt("viewCounts");
                JSONArray jsonArrayArr = jo_inside.getJSONArray("images");

                images = new ArrayList<>();

                for (int j = 0; j < jsonArrayArr.length(); j++) {

                    images.add(jsonArrayArr.getString(j));
                    Log.i("info_json", "image: " + images.get(j));

                }

                eventsItemListAll.add(new EventsItem(
                        images,
                        title,
                        description,
                        isRead,
                        viewCounts));
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onItemClick(int pos) {
        Toast.makeText(getActivity(), "Pos :" + pos + " " + eventsItemList.get(pos).getTitle(), Toast.LENGTH_SHORT).show();
    }

}