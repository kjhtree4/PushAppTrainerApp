package com.example.pushuptrainer;


import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


public class ActivityP2Input extends AppCompatActivity {

    EditText editText;
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_p2_input);



    }


    public void onAcceptButtonClicked(View view) {
        editText=(EditText)findViewById(R.id.p2CountIndexInput);
        ((ActivityP2Main)ActivityP2Main.p2context_main).Add_p2CounterIndexString(editText.getText().toString());
        //메인액티비티의 카운터인덱스 스트링 값을 에디트박스에 입력한 값으로 바꿈

        finish();
    }
}
