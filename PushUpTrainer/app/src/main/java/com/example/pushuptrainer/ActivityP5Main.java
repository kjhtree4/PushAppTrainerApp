package com.example.pushuptrainer;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ActivityP5Main extends Activity {
    int p5count=0;    //사용할 카운트 값

    String p5SelectedCounterIndexString=((ActivityP2Main)ActivityP2Main.p2context_main).SelectedCounterIndexString; //Activity2Main에서 받아온 현재 리스트뷰 이름
    MyDBHelper myhelper;    //여기서 생성한 DB
    SQLiteDatabase sqlDB;

    long now = System.currentTimeMillis();//현재시각 가져오기
    java.util.Date mDate = new Date(now);//현재시각을 Date형식으로 고친다.

    SimpleDateFormat Year = new SimpleDateFormat("yyyy");//년
    SimpleDateFormat Month = new SimpleDateFormat("MM");//월
    SimpleDateFormat Date = new SimpleDateFormat("dd");//일

    //SimpleDateFormat 형식을 int타입으로 바꿈
    int intYear=Integer.parseInt(Year.format(mDate));
    int intMonth=Integer.parseInt(Month.format(mDate));
    int intDate=Integer.parseInt(Date.format(mDate));
    @SuppressLint("HandlerLeak")    //?? 이거 없어도 잘 돌아갔음
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_p5_main);
        myhelper=new MyDBHelper(this);

        Chronometer p5chronometer=(Chronometer) findViewById(R.id.chronometer1);
        Button p5btn1=(Button) findViewById(R.id.btn1);
        Button p5btn2=(Button) findViewById(R.id.btn2);
        TextView p5textView=(TextView) findViewById((R.id.tb));


        //초기값 0으로 설정하고 초시계도 바로 시작
        Intent intent = getIntent();
        final String p5CounterIndexName=intent.getStringExtra("CounterIndexName");

        try {
            sqlDB = myhelper.getReadableDatabase();
            Cursor cursor = sqlDB.rawQuery("SELECT * FROM PushUpDB WHERE Name=? AND Year=? AND Month=? AND Date=?;",
                    new String[]{p5SelectedCounterIndexString, Integer.toString(intYear), Integer.toString(intMonth), Integer.toString(intDate)});  //SELECT로 cursor 변수에 모든 데이터가 들어감

            if (!cursor.moveToFirst()) {    //중복되지 않은 데이터만 삽입
                sqlDB = myhelper.getWritableDatabase();
                sqlDB.execSQL("INSERT INTO PushUpDB VALUES ( '"
                        + p5SelectedCounterIndexString + "' , " //푸쉬업종류
                        + 0 + "," //실시한개수   //일부러 0으로 설정한 이유는 밑의 UPDATE를 통해 값을 누적시킬 것이기 때문
                        + intYear + "," //년
                        + intMonth + "," //월
                        + intDate + "," //일
                        + 0 +  //수행 시간
                        ");");
                sqlDB.close();
            }
        } catch (Exception e){
            e.printStackTrace();
        }

        p5textView.setText(p5CounterIndexName); //카운터인덱스 상단 위치
        p5chronometer.setBase(SystemClock.elapsedRealtime());   //시간 측정 시작
        p5chronometer.start();  //크로노미터 시작

        //카운트 버튼. 누르면 1씩 숫자가 올라간다.
        p5btn1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                p5count++;
                //p5textView.setText(p5count+"");
            }
        });

        //종료 버튼을 누르면 바로 메인 메뉴인 MainActivity로 돌아가고, 여기서 얻은 카운트 값을 전송. 동시에 시계도 멈추고
        //이 액티비티도 종료
        p5btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //String klk="rrfffgg";
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.putExtra("Number", p5count);
                p5chronometer.stop();
                int second= (int) (SystemClock.elapsedRealtime()-p5chronometer.getBase())/1000; //측정 시간을 정수로 바꾼다.

                //UPDATE를 이용해 값을 누적시키기
                try{
                    sqlDB=myhelper.getWritableDatabase();
                    //String sqlUpdate="UPDATE PushUpDBTable SET NO=2, NAME='ppotta2'" ;
                    //sqlDB.execSQL("UPDATE PushUpDBTable " + "SET "+"Number=Number"+p5count+", "+"Second=Second"+second+";");
                    sqlDB.execSQL("UPDATE PushUpDB SET Number=Number+?, Time=Time+? WHERE Name=? AND Year=? AND Month=? AND Date=?;",
                            new String[]{Integer.toString(p5count), Integer.toString(second), p5SelectedCounterIndexString ,Integer.toString(intYear), Integer.toString(intMonth), Integer.toString(intDate)});
                /*sqlDB.execSQL("INSERT INTO PushUpDBTable VALUES ( '"
                        + p5SelectedCounterIndexString + "' , " //푸쉬업종류
                        + p5count + "," //실시한개수
                        + intYear + "," //년
                        + intMonth+ "," //월
                        + intDate+ "," //월
                        + second +  //일
                        ");");*/

                    sqlDB.close();
                    Toast.makeText(getApplicationContext(), "입력됨", Toast.LENGTH_SHORT).show();
                } catch (Exception e){
                    e.printStackTrace();
                }

                startActivity(intent);  //액티비티 종료
                finish();
            }
        });
    }
    class MyDBHelper extends SQLiteOpenHelper { //DB, 테이블 생성
        public MyDBHelper(Context context) {
            super(context, "PushUpDB", null, 1);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            try{
                db.execSQL("CREATE TABLE PushUpDB (Name CHAR(20), Number Integer, Year Integer, Month Integer, Date Integer, Time Integer);");
            } catch (Exception e){
                e.printStackTrace();
            }
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS PushUpDB");
            onCreate(db);
        }
    }
}