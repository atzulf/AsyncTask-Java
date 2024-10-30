package com.example.asynctask;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnStart = findViewById(R.id.btn_start);
        TextView tvStatus = findViewById(R.id.tv_status);
        ProgressBar progressBar = findViewById(R.id.progressBar);
        ImageView ivCompletedImage = findViewById(R.id.iv_completed_image);  // Tambahkan ImageView

        ExecutorService executor = Executors.newSingleThreadExecutor();
        Handler handler = new Handler(Looper.getMainLooper());

        btnStart.setOnClickListener(v -> {
            // Set gambar menjadi GONE setiap kali tombol ditekan
            ivCompletedImage.setVisibility(View.GONE);
            progressBar.setVisibility(View.VISIBLE);
            tvStatus.setText(R.string.compressing);  // Set teks awal status

            executor.execute(() -> {
                try {
                    for (int i = 0; i <= 10; i++) {
                        Thread.sleep(500);
                        int percentage = i * 10;

                        handler.post(() -> {
                            tvStatus.setText(String.format(getString(R.string.compressing), percentage));
                            progressBar.setProgress(percentage);
                        });
                    }

                    // Setelah task selesai
                    handler.post(() -> {
                        tvStatus.setText(R.string.task_completed);  // Update teks status
                        progressBar.setVisibility(View.GONE);        // Sembunyikan ProgressBar
                        ivCompletedImage.setVisibility(View.VISIBLE); // Tampilkan gambar
                    });

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        });
    }

}