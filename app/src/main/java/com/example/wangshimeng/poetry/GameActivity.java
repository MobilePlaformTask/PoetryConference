package com.example.wangshimeng.poetry;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.FindCallback;
import com.avos.avoscloud.SaveCallback;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import Entity.Mistakes;

public class GameActivity extends AppCompatActivity {

    private TextView stateprogressView, txtQuestionContent, txtQuestionContent2, txtQuestionContent41, txtQuestionContent43; // 各种状态信息
    private Button btnAnswer1, btnAnswer2, btnAnswer3, btnWord1, btnWord2, btnWord3, btnWord4, btnWord5, btnWord6, btnWord7, btnWord8, btnWord9,btnCancelAns;// 4个答案选项按钮
    private Button[] btnAns=new Button[5];
    private Button[] btnWord=new Button[9];
    private EditText txtQuestionAnswer2, txtQuestionAnswer3, txtQuestionAnswer42;
    private ProgressBar timeprogress; // 时间进度条

    private int type3_current_id=0;

    //错题集
    private List<Mistakes> mistakes = new ArrayList<Mistakes>();
    private int i = 0;//题的编号  0-9
    private int mistakeCount = 0;//错题数量
    private String mistakenumber = "";//错题序号

    private final static int CHANGE_QUESTION = 1; // 显示游戏界面题目的标识符
    private final static int SETPROGRESS = 2; // 表示设置时间进度条的标识符
    private final static int RESTARTGAME = 3; // 结束游戏到结果界面
    private final static int NEXTQUESTION = 4;// 加载下一题
    private static boolean OVERTIME = false; // 是否已经超时标识符

    private boolean flag = false; // 此题是否答对
    private int progressBarValue = 0; // 表示时间进度条的进度
    private final static int TOTALPROGRESS = 10; // 设置时间进度条的最大值
    private Timer timer; // 设置一个定时器
    //private Random random = new Random(); // 设置一个随机数来随机抽取题目

    private List<AVObject> questionList = new ArrayList<AVObject>(); //题目列表
    private AVObject questionSet;//当前questionSet
    private AVObject currentQuestion;//当前题目
    private String userAnswer;//用户的答题结果

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        i = 0;//题的编号  0-9
        mistakeCount = 0;//错题数量

