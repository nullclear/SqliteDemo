package com.union.sqlitedemo;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.insert_btn)
    Button insertBtn;
    @BindView(R.id.update_btn)
    Button updateBtn;
    @BindView(R.id.delete_btn)
    Button deleteBtn;
    @BindView(R.id.query_btn)
    Button queryBtn;
    @BindView(R.id.del_all_btn)
    Button delAllBtn;
    @BindView(R.id.text_view)
    TextView textView;

    private DatabaseClient client;
    private ExecutorService exec;
    private Timer timer = new Timer();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        exec = Executors.newFixedThreadPool(5);
        ButterKnife.bind(this);
        //数据库初始化, DatabaseClient是自定义继承自RoomDatabase的类, 最后一个是数据库名称
        client = Room.databaseBuilder(this, DatabaseClient.class, "union.db").build();
        //每隔一秒查询一次
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                new QueryTask().run();
            }
        }, 0, 1000);
    }

    @OnClick({R.id.insert_btn, R.id.update_btn, R.id.delete_btn, R.id.query_btn, R.id.del_all_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.insert_btn:
                exec.execute(new InsertTask());
                break;
            case R.id.update_btn:
                exec.execute(new UpdateTask());
                break;
            case R.id.delete_btn:
                exec.execute(new DeleteTask());
                break;
            case R.id.query_btn:
                exec.execute(new QueryTask());
                break;
            case R.id.del_all_btn:
                exec.execute(new DeleteAllTask());
                break;
        }
    }

    //数据库操作不能在主线程进行
    class InsertTask implements Runnable {
        @Override
        public void run() {
            Person person = Person.Builder().name("jack").age(27).build();
            client.getPersonDao().insertPersons(person);
        }
    }

    class UpdateTask implements Runnable {
        @Override
        public void run() {
            client.getPersonDao().updatePersons("rose", 27);
        }
    }

    class DeleteTask implements Runnable {
        @Override
        public void run() {
            client.getPersonDao().deletePersons("rose");
        }
    }

    class QueryTask implements Runnable {
        @Override
        public void run() {
            List<Person> people = client.getPersonDao().queryAllPersons();
            //子线程无法修改主线程的UI, 将需要修改的内容发布到UI主线程队列
            MainActivity.this.runOnUiThread(() -> textView.setText(people.toString()));
        }
    }

    class DeleteAllTask implements Runnable {
        @Override
        public void run() {
            client.getPersonDao().deleteAll();
            //清除所有的表, 不会重置ID增长初始值
            //client.clearAllTables();
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        //取消定时任务
        timer.cancel();
        //关闭线程池
        exec.shutdown();
    }
}
