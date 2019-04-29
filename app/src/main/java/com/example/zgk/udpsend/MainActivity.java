package com.example.zgk.udpsend;

import android.app.Person;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import org.json.JSONObject;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Hashtable;

public class MainActivity extends AppCompatActivity {


    EditText tvServiceMsg, IP;
    TextView msgShow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvServiceMsg = findViewById(R.id.tvServiceMsg);
        msgShow = findViewById(R.id.msgShow);
        IP = findViewById(R.id.IP);
        msgShow.setMovementMethod(ScrollingMovementMethod.getInstance());
        findViewById(R.id.send).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                sendMsg();
            }
        });

    }
    private  Gson gson = new Gson();
    private DatagramSocket sendSocket = null;
    private int sendPort = 8856;//发送端口
    private void sendMsg(float x, float y) {
        try {
            if (sendSocket == null) {
                sendSocket = new DatagramSocket(sendPort);
            }
            InetAddress inetAddress = InetAddress.getByName(IP.getText().toString());
//            byte[] bytes = tvServiceMsg.getText().toString().getBytes();
            String jsno = getJsonStr(x,y);
            byte[] bytes = jsno.getBytes();
            DatagramPacket datagramPacket = new DatagramPacket(bytes, bytes.length, inetAddress, sendPort);
            sendSocket.send(datagramPacket);
            msgShow.setText("点击的坐标" + x + y);
        } catch (SocketException e) {
            e.printStackTrace();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    public String getJsonStr(float x, float y) {
        Data p = new Data();
        p.setX(x);
        p.setY(y);
        return gson.toJson(p);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        msgShow.setText("点击位置的X坐标" + ev.getX() + "点击位置的Y坐标" + ev.getY());
        switch (ev.getAction()) {
            /**
             * 点击的开始位置
             */
            case MotionEvent.ACTION_DOWN:
                msgShow.setText("起始位置：(" + ev.getX() + "," + ev.getY());
                break;
            /**
             * 触屏实时位置
             */
            case MotionEvent.ACTION_MOVE:
                msgShow.setText("实时位置：(" + ev.getX() + "," + ev.getY());
                break;
            /**
             * 离开屏幕的位置
             */
            case MotionEvent.ACTION_UP:
                msgShow.setText("结束位置：(" + ev.getX() + "," + ev.getY());
                sendMsg(ev.getX(), ev.getY());
                break;
            default:
                break;
        }
        /**
         *  注意返回值
         *  true：view继续响应Touch操作；
         *  false：view不再响应Touch操作，故此处若为false，只能显示起始位置，不能显示实时位置和结束位置
         */
        return super.dispatchTouchEvent(ev);
    }
}
