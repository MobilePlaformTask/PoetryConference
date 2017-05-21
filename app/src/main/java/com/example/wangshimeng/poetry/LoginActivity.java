package com.example.wangshimeng.poetry;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.LogInCallback;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        if(AVUser.getCurrentUser()!=null){
            Intent intent = new Intent();
            intent.setClass(LoginActivity.this, HomeActivity.class);
            LoginActivity.this.startActivity(intent);
        }

    }
    public void Login(View view){

        EditText edtName=(EditText)findViewById(R.id.edtName);
        EditText edtPassword=(EditText)findViewById(R.id.edtLoginPassword);
        //EditText edtEmail=(EditText)findViewById(R.id.edtName);

        AVUser.logInInBackground(edtName.getText().toString(), edtPassword.getText().toString(), new LogInCallback<AVUser>() {
            @Override
            public void done(AVUser avUser, AVException e) {

                if (e == null) {
                    // 登录成功
                    Toast.makeText(getApplication(), "登录成功", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent();
                    intent.setClass(LoginActivity.this, HomeActivity.class);
                    LoginActivity.this.startActivity(intent);
                } else {
                    // 失败的原因可能有多种
                    Toast.makeText(getApplication(),"登录失败", Toast.LENGTH_SHORT).show();

                }
            }
        });
    }

    public void Register(View view){
        Intent intent = new Intent();
        intent.setClass(this, RegisterActivity.class);
        this.startActivity(intent);
    }

    public void forgetPassword(View view){
        Toast.makeText(getApplication(),"忘记密码", Toast.LENGTH_SHORT).show();
    }
}
