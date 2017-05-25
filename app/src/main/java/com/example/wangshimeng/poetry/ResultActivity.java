package com.example.wangshimeng.poetry;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.RatingBar;
import android.widget.TextView;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.FindCallback;
import com.avos.avoscloud.GetCallback;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ResultActivity extends AppCompatActivity {
    private TextView txtRecord_precision,txtMistakesNumber,txtRecordScore;
    private RatingBar rtbScoreResult;
    String value;//参数recordId
    int type;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        txtRecord_precision= (TextView) findViewById(R.id.txtRecord_precision);
        txtMistakesNumber= (TextView) findViewById(R.id.txtMistakesNumber);
        txtRecordScore= (TextView) findViewById(R.id.txtRecordScore);
        rtbScoreResult= (RatingBar) findViewById(R.id.rtbScoreResult);

        //使用Intent对象得到传递来的参数
        Intent intent = getIntent();
        value= intent.getStringExtra("record_id");
        type = intent.getIntExtra("type",1);

        System.out.println(value+"value");

        AVQuery<AVObject> query = new AVQuery<>("Record");
        query.getInBackground(value, new GetCallback<AVObject>() {
            @Override
            public void done(AVObject record, AVException e) {
                txtRecord_precision.setText(record.getString("record_precision"));
                rtbScoreResult.setRating(5*Float.parseFloat(record.getString("record_precision")));
                txtRecordScore.setText("+"+(int)(Float.parseFloat(record.getString("record_precision"))*100));
                AVQuery<AVObject> query2 = new AVQuery<>("Mistakes");
                //query2.include("question_set_id");
                query2.include("question_id");

                query2.whereEqualTo("record_id", AVObject.createWithoutData("Record",record.getObjectId()));
                query2.findInBackground(new FindCallback<AVObject>() {
                    @Override
                    public void done(List<AVObject> list, AVException e) {

                        List<Integer> misList=new ArrayList<Integer>();
                        String numbers="";
                        for(AVObject mistake:list){

                            AVObject question=mistake.getAVObject("question_id");
                            misList.add(question.getInt("question_number"));
                        }
                        Collections.sort(misList);
                        for(int i=0;i<misList.size();i++){
                            numbers+=misList.get(i);
                            numbers+=",";
                        }
                        txtMistakesNumber.setText(numbers.substring(0,numbers.length()-1));

                    }
                });

            }
        });

    }

    public void getanalysis(View v){
        //跳转到解析
        Intent intent = new Intent(ResultActivity.this, AnalysisActivity.class);
        //传递参数
        intent.putExtra("record_id",value);
        this.startActivity(intent);
    }
    public void continuePlay(View v){
        Intent intent=new Intent(ResultActivity.this,GameActivity.class);
        intent.putExtra("type",type+"");
        this.startActivity(intent);
        finish();
    }
    public void returnToHistory(View v){
        onBackPressed();
    }

//
//    //重写返回键的方法
//    public boolean onKeyDown(int keyCode,KeyEvent event) {
//
//        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0){
//            //这里重写返回键
//            Intent intent=new Intent(ResultActivity.this,HomeActivity.class);
//            startActivity(intent);
//        }
//        return false;
//    }





}






//
// System.out.println("count2" + mistakeCount);
//         timer.cancel();
//         progressBarValue = 0; // 将时间进度重置为0
////题的数量减1
//
////                        try {
////                            Thread.sleep(1000);
////                        } catch (InterruptedException e) {
////                            e.printStackTrace();
////                        }
////增加一条记录
//final AVObject record = new AVObject("Record");
//        // 构建对象
//        record.put("record_precision", (float) (10 -
//        mistakeCount) / 10 + "");// 设置
//        record.put("question_set_id", questionSet);// 设置
//        //record.put("user_id", AVUser.getCurrentUser());// 设置
//        //record.put("record_id", NULL);//
//
//        record.saveInBackground(new SaveCallback() {
//@Override
//public void done(AVException e) {
//        if (e == null) {
//        // 存储成功
//        //System.out.println("jilu:" + record.getObjectId());
//
//        timer.cancel();
////                                存储错误而题目
//        for (Mistakes mistake : mistakes) {
//        AVObject avobject = new AVObject
//        ("Mistakes");// 构建对象
//        avobject.put("question_id",
//        mistake.getQuestion());// 设置
//        avobject.put("record_id",
//        record);// 设置
//        System.out.println
//        (record.getObjectId() + "-----1234567");
//        avobject.put("mistake_answer",
//        mistake.getMistakeanswer());// 设置
//        avobject.saveInBackground(new SaveCallback() {
//@Override
//public void done(AVException
//        e) {
//        if (e == null) {
//
//        //跳转到结果
//        Intent intent = new
//        Intent(GameActivity.this, ResultActivity.class);
//        //传递参数
//        intent.putExtra
//        ("record_id", record.getString("record_id"));
//
//
//        GameActivity.this.startActivity(intent);
//
//        // 存储成功
//        //System.out.println("jilu:" + record.getObjectId());
//        } else {
//
//        }
//        }
//        });
//        }
//
//        } else {
//        // 失败的话，请检查网络环境以及 SDK 配置是否正确
//        }
//        }
//        });