package com.gonyb.share.domain.param;

/**
 * 记录车辆密码
 * Created by Gonyb on 2017/5/5.
 */
public class BicyclePasswordParam {

    private int id;
    private String password;
    private boolean isCorrect;

    public boolean isCorrect() {
        return isCorrect;
    }

    public void setCorrect(boolean correct) {
        isCorrect = correct;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
