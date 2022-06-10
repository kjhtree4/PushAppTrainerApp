package com.example.pushuptrainer;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    Button p1btnset, p1record, p1start, p1recommend;   //사용할 위젯 객체 변수 선언
    TextView p1countIndex, p1goal, p1countText;
    myDBHelper myHelper;
    SQLiteDatabase sqlDB;
    myDBHelper2 myHelper2;
    SQLiteDatabase sqlDB2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //setTitle("PushUpTrainer");
        myHelper = new myDBHelper(this);
        myHelper2 = new myDBHelper2(this);

        p1btnset=(Button) findViewById(R.id.setting);   //연결
        p1record=(Button) findViewById(R.id.record);
        p1start=(Button) findViewById(R.id.startbutton);
        p1recommend=(Button) findViewById(R.id.recommend);

        p1countIndex =(TextView) findViewById(R.id.count_index);
        p1goal =(TextView) findViewById(R.id.pag);
        //p1countText =(TextView) findViewById(R.id.counting);

        //p1start를 제외하고 아무 기능 없음, 텍스트뷰인 p1countIndex, p1goal을 클릭하면 다른 액티비티로 이동
        p1btnset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(), ActivityP8Main.class);
                startActivity(intent);
            }
        });

        //아무 기능 없음, 다른 액티비티로 이동은 가능
        p1record.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(), ActivityP7Main.class);
                startActivity(intent);
            }
        });

        //아무 기능 없음, 다른 액티비티로 이동은 가능
        p1recommend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(), ActivityP6Main.class);
                startActivity(intent);
            }
        });

        //Activity5로 이동
        p1start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ActivityP4Main.class);
                startActivity(intent);
            }
        });

        //아무 기능 없음, 다른 액티비티로 이동은 가능
        p1countIndex.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(), ActivityP2Main.class);
                startActivity(intent);
            }
        });

        //아무 기능 없음, 다른 액티비티로 이동은 가능
        p1goal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(), ActivityP3Main.class);
                startActivity(intent);
            }
        });

        //Activity5에서 받은 값으로 자신의 카운트 값을 알 수 있다.
        //Intent inIntent = getIntent();
        //final int countValue=inIntent.getIntExtra("Number", 0);
        //p1countText.setText("갯수: "+countValue);
        if(ActivityP2Main.p2context_main != null) {
            String str = ((ActivityP2Main) ActivityP2Main.p2context_main).SelectedCounterIndexString;
            p1countIndex.setText(str);
            p1goal.setText("현재 "+getnowcount(str)+"개 / "+"목표 "+getmaxcount(str)+"개");
        }
    }
    private String getnowcount(String counterindex){

        long now = System.currentTimeMillis();//현재시각 가져오기
        Date mDate = new Date(now);//현재시각을 Date형식으로 고친다.

        SimpleDateFormat Year = new SimpleDateFormat("yyyy");//년
        SimpleDateFormat Month = new SimpleDateFormat("MM");//월
        SimpleDateFormat Date = new SimpleDateFormat("dd");//일

        //SimpleDateFormat 형식을 int타입으로 바꿈
        int intYear=Integer.parseInt(Year.format(mDate));
        int intMonth=Integer.parseInt(Month.format(mDate));
        int intDate=Integer.parseInt(Date.format(mDate));

        sqlDB = myHelper.getWritableDatabase();
        Cursor cursor = sqlDB.rawQuery("SELECT Number FROM PushUpDB WHERE Name = '"+counterindex+"'AND Year = "+ intYear +" AND Month = "+ intMonth +" AND Date = "+ intDate+";",null);

        cursor.moveToNext();
        sqlDB.close();
        try{
            if(cursor.getString(0) != null)
                return cursor.getString(0);
        }catch (Exception e){

        }

        return "0";

    }
    private String getmaxcount(String counterindex){
        sqlDB2 = myHelper2.getWritableDatabase();
        Cursor cursor = sqlDB2.rawQuery("SELECT GoalNumber FROM CounterIndexDB WHERE Name = '"+counterindex+"';",null);
        cursor.moveToNext();
        sqlDB2.close();
        try{
            if(cursor.getString(0) != null)
                return cursor.getString(0);
        }catch (Exception e){

        }

        return "0";

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
    public class myDBHelper2 extends SQLiteOpenHelper {
        public myDBHelper2(Context context) {
            super(context, "CounterIndexDB", null, 1);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL("CREATE TABLE  CounterIndexDB ( Name CHAR(20) PRIMARY KEY,GoalNumber INTEGER);");
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS CounterIndexDB");
            onCreate(db);
        }
    }
}
