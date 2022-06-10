package com.example.pushuptrainer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


public class ActivityP2Main extends AppCompatActivity {

    myDBHelper myHelper;
    SQLiteDatabase sqlDB;
    ListView p2listView;
    SingerAdapter p2adapter;
    Button button;



    public static Context p2context_main;//다른 액티비티에서 이 액티비티의 변수나 함수 쓸수 있도록
    public String CounterIndexStringToAdd;//카운터인덱스를 추가하기 위해 임시로 쓰여지는 변수
    public String SelectedCounterIndexString;//사용자가 선택한 카운터인덱스의 이름을 넣어놓은 변수

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_p2_main);

        p2context_main=this;//다른 액티비티에서 이 액티비티의 변수나 함수 쓸수 있도록

        button=(Button)findViewById((R.id.AddButton));

        p2adapter= new SingerAdapter();
        p2adapter.addItem(new ActivityP2SingerItem("인클라인"));

        p2listView =(ListView)findViewById(R.id.listView);
        p2listView.setAdapter(p2adapter);

        myHelper = new myDBHelper(this);

        UpdateListView();

        p2listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                // SingerItem item=(SingerItem)p2adapter.getItem(position);
                Toast.makeText(getApplicationContext(), "눌러짐",
                        Toast.LENGTH_SHORT).show();
            }
        });//이 부분이 되야지 삭제기능을 할 수있는데 왜 안되는지 아직 못찾음



    }

    public class myDBHelper extends SQLiteOpenHelper {
        public myDBHelper(Context context) {
            super(context, "CounterIndexDB", null, 1);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            //db.execSQL("CREATE TABLE  PushUpDB ( Name CHAR(20), Number INTEGER, Year INTEGER, Month INTEGER, Date INTEGER, Time INTEGER);");
            db.execSQL("CREATE TABLE  CounterIndexDB ( Name CHAR(20)PRIMARY KEY, GoalNumber INTEGER);");
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            //db.execSQL("DROP TABLE IF EXISTS PushUpDB");
            db.execSQL("DROP TABLE IF EXISTS CounterIndexDB");
            onCreate(db);
        }
    }

    public void OnAddButtonClicked(View view) {//추가하기 버튼 눌렀을때 p2InputActivity가 대화상자 형태로 열린다.
        Intent intent = new Intent(getApplicationContext(), ActivityP2Input.class);
        startActivity(intent);
    }

    public void OnDeleteButtonClicked(View view) {

    }


    class SingerAdapter extends BaseAdapter{
        ArrayList<ActivityP2SingerItem> items = new ArrayList<ActivityP2SingerItem>();

        @Override
        public int getCount() {
            return items.size();
        }
        @Override
        public Object getItem(int position) {
            return items.get(position);
        }
        @Override
        public long getItemId(int position) {
            return position;
        }
        @Override
        public View getView(int position, View convertView, ViewGroup viewGroup) {
            ActivityP2SingerItemView view=new ActivityP2SingerItemView(getApplicationContext());
            ActivityP2SingerItem item = items.get(position);
            Button DeleteButton = view.getDeleteButton();
            Button SelectButton = view.getSelectButton();
            final String Name=item.getName();

            DeleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(getApplicationContext(), "눌러짐"+Name,
                            Toast.LENGTH_SHORT).show();

                    DeleteItem(Name);
                    UpdateListView();
                }
            });

            SelectButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(getApplicationContext(), "눌러짐"+Name,
                            Toast.LENGTH_SHORT).show();

                    SelectedCounterIndexString=Name;
                    Intent intent= new Intent(getApplicationContext(), ActivityP4Main.class);
                    intent.putExtra("CountIndexName", Name);
                    startActivity(intent);
                }
            });


            view.setName(item.getName());

            return view;
        }

        public void addItem(ActivityP2SingerItem item){
            items.add(item);
        }
        public void clear(){
            items.clear();
        }
    }

    public void Add_p2CounterIndexString(String string){//p2InputActivity에서 실행됨, 거기서 입력한 값 여기의 리스트에 add 시켜줌
        CounterIndexStringToAdd =string;
        AddCounterIndex();
    }

    public void AddCounterIndex(){//아이템이 추가하고 갱신하는 함수
        p2adapter.addItem(new ActivityP2SingerItem((CounterIndexStringToAdd)));
        p2adapter.notifyDataSetChanged();
        //리스트 변수에 CounterIndexString을 추가시키고 그것이 리스트뷰에 나오도록 업데이트 시켜준다.

        /*
        long now = System.currentTimeMillis();//현재시각 가져오기
        Date mDate = new Date(now);//현재시각을 Date형식으로 고친다.

        SimpleDateFormat Year = new SimpleDateFormat("yyyy");//년
        SimpleDateFormat Month = new SimpleDateFormat("MM");//월
        SimpleDateFormat Date = new SimpleDateFormat("dd");//일

        //SimpleDateFormat 형식을 int타입으로 바꿈
        int intYear=Integer.parseInt(Year.format(mDate));
        int intMonth=Integer.parseInt(Year.format(mDate));
        int intDate=Integer.parseInt(Year.format(mDate));



        //디비에 (푸쉬업이름, 개수0, 현재 년, 현재 월, 현재 일, 걸린시간 0) 추가
        sqlDB = myHelper.getWritableDatabase();

        sqlDB.execSQL("INSERT INTO PushUpDB VALUES ( '"
                + CounterIndexStringToAdd + "' , " //푸쉬업종류
                + 0 + "," //실시한개수
                + intYear + "," //년
                + intMonth+ "," //월
                + intDate + "," //일
                + 0 +
                ");");

        sqlDB.close();
         */

        sqlDB = myHelper.getWritableDatabase();
        sqlDB.execSQL("INSERT INTO CounterIndexDB VALUES ( '"
                + CounterIndexStringToAdd + "' , " //푸쉬업종류
                + 0 +  //목표개수
                ");");

        sqlDB.close();

        Toast.makeText(getApplicationContext(), "입력됨",
                Toast.LENGTH_SHORT).show();

    }

    public void UpdateListView(){
        sqlDB = myHelper.getReadableDatabase();
        Cursor cursor;
        //cursor = sqlDB.rawQuery("SELECT Name FROM PushUpDB;", null);
        cursor = sqlDB.rawQuery("SELECT Name FROM CounterIndexDB;", null);

        String Name;

        p2adapter.clear();
        while (cursor.moveToNext()) {
            Name = cursor.getString(0);

            p2adapter.addItem(new ActivityP2SingerItem((Name)));
            p2adapter.notifyDataSetChanged();
        }

        cursor.close();
        sqlDB.close();
    }

    public void DeleteItem(String Name){
        String DeleteName=Name;
        sqlDB = myHelper.getReadableDatabase();
        //sqlDB.execSQL("DELETE FROM PushUpDB WHERE Name='" +DeleteName+ "';");
        sqlDB.execSQL("DELETE FROM CounterIndexDB WHERE Name='" +DeleteName+ "';");
        sqlDB.close();
        Toast.makeText(getApplicationContext(), DeleteName+"삭제됨",
                Toast.LENGTH_SHORT).show();
    }
}

