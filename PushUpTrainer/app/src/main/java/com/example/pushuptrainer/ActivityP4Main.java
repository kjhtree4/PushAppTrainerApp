package com.example.pushuptrainer;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;


public class ActivityP4Main extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_p4_main);

        Button p4btn1= (Button) findViewById(R.id.p4btn1);
        p4btn1.setText("카운트 인덱스");
        p4btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),ActivityP2Main.class);
                startActivity(intent);
            }
        });
        Intent intent= getIntent();
        final String p4CountIndexName=intent.getStringExtra("CountIndexName");
        if(p4CountIndexName!=null)
            p4btn1.setText(p4CountIndexName);
        else
            p4btn1.setText("푸시업 선택");

        Button p4btn2= (Button) findViewById(R.id.p4btn2);
        p4btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),ActivityP5Main.class);
                intent.putExtra("CounterIndexName", p4CountIndexName);
                startActivity(intent);
            }
        });
    }
}
