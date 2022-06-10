package com.example.pushuptrainer;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ActivityP9Main extends AppCompatActivity {
    RadioButton now,all;
    Button deleteBtn;
    myDBHelper myHelper;
    SQLiteDatabase sqlDB;
    public void onBackPressed(){
        Intent intent=new Intent(getApplicationContext(), ActivityP8Main.class);
        startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_p9_main);
        now = findViewById(R.id.p9checkBox);
        all = findViewById(R.id.p9checkBox2);
        deleteBtn = findViewById(R.id.p9button5);
        myHelper = new myDBHelper(this);
        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                sqlDB = myHelper.getWritableDatabase();
                if(now.isChecked()){
                    long now = System.currentTimeMillis();//현재시각 가져오기
                    Date mDate = new Date(now);//현재시각을 Date형식으로 고친다.

                    SimpleDateFormat Year = new SimpleDateFormat("yyyy");//년
                    SimpleDateFormat Month = new SimpleDateFormat("MM");//월
                    SimpleDateFormat Date = new SimpleDateFormat("dd");//일

                    //SimpleDateFormat 형식을 int타입으로 바꿈
                    int intYear=Integer.parseInt(Year.format(mDate));
                    int intMonth=Integer.parseInt(Month.format(mDate));
                    int intDate=Integer.parseInt(Date.format(mDate));
                    sqlDB.execSQL("DELETE FROM PushUpDB WHERE Year = "+intYear+" AND Month = "+intMonth+" AND Date = "+intDate+";");
                }
                if(all.isChecked()){
                    sqlDB.execSQL("DELETE FROM PushUpDB;");
                }

                sqlDB.close();
                Toast.makeText(getApplicationContext(),"삭제 완료",Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }


    public class myDBHelper extends SQLiteOpenHelper {
        public myDBHelper(Context context) {
            super(context, "PushUpDB", null, 1);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL("CREATE TABLE  PushUpDB ( Name CHAR(20) , Number INTEGER, Year INTEGER, Month INTEGER, Date INTEGER, Time INTEGER);");
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS PushUpDB");
            onCreate(db);
        }
    }
}
