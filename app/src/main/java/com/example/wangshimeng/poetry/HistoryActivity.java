package com.example.wangshimeng.poetry;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.FindCallback;

import java.util.ArrayList;
import java.util.List;

import task.HistoryListAdapter;

public class HistoryActivity extends AppCompatActivity {

    private ListView lstHistory;
    private List<AVObject> data;

    HistoryListAdapter historyListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
//        System.out.println("123");
        lstHistory = (ListView) findViewById(R.id.lstHistory);
        data = new ArrayList<AVObject>();
        lstHistory.setOnItemClickListener(new OnItemClickListenerImpl());

        filllist();
    }

    public void filllist() {
//        System.out.println("22222");
        AVQuery<AVObject> query2 = new AVQuery<>("Record");
        query2.include("question_set_id");
        query2.orderByDescending("record_id");

        query2.whereEqualTo("user_id", AVObject.createWithoutData("_User", AVUser.getCurrentUser().getObjectId()));
        query2.findInBackground(new FindCallback<AVObject>() {
            @Override
            public void done(List<AVObject> list, AVException e) {
                System.out.println("------" + list.size() + "------");
                data.addAll(list);
                historyListAdapter = new HistoryListAdapter(data, HistoryActivity.this);
                lstHistory.setAdapter(historyListAdapter);
            }
        });
    }


    // 定义事件监听器
    private class OnItemClickListenerImpl implements
            AdapterView.OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position,
                                long id) {

            AVObject object = (AVObject) parent.getItemAtPosition(position);

            //跳转到结果
            Intent intent = new Intent(HistoryActivity.this, AnalysisActivity.class);
            //传递参数
            intent.putExtra("record_id",object.getObjectId());
            HistoryActivity.this.startActivity(intent);
        }
    }

    //返回
    public void returnToHome(View view){
        Intent intent = new Intent();
        intent.setClass(this, HomeActivity.class);
        this.startActivity(intent);
    }

}
