package com.example.pushuptrainer;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.jaredrummler.materialspinner.MaterialSpinner;

import java.util.List;

public class ActivityP6Main extends Activity {
    //String Option1;//난이도
    String Option2;//훈련근육
    Button Button;//확인버튼
    TextView textView;//설명글
    ImageView imageView;//사진
    ImageView imageView2;//사진
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_p6_main);

        //Spinner spinner1=(Spinner)findViewById(R.id.spinner1);
        Spinner spinner2=(Spinner)findViewById(R.id.spinner2);

        /*
        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Option1=parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

         */

        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Option2=parent.getItemAtPosition(position).toString();
                ((TextView) parent.getChildAt(0)).setTextColor(Color.WHITE);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public void OnAcceptButtonClicked(View view) {
        textView=(TextView)findViewById(R.id.textView);
        imageView=(ImageView)findViewById(R.id.imageView);
        imageView2=(ImageView)findViewById(R.id.imageView2);

        switch (Option2){
            case "대흉근" :
                textView.setText("<클래식푸쉬업>\n" +
                        "1. 손바닥을 어깨너비만큼 벌리는것이 적당합니다.\n" +
                        "2. 손가락은 앞방향을 가리키고 있어야합니다.\n" +
                        "3. 팔과 몸의 각도는 45도가 적당합니다.\n" +
                        "4.상체, 엉덩이, 다리가 휘어짐 없이 곧은 직선을 이루어야 합니다.\n" +
                        "5.시선은 아래로 향합니다.\n" +
                        "6.팔을 굽힐때에는 몸 전체가 아래로 내려올 수 있도록 합니다.\n" +
                        "7.팔을 펼때에는 몸전체가 위로 올라올 수 있도록 합니다.");
                imageView.setImageResource(R.drawable.classic1);
                imageView2.setImageResource(R.drawable.classic2);
                break;
            case "바깥가슴" :
                textView.setText("<와이드 그립 푸쉬업>\n" +
                        "1. 손바닥을 어깨너비의 2배만큼 벌리는 것이 적당합니다.\n" +
                        "2. 손가락은 앞방향을 가리키고 있어야합니다.\n" +
                        "3. 팔과 몸의 각도는 80도가 적당합니다.\n" +
                        "4.상체, 엉덩이, 다리가 휘어짐 없이 곧은 직선을 이루어야 합니다.\n" +
                        "5.시선은 아래로 향합니다.\n" +
                        "6.팔을 굽힐때에는 몸 전체가 아래로 내려올 수 있도록 합니다.\n" +
                        "7.팔을 펼때에는 몸전체가 위로 올라올 수 있도록 합니다.");
                imageView.setImageResource(R.drawable.wide1);
                imageView2.setImageResource(R.drawable.wide2);
                break;
            case "안쪽가슴" :
                textView.setText("<클로즈 그립 푸쉬업>\n" +
                        "1. 손바닥을 어깨너비보다 한뼘 좁게 벌리는것이 적당합니다.\n" +
                        "2. 손가락은 앞방향을 가리키고 있어야합니다.\n" +
                        "3. 팔과 몸의 각도는 10도가 적당합니다.\n" +
                        "4.상체, 엉덩이, 다리가 휘어짐 없이 곧은 직선을 이루어야 합니다.\n" +
                        "5.시선은 아래로 향합니다.\n" +
                        "6.팔을 굽힐때에는 몸 전체가 아래로 내려올 수 있도록 합니다.\n" +
                        "7.팔을 펼때에는 몸전체가 위로 올라올 수 있도록 합니다.\n");
                imageView.setImageResource(R.drawable.close1);
                imageView2.setImageResource(R.drawable.close2);
                break;
            case "윗가슴" :
                textView.setText("<디클라인 푸쉬업>\n" +
                        "1. 하체를 상체보다 높은 곳에 위치시킵니다.\n" +
                        "2. 손바닥을 어깨너비만큼 벌리는것이 적당합니다.\n" +
                        "3. 손가락은 앞방향을 가리키고 있어야합니다.\n" +
                        "4. 팔과 몸의 각도는 45도가 적당합니다.\n" +
                        "5.상체, 엉덩이, 다리가 휘어짐 없이 곧은 직선을 이루어야 합니다.\n" +
                        "6.시선은 아래로 향합니다.\n" +
                        "7.팔을 굽힐때에는 몸 전체가 아래로 내려올 수 있도록 합니다.\n" +
                        "8.팔을 펼때에는 몸전체가 위로 올라올 수 있도록 합니다.");
                imageView.setImageResource(R.drawable.decline1);
                imageView2.setImageResource(R.drawable.decline2);
                break;
            case "아랫가슴" :
                textView.setText("<인클라인푸쉬업>\n" +
                        "1. 상체를 하체보다 높은 곳에 위치시킵니다.\n" +
                        "2. 손바닥을 어깨너비만큼 벌리는것이 적당합니다.\n" +
                        "3. 손가락은 앞방향을 가리키고 있어야합니다.\n" +
                        "4. 팔과 몸의 각도는 45도가 적당합니다.\n" +
                        "5.상체, 엉덩이, 다리가 휘어짐 없이 곧은 직선을 이루어야 합니다.\n" +
                        "6.시선은 아래로 향합니다.\n" +
                        "7.팔을 굽힐때에는 몸 전체가 아래로 내려올 수 있도록 합니다.\n" +
                        "8.팔을 펼때에는 몸전체가 위로 올라올 수 있도록 합니다.\n");
                imageView.setImageResource(R.drawable.incline1);
                imageView2.setImageResource(R.drawable.incline2);
                break;
            case "어깨" :
                textView.setText("<파이크 푸쉬업>\n" +
                        "1. 엉덩이를 치켜세운 후 수행하는 동작으로, 머리를 땅으로 꽂는다는 듯이 운동을 합니다\n" +
                        "2. 손바닥을 어깨너비만큼 벌리는것이 적당합니다.\n" +
                        "3. 손가락은 앞방향을 가리키고 있어야합니다.\n" +
                        "4. 팔과 몸의 각도는 45도가 적당합니다.\n" +
                        "5.상체, 엉덩이, 다리가 휘어짐 없이 곧은 직선을 이루어야 합니다.\n" +
                        "6.시선은 아래로 향합니다.\n" +
                        "7.팔을 굽힐때에는 머리를 땅으로 꽂는 듯 몸 전체가 내려올 수 있도록 합니다.\n" +
                        "8.팔을 펼때에는 몸전체가 위로 올라올 수 있도록 합니다.\n");
                imageView.setImageResource(R.drawable.pike1);
                imageView2.setImageResource(R.drawable.pike2);
                break;

        }
    }
}
