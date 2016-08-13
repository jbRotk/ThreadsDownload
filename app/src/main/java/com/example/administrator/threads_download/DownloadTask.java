package com.example.administrator.threads_download;

/**
 * Created by Administrator on 2016/8/12.
 */
public class DownloadTask {
    private int progress;
    private int position;
    private String Taskname;


    public void setProgress(int progress) {
        this.progress = progress;
    }

    public int getProgress() {
        return progress;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public int getPosition() {
        return position;
    }

    public void setTaskname(String taskname) {
        Taskname = taskname;
    }

    public String getTaskname() {
        return Taskname;
    }

    public DownloadTask()
    {
    }
}
