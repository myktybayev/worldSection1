package kz.project.navigation.ui.tickets;

import static android.app.Activity.RESULT_OK;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Random;

import kz.project.navigation.R;

public class AddNewTicketFragment extends Fragment {
    Button choosePicture, create;
    private static final int PICK_IMAGE_REQUEST = 1;
    ImageView preview_image;
    TextView preview_text;
    EditText et_name;
    Spinner spinner;
    View root;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        root =  inflater.inflate(R.layout.activity_add_new_ticket, container, false);

        spinner = root.findViewById(R.id.spinner);
        et_name = root.findViewById(R.id.et_name);
        choosePicture = root.findViewById(R.id.choosePicture);
        create = root.findViewById(R.id.create);
        preview_image = root.findViewById(R.id.preview_image);
        preview_text = root.findViewById(R.id.preview_text);

        choosePicture.setOnClickListener(view -> {
            Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(intent, PICK_IMAGE_REQUEST);
        });

        create.setOnClickListener(view -> {
            String time = new SimpleDateFormat("yyyy-MM-dd hh:mm", Locale.getDefault()).format(new Date());

            String abc = "ABC";
            int a = new Random().nextInt(3);

            int n1 = new Random().nextInt(10);
            int n2 = new Random().nextInt(10);
            int n3 = new Random().nextInt(10);

            String seat = abc.charAt(a)+""+n1+" Row"+n2+" Column"+n3;

            if(imageUri != null) {
                Ticket ticket = new Ticket(imageUri.toString(), et_name.getText().toString(), time, seat, spinner.toString());

                //Save to Database
            }
        });

        return root;
    }

    Uri imageUri;

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null) {
            imageUri = data.getData();
            preview_image.setImageURI(imageUri);
            preview_text.setText(" ");

            // You now have the URI of the selected image, and you can use it as needed.
        }
    }
}