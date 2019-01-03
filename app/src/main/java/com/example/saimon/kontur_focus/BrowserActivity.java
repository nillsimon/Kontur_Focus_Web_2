package com.example.saimon.kontur_focus;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.TextView;

public class BrowserActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_browser);


        final WebView webView = findViewById(R.id.browse);
        final TextView url = findViewById(R.id.url);
        final TextView status = findViewById(R.id.status);
        Button okBtn = findViewById(R.id.okBtn);
        // webView.loadUrl(url.getText().toString());
        // Создаем объект класса делателя запросов и на лету делаем анонимный класс слушателя
        final RequestMaker requestMaker = new RequestMaker(new RequestMaker.OnRequestListener() {
            // Обновим прогресс
            @Override
            public void onStatusProgress(String updateProgress) {
                status.setText(updateProgress);
            }
            // По окончании загрузки страницы вызовем этот метод, который и вставит текст в WebView
            @Override
            public void onComplete(String result) {
                webView.loadData(result, "text/html; charset=utf-8", "utf-8");
            }
        });

        // При нажатии на кнопку отправим запрос
        okBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Запрос - сделать!
                requestMaker.make(url.getText().toString());
            }
        });
    }
}
