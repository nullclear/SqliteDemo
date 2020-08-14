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
    @BindView(R.id.text_view)
    TextView textView;

    private DatabaseClient client;
    private ExecutorService exec;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        exec = Executors.newFixedThreadPool(5);
        ButterKnife.bind(this);
        client = Room.databaseBuilder(this, DatabaseClient.class, "union.db").build();
    }

    @OnClick({R.id.insert_btn, R.id.update_btn, R.id.delete_btn, R.id.query_btn})
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
        }
    }

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
            Person person = Person.Builder().id(2L).name("rose").age(27).build();
            client.getPersonDao().updatePersons(person);
        }
    }

    class DeleteTask implements Runnable {
        @Override
        public void run() {
            Person person = Person.Builder().id(5L).name("jack").age(27).build();
            client.getPersonDao().deletePersons(person);
        }
    }

    class QueryTask implements Runnable {
        @Override
        public void run() {
            List<Person> people = client.getPersonDao().queryAllPersons();
            MainActivity.this.runOnUiThread(() -> textView.setText(people.toString()));
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        exec.shutdown();
    }
}
