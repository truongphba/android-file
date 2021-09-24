package com.example.file;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.tbruyelle.rxpermissions2.RxPermissions;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    public final static String FILE_NAME = "note.txt";
    public final static String FILE_PATH = "/sdcard/Demo/";

    EditText edText;
    Button btSave;
    Button btRead;
    RadioGroup radGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        edText = findViewById(R.id.edText);
        btSave = findViewById(R.id.btSave);
        btRead = findViewById(R.id.btRead);
        radGroup = findViewById(R.id.radGroup);

        btSave.setOnClickListener(this);
        btRead.setOnClickListener(this);
    }
//    private void permission() {
//        final RxPermissions rxPermissions = new RxPermissions(this);
//        rxPermissions
//                .request(Manifest.permission.WRITE_EXTERNAL_STORAGE)
//                .subscribe(granted -> {
//                    if (granted) { // Always true pre-M
//                        // I can control the camera now
//                    } else {
//                        // Oups permission denied
//                    }
//                });
//    }
    private void onSaveInternal() {
        try {
            FileOutputStream fos = openFileOutput(FILE_NAME, MODE_PRIVATE);
            fos.write(edText.getText().toString().getBytes());
            fos.flush();
            fos.close();
            Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void onReadInternal() {
        try {
            FileInputStream fis = openFileInput(FILE_NAME);
            BufferedReader reader = new BufferedReader(new InputStreamReader(fis));
            StringBuffer sb = new StringBuffer();
            String line = reader.readLine();
            while (line != null) {
                sb.append(line);
                line = reader.readLine();
            }
            Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void onReadExternal() {
        File file = new File(FILE_PATH + FILE_NAME);
        try {
            FileInputStream fis = new FileInputStream(file);
            BufferedReader reader = new BufferedReader(new InputStreamReader(fis));
            StringBuffer sb = new StringBuffer();
            String line = reader.readLine();
            while (line != null) {
                sb.append(line);
                line = reader.readLine();
            }
            Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void onSaveExternal() {
        File directory = new File(FILE_PATH);
        File file = new File(FILE_PATH + FILE_NAME);
        try {
            if (!directory.exists()) {
                directory.mkdir();
            }
            file.createNewFile();
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(edText.getText().toString().getBytes());
            fos.flush();
            fos.close();
            Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View view) {
        int idChecked = radGroup.getCheckedRadioButtonId();
        switch (view.getId()) {
            case R.id.btSave:
                if (idChecked == R.id.radInternal) {
                    onSaveInternal();
                } else {
                    onSaveExternal();
                }
                break;
            case R.id.btRead:
                if (idChecked == R.id.radInternal) {
                    onReadInternal();
                } else {
                    onReadExternal();
                }
            default:
                break;
        }
    }
}