package com.example.wangshimeng.poetry;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.RequestPasswordResetCallback;
import com.avos.avoscloud.UpdatePasswordCallback;

public class ChangePasswordActivity extends AppCompatActivity {
    EditText edtOldPass, edtNewPass1, edtNewPass2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        edtOldPass = (EditText) findViewById(R.id.edtOldPass);
        edtNewPass1 = (EditText) findViewById(R.id.edtNewPass1);
        edtNewPass2 = (EditText) findViewById(R.id.edtNewPass2);
    }

    //通过电子邮件重置密码
    public void changeFromEmail(View view) {
        AVUser.requestPasswordResetInBackground(AVUser.getCurrentUser().getEmail(), new RequestPasswordResetCallback() {
            @Override
            public void done(AVException e) {
                if (e == null) {
                    Toast.makeText(getApplication(), "邮件发送成功", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplication(), "邮件发送失败", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
            }
        });

    }

    //返回
    public void returnToHome(View view) {
        Intent intent = new Intent();
        intent.setClass(this, HomeActivity.class);
        this.startActivity(intent);
    }

    public void savePassword(View view) {
        if (edtNewPass1.getText().toString().equals(edtNewPass2.getText().toString())) {
            AVUser.getCurrentUser().updatePasswordInBackground(edtOldPass.getText().toString(), edtNewPass1.getText().toString(), new UpdatePasswordCallback() {
                @Override
                public void done(AVException e) {
                    if (e == null) {
                        Toast.makeText(ChangePasswordActivity.this, "修改成功", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(ChangePasswordActivity.this, "修改失败", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        } else {
            Toast.makeText(this, "两次密码不相同", Toast.LENGTH_SHORT).show();
        }
    }

}
