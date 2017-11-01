package db;

/**
 * Created by BHAWYYA MITTAL on 28-10-2017.
 */



/**
 * Created by BHAWYYA MITTAL on 24-10-2017.
 */
        import android.content.ContentValues;
        import android.content.Context;
        import android.database.Cursor;
        import android.database.sqlite.SQLiteDatabase;
        import android.database.sqlite.SQLiteOpenHelper;
        import android.util.Log;

public class TaskDbHelper extends SQLiteOpenHelper {

    public TaskDbHelper(Context context) {
        super(context, TaskContract.DB_NAME, null, TaskContract.DB_VERSION);
        Log.d("DATABASE CREATIONS","Database is Created!!");
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TaskContract.TABLE + "(" +
                TaskContract.KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                TaskContract.COL_TASK_TITLE + " TEXT NOT NULL," +
                TaskContract.COL_TASK_TIME + " TEXT NOT NULL);";

        db.execSQL(createTable);
        Log.d("DATABASE CREATIONS","Table is Created!!");
    }

    public void insertDB(SQLiteDatabase db, String task, String hour, String minute){
        //
        String tm = String.valueOf(hour)+ ":" + String.valueOf(minute);
        //SQLiteDatabase db = mHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(TaskContract.COL_TASK_TITLE, task);
        values.put(TaskContract.COL_TASK_TIME, tm);
        db.insert(TaskContract.TABLE,
                null,
                values);
        Log.d("DATABASE CREATIONS","One row inserted!!");
        db.close();
    }

    public Cursor getInformation(SQLiteDatabase db){
        String[] data = {TaskContract.COL_TASK_TITLE,TaskContract.COL_TASK_TIME};
        Cursor cursor = db.query(TaskContract.TABLE,data,null,null,null,null,null);
        return cursor;

    }



    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TaskContract.TABLE);
        onCreate(db);
    }
}

