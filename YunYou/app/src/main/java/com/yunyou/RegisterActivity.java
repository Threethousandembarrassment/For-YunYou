package com.yunyou;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


public class RegisterActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private EditText mobile,code,pass_word,seq,beinvited_uid;
    private String mobile1,zone1,code1,pass_word1,seq1;
    private int beinvited_uid1,timestamp1;
    private TextView text1;
    private Button reg;
    final OkHttpClient client = new OkHttpClient();

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 1) {
                String qq = (String) msg.obj;
                Log.i("WUJIE", qq);
                text1.setText(qq);
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        /**
         * 初始化数据
         */
        mobile = (EditText) findViewById(R.id.mobile);
        code = (EditText) findViewById(R.id.code);
        pass_word = (EditText) findViewById(R.id.pass_word);
        seq = (EditText) findViewById(R.id.seq );
        beinvited_uid = (EditText) findViewById(R.id. beinvited_uid);

        reg = (Button) findViewById(R.id.reg);
        text1 = (TextView) findViewById(R.id.textview);

        /**
         * 注册按钮监听
         */

        reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //获取相关参数
                mobile1 = mobile.getText().toString().trim();
                zone1 = "86";
                code1 = code.getText().toString().trim();
                pass_word1 = pass_word.getText().toString().trim();
                seq1 =seq.getText().toString().trim();
                timestamp1= (int) System.currentTimeMillis();
                beinvited_uid1 = Integer.parseInt(beinvited_uid.getText().toString().trim());
                //通过okhttp发起post请求
                postRequest(mobile1,zone1,code1,pass_word1,seq1,timestamp1,beinvited_uid1);
            }
        });
    }

    /**
     * post请求后台
     */
    private void postRequest(String mobile, String zone, String code, String pass_word, String seq, int timestamp, int beinvited_uid) {
        //建立请求表单，添加上传服务器的参数
        RequestBody formBody = new FormBody.Builder()
                .add("mobile", mobile)
                .add("zone", zone)
                .add("code",code)
                .add("pass_word", pass_word)
                .add("seq",seq)
                .add("timestamp", timestamp+"")
                .add("beinvited_uid", beinvited_uid+"")
                .add("method", "okhttpreg")
                .build();
        //发起请求
        final Request request = new Request.Builder()
                .url("http://47.95.8.136/register")
                .post(formBody)
                .build();
        //新建一个线程，用于得到服务器响应的参数
        new Thread(new Runnable() {
            @Override
            public void run() {
                Response response = null;
                try {
                    //回调
                    response = client.newCall(request).execute();
                    if (response.isSuccessful()) {
                        //将服务器响应的参数response.body().string())发送到hanlder中，并更新ui
                        mHandler.obtainMessage(1, response.body().string()).sendToTarget();
                    } else {
                        throw new IOException("Unexpected code:" + response);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
        }
}
