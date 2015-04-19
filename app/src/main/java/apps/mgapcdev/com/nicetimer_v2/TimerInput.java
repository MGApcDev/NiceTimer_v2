package apps.mgapcdev.com.nicetimer_v2;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.*;

public class TimerInput extends Activity implements OnClickListener {
    TextView Timer;
    Button num1, num2, num3, num4, num5, num6, num7, num8, num9, num0, numC,
            numS, numD;
    int h, m, s;
    String hour, min, sec;
    TempHolder th;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_input);
        setup();
        h = m = s = 0;
        hour = min = sec = null;
    }

    private void setup() {
        // TODO Auto-generated method stub
        Timer = (TextView) findViewById(R.id.tvTimerInput);

        num1 = (Button) findViewById(R.id.bNumber1);
        num1.setOnClickListener(this);
        num2 = (Button) findViewById(R.id.bNumber2);
        num2.setOnClickListener(this);
        num3 = (Button) findViewById(R.id.bNumber3);
        num3.setOnClickListener(this);
        num4 = (Button) findViewById(R.id.bNumber4);
        num4.setOnClickListener(this);
        num5 = (Button) findViewById(R.id.bNumber5);
        num5.setOnClickListener(this);
        num6 = (Button) findViewById(R.id.bNumber6);
        num6.setOnClickListener(this);
        num7 = (Button) findViewById(R.id.bNumber7);
        num7.setOnClickListener(this);
        num8 = (Button) findViewById(R.id.bNumber8);
        num8.setOnClickListener(this);
        num9 = (Button) findViewById(R.id.bNumber9);
        num9.setOnClickListener(this);
        num0 = (Button) findViewById(R.id.bNumber0);
        num0.setOnClickListener(this);

        numC = (Button) findViewById(R.id.bNumberCancel);
        numC.setOnClickListener(this);
        numD = (Button) findViewById(R.id.bNumberDelete);
        numD.setOnClickListener(this);
        numS = (Button) findViewById(R.id.bNumberStart);
        numS.setOnClickListener(this);
        th = new TempHolder();
    }

    @Override
    protected void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
        finish();
    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        switch (v.getId()) {
            case R.id.bNumber0:
                insertNum(0);
                break;
            case R.id.bNumber1:
                insertNum(1);
                break;
            case R.id.bNumber2:
                insertNum(2);
                break;
            case R.id.bNumber3:
                insertNum(3);
                break;
            case R.id.bNumber4:
                insertNum(4);
                break;
            case R.id.bNumber5:
                insertNum(5);
                break;
            case R.id.bNumber6:
                insertNum(6);
                break;
            case R.id.bNumber7:
                insertNum(7);
                break;
            case R.id.bNumber8:
                insertNum(8);
                break;
            case R.id.bNumber9:
                insertNum(9);
                break;
            case R.id.bNumberCancel:
                finish();
                break;
            case R.id.bNumberDelete:

                break;
            case R.id.bNumberStart:
                Intent i = new Intent(this, MainActivity.class);
                i.putExtra("hour", h);
                i.putExtra("min", m);
                i.putExtra("sec", s);
                //instead of starting a new activity, pass information in a separate method

                th.setValues(h, m, s, true);
//			startActivity(i);
//			overridePendingTransition(0, 0);

                finish();
                break;
        }
    }

    private void insertNum(int i) {
        // TODO Auto-generated method stub
        if (h == 0) {
            if (m == 0) {
                if (s == 0) {
                    s = i;
                } else if (s <= 9) {
                    s = s * 10 + i;
                } else if (s > 9) {
                    m = (s - (s % 10)) / 10;
                    while (s > 9)
                        s -= 10;
                    s = s * 10 + i;
                }

            } else if (m <= 9) {
                m = m * 10 + (s - (s % 10)) / 10;
                while (s > 9)
                    s -= 10;
                s = s * 10 + i;
            } else if (m > 9) {
                h = (m - (m % 10)) / 10;
                while (m > 9)
                    m -= 10;
                m = m * 10 + ((s - (s % 10)) / 10);
                while (s > 9)
                    s -= 10;
                s = s * 10 + i;
            }

        }else if(h<=9){
            h = h * 10 + (m - (m % 10)) / 10;
            while (m > 9)
                m -= 10;
            m = m * 10 + ((s - (s % 10)) / 10);
            while (s > 9)
                s -= 10;
            s = s * 10 + i;
        }
        setTime();
    }
    //Changes hour, min and sec to a string
    //Text is then set to the string
    private void setTime() {
        // TODO Auto-generated method stub
        if (h <= 9)
            hour = "0" + h;
        else
            hour = "" + h;
        if (m <= 9)
            min = "0" + m;
        else
            min = "" + m;
        if (s <= 9)
            sec = "0" + s;
        else
            sec = "" + s;
        Timer.setText(hour + ":" + min + ":" + sec);
    }
}


