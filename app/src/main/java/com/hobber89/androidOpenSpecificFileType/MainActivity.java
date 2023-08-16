package com.hobber89.androidOpenSpecificFileType;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import java.io.BufferedWriter;
import java.io.File;
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
}