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

import static com.example.admin.myapplication.Volume.DOWN;
import static com.example.admin.myapplication.Volume.UP;

public class MainActivity extends AppCompatActivity {

    float[] hsv = new float[3];

    Button btn;
    EditText input;
    LinearLayout layout;
    TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        hsv[0] = 0.0f; // Hue
        hsv[1] = 0.0f; // Saturation
        hsv[2] = 1.0f; // Value

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn = (Button)findViewById(R.id.Button);
        input = (EditText)findViewById(R.id.EditText);
        layout = (LinearLayout) findViewById(R.id.Linearlayout);
        tv = (TextView)findViewById(R.id.TextView);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                switch(v.getId()){
                    case R.id.Button:
                        copyText();
                        break;
                }
            }
        });

        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearText();
            }
        });

        layout.setOnTouchListener(new View.OnTouchListener(){

            @Override
            public boolean onTouch(View v, MotionEvent event) {

                onTouchEvent(event);
                return false;
            }
        });
    }

    public void clearText(){

        tv.setText("");
    }

    public void copyText(){

        String text = input.getText().toString();
        tv.setText(text);
        input.setText("");
    }

    public void changeBackgroundColour(Volume vol){
        if(vol == UP && hsv[2] < 1.0){
            hsv[2] += 0.1;
            layout.setBackgroundColor(Color.HSVToColor(hsv));
        }else if(vol == DOWN && hsv[2] > 0.0){
            hsv[2] -= 0.1;
            layout.setBackgroundColor(Color.HSVToColor(hsv));
        }
    }

    public void changeBackgroundColour(MotionEvent event){

        float eventX = event.getX();
        float eventY = event.getY();
        float height = layout.getHeight();
        float width = layout.getWidth();

        hsv[0] = eventY / height * 360; // (0 to 360)
        hsv[1] = eventX / width + 0.1f; // (0.1 to 1)
        layout.setBackgroundColor(Color.HSVToColor(hsv));
    }

    public boolean onTouchEvent(MotionEvent event){
        switch(event.getAction()){
            case (MotionEvent.ACTION_DOWN):
                Log.d("Touch", "Action was DOWN");
                layout.setBackgroundColor(Color.RED);
                return true;
            case (MotionEvent.ACTION_MOVE):
                Log.d("Touch","Action was MOVE");
                changeBackgroundColour(event);
                return true;
            case (MotionEvent.ACTION_UP):
                Log.d("Touch","Action was UP");
                layout.setBackgroundColor(Color.GREEN);
                return true;
            default: return super.onTouchEvent(event);
        }
    }

    public boolean onKeyDown(int keyCode, KeyEvent event){
        Log.d("on key", "onKeyDown function called");
        Volume vol;

        switch(keyCode){
            case KeyEvent.KEYCODE_VOLUME_UP:
                vol = UP;
                changeBackgroundColour(vol);
                Toast.makeText(getApplicationContext(), "Volume increased!!", Toast.LENGTH_LONG).show();
                return false;
            case KeyEvent.KEYCODE_VOLUME_DOWN:
                vol = DOWN;
                changeBackgroundColour(vol);
                Toast.makeText(getApplicationContext(), "Volume Decreased!!", Toast.LENGTH_LONG).show();
                return false;
        }

        return false;
    }
}
