package task;


import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.avos.avoscloud.AVObject;
import com.example.wangshimeng.poetry.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by lenovo on 2017/4/30.
 */

public class HistoryListAdapter extends BaseAdapter {

    List<AVObject> data;
    Context context;

    public HistoryListAdapter(List<AVObject> data, Context context) {
        this.context = context;
        this.data = data;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        // 获取view对象
        view = View.inflate(context, R.layout.item_history, null);
        // 获取各个组件
        TextView his_date = (TextView) view.findViewById(R.id.his_date);
        TextView his_time = (TextView) view.findViewById(R.id.his_time);
        ImageView imgModel = (ImageView) view.findViewById(R.id.imgModel);
        RatingBar rtbScore = (RatingBar) view.findViewById(R.id.rtbScore);

        // 填充数据
        AVObject object = data.get(position);

        AVObject set=object.getAVObject("question_set_id");
//        if(set.getInt("Module_type")==1){
//            imgModel.setImageResource(R.drawable.tellus);
//        }
//        else{
//            imgModel.setImageResource(R.drawable.model1);
//        }
        imgModel.setImageResource(R.drawable.model1);

        System.out.println((object.getCreatedAt()));


        System.out.println(object.getString("record_precision"));
        String date;
        String time;
       // date=object.getCreatedAt().getYear()+"."+object.getCreatedAt().getMonth()+"."+object.getCreatedAt().getDate();

        Date d=object.getCreatedAt();
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy.MM.dd");
        date=sdf.format(d);

        his_date.setText(date);
        sdf=new SimpleDateFormat("HH:mm");
        time=sdf.format(d);

        //time=object.getCreatedAt().getHours()+":"+object.getCreatedAt().getMinutes();
        his_time.setText(time);
        rtbScore.setRating(Float.parseFloat(object.getString("record_precision"))*5);
        return view;

    }
}
