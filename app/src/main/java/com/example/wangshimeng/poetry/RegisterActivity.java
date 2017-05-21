package com.example.wangshimeng.poetry;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.RequestEmailVerifyCallback;
import com.avos.avoscloud.SignUpCallback;

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
    }

    public void Register(View view){
        EditText edtName=(EditText)findViewById(R.id.edtRegisterName);
        EditText edtPassword=(EditText)findViewById(R.id.edtPassword);
        EditText edtEmail=(EditText)findViewById(R.id.edtEmail);

        String username=edtName.getText().toString();
        String userPassword=edtPassword.getText().toString();
        String userEmail=edtEmail.getText().toString();

        AVUser user = new AVUser();// 新建 AVUser 对象实例
        user.setUsername(username);// 设置用户名
        user.setPassword(userPassword);// 设置密码
        user.setEmail(userEmail);// 设置邮箱
        user.signUpInBackground(new SignUpCallback() {
            @Override
            public void done(AVException e) {
                if (e == null) {
                    // 注册成功
                    Toast.makeText(getApplication(), "注册成功,请前往邮箱验证", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent();
                    intent.setClass(getApplication(), LoginActivity.class);
                    getApplication().startActivity(intent);
                } else {
                    // 失败的原因可能有多种，常见的是用户名已经存在。
                    Toast.makeText(getApplication(), "注册失败", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void CheckEmail(View view){

        AVUser.requestEmailVerifyInBackground("757330195@qq.com", new RequestEmailVerifyCallback() {
            @Override
            public void done(AVException e) {
                if (e == null) {
                    // 求重发验证邮件成功
                    Toast.makeText(getApplication(), "发送成功", Toast.LENGTH_SHORT).show();

                } else {
                    // 失败的原因可能有多种
                    Toast.makeText(getApplication(), "发送失败", Toast.LENGTH_SHORT).show();

                }
            }
        });
    }
    public void Cancel(View view){

        Intent intent = new Intent();
        intent.setClass(this, LoginActivity.class);
        this.startActivity(intent);
    }
}
