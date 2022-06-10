package com.example.pushuptrainer;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ActivityP2SingerItemView extends LinearLayout {
    TextView textview;
    Button buttonDelete;
    Button buttonSelect;

    public ActivityP2SingerItemView(Context context) {
        super(context);
        init(context);
    }

    public ActivityP2SingerItemView(Context context, AttributeSet attrs){
        super(context, attrs);
        init(context);
    }

    public void init(Context context){
        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.activity_p2_singer_item,this,true);

        textview=(TextView) findViewById(R.id.textViewActivity2);
        buttonDelete=(Button)findViewById((R.id.button));
        buttonSelect=(Button)findViewById((R.id.button2));

    }

    public String getName(){
        return textview.getText().toString();
    }

    public void setName(String Name){
        textview.setText(Name);
    }

    public Button getDeleteButton() {
        return buttonDelete;
    }

    public Button getSelectButton() {
        return buttonSelect;
    }

    public void OnDeleteButtonClicked(View view) {

    }
}