        getquestions();

    }

    // 用线程和handler来处理消息
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case CHANGE_QUESTION:
                    userAnswer = "";

                    currentQuestion = (AVObject) msg.obj;

                    int type_id = currentQuestion.getInt("type_id");

                    switch (type_id) {
                        case 1:
                            setContentView(R.layout.question_type1);

                            stateprogressView = (TextView) GameActivity.this.findViewById(R.id.stateprogress);
                            timeprogress = (ProgressBar) GameActivity.this.findViewById(R.id.progressBar);
                            timeprogress.setMax(TOTALPROGRESS);

                            txtQuestionContent = (TextView) GameActivity.this.findViewById(R.id.txtQuestionContent1);


                            btnAnswer1 = (Button) GameActivity.this.findViewById(R.id.btnAnswer1);
                            btnAnswer1.setAlpha((float) 0.5);
                            //btnAnswer1.setOnClickListener(GameActivity.this);
                            btnAnswer1.setOnClickListener(new SeceltQuestion());
                            btnAnswer2 = (Button) GameActivity.this.findViewById(R.id.btnAnswer2);
                            btnAnswer2.setAlpha((float) 0.5);
                            btnAnswer2.setOnClickListener(new SeceltQuestion());
                            btnAnswer3 = (Button) GameActivity.this.findViewById(R.id.btnAnswer3);
                            btnAnswer3.setAlpha((float) 0.5);
                            btnAnswer3.setOnClickListener(new SeceltQuestion());
                            txtQuestionContent.setText(currentQuestion.getString("question_content"));
                            String[] answers = currentQuestion.getString("note").split(" ");

                            btnAnswer1.setText(answers[0]);
                            btnAnswer2.setText(answers[1]);
                            btnAnswer3.setText(answers[2]);
                            break;
                        case 2:
                            setContentView(R.layout.question_type2);
                            stateprogressView = (TextView) GameActivity.this.findViewById(R.id.stateprogress);
                            timeprogress = (ProgressBar) GameActivity.this.findViewById(R.id.progressBar);
                            timeprogress.setMax(TOTALPROGRESS);

                            txtQuestionContent2 = (TextView) GameActivity.this.findViewById(R.id.txtQuestionContent2);
                            txtQuestionAnswer2 = (EditText) GameActivity.this.findViewById(R.id.txtQuestionAnswer2);
                            txtQuestionContent2.setText(currentQuestion.getString("question_content"));


//上半句和下半句的处理



                            break;
                        case 3:

                            setContentView(R.layout.question_type3);

                            stateprogressView = (TextView) GameActivity.this.findViewById(R.id.stateprogress);
                            timeprogress = (ProgressBar) GameActivity.this.findViewById(R.id.progressBar);
                            timeprogress.setMax(TOTALPROGRESS);
                            type3_current_id=0;

                            btnCancelAns = (Button) GameActivity.this.findViewById(R.id.btnCancelAns);
                            txtQuestionAnswer3 = (EditText) GameActivity.this.findViewById(R.id.txtQuestionAnswer3);
                            btnWord[0] = (Button) GameActivity.this.findViewById(R.id.btnWord1);
                            btnWord[1] = (Button) GameActivity.this.findViewById(R.id.btnWord2);
                            btnWord[2] = (Button) GameActivity.this.findViewById(R.id.btnWord3);
                            btnWord[3] = (Button) GameActivity.this.findViewById(R.id.btnWord4);
                            btnWord[4] = (Button) GameActivity.this.findViewById(R.id.btnWord5);
                            btnWord[5] = (Button) GameActivity.this.findViewById(R.id.btnWord6);
                            btnWord[6] = (Button) GameActivity.this.findViewById(R.id.btnWord7);
                            btnWord[7] = (Button) GameActivity.this.findViewById(R.id.btnWord8);
                            btnWord[8] = (Button) GameActivity.this.findViewById(R.id.btnWord9);
                            btnAns[0]=(Button) GameActivity.this.findViewById(R.id.btnAns1);
                            btnAns[1]=(Button) GameActivity.this.findViewById(R.id.btnAns2);
                            btnAns[2]=(Button) GameActivity.this.findViewById(R.id.btnAns3);
                            btnAns[3]=(Button) GameActivity.this.findViewById(R.id.btnAns4);
                            btnAns[4]=(Button) GameActivity.this.findViewById(R.id.btnAns5);

                            String question_content3 = currentQuestion.getString("question_content");
                            char[] question_char = question_content3.toCharArray();
                            System.out.println(question_char);

                            for(int i=0;i<9;i++) {
                                btnWord[i].setText(question_char[i] + "");
                                btnWord[i].setOnClickListener(new MergeWordQuestion());
                            }
                            break;
                        case 4:
                            setContentView(R.layout.question_type4);
                            stateprogressView = (TextView) GameActivity.this.findViewById(R.id.stateprogress);
                            timeprogress = (ProgressBar) GameActivity.this.findViewById(R.id.progressBar);
                            timeprogress.setMax(TOTALPROGRESS);

                            txtQuestionContent41 = (TextView) GameActivity.this.findViewById(R.id.txtQuestionContent41);
                            txtQuestionContent43 = (TextView) GameActivity.this.findViewById(R.id.txtQuestionContent43);
                            txtQuestionAnswer42 = (EditText) GameActivity.this.findViewById(R.id.txtQuestionAnswer42);

                            String question_content4 = currentQuestion.getString("question_content");
                            String question_array[] = question_content4.split(" ");
                            txtQuestionContent41.setText(question_array[0]);
                            txtQuestionContent43.setText(question_array[1]);

                            break;
                        default:
                            setContentView(R.layout.question_type1);
                    }
                    break;
                case SETPROGRESS:
                    int progress = (Integer) msg.obj;
                    stateprogressView.setText((10 - progress) + "");
                    timeprogress.setProgress(progress);
                    break;
                case NEXTQUESTION:

                    // 检查错误
                    //CheckAnswer("sss");

                    int type_id3 = currentQuestion.getInt("type_id");
                    switch (type_id3) {
                        case 1:
                            CheckAnswer(userAnswer);
                            break;
                        case 2:
                            userAnswer = txtQuestionAnswer2.getText().toString();
                            CheckAnswer(userAnswer);
                            break;
                        case 3:
                            CheckAnswer(userAnswer);
                            break;
                        case 4:
                            userAnswer = txtQuestionAnswer42.getText().toString();
                            CheckAnswer(userAnswer);
                            //System.out.println("count"+mistakeCount);
                            break;
                    }
                    timer.cancel();
                    progressBarValue = 0; // 将时间进度重置为0


                    if(i<4) {
                        new Thread(new StartGame()).start();
                    }
                    else{

                        System.out.println("count2" + mistakeCount);
//题的数量减1

//                        try {
//                            Thread.sleep(1000);
//                        } catch (InterruptedException e) {
//                            e.printStackTrace();
//                        }
//增加一条记录
                        final AVObject record = new AVObject("Record");
                        // 构建对象
                        record.put("record_precision", (float) (10 -
                                mistakeCount) / 10 + "");// 设置
                        record.put("question_set_id", questionSet);// 设置
                        //record.put("user_id", AVUser.getCurrentUser());// 设置
                        //record.put("record_id", NULL);//

                        record.saveInBackground(new SaveCallback() {
                            @Override
                            public void done(AVException e) {
                                if (e == null) {
                                    // 存储成功
                                    //System.out.println("jilu:" + record.getObjectId());

                                    timer.cancel();
//                                存储错误而题目
                                    for (Mistakes mistake : mistakes) {
                                        AVObject avobject = new AVObject
                                                ("Mistakes");// 构建对象
                                        avobject.put("question_id",
                                                mistake.getQuestion());// 设置
                                        avobject.put("record_id",
                                                record);// 设置
                                        System.out.println
                                                (record.getObjectId() + "-----1234567");
                                        avobject.put("mistake_answer",
                                                mistake.getMistakeanswer());// 设置
                                        avobject.saveInBackground(new SaveCallback() {
                                            @Override
                                            public void done(AVException
                                                                     e) {
                                                if (e == null) {

                                                    //跳转到结果
                                                    Intent intent = new
                                                            Intent(GameActivity.this, ResultActivity.class);
                                                    //传递参数
                                                    intent.putExtra
                                                            ("record_id",record.getObjectId());


                                                    GameActivity.this.startActivity(intent);

                                                    // 存储成功
                                                    //System.out.println("jilu:" + record.getObjectId());
                                                } else {

                                                }
                                            }
                                        });
                                    }

                                } else {
                                    // 失败的话，请检查网络环境以及 SDK 配置是否正确
                                }
                            }
                        });


                    }
                    break;
                case RESTARTGAME:

                    break;
            }
        }

        ;
    };

    class SeceltQuestion implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.btnAnswer1:
                    userAnswer = "A";
                    btnAnswer1.setBackgroundColor(Color.BLUE);
                    btnAnswer2.setBackgroundColor(Color.GRAY);
                    btnAnswer3.setBackgroundColor(Color.GRAY);
                    break;
                case R.id.btnAnswer2:
                    userAnswer = "B";
                    btnAnswer2.setBackgroundColor(Color.BLUE);
                    btnAnswer1.setBackgroundColor(Color.GRAY);
                    btnAnswer3.setBackgroundColor(Color.GRAY);
                    break;
                case R.id.btnAnswer3:
                    userAnswer = "C";
                    btnAnswer3.setBackgroundColor(Color.BLUE);
                    btnAnswer1.setBackgroundColor(Color.GRAY);
                    btnAnswer2.setBackgroundColor(Color.GRAY);
                    break;
            }
        }
    }
    class MergeWordQuestion implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            if(type3_current_id<5) {

                Button b = (Button) findViewById(v.getId());
                userAnswer += b.getText().toString();
                txtQuestionAnswer3.setText(userAnswer);

                btnAns[type3_current_id].setText(b.getText().toString());
                type3_current_id++;
            }
        }
    }
    public void cancelWord(View v){
        if(type3_current_id==0){

        }
        else{
            btnAns[type3_current_id-1].setText("");
            type3_current_id--;
            userAnswer= userAnswer.substring(0,userAnswer.length()-1);
            txtQuestionAnswer3.setText(userAnswer);
        }
    }

    // 初始化QuestionNum数组,随机抽取
    public class StartGame implements Runnable {
        @Override
        public void run() {
            timer = new Timer(true);
            timer.schedule(new TimerTask() {
                @Override
                public void run() {

//                    if (i >= 4) {
//                        handler.sendEmptyMessage(RESTARTGAME);
//                    }

                    // TODO Auto-generated method stub
//                    else {
                        if (progressBarValue == TOTALPROGRESS) {
                            // 超出游戏时间，弹出对话框提示玩家
                            //if (i < 4) {
                                handler.sendEmptyMessage(NEXTQUESTION);
                            //}
//                            else {
//                                handler.sendEmptyMessage(RESTARTGAME);
//                            }

                        } else {
                            // 将信息传送给handler来更新进度条
                            Message message = Message.obtain();
                            message.obj = progressBarValue;
                            message.what = SETPROGRESS;
                            handler.sendMessage(message);
                            // 时间进度自增
                            progressBarValue++;
                        }
                    }
//                }
            }, 0, 1000);

            System.out.println("questionList.size()3---" + questionList.size());

            // 用message来向主线程传递信息并处理
            Message message = Message.obtain();
            message.obj = questionList.get(i); // 将map信息放入message中
            i++;
            message.what = CHANGE_QUESTION; // 设定message的标示符
            handler.sendMessage(message); // 向主线程中的handler发送信息
        }
    }

    //检查答案
    public void CheckAnswer(String answer) {
        if (answer.equals(currentQuestion.get("answer"))) {
            Toast.makeText(this, "true", Toast.LENGTH_SHORT).show();
        } else {
            //将错误信息存储起来
            Mistakes mistake = new Mistakes();
            mistake.setQuestion(currentQuestion);
            mistake.setMistakeanswer(answer);
            mistakes.add(mistake);
            mistakenumber += currentQuestion;
            System.out.println();
            System.out.println(currentQuestion.getInt("question_number") + mistakeCount + "");
            mistakeCount++;
            Toast.makeText(this, "false", Toast.LENGTH_SHORT).show();
        }
    }

    public void getquestions() {
        AVQuery<AVObject> query1 = new AVQuery<>("Question_set");
        query1.whereEqualTo("question_set_id", 5);
        query1.findInBackground(new FindCallback<AVObject>() {
            @Override
            public void done(List<AVObject> list, AVException e) {
                //查找这套题的全部题目
                questionSet = list.get(0);
                AVQuery<AVObject> query2 = new AVQuery<>("Questions");
                //query2.include("question_set_id");
                query2.orderByAscending("question_number");
                query2.whereEqualTo("question_set_id", AVObject.createWithoutData("Question_set", list.get(0).getObjectId()));

                query2.findInBackground(new FindCallback<AVObject>() {
                    @Override
                    public void done(List<AVObject> list, AVException e) {
                        if (e == null) {
                            questionList.addAll(list);
                            for (AVObject question : list) {

                                String question_content = question.getString("question_content");//读取 question_content
                                String answer = question.getString("answer");// 读取 answer
                                Integer type = question.getInt("type_id");// 读取 type_id
                                String note = question.getString("note");// 读取 note

                                System.out.println(question_content + answer + type + note);

                            }

                            new Thread(new StartGame()).start();
                        } else {
                            e.printStackTrace();
                        }
                    }
                });
            }
        });

//        AVQuery<AVObject> query = new AVQuery<>("Questions");
//        query.include("question_set_id");
//        query.orderByAscending("question_number");
//
//        query.findInBackground(new FindCallback<AVObject>() {
//            @Override
//            public void done(List<AVObject> list, AVException e) {
//                if (e == null) {
//                    questionList.addAll(list);
//
//                    System.out.println("questionList.size()" + questionList.size());
//                    for (AVObject question : list) {
//                        //AVObject question=question11.getAVObject("Questions");
//                        questionSet = question.getAVObject("question_set_id");
//                        String question_content = question.getString("question_content");//读取 question_content
//                        String answer = question.getString("answer");// 读取 answer
//                        Integer type = question.getInt("type_id");// 读取 type_id
//                        String note = question.getString("note");// 读取 note
//                        //System.out.println(question_set.getInt("Module_type"));
//                        System.out.println(questionSet.getAVObject("Module_type").getObjectId());
//                        System.out.println(question.getAVObject("question_set_id").toString());
//                        System.out.println(question_content + answer + type + note);
//                    }
//                    stateprogressView = (TextView) GameActivity.this.findViewById(R.id.stateprogress);
//                    timeprogress = (ProgressBar) GameActivity.this.findViewById(R.id.progressBar);
//                    timeprogress.setMax(TOTALPROGRESS);
//                    new Thread(new StartGame()).start();
//
//                } else {
//                    e.printStackTrace();
//                }
//            }
//        });

    }
}