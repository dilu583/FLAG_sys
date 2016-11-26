package com.example.dilu583.flag_sys.DbHelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;
import android.util.Log;

import com.example.dilu583.flag_sys.Common.Common;
import com.example.dilu583.flag_sys.Model.Question;
import com.example.dilu583.flag_sys.Model.Ranking;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by dilu583 on 10/24/2016.
 */
public class DbHelper extends SQLiteOpenHelper {

    private static String DB_NAME= "Mydb22.db";
    private static String TAG = "DataBaseHelper";
    private static String DB_PATH="";
    private SQLiteDatabase mDataBase;
    private Context mContext = null;

    public DbHelper(Context context) {
        super(context, DB_NAME,null ,1);
        if(android.os.Build.VERSION.SDK_INT >= 17){
            DB_PATH = context.getApplicationInfo().dataDir + "/databases/";
        }
        else
        {
            DB_PATH = "/data/data/" + context.getPackageName() + "/databases/";
        }
        openDataBase();
        System.out.println(DB_PATH);
        this.mContext = context;
    }

    public boolean openDataBase()
    {
        String myPath= DB_PATH+DB_NAME;
        mDataBase = SQLiteDatabase.openDatabase(myPath,null,SQLiteDatabase.OPEN_READONLY);
        return mDataBase != null;
    }

    public void copyDataBase() throws IOException
    {
        try{
            InputStream myInput = mContext.getAssets().open(DB_NAME);
            String outputFileName = DB_PATH + DB_NAME;
            OutputStream myOutput = new FileOutputStream(outputFileName);
            byte[] buffer = new byte[1024];
            int length;
            while((length=myInput.read(buffer))> 0 )
                myOutput.write(buffer,0,length);
            myOutput.flush();
            myOutput.close();
            myInput.close();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    private boolean checkDataBase()
    {
        File dbFile = new File(DB_PATH + DB_NAME);
        //Log.v("dbFile", dbFile + "   "+ dbFile.exists());
        return dbFile.exists();
    }

    public void createDataBase() throws IOException
    {
        boolean isDBExists = checkDataBase();
        if(isDBExists)
        {}
        else
        {
            this.getReadableDatabase();
            this.close();
            try{
                copyDataBase();
                Log.e(TAG, "createDatabase database created");
            }
            catch(IOException e)
            {
                e.printStackTrace();
            }
            }
        }

    @Override
    public  synchronized void close()
    {
        if(mDataBase!=null)
            mDataBase.close();
        super.close();
    }


    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public String getTablename()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c;
        String Id = "";
        try {
            c = db.rawQuery("SELECT name FROM sqlite_master WHERE type='table'", null);
            if (c == null) return null;
            do {

                String Image = c.getString(c.getColumnIndex("name"));
                Id += " " + Image;
            }
            while (c.moveToNext());
            c.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        db.close();
        return Id;
    }
    public List<Question> getQuestionMode(String mode)
    {
        List<Question> listQuestion = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c;
        int limit = 0;
        if(mode.equals(Common.MODE.EASY.toString()))
            limit =30;
        if(mode.equals(Common.MODE.MEDIUM.toString()))
            limit =50;
        if(mode.equals(Common.MODE.HARD.toString()))
            limit =100;
        if(mode.equals(Common.MODE.HARDEST.toString()))
            limit =200;

        try{
            c=db.rawQuery(String.format("SELECT *FROM Question ORDER BY Random() LIMIT %d",limit),null);
            if(c==null) return null;
            c.moveToFirst();
            do{
                int Id =c.getInt(c.getColumnIndex("ID"));
                String Image = c.getString(c.getColumnIndex("Image"));
                String AnswerA = c.getString(c.getColumnIndex("AnswerA"));
                String AnswerB = c.getString(c.getColumnIndex("AnswerB"));
                String AnswerC = c.getString(c.getColumnIndex("AnswerC"));
                String AnswerD = c.getString(c.getColumnIndex("AnswerD"));
                String CorrectAnswer = c.getString(c.getColumnIndex("CorrectAnswer"));

                Question question = new Question(Id, Image, AnswerA, AnswerB,AnswerC,AnswerD,CorrectAnswer);
                listQuestion.add(question);
            }
            while(c.moveToNext());
            c.close();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        db.close();
        return listQuestion;

    }
    public List<Question> getAllQuestion()
    {
        List<Question> listQuestion = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c;
        try{
            c=db.rawQuery("SELECT *FROM Question ORDER BY Random()",null);
            if(c==null) return null;
            c.moveToFirst();
            do{
                int Id =c.getInt(c.getColumnIndex("ID"));
                String Image = c.getString(c.getColumnIndex("Image"));
                String AnswerA = c.getString(c.getColumnIndex("AnswerA"));
                String AnswerB = c.getString(c.getColumnIndex("AnswerB"));
                String AnswerC = c.getString(c.getColumnIndex("AnswerC"));
                String AnswerD = c.getString(c.getColumnIndex("AnswerD"));
                String CorrectAnswer = c.getString(c.getColumnIndex("CorrectAnswer"));

                Question question = new Question(Id, Image, AnswerA, AnswerB,AnswerC,AnswerD,CorrectAnswer);
                listQuestion.add(question);
            }
            while(c.moveToNext());
            c.close();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        db.close();
        return listQuestion;

    }

    public Question getQuestion(String name)
    {
       Question question = new Question(0,"","","","","","");
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c;
        try{
            c=db.rawQuery("SELECT *FROM Question WHERE CorrectAnswer = ?",new String[]{name});
            if(c==null) return null;
            c.moveToFirst();

                int Id =c.getInt(c.getColumnIndex("ID"));
                String Image = c.getString(c.getColumnIndex("Image"));
                String AnswerA = c.getString(c.getColumnIndex("AnswerA"));
                String AnswerB = c.getString(c.getColumnIndex("AnswerB"));
                String AnswerC = c.getString(c.getColumnIndex("AnswerC"));
                String AnswerD = c.getString(c.getColumnIndex("AnswerD"));
                String CorrectAnswer = c.getString(c.getColumnIndex("CorrectAnswer"));

                question = new Question(Id, Image, AnswerA, AnswerB,AnswerC,AnswerD,CorrectAnswer);



            c.close();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        db.close();
        return question;

    }

    public void insetScore(int Score)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("Score",Score);
        db.insert("Ranking",null,contentValues);
    }

    public List<Ranking> getRanking()
    {
        List<Ranking> listRanking = new ArrayList<>();
        SQLiteDatabase db = this .getWritableDatabase();
        Cursor c;
        try
        {
            c=db.rawQuery("SELECT * FROM Ranking ORDER BY Score DESC",null);
            if(c==null) return null;
            c.moveToNext();

            do{
                int Id = c.getInt(c.getColumnIndex("id"));
                int score = c.getInt(c.getColumnIndex("Score"));

                Ranking ranking = new Ranking(Id, score);
                listRanking.add(ranking);

            }
            while(c.moveToNext());
            c.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        db.close();
        return listRanking;
    }
}
