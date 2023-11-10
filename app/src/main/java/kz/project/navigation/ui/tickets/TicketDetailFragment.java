package kz.project.navigation.ui.tickets;

import static androidx.core.content.PermissionChecker.checkSelfPermission;

import android.Manifest;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.PermissionChecker;
import androidx.fragment.app.Fragment;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import kz.project.navigation.R;

public class TicketDetailFragment extends Fragment {
    Button btn_download;
    final int rCode = 138;
    View view;
    TextView img_path;
    TextView tType;
    TextView tName;
    TextView tTime;
    TextView tSeat;

    Ticket ticket;
    String seat = "";
    public TicketDetailFragment(Ticket ticket) {
        this.ticket = ticket;
    }

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.activity_ticket_detail, container, false);

        btn_download = view.findViewById(R.id.btn_download);
        img_path = view.findViewById(R.id.img_path);
        tType = view.findViewById(R.id.tType);
        tName = view.findViewById(R.id.tName);
        tTime = view.findViewById(R.id.tTime);
        tSeat = view.findViewById(R.id.tSeat);

        tType.setText("Ticket type: "+ticket.getCeremonyType());
        tName.setText("Audience’s name: "+ticket.getName());
        tTime.setText("Time: "+ticket.getTime());
        tSeat.setText("Seat: "+ticket.getSeat());

        btn_download.setOnClickListener(view -> {
            Toast.makeText(getActivity(), "Download", Toast.LENGTH_SHORT).show();

            savebitmap();

            if (ActivityCompat.checkSelfPermission(getContext(),
                    android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                    ActivityCompat.checkSelfPermission(getContext(),
                            android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(
                        new String[]{android.Manifest.permission.ACCESS_COARSE_LOCATION,
                                android.Manifest.permission.ACCESS_FINE_LOCATION},
                        103);
            } else {
                savebitmap();
            }
        });
        return view;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == rCode) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                savebitmap();
            } else {
                Toast.makeText(getActivity(), "Permission denied. Cannot write to external storage.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void savebitmap() {
        View imgView = getActivity().getWindow().getDecorView().getRootView();
        Bitmap bitmap = Bitmap.createBitmap(imgView.getWidth(), imgView.getHeight() / 2 + 180, Bitmap.Config.RGB_565);
        Canvas canvas = new Canvas(bitmap);
        imgView.draw(canvas);

        ContextWrapper cw = new ContextWrapper(getContext());
        File directory = cw.getDir("imageDir", Context.MODE_PRIVATE);

        String date = new SimpleDateFormat("yyyyMMddhhmmss", Locale.getDefault()).format(new Date());
        File file = new File(directory, "tickets" + date + ".jpg");

        if (!file.exists()) {
            Log.d("path", file.toString());
            img_path.setText(file.toString());
            FileOutputStream fos = null;
            try {
                fos = new FileOutputStream(file);
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
                fos.flush();
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}