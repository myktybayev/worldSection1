package kz.project.navigation.ui.records;

import android.Manifest;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import kz.project.navigation.R;
import kz.project.navigation.ui.events.ItemClick;

public class AudioRecordPlayFragment extends Fragment implements ItemClick {

    MediaRecorder mediaRecorder;
    MediaPlayer mediaPlayer;
    String audioFilePath;
    Button recordButton;
    Button playButton, btn_submit;
    boolean isRecording = false;
    boolean permissionToRecordAccepted = false;
    boolean permissionToStoreAccepted = false;
    String[] permissions = {Manifest.permission.RECORD_AUDIO, Manifest.permission.WRITE_EXTERNAL_STORAGE};
    List<String> audioFiles = new ArrayList<>();
    RecyclerView recyclerView;
    AudioListAdapter audioListAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_records, container, false);

        btn_submit = view.findViewById(R.id.btn_submit);
        btn_submit.setEnabled(false);

        recordButton = view.findViewById(R.id.btn_voice_record);
        playButton = view.findViewById(R.id.btn_voice_play);
        recyclerView = view.findViewById(R.id.recyclerView);
        audioListAdapter = new AudioListAdapter(getActivity(), audioFiles, this::onItemClick);
        recyclerView.setAdapter(audioListAdapter);

        recordButton.setOnClickListener(v -> {

            if (ContextCompat.checkSelfPermission(getActivity(), permissions[0]) != PackageManager.PERMISSION_GRANTED ||
                    ContextCompat.checkSelfPermission(getActivity(), permissions[1]) != PackageManager.PERMISSION_GRANTED) {

                ActivityCompat.requestPermissions(getActivity(), permissions, 200);

            } else {
                if (isRecording) {
                    stopRecording();
                    recordButton.setText("Start Recording");
                    playButton.setEnabled(true);

                } else {

                    startRecording();
                    recordButton.setText("Stop Recording");
                    playButton.setEnabled(false);
                }

                isRecording = !isRecording;
            }
        });

        playButton.setOnClickListener(v -> playAudio());

        btn_submit.setOnClickListener(view1 -> {
            audioFiles.add(audioFilePath);
            audioListAdapter.notifyDataSetChanged();
            Toast.makeText(getActivity(), "Отправить успешно", Toast.LENGTH_SHORT).show();
        });

        return view;
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == 200) {
            permissionToRecordAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
            permissionToStoreAccepted = grantResults[1] == PackageManager.PERMISSION_GRANTED;
        }

        if (!permissionToRecordAccepted || !permissionToStoreAccepted) {
            Toast.makeText(getActivity(), "Permissions not granted", Toast.LENGTH_SHORT).show();
        }
    }

    private void startRecording() {

        String uniqueID = new SimpleDateFormat("HHmmss").format(Calendar.getInstance().getTime());
        audioFilePath = getActivity().getExternalFilesDir(null).getAbsolutePath() + "/myaudio" + uniqueID + ".3gp";

        try {
            mediaRecorder = new MediaRecorder();
            mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
            mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
            mediaRecorder.setOutputFile(audioFilePath);
            mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
            mediaRecorder.prepare();

            mediaRecorder.start();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void stopRecording() {
        if (mediaRecorder != null) {
            mediaRecorder.stop();
            mediaRecorder.release();
            mediaRecorder = null;

            btn_submit.setEnabled(true);
        }
    }

    @Override
    public void onItemClick(int pos) {
        audioFilePath = audioFiles.get(pos);
        playAudio();
    }

    private void playAudio() {
        try {

            mediaPlayer = new MediaPlayer();
            mediaPlayer.setDataSource(audioFilePath);
            mediaPlayer.prepare();
            mediaPlayer.start();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}