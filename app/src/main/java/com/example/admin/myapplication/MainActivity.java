package com.example.admin.myapplication;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    TextView tv = (TextView)findViewById(R.id.TextView);
    Button btn = (Button)findViewById(R.id.Button);
    EditText input = (EditText)findViewById(R.id.EditText);
    LinearLayout layout = (LinearLayout) findViewById(R.id.Linearlayout);

    float[] hsv = new float[3];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        hsv[0] = 0.0f;
        hsv[1] = 0.0f;
        hsv[2] = 1.0f;

        btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                switch (v.getId()){
                    case R.id.Button:
                        copyText();
                        break;
                }
            }
        });

        layout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch(event.getAction()){
                    case (MotionEvent.ACTION_DOWN):
                        Log.d("Touch", "Action was down");
                        changeBackgroundColour(event);
                        return true;
                    case (MotionEvent.ACTION_UP):
                        Log.d("Touch", "Action was up");
                        changeBackgroundColour(event);
                        return true;
                    case (MotionEvent.ACTION_MOVE):
                        Log.d("Touch", "Action was move");
                        changeBackgroundColour(event);
                        return true;
                    default: return MainActivity.super.onTouchEvent(event);

                }
            }
        });
    }

    public void changeBackgroundColour(MotionEvent event){
        float eventX = event.getX();
        float eventY = event.getY();
        float height = layout.getHeight();
        float width = layout.getWidth();
        hsv[0] = eventY / height * 360;
        hsv[1] = eventX / width + 0.1f;

        layout.setBackgroundColor(Color.HSVToColor(hsv));
    }

    public boolean onKeyDown(int keyCode, KeyEvent event){
        Log.d("on key", "onKeyDown function called");
        switch(keyCode){
            case KeyEvent.KEYCODE_VOLUME_UP:
                Toast.makeText(getApplicationContext(), "Volume increased", Toast.LENGTH_LONG).show();
                return false;
            case KeyEvent.KEYCODE_VOLUME_DOWN:
                Toast.makeText(getApplicationContext(), "Volume decreased!", Toast.LENGTH_LONG).show();
                return false;
        }

        return false;
    }

    public void copyText(){
        String text = input.getText().toString();
        tv.setText(text);
        input.setText("");
    }

    public void clearText(){
        tv.setText("");
    }
}
