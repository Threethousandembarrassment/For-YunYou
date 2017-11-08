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

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static android.widget.Toast.makeText;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener  {
    private static final String TAG = "MainActivity";
    private EditText edmobile;
    private EditText edpass_word;
    private Button login;
    private TextView mTvResult;
    private String url ="http://47.95.8.136/login";
    private String mobile,pass_word;
    private final OkHttpClient client = new OkHttpClient();
    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        login=(Button)findViewById(R.id.login);
        edmobile = (EditText) findViewById(R.id.mobile);
        edpass_word= (EditText) findViewById(R.id.pass_word);
        mTvResult = (TextView) findViewById(R.id.login_tv_result);
        login.setOnClickListener(this);
    }

    public String login(String url, String json) throws IOException {
        //把请求的内容字符串转换为json
        RequestBody body = RequestBody.create(JSON, json);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        Response response = client.newCall(request).execute();
        String result = response.body().string();
        return result;

    }

    public String bolwingJson(String mobile,String zone, String pass_word) {
        return "{'mobile':" + mobile + "," + "'zone':"+ zone + "," + "'pass_word':"+ pass_word + "}";
    }

    //点击跳转到注册页面
    public void reg(View view) {
        startActivityForResult(new Intent(this, RegisterActivity.class), 0);
    }

    /**
     * 设置监听器
     */
   /* private void initListener() {
       login.setOnClickListener(this);
    }
*/
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
        mobile = edmobile.getText().toString().trim();
        pass_word = edpass_word.getText().toString().trim();
        if(TextUtils.isEmpty(mobile) || TextUtils.isEmpty(pass_word)){
            makeText(LoginActivity.this, "用户名或者密码不能为空", Toast.LENGTH_SHORT).show();
            return;
        }

        new Thread() {
            @Override
            public void run() {
                //转换为JSON
                String user = bolwingJson(mobile,"86", pass_word);
                Log.d(TAG, "user:" + user);
                try {
                    final String result = login(url, user);
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
