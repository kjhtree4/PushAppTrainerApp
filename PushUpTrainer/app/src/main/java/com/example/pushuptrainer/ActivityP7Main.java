package com.example.pushuptrainer;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Color;
import android.graphics.RenderNode;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.jaredrummler.materialspinner.MaterialSpinner;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class ActivityP7Main extends Activity {

    private LineChart chart;
    private Button selleft,selright,selcount,selcal,seltime;
    private MaterialSpinner counterspinner;
    private TextView timeview;
    private int intYear,intMonth;
    private int selec;
    private myDBHelper myHelper;
    private SQLiteDatabase sqlDB;
    private myDBHelper2 myHelper2;
    private SQLiteDatabase sqlDB2;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_p7_main);

        myHelper = new myDBHelper(this);
        myHelper2 = new myDBHelper2(this);
        //예시 데이터를 만들고 싶으면 주석 해제
        //maketestdata();

        /*
        주석 해제 후 기록 버튼 누르면 데이터 저장
        2년치에 대해 2가지 카운터 인덱스 데이터가 저장되므로 쿼리가 1500번가량 실행됨
        AVD가 느린경우 멈춤현상 발생가능 작동할때까지 잠깐 기다리면 정상작동 오류뜬 경우 wait누르기

         실행이 완료되었으면 종료후 주석처리한뒤 앱을 다시 빌드후 실행
         */

        chart = findViewById(R.id.p7linechart);
        counterspinner = (MaterialSpinner) findViewById(R.id.p7spinner);
        selleft =findViewById(R.id.p7button6);
        selright =findViewById(R.id.p7button8);
        selcount = findViewById(R.id.p7button3);
        selcal = findViewById(R.id.p7button4);
        seltime = findViewById(R.id.p7button5);
        timeview = findViewById(R.id.p7textview);
        counterspinner.setBackgroundColor(Color.argb(255,6,168,89));
        //counterspinner.setTextColor(Color.WHITE);

        selec = 1;

        long now = System.currentTimeMillis();//현재시각 가져오기
        Date mDate = new Date(now);//현재시각을 Date형식으로 고친다.

        SimpleDateFormat Year = new SimpleDateFormat("yyyy");//년
        SimpleDateFormat Month = new SimpleDateFormat("MM");//월

        intYear=Integer.parseInt(Year.format(mDate));
        intMonth=Integer.parseInt(Month.format(mDate));

        timeview.setText(Integer.toString(intYear)+"년 "+Integer.toString(intMonth)+"월");

        selleft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intMonth--;
                if(intMonth <= 0){
                    intYear--;
                    intMonth = 12;
                }
                timeview.setText(Integer.toString(intYear)+"년 "+Integer.toString(intMonth)+"월");
                update();

            }
        });
        selright.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intMonth++;
                if(intMonth >= 13){
                    intYear++;
                    intMonth = 1;
                }
                timeview.setText(Integer.toString(intYear)+"년 "+Integer.toString(intMonth)+"월");
                update();
            }
        });
        selcount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selec = 1;
                update();
            }
        });
        selcal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selec = 2;
                update();
            }
        });
        seltime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selec = 3;
                update();
            }
        });
        counterspinner.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, Object item) {
                update();
            }
        });

        setspinner();
        //counterspinner.setSelectedIndex(getpreselected());
        update();

    }

    private void update(){
        List<String> list = counterspinner.getItems();
        String counter = list.get(counterspinner.getSelectedIndex());
        Calendar cal = Calendar.getInstance();
        cal.set(intYear,intMonth-1,1);
        int last = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
        String what = "Number";
        int calc = 1;
        if(selec == 1) what = "Number";
        else if(selec == 3) what = "Time";
        else if(selec == 2){
            what = "Number";
            calc = 2;
        }
        else return;

        ArrayList<Entry> values = new ArrayList<>();
        sqlDB = myHelper.getWritableDatabase();
        for(int i = 1; i <= 31; i++){
            int val = calc * getdata(what,counter,intYear,intMonth,i);
            values.add(new Entry(i, val));
            if(i >last) break;
        }
        sqlDB.close();

        LineDataSet set1;
        if(selec == 1) what = "Number";
        else if(selec == 3) what = "Time";
        else if(selec == 2){
            what = "Calorie";
        }
        set1 = new LineDataSet(values, what);

        ArrayList<ILineDataSet> dataSets = new ArrayList<>();
        dataSets.add(set1); // add the data sets

        // create a data object with the data sets
        LineData data = new LineData(dataSets);

        // black lines and points

        set1.setColor(Color.argb(255,6,168,89));
        set1.setCircleColor(Color.argb(255,6,168,89));
        XAxis xAxis = chart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        chart.getAxisLeft().setAxisMinimum(0);
        chart.getAxisRight().setAxisMinimum(0);
        chart.getAxisLeft().setTextColor(Color.WHITE); // left y-axis
        chart.getXAxis().setTextColor(Color.WHITE);
        chart.getLegend().setTextColor(Color.WHITE);
        chart.getDescription().setTextColor(Color.WHITE);

        // set data
        chart.setData(data);
        chart.invalidate();

    }
    private int getdata(String what,String counter,int year,int month,int date){

        int result = 0;
        try{
            Cursor cursor = sqlDB.rawQuery("SELECT "+what+" FROM PushUpDB WHERE Name = '"+counter+"' AND Year = "+year+" AND Month = "+month+" AND Date = "+date+";",null);
            cursor.moveToNext();
            result = cursor.getInt(0);

        } catch(Exception e){

        }
        return result;
    }
    private int getpreselected(){
        if(ActivityP2Main.p2context_main != null) {
            String str = ((ActivityP2Main) ActivityP2Main.p2context_main).SelectedCounterIndexString;
            sqlDB2 = myHelper2.getWritableDatabase();
            Cursor cursor = sqlDB2.rawQuery("SELECT Name FROM CounterIndexDB;",null);
            int i = 0;
            while(cursor.moveToNext()){
                if(cursor.getString(0) == str) break;
                i++;
            }
            sqlDB2.close();
            return i;
        }
        return 0;
    }
    private void setspinner(){
        sqlDB2 = myHelper2.getWritableDatabase();
        List<String> items = new ArrayList<String>();
        items.clear();

        Cursor cursor = sqlDB2.rawQuery("SELECT Name FROM CounterIndexDB;",null);
        while(cursor.moveToNext()){
            items.add(cursor.getString(0));
        }
        sqlDB2.close();
        if(items.size() != 0) counterspinner.setItems(items);
        if(counterspinner.getItems()==null) counterspinner.setItems("없음");

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

    private void maketestdata(){
        sqlDB = myHelper.getWritableDatabase();
        String Name = "인클라인";
        sqlDB2 = myHelper2.getWritableDatabase();
        sqlDB2.execSQL("INSERT INTO CounterIndexDB VALUES ('"+Name+"',"
                + 0 +
                " );");
        sqlDB2.close();
        int year;
        int month;
        int date;
        for(year = 2020;year <=2021;year++){
            for(month = 1;month <=12;month++){
                for(date = 1;date <=31;date++){
                    inputtestdata(Name,(int)(Math.random()*100),year,month,date,(int)(Math.random()*200));
                }
            }
        }
        Name = "와이드";
        sqlDB2 = myHelper2.getWritableDatabase();
        sqlDB2.execSQL("INSERT INTO CounterIndexDB VALUES ('"+Name+"',"
                + 0 +
                " );");
        sqlDB2.close();

        for(year = 2020;year <=2021;year++){
            for(month = 1;month <=12;month++){
                for(date = 1;date <=31;date++){
                    inputtestdata(Name,(int)(Math.random()*100),year,month,date,(int)(Math.random()*200));
                }
            }
        }
        sqlDB.close();
    }


    private void inputtestdata(String Name,int number,int year,int month,int date,int time){

        sqlDB.execSQL("INSERT INTO PushUpDB VALUES ( '"
                + Name + "' , " //푸쉬업종류
                + number + "," //실시한개수
                + year + "," //년
                + month+ "," //월
                + date + "," //일
                + time +
                ");");
    }


}