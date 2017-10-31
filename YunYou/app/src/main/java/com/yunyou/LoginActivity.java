package com.yunyou;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class LoginActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private EditText mobile;
    private EditText zone;
    private EditText pass_word;
    private Button login;
    private final OkHttpClient client = new OkHttpClient();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        login=(Button)findViewById(R.id.login);
        mobile = (EditText) findViewById(R.id.mobile);
        zone = (EditText) findViewById(R.id.zone);
        pass_word= (EditText) findViewById(R.id.password);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                switch (v.getId()){
                    case R.id.login:
                        new AnsyTry().execute(mobile.getText().toString(), zone.getText().toString(), pass_word.getText().toString());
                        break;
                }
            }
        });
    }

    //点击跳转到注册页面
    public void reg(View view) {
        startActivityForResult(new Intent(this, RegisterActivity.class), 0);
    }

    private class AnsyTry extends AsyncTask<String, String, String> {
        @Override
        protected String doInBackground(String... params) {
            // TODO Auto-generated method stub
            try {
                RequestBody formBody = new FormBody.Builder()
                        .add("mobile", params[0])
                        .add("zone", params[1])
                        .add("pass_word", params[2])
                        .build();
                Request request = new Request.Builder()
                        .url("http://47.95.8.136/login")
                        .post(formBody)
                        .build();
                Response response = client.newCall(request).execute();
                if(response.isSuccessful()){
                    return response.body().string();
                }
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            // TODO Auto-generated method stub
            super.onPostExecute(result);
            //TODO 此处判断返回值
            Intent intent = new Intent();
            intent.setClass(LoginActivity.this, HomePageFragment.class);
            startActivity(intent);
            finish();
        }



/*   @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.register:
                Intent intent = new Intent(LoginActivity.this,RegisterActivity.class);
                startActivity(intent);
                break;
            case R.id.login_bt:
                login();
                break;
            case R.id.login_modify_bt:
                Intent intent2 = new Intent(LoginActivity.this,ModifyPasswordActivity.class);
                startActivity(intent2);
                break;
            case R.id.visitor:
                Intent intent3 = new Intent(LoginActivity.this,MainActivity.class);
                startActivity(intent3);
                finish();
                break;
        }
    }*/


        //点击跳转到主页面
   // public void homepage(View view) {
       /* String currentUsername = user_phoneEditText.getText().toString().trim();
        String currentPassword = passwordEditText.getText().toString().trim();
        startActivityForResult(new Intent(this, MainActivity.class), 0);
   */
        /*final String user_phone = mobile.getText().toString().trim();
        final String pwd = pass_word.getText().toString().trim();

        if (TextUtils.isEmpty(user_phone)) {
            Toast.makeText(this, "请输入手机号", Toast.LENGTH_SHORT).show();
            mobile.requestFocus();
            return;
        } else if (TextUtils.isEmpty(pwd)) {
            Toast.makeText(this, "密码不能为空", Toast.LENGTH_SHORT).show();
            pass_word.requestFocus();
            return;
        }*/ /*else if (TextUtils.isEmpty(confirm_pwd)) {
            Toast.makeText(this, "请确认密码", Toast.LENGTH_SHORT).show();
            confirmPwdEditText.requestFocus();
            return;
        } else if (!pwd.equals(confirm_pwd)) {
            Toast.makeText(this, "两次输入的密码不相同", Toast.LENGTH_SHORT).show();
            return;
        }*/

       /* new Thread() {
            @Override
            public void run() {
                HttpUtils httpUtils = new HttpUtils();
                //转换为JSON
                String user = httpUtils.bolwingJson(user_phone, pwd);
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
        }.start();*/
    }
}
