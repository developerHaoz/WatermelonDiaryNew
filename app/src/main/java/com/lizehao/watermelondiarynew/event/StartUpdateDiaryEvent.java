package com.lizehao.watermelondiarynew.event;

/**
 * Created by 李 on 2017/1/26.
 */
public class StartUpdateDiaryEvent {

    private int position;

    public StartUpdateDiaryEvent(int position){
        this.position = position;
    }

    public void setPosition(int position){
        this.position = position;
    }
    public int getPosition(){
        return position;
    }
}