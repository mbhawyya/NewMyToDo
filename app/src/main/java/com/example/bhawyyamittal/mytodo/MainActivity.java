package com.example.bhawyyamittal.mytodo;
        import android.app.AlarmManager;
        import android.app.PendingIntent;
       // import android.content.ContentValues;
        import android.content.Context;
    //    import android.content.DialogInterface;
        import android.content.Intent;
        import android.database.Cursor;
        import android.database.sqlite.SQLiteDatabase;
        import android.os.AsyncTask;
        import android.os.Bundle;
        //import android.support.v7.app.AlertDialog;
        import android.support.v7.app.AppCompatActivity;
        import android.util.Log;
        import android.view.Menu;
        import android.view.MenuItem;
        import android.view.View;
        import android.widget.ArrayAdapter;
     //   import android.widget.EditText;
        import android.widget.ListView;
        import android.widget.SimpleCursorAdapter;
        import android.widget.TextView;
        import android.widget.Toast;
        import db.TaskContract;
        import db.TaskDbHelper;

        import java.lang.reflect.Array;
        import java.util.ArrayList;
        import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private TaskDbHelper mHelper;
    private SimpleCursorAdapter cursorAdapter;
   // private ListView mTaskListView;
   // private ArrayAdapter<String> mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.e(TAG,"Successfully layout rendered");
        mHelper = new TaskDbHelper(this);
     //   mTaskListView = (ListView) findViewById(R.id.list_todo);
        updateUI();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        Log.e(TAG,"Menu is loaded");
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_add_task:
                Intent intent = new Intent(this,AlarmSetActivity.class);
                startActivityForResult(intent, 2);
                Log.e(TAG,"Add button Clicked");
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        String task = data.getStringExtra("TASK");
        String hour = data.getStringExtra("HOUR");
        String minute = data.getStringExtra("MINUTE");
        //Log.e("",task);
        //Log.e("",hour);
        //Log.e("",minute);
        BackgroundTask backgroundTask = new BackgroundTask(this);
        backgroundTask.execute("add_info",task,String.valueOf(hour),String.valueOf(minute));
        if(requestCode==2)
        {
            updateUI();
            Toast.makeText(this,"Returned to previous", Toast.LENGTH_LONG).show();
        }
    }

    public void deleteTask(View view) {
        View parent = (View) view.getParent();
        TextView taskTextView = (TextView) parent.findViewById(R.id.task_title);
        String task = String.valueOf(taskTextView.getText());
        SQLiteDatabase db = mHelper.getWritableDatabase();
        db.delete(TaskContract.TABLE,
                TaskContract.COL_TASK_TITLE + " = ?",
                new String[]{task});
        db.close();
        updateUI();
    }

    private void updateUI() {

        BackgroundTask background = new BackgroundTask(this);
        background.execute("get_info");
    }



    // Inner Private Class :

    private class BackgroundTask extends AsyncTask<String,Void,List<String>> {
        Context ctx;
        Cursor cursor;
        ArrayAdapter<String> mAdapter;
        ListView mTaskListView;
        BackgroundTask(Context ctx){
            this.ctx=ctx;

        }

        @Override
        protected List<String>  doInBackground(String... params) {
            String method = params[0];
            List<String> taskList = new ArrayList<String>();
            TaskDbHelper taskDbHelper = new TaskDbHelper(ctx);
            if(method.equals("add_info")){
                String task = params[1];
                String hour = params[2];
                String minute = params[3];
                SQLiteDatabase db = taskDbHelper.getWritableDatabase();
                taskDbHelper.insertDB(db,task,hour,minute);

                taskList.add("One row is inserted!");
                return taskList;
            }
            else if(method.equals("get_info")){
                SQLiteDatabase db = taskDbHelper.getReadableDatabase();
                cursor = taskDbHelper.getInformation(db);
                while (cursor.moveToNext()) {
                int idx = cursor.getColumnIndex(TaskContract.COL_TASK_TITLE);
                int time = cursor.getColumnIndex(TaskContract.COL_TASK_TIME);
                // Toast.makeText(ctx,"time is" + time, Toast.LENGTH_LONG).show();
                 taskList.add(String.valueOf(idx)+String.valueOf(time));
                }
                cursor.close();
                db.close();
                return taskList ;
                }
                return taskList;

                }
                @Override
protected void onPreExecute() {

        super.onPreExecute();
        }


protected void onPostExecute(List<String> result) {
        // super.onPostExecute(aVoid);
        mTaskListView = (ListView) findViewById(R.id.list_todo);
       // mAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,result);
    ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(MainActivity.this,R.layout.item_todo,R.id.task_title,result);
                mTaskListView.setAdapter(arrayAdapter);
        }

@Override
protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
        }
        }
}

