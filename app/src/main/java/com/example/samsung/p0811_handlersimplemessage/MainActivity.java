package com.example.samsung.p0811_handlersimplemessage;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {

    final String LOG_TAG = "myLogs";

    final int STATUS_NONE = 0,      //Нет подключения
              STATUS_CONNECTING = 1,//подключаемся
              STATUS_CONNECTED = 2; //подключено

    Handler handler;

    Button btnConnect;
    TextView tvStatus;
    ProgressBar pbConnect;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvStatus = (TextView) findViewById(R.id.tvStatus);
        pbConnect = (ProgressBar) findViewById(R.id.pbConnect);
        btnConnect = (Button) findViewById(R.id.btnConnect);

        handler = new Handler() {
            @Override
            public void handleMessage(android.os.Message msg) {

                switch (msg.what) {

                    case STATUS_NONE :
                        btnConnect.setEnabled(true);
                        tvStatus.setText("Not connected");
                        break;
                    case STATUS_CONNECTING:
                        btnConnect.setEnabled(false);
                        pbConnect.setVisibility(View.VISIBLE);
                        tvStatus.setText("Connecting");
                        break;
                    case STATUS_CONNECTED :
                        pbConnect.setVisibility(View.GONE);
                        tvStatus.setText("Connected");
                        break;

                }

            }
        };
        handler.sendEmptyMessage(STATUS_NONE);
    }

    public void onClickButton(View view) {

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    //Эмуляция процесса подключения
                    handler.sendEmptyMessage(STATUS_CONNECTING);
                    TimeUnit.SECONDS.sleep(2);
                    //Эмуляция установленного подключения
                    handler.sendEmptyMessage(STATUS_CONNECTED);
                    //Эмуляция выполнения какой-то работы
                    TimeUnit.SECONDS.sleep(3);
                    //Эмуляция разрыва подключения
                    handler.sendEmptyMessage(STATUS_NONE);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();

    }
}
