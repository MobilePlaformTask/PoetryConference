package com.example.wangshimeng.poetry;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVFile;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.GetDataCallback;
import com.avos.avoscloud.ProgressCallback;
import com.avos.avoscloud.feedback.FeedbackAgent;

import Entity.SysApplication;
import task.compressImage;
import view.DrawCircleView;
import view.MySeekBar;
import view.SlideMenu;

public class HomeActivity extends AppCompatActivity implements SeekBar.OnSeekBarChangeListener{

    private long exitTime = 0;
    private TextView txtHomeUserName,txtHomeScore,txtMenuSign,txtMenuScore,txtMenuUserName,txtHomePrecisionNumber;
    private MySeekBar txtHomePrecision;
    private ImageView btn_back, img_myphoto;
    private SlideMenu slideMenu;
    private View layQuestionHistory;
    AVFile photo;
    Bitmap b;
    MyLeanCloudApp myLeanCloudApp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        SysApplication.getInstance().addActivity(this);

        layQuestionHistory=findViewById(R.id.layQuestionHistory);
        btn_back = (ImageView)findViewById(R.id.btn_back);
        img_myphoto = (ImageView)findViewById(R.id.img_myphpto);
        slideMenu = (SlideMenu)findViewById(R.id.slideMenu);

        txtHomeUserName= (TextView) findViewById(R.id.txtHomeUserName);
        txtHomeScore= (TextView) findViewById(R.id.txtHomeScore);
        txtHomePrecision= (MySeekBar) findViewById(R.id.txtHomePrecision);
        txtMenuSign= (TextView) findViewById(R.id.txtMenuSign);
        txtMenuScore= (TextView) findViewById(R.id.txtMenuScore);
        txtMenuUserName= (TextView) findViewById(R.id.txtMenuUserName);
        txtHomePrecisionNumber= (TextView) findViewById(R.id.txtHomePrecisionNumber);
        //点击返回键打开或关闭Menu
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                slideMenu.switchMenu();
            }
        });
//        img_myphoto.getBackground();
        myLeanCloudApp= (MyLeanCloudApp) this.getApplication();


