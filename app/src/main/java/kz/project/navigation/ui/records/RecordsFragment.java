package kz.project.navigation.ui.records;

import android.Manifest;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import kz.project.navigation.R;

public class RecordsFragment extends Fragment {
    View view;
    Button startButton;
    Button stopButton;
    Button btn_submit;
    private MediaRecorder mediaRecorder;
    private MediaPlayer mediaPlayer;
    private String fileName;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_records, container, false);
        startButton = view.findViewById(R.id.btn_voice_record);
        stopButton = view.findViewById(R.id.btn_voice_play);
        btn_submit = view.findViewById(R.id.btn_submit);

        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

                    Log.i("record", "requestPermissions");

                    requestPermissions( //Method of Fragment
                            new String[]{Manifest.permission.RECORD_AUDIO,
                                    Manifest.permission.WRITE_EXTERNAL_STORAGE},
                            138
                    );
                } else {
                    recordStart();
                }
            }
        });

        stopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                recordStop();
            }
        });

        return view;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 138) {
            if (permissions[0].equals(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                recordStart();
            }
        }
    }

    public void recordStart() {
        try {
            Log.i("record", "recordStart");
            releaseRecorder();

            /*
            fileName = Environment.getExternalStorageDirectory() + "/Recordings/record.3gpp";
            File outFile = new File(fileName);

            String dirPath = context.getExternalFilesDir(null).getAbsolutePath();
            String filePath = direPath + "/recording";
            audioFile = new File(filePath);

             */
            
            ContextWrapper cw = new ContextWrapper(getContext());
            File musicDirectory = cw.getExternalFilesDir(Environment.DIRECTORY_MUSIC);

//            if (!outFile.exists()) {
//                outFile.mkdir();
//            }

            mediaRecorder = new MediaRecorder();
            mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
            mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
            mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
            mediaRecorder.setOutputFile(musicDirectory);
            mediaRecorder.prepare();
            mediaRecorder.start();

            Log.i("record", "fileName: "+fileName.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void recordStop() {

        Log.i("record", "recordStop");
        if (mediaRecorder != null) {
            mediaRecorder.stop();
        }
    }

    public void playStart() {
        try {
            releasePlayer();
            mediaPlayer = new MediaPlayer();
            mediaPlayer.setDataSource(fileName);
            mediaPlayer.prepare();
            mediaPlayer.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void playStop(View v) {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
        }
    }

    private void releaseRecorder() {
        if (mediaRecorder != null) {
            mediaRecorder.release();
            mediaRecorder = null;
        }
    }

    private void releasePlayer() {
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }
}