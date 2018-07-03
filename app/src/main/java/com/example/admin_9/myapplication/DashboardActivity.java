package com.example.admin_9.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;

public class DashboardActivity extends AppCompatActivity {


    private OkHttpClient client=new OkHttpClient();
    WebSocket ws;
    private TextView output;
    String data=" ";
    int count=0;
    private Toolbar mTopToolbar;
    FrameLayout frameLayout;


    private final class EchoWebSocketListener extends WebSocketListener
    {
        private static final int NORMAL_CLOSURE_STATUS = 1000;

        @Override
        public void onMessage(WebSocket webSocket, final String text)
        {
            count++;
            Log.d("msg","count="+count);

            DashboardActivity.this.runOnUiThread(new Runnable() {
                @Override
                public void run()
                {
                    TextView notification_count=findViewById(R.id.notification_count);
                    Log.d("set",notification_count.toString());
                    notification_count.setText(String.valueOf(count));
                }
            });
            data+=text+"\n";
        }

        @Override
        public void onClosing(WebSocket webSocket, int code, String reason)
        {
            webSocket.close(NORMAL_CLOSURE_STATUS, null);
            Log.d("close","Closing : " + code + " / " + reason);
        }

        @Override
        public void onFailure(WebSocket webSocket, Throwable t, Response response) {
            Log.d("error","Error : " + t.getMessage());
        }
    }

   /* @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.dashboard_menu, menu);
        return true;
    }*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        /*mTopToolbar = (Toolbar) findViewById(R.id.toolbar1);
        setSupportActionBar(mTopToolbar);
*/
        Request request = new Request.Builder().url("ws://echo.websocket.org").build();
        EchoWebSocketListener listener = new EchoWebSocketListener();
        ws = client.newWebSocket(request, listener);
        ws.send("Welcome to the app");
        ws.send("Thanks for using this app");

        TextView notification_count=findViewById(R.id.notification_count);
        notification_count.setText(String.valueOf(count));

        output=findViewById(R.id.output);
        ImageView button=findViewById(R.id.bellbutton);
        button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {
                output.setText(data);
                /*frameLayout=findViewById(R.id.frame);
                frameLayout.setVisibility(View.INVISIBLE);*/

            }
        });
    }
}
