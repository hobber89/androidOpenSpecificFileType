package com.hobber89.androidOpenSpecificFileType;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
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
                startActivityForResult(intent, 0);

            } catch (Exception e) {
                Toast.makeText(this, R.string.errorMessageFailedToLoadFile, Toast.LENGTH_LONG).show();
            }
        }
    }
}