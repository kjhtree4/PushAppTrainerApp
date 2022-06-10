package com.example.pushuptrainer;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import com.jaredrummler.materialspinner.MaterialSpinner;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ActivityP3Main extends Activity  {
    Button goalbutton;
    TextView nowcount;
    private MaterialSpinner counterspinner;
    myDBHelper myHelper;
    SQLiteDatabase sqlDB;
    myDBHelper2 myHelper2;
    SQLiteDatabase sqlDB2;
    @Override
    public void onBackPressed(){
        Intent intent=new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
    }
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_p3_main);
        myHelper = new myDBHelper(this);
        myHelper2 = new myDBHelper2(this);
        nowcount = findViewById(R.id.p3textView);
        counterspinner = (MaterialSpinner) findViewById(R.id.p3spinner);
        setspinner();
        counterspinner.setBackgroundColor(Color.argb(255,6,168,89));
        counterspinner.setTextColor(Color.WHITE);

        /*
        if(ActivityP2Main.p2context_main != null) {
            String str = ((ActivityP2Main) ActivityP2Main.p2context_main).SelectedCounterIndexString;
            nowcount.setText("현재개수 "+getnowcount(str) +"개");
            goalbutton.setText("목표개수 "+getmaxcount(str) +"개");
        }
        */
        //counterspinner.setSelectedIndex(getpreselected());

        goalbutton=(Button)findViewById((R.id.p3button2));
        goalbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showMAXPicker();
            }
        });
        counterspinner.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, Object item) {
                List<String> list = counterspinner.getItems();
                String str = list.get(counterspinner.getSelectedIndex());
                nowcount.setText("현재개수 "+getnowcount(str) +"개");
                goalbutton.setText("목표개수 "+getmaxcount(str) +"개");
            }
        });

        List<String> list = counterspinner.getItems();
        String str = list.get(counterspinner.getSelectedIndex());
        nowcount.setText("현재개수 "+getnowcount(str) +"개");
        goalbutton.setText("목표개수 "+getmaxcount(str) +"개");
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

    private void showMAXPicker (){


            int num = 0;
            final Dialog MAXPicker = new Dialog(this);
            MAXPicker.requestWindowFeature(Window.FEATURE_NO_TITLE);
        MAXPicker.setContentView(R.layout.activity_p3_select);
            Button okbtn = (Button) MAXPicker.findViewById(R.id.p3checkBtn);
            Button clbtn = (Button) MAXPicker.findViewById(R.id.p3cancleBtn);
            final NumberPicker np = (NumberPicker) MAXPicker.findViewById(R.id.p3numpicker);
            np.setMaxValue(1000);
            np.setMinValue(0);
            np.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
            np.setWrapSelectorWheel(false);
            np.setValue(num);
            np.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
                @Override
                public void onValueChange(NumberPicker picker, int oldVal, int newVal) {

                }
            });
            okbtn.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v){
                    if(ActivityP2Main.p2context_main != null) {
                        //String str = ((ActivityP2Main) ActivityP2Main.p2context_main).SelectedCounterIndexString;
                        //setmaxcount(str,np.getValue());
                        //goalbutton.setText("목표개수 "+Integer.toString(np.getValue()) +"개");
                    }
                        List<String> list = counterspinner.getItems();
                        String str = list.get(counterspinner.getSelectedIndex());
                        setmaxcount(str,np.getValue());
                        goalbutton.setText("목표개수 "+Integer.toString(np.getValue()) +"개");
                    MAXPicker.dismiss();
                }

            });
            clbtn.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v){
                    MAXPicker.dismiss();
                }
            });
            MAXPicker.show();
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
    private void setmaxcount(String counterindex,int count){
        sqlDB2 = myHelper2.getWritableDatabase();
        sqlDB2.execSQL("UPDATE CounterIndexDB SET GoalNumber = "+count+" WHERE Name = '"+counterindex+"';");
        sqlDB2.close();
    }

}

