package com.yunyou;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.yunyou.util.HttpUtils;

import java.io.IOException;

import okhttp3.OkHttpClient;

import static android.widget.Toast.makeText;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener  {
    private static final String TAG = "MainActivity";
    private EditText mobile;
    private EditText zone;
    private EditText pass_word;
    private Button login;
    private TextView mTvResult;
    private String url ="http://47.95.8.136/login";
    private String mobile1,zone1,pass_word1;
    private final OkHttpClient client = new OkHttpClient();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        login=(Button)findViewById(R.id.login);
        mobile = (EditText) findViewById(R.id.mobile);
        zone = (EditText) findViewById(R.id.zone);
        pass_word= (EditText) findViewById(R.id.pass_word);
        mTvResult = (TextView) findViewById(R.id.login_tv_result);
        initListener();
    }

    //点击跳转到注册页面
    public void reg(View view) {
        startActivityForResult(new Intent(this, RegisterActivity.class), 0);
    }

    /**
     * 设置监听器
     */
    private void initListener() {
       login.setOnClickListener(this);
    }

    /*
单击事件监听
 */
    @Override
    public void onClick(View v) {
        if(v==login){
            login();
        }
    }

    /*
 登录
  */
    private void login() {
       mobile1 = mobile.getText().toString().trim();
       zone1 = zone.getText().toString().trim();
        pass_word1 = pass_word.getText().toString().trim();
        if(TextUtils.isEmpty(mobile1) || TextUtils.isEmpty(pass_word1)){
            makeText(LoginActivity.this, "用户名或者密码不能为空", Toast.LENGTH_SHORT).show();
            return;
        }

        new Thread() {
            @Override
            public void run() {
                HttpUtils httpUtils = new HttpUtils();
                //转换为JSON
                String user = httpUtils.bolwingJson(mobile1,zone1, pass_word1);
                Log.d(TAG, "user:" + user);
                try {
                    final String result = httpUtils.login(url, user);
                    Log.d(TAG, "结果:" + result);
                    //更新UI,在UI线程中
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if ("SUCCESS".equals(result)) {
                                 mTvResult.setText("登录成功");
                            } else {
                                 mTvResult.setText("登录失败");
                            }
                        }
                    });
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }
}
