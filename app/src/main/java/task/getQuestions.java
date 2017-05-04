package task;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.FindCallback;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lenovo on 2017/4/26.
 */

public class getQuestions {
    static List<AVObject> questionList=new ArrayList<AVObject>();

    public static List<AVObject> getquestions(){

        AVQuery<AVObject> query = new AVQuery<>("Questions");

        query.findInBackground(new FindCallback<AVObject>() {
            @Override
            public void done(List<AVObject> list, AVException e) {
                if (e == null) {
                    //questionList.addAll(list);
                    System.out.println("List.size()"+list.size());
                    System.out.println("questionList.size()0---"+questionList.size());
                    for (AVObject question:list) {
                        questionList.add(question);
                        String question_content = question.getString("question_content");//读取 question_content
                        String answer = question.getString("answer");// 读取 answer
                        String type = question.getString("type_id");// 读取 answer
                        String note = question.getString("note");// 读取 answer
                        System.out.println(question_content+answer+type+note);
                    }
                    System.out.println("questionList.size()1---"+questionList.size());
                } else {
                    e.printStackTrace();
                }
            }
        });
        return questionList;
    }
}
