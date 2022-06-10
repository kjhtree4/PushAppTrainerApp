package com.example.pushuptrainer;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class ActivityP8Main extends Activity {
    public void onBackPressed(){
        Intent intent=new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
    }
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_p8_main);


        Button p8btn1 = (Button) findViewById(R.id.p8btn1);
        p8btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),ActivityP9Main.class);
                startActivity(intent);
            }
        });

        //p8btn2버튼은 [버전정보 / 릴리즈노트]로 해당버튼을 클릭 시 엑티비티10로 넘어가는 코드입니다
        Button p8btn2 = (Button) findViewById(R.id.p8btn2);
        p8btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),ActivityP10Main.class);
                startActivity(intent);
            }
        });

    }
}
