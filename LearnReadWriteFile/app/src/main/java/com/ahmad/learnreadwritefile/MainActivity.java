package com.ahmad.learnreadwritefile;

import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.ahmad.learnreadwritefile.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.buttonNew.setOnClickListener(view -> newFile());
        binding.buttonOpen.setOnClickListener(view -> showList());
        binding.buttonSave.setOnClickListener(view -> saveFile());
    }

    private void newFile() {
        binding.editTitle.setText("");
        binding.editFile.setText("");
        Toast.makeText(this, "Clearing file", Toast.LENGTH_SHORT).show();
    }

    private void showList() {
        final CharSequence[] items = fileList();
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Pilih file yang diinginkan");
        builder.setItems(items, (dialog, item) -> loadData(items[item].toString()));
        AlertDialog alert = builder.create();
        alert.show();
    }

    private void loadData(String title) {
        FileModel fileModel = FileHelper.readFromFile(this, title);
        binding.editTitle.setText(fileModel.getFilename());
        binding.editFile.setText(fileModel.getData());
        Toast.makeText(this, "Loading " + fileModel.getFilename() + " data", Toast.LENGTH_SHORT).show();
    }

    private void saveFile() {
        if (binding.editTitle.getText().toString().isEmpty()) {
            Toast.makeText(this, "Title harus diisi terlebih dahulu", Toast.LENGTH_SHORT).show();
        } else if (binding.editFile.getText().toString().isEmpty()) {
            Toast.makeText(this, "Kontent harus diisi terlebih dahulu", Toast.LENGTH_SHORT).show();
        } else {
            String title = binding.editTitle.getText().toString();
            String text = binding.editFile.getText().toString();
            FileModel fileModel = new FileModel();
            fileModel.setFilename(title);
            fileModel.setData(text);
            FileHelper.writeToFile(fileModel, this);
            Toast.makeText(this, "Saving " + fileModel.getFilename() + " file", Toast.LENGTH_SHORT).show();
        }
    }

}