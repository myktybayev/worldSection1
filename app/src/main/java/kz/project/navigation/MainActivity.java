package kz.project.navigation;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import kz.project.navigation.ui.events.EventsFragment;
import kz.project.navigation.ui.records.RecordsFragment;
import kz.project.navigation.ui.tickets.AddNewTicketFragment;
import kz.project.navigation.ui.tickets.Ticket;
import kz.project.navigation.ui.tickets.TicketDetailFragment;
import kz.project.navigation.ui.tickets.TicketsFragment;

public class MainActivity extends AppCompatActivity {
    TextView tv_events;
    TextView tv_tickets;
    TextView tv_records;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Window window = getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(ContextCompat.getColor(this, R.color.white));

        tv_events = findViewById(R.id.tv_events);
        tv_tickets = findViewById(R.id.tv_tickets);
        tv_records = findViewById(R.id.tv_records);

        switchFragment(new EventsFragment());

        tv_events.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                tv_events.setTextColor(getColor(R.color.red));
                tv_tickets.setTextColor(getColor(R.color.black));
                tv_records.setTextColor(getColor(R.color.black));

                switchFragment(new EventsFragment());
            }
        });
        tv_tickets.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tv_events.setTextColor(getColor(R.color.black));
                tv_tickets.setTextColor(getColor(R.color.red));
                tv_records.setTextColor(getColor(R.color.black));
                switchFragment(new TicketsFragment());
            }
        });
        tv_records.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                tv_events.setTextColor(getColor(R.color.black));
                tv_tickets.setTextColor(getColor(R.color.black));
                tv_records.setTextColor(getColor(R.color.red));
                switchFragment(new RecordsFragment());
            }
        });

        Intent intent = getIntent();
        String fragmentName = intent.getStringExtra("fragmentName");

        if (fragmentName != null) {
            switch (fragmentName) {
                case "addNewTicketFragment":
                    tv_tickets.setTextColor(getColor(R.color.red));
                    switchFragment(new AddNewTicketFragment());
                    break;

                case "ticketDetailFragment":

                    Ticket ticket = (Ticket) intent.getSerializableExtra("ticket");
                    tv_tickets.setTextColor(getColor(R.color.red));
                    switchFragment(new TicketDetailFragment(ticket));

                    break;
            }
        }

    }

    private void switchFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.content, fragment);
        transaction.addToBackStack(null); // Optional: add to back stack
        transaction.commit();
    }
}