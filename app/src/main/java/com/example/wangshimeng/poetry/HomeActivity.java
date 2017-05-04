package com.example.wangshimeng.poetry;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;

import view.DrawCircleView;
import view.SlideMenu;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener{

    private ImageView btn_back,img_myphpto;
    private SlideMenu slideMenu;
    private View layQuestionHistory;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        this.requestWindowFeature(Window.FEATURE_NO_TITLE);//去掉标题栏

        setContentView(R.layout.activity_home);

        layQuestionHistory=findViewById(R.id.layQuestionHistory);
        btn_back = (ImageView)findViewById(R.id.btn_back);
        img_myphpto = (ImageView)findViewById(R.id.img_myphpto);
        slideMenu = (SlideMenu)findViewById(R.id.slideMenu);
        //点击返回键打开或关闭Menu
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                slideMenu.switchMenu();
            }
        });
        img_myphpto.getBackground();
        Bitmap b = BitmapFactory.decodeResource(getResources(),R.drawable.touxiang);
        btn_back.setImageBitmap(DrawCircleView.drawCircleView01(b));

        b = BitmapFactory.decodeResource(getResources(), R.drawable.touxiang);
        img_myphpto.setImageBitmap(DrawCircleView.drawCircleView01(b));
        layQuestionHistory.setOnClickListener(this);
        //img_myphpto
    }
    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.layQuestionHistory:
                Intent intent=new Intent(HomeActivity.this,HistoryActivity.class);
                this.startActivity(intent);
                break;
        }
    }

    public void putongmoshi(View v){
        Intent intent=new Intent(HomeActivity.this,GameActivity.class);
        this.startActivity(intent);
    }


}
