package com.example.administrator.threads_download;

import android.os.Handler;
import android.os.Message;


/**
 * Created by Administrator on 2016/8/12.
 */
public class DownloadThreadModel extends Thread {
    private int position;
    private String Taskname;
    private Handler handler;
    private int progress;

    public int getPosition() {
        return position;
    }

    public String getTaskname() {
        return Taskname;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public void setTaskname(String taskname) {
        Taskname = taskname;
    }

    public void setHandler(Handler handler) {
        this.handler = handler;
    }

    public Handler getHandler() {
        return handler;
    }

    public void setProgress(int progress) {
        this.progress = progress;
    }

    public int getProgress() {
        return progress;
    }

    public DownloadThreadModel()
    {}

    @Override
    public void run() {
        super.run();
        progress = 0;
        while(!(progress>=100))
        {
            try {
                int dpwnloadSum = (int) (Math.random()*20);
                progress += dpwnloadSum;
                Message message = new Message();
                message.arg1 = position;
                message.arg2 = progress;
                this.handler.sendMessage(message);
                sleep(1000);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
