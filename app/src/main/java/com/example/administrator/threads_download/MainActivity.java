package com.example.administrator.threads_download;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.threads_download.Service.ThreadService;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    ListView listView;
    listAdapter listAdapter;

    ThreadService threadService;
    ArrayList<DownloadTask> downloadTasks;

    ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            threadService = ((ThreadService.ThreadBinder)service).getThreadService();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        initData();
        /*setOnclickListener();*/
        bindService(new Intent(MainActivity.this, ThreadService.class), serviceConnection, BIND_AUTO_CREATE);
    }
    private void init()
    {
        listView = (ListView)findViewById(R.id.list);
        downloadTasks = new ArrayList<>();
        this.listAdapter = new listAdapter(downloadTasks,MainActivity.this);
        listView.setAdapter(listAdapter);
    }

    /*private void setOnclickListener()
    {
        *//*button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    threadService.startDownload(downloadTasks.size(),handler,0);
                }
                catch (Exception e)
                {
                    Toast.makeText(MainActivity.this,"等会儿再按吧！！！",Toast.LENGTH_SHORT).show();
                }
            }
        });*//*

    }*/

    private void initData()
    {
        for(int i=0;i<15;i++)
        {
            DownloadTask downloadTask = new DownloadTask();
            downloadTask.setProgress(0);
            downloadTask.setPosition(i);
            downloadTask.setTaskname("这是第" + (i + 1) + "个任务：");
            downloadTasks.add(downloadTask);
        }
        listAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService(serviceConnection);
    }

    Handler handler = new Handler()
    {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            /*progressBar.setProgress(msg.arg2);
            button.setText(msg.arg2+"%");*/
            listAdapter.progressBars.get(msg.arg1).setProgress(msg.arg2);
            listAdapter.notifyDataSetChanged();
          //  System.out.println("progress: " + msg.arg2 + " position: " + msg.arg1);
        }
    };

    public class listAdapter extends BaseAdapter {
        ArrayList<DownloadTask> downloadTasks;
        Context context;
        List<DownloadTask> progressBars;

        listAdapter(ArrayList<DownloadTask> downloadTasks,Context context)
        {
            this.context = context;
            this.downloadTasks = downloadTasks;
            progressBars = new ArrayList<DownloadTask>();
        }

        @Override
        public int getCount() {
            return downloadTasks.size();
        }

        @Override
        public Object getItem(int position) {
            return downloadTasks.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder;
            if(convertView == null)
            {
                convertView = LayoutInflater.from(context).inflate(R.layout.downloads_adapter,null);
                viewHolder = new ViewHolder();
                viewHolder.progressBar = (ProgressBar)convertView.findViewById(R.id.progressBar2);
                viewHolder.textView = (TextView)convertView.findViewById(R.id.textView);
                viewHolder.button = (Button)convertView.findViewById(R.id.button2);
                DownloadTask downloadTask = new DownloadTask();
                downloadTask.setProgress(0);
                progressBars.add(downloadTask);
                convertView.setTag(viewHolder);
                viewHolder.progressBar.setTag(position);
            }
            else viewHolder = (ViewHolder)convertView.getTag();

           try
           {
               if(viewHolder.progressBar.getTag() != position)
               {
                   if(position >= progressBars.size())
                   {
                       DownloadTask downloadTask = new DownloadTask();
                       downloadTask.setProgress(0);
                       progressBars.add(downloadTask);
                       System.out.println("Size:" + progressBars.size());
                   }
               }
               else
               {

               }
           }
           catch (Exception e)
           {
               //Toast.makeText(MainActivity.this,"出错了！",Toast.LENGTH_SHORT).show();
           }
            viewHolder.textView.setText(downloadTasks.get(position).getTaskname());
            System.out.println(position + "  " + viewHolder.progressBar.getTag() + " ");

            viewHolder.progressBar.setProgress(progressBars.get(position).getProgress());
            viewHolder.button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        threadService.startDownload(position, handler, 0);
                    } catch (Exception e) {
                        Toast.makeText(MainActivity.this, "等会儿再按吧！！！", Toast.LENGTH_SHORT).show();
                    }
                }
            });
            viewHolder.progressBar.setTag(position);

            return convertView;
        }

        public class ViewHolder
        {
            TextView textView;
            ProgressBar progressBar;
            Button button;
        }
    }
}
