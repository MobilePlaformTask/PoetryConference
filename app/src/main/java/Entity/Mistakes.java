package Entity;

import com.avos.avoscloud.AVObject;

/**
 * Created by lenovo on 2017/4/27.
 */

public class Mistakes {
    private AVObject question;
    private AVObject record;
    private String mistakeanswer;

    public AVObject getQuestion() {
        return question;
    }

    public AVObject getRecord() {
        return record;
    }

    public String getMistakeanswer() {
        return mistakeanswer;
    }

    public void setQuestion(AVObject question) {
        this.question = question;
    }

    public void setMistakeanswer(String mistakeanswer) {
        this.mistakeanswer = mistakeanswer;
    }

    public void setRecord(AVObject record) {
        this.record = record;
    }
}