//        layQuestionHistory.setOnClickListener(this);

        //设置值
        AVFile photo=AVUser.getCurrentUser().getAVFile("phpto");

        txtHomeUserName.setText(AVUser.getCurrentUser().getString("username"));
        txtHomeScore.setText(AVUser.getCurrentUser().getInt("score")+"");
        txtHomePrecisionNumber.setText(AVUser.getCurrentUser().getString("user_precision"));
        txtMenuUserName.setText(AVUser.getCurrentUser().getString("username"));
        txtMenuScore.setText(AVUser.getCurrentUser().getInt("score")+"");

        txtMenuSign.setText(AVUser.getCurrentUser().getString("signature"));
        //System.out.println(AVUser.getCurrentUser().getString("signature"));
        txtHomePrecision.setMax(100);
        //txtHomePrecision.setEnabled(false);
        //System.out.println(AVUser.getCurrentUser().getString("user_precision"));
        // System.out.println((int)((Float.parseFloat(AVUser.getCurrentUser().getString("user_precision")))*100));

        txtHomePrecision.setProgress((int)(Float.parseFloat(AVUser.getCurrentUser().getString("user_precision"))*100));


        b=null;
        photo=null;

        if(myLeanCloudApp.getBitmap()==null){
            AVFile file=AVUser.getCurrentUser().getAVFile("photo");
//       myLeanCloudApp.setFile(file);
            if(file==null){
                b = BitmapFactory.decodeResource(getResources(),R.drawable.touxiang);
                b=compressImage.comp(b);
                img_myphoto.setImageBitmap(DrawCircleView.drawCircleView01(b));
                btn_back.setImageBitmap(DrawCircleView.drawCircleView01(b));
                myLeanCloudApp.setBitmap(b);
            }
            else {
                file.getThumbnailUrl(true, 100, 100);
                file.getDataInBackground(new GetDataCallback() {
                    @Override
                    public void done(byte[] bytes, AVException e) {
                        // bytes 就是文件的数据流
//                        Log.d("baos len",bytes.length+"");
                        b = getPicFromBytes(bytes);
                        compressImage.compressImage(b);
                        btn_back.setImageBitmap(DrawCircleView.drawCircleView01(b));
                        img_myphoto.setImageBitmap(DrawCircleView.drawCircleView01(b));
                        myLeanCloudApp.setBitmap(b);
//                Bitmap b = BitmapFactory.decodeResource(getResources(),R.drawable.touxiang);
//                btn_back.setImageBitmap(DrawCircleView.drawCircleView01(b));
                    }
                }, new ProgressCallback() {
                    @Override
                    public void done(Integer integer) {
                        // 下载进度数据，integer 介于 0 和 100。
                    }
                });
            }
        }
        else{
            img_myphoto.setImageBitmap(DrawCircleView.drawCircleView01(myLeanCloudApp.getBitmap()));
            btn_back.setImageBitmap(DrawCircleView.drawCircleView01(myLeanCloudApp.getBitmap()));
        }




        txtHomePrecision.banClick(true);
        txtHomePrecision.banDrag(true);
        txtHomePrecision.setOnBanSeekBarChangeListener(new MySeekBar.OnBanSeekBarChangeListener() {

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                Log.d("myf", "3333333333333333");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                Log.d("myf", "1111111111111111");
            }

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress,
                                          boolean fromUser) {
                Log.d("myf", "=========="+progress);
            }
        });


    }


    public void gotoHistory(View v){
        Intent intent=new Intent(HomeActivity.this,HistoryActivity.class);
        this.startActivity(intent);
    }

    //将字节数组转换为ImageView可调用的Bitmap对象
    public static Bitmap getPicFromBytes(byte[] bytes) {
        return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
    }
    public void putongmoshi(View v){
        Intent intent=new Intent(HomeActivity.this,GameActivity.class);
        intent.putExtra("type","2");
        this.startActivity(intent);
    }
    public void xianchangmoshi(View v){
        Intent intent=new Intent(HomeActivity.this,GameActivity.class);
        intent.putExtra("type","1");
        this.startActivity(intent);
    }

    //联系我们
    public void contactUs(View view){
        FeedbackAgent agent = new FeedbackAgent(getApplicationContext());
        agent.startDefaultThreadActivity();
    }

    //通过电子邮件重置密码
    public void changePassword(View view){

        Intent intent = new Intent();
        intent.setClass(this,ChangePasswordActivity.class);
        this.startActivity(intent);
    }

    //退出
    public void backtoLogin(View view){
        AVUser.getCurrentUser().logOut();
        Intent intent = new Intent();
        intent.setClass(this, LoginActivity.class);
        this.startActivity(intent);
        finish();
    }
//
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN){
            if((System.currentTimeMillis()-exitTime) > 2000){
                Toast.makeText(getApplicationContext(), "再按一次退出程序", Toast.LENGTH_SHORT).show();
                exitTime = System.currentTimeMillis();
            } else {
//                finish();
//                android.os.Process.killProcess(android.os.Process.myPid());   //获取PID
                System.exit(0);
//                System.exit(0);
//                ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
//                manager.restartPackage(getPackageName());
//               finish();
               // SysApplication.getInstance().exit();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


//    @Override
//    public void onBackPressed() {
//        finish();
//    }

    //个人中心
    public void goToCenter(View view){
        Intent intent = new Intent();
        intent.setClass(this, UserCenterActivity.class);
//        this.startActivity(intent);
        startActivityForResult(intent, 1);
//        finish();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==1&&resultCode==2)//通过请求码(去SActivity)和回传码（回传数据到第一个页面）判断回传的页面
        {
            img_myphoto.setImageBitmap(DrawCircleView.drawCircleView01(myLeanCloudApp.getBitmap()));
            btn_back.setImageBitmap(DrawCircleView.drawCircleView01(myLeanCloudApp.getBitmap()));



            txtHomeUserName.setText(AVUser.getCurrentUser().getString("username"));
            txtMenuUserName.setText(AVUser.getCurrentUser().getString("username"));
            txtMenuSign.setText(AVUser.getCurrentUser().getString("signature"));


        }


    }




    //拖动中
    @Override
    public void onProgressChanged(SeekBar seekBar, int progress,
                                  boolean fromUser) {
    }
    //开始拖动
    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
    }
    //结束拖动
    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
    }

}
