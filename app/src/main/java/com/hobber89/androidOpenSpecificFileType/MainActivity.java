package com.hobber89.androidOpenSpecificFileType;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.DocumentsContract;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private final int ShowContentActivityRequestId = 1;
    private final int LoadFromFileActivityRequestId = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.createExampleFileButton) {
            createExampleFile();
        }
        else if(view.getId() == R.id.loadExampleFileButton) {
            loadExampleFile();
        }
        else if(view.getId() == R.id.loadFileButton) {
            loadFile();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode != RESULT_OK)
            return;

        if(requestCode == LoadFromFileActivityRequestId) {
            Uri uri = data.getData();
            String path = uri.getPath();

            //This works at least for the example file when it is copied to Download directory
            // and is loaded from there (original test file is not visible!)
            if(path.startsWith("/root/"))
                path = path.substring(5);

            File file = new File(path);
            loadFile(file);
        }
    }

    private void createExampleFile() {
        try {
            FileContentModel exampleFileContent = new FileContentModel("This is an example file");
            String jsonExampleFileContent = exampleFileContent.toJson().toString();
            File storageDirectory = getExternalFilesDir(null);
            File file = new File(storageDirectory, "exampleFile.test");

            if (!file.exists())
                file.createNewFile();
            else {
                Toast.makeText(this, R.string.infoMessageExampleFileAlreadyExists, Toast.LENGTH_LONG).show();
                return;
            }

            BufferedWriter writer = new BufferedWriter(new FileWriter(file));
            writer.write(jsonExampleFileContent);
            writer.close();
            Toast.makeText(this, R.string.infoMessageExampleFileWasCreated, Toast.LENGTH_LONG).show();
        } catch (IOException e) {
            Toast.makeText(this, R.string.errorMessageFailedToCreateExampleFile, Toast.LENGTH_LONG).show();
        }
    }

    private void loadExampleFile() {
        File storageDirectory = getExternalFilesDir(null);
        File file = new File(storageDirectory, "exampleFile.test");
        if (!file.exists()) {
            Toast.makeText(this, R.string.errorMessageExampleFileDoesNotExist, Toast.LENGTH_LONG).show();
            return;
        }

        loadFile(file);
    }

    private void loadFile() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("*/*.test");
        Uri pickerInitialUri = Uri.fromFile(getExternalFilesDir(null));
        intent.putExtra(DocumentsContract.EXTRA_INITIAL_URI, pickerInitialUri);
        startActivityForResult(intent, LoadFromFileActivityRequestId);
    }

    private void loadFile(File file) {
        if (file != null) {
            StringBuilder stringBuilder = new StringBuilder();
            BufferedReader reader;
            try {
                reader = new BufferedReader(new FileReader(file));
                String line;
                while ((line = reader.readLine()) != null) {
                    stringBuilder.append(line);
                }
                reader.close();
                String jsonContent = stringBuilder.toString();

                FileContentModel content = new Gson().fromJson(jsonContent, FileContentModel.class);

                Intent intent = new Intent(this, ShowContentActivity.class);
                intent.putExtra("content", content);
                startActivityForResult(intent, ShowContentActivityRequestId);

            } catch (Exception e) {
                Toast.makeText(this, R.string.errorMessageFailedToLoadFile, Toast.LENGTH_LONG).show();
            }
        }
    }
}