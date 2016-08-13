package com.example.administrator.threads_download.Service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;

import com.example.administrator.threads_download.DownloadThreadModel;

import java.util.ArrayList;


public class ThreadService extends Service {
    ArrayList<DownloadThreadModel> downloads;
    public ThreadService() {
    }

    public void startDownload(int position,Handler handler,int progress)
    {
        DownloadThreadModel downloadThreadModel = new DownloadThreadModel();
        downloadThreadModel.setHandler(handler);
        downloadThreadModel.setPosition(position);
        downloadThreadModel.setProgress(progress);
        downloadThreadModel.start();
    }

    @Override
    public IBinder onBind(Intent intent) {
        downloads = new ArrayList<>();
        // TODO: Return the communication channel to the service.
        return new ThreadBinder();
    }
    public class ThreadBinder extends Binder
    {
        ThreadBinder(){}
        public ThreadService getThreadService()
        {
            return ThreadService.this;
        }
    }
}
