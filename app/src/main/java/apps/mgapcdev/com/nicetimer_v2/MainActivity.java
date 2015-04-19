package apps.mgapcdev.com.nicetimer_v2;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Looper;
import android.os.Message;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.*;
import android.view.View.OnClickListener;
import android.view.*;


import android.os.Handler;

public class MainActivity extends Activity implements OnClickListener, Runnable{

    TextView tvTimer;
    Button bStart, bAddTime, bReset, bEdit, bSettings;
    String hour, min, sec;
    int h, m, s;
    int memH, memM, memS;
    int running = -1;
    MediaPlayer alarmMusic;
    Thread thread;
    TempHolder th;
    SharedPreferences getPrefs;
    Handler mHandler;
    int addTime;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setup();
        hour = min = sec = null;
        mHandler = new Handler() {
            public void handleMessage(Message msg) {
                setTime();
                setAddTime();
                if (running == -1)
                    bStart.setText("Start");
            }
        };
    }
    //Converts time to a string and sets it in the View
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
        tvTimer.setText(hour + ":" + min + ":" + sec);
    }
    //Check/converts time to a valid time
    private void checkTime() {
        // TODO Auto-generated method stub
        if (s >= 60) {
            s -= 60;
            m++;
        }
        if (m >= 60) {
            m -= 60;
            h++;
        }
        if (h >= 24) {
            h = 23;
            m = 59;
            s = 59;
        }
    }
    //Sets references to Views and OnclickListener
    private void setup() {
        //Try to get values from PreferenceManager
        getPrefs = PreferenceManager
                .getDefaultSharedPreferences(this);
        // TODO Auto-generated method stub
        bStart = (Button) findViewById(R.id.bStartTimer);
        bEdit = (Button) findViewById(R.id.bEditTimer);
        bReset = (Button) findViewById(R.id.bResetTimer);
        bStart.setOnClickListener(this);
        bReset.setOnClickListener(this);
        bEdit.setOnClickListener(this);
        bAddTime = (Button) findViewById(R.id.bAddTimer);
        bAddTime.setOnClickListener(this);

        //bSettings=(Button)findViewById(R.id.action_settings);
        //bSettings.setOnClickListener(this);

        tvTimer = (TextView) findViewById(R.id.tvRealTimer);
        th = new TempHolder();

        setAddTime();
    }

    @Override
    //Implementation of OnClickListener
    public void onClick(View v) {
        // TODO Auto-generated method stub
        switch (v.getId()) {
            case R.id.bStartTimer:
                if (running == -1) {
                    bStart.setText("Pause");
                    running = 1;

                    thread = new Thread(this);
                    thread.start();

                } else if (running == 1) {
                    bStart.setText("Start");
                    running = -1;
                }
                break;

            case R.id.bResetTimer:
                running = -1;
                bStart.setText("Start");

                s = memS;
                m = memM;
                h = memH;
                checkTime();
                setTime();
                break;
            case R.id.bEditTimer:
                running = -1;
                bStart.setText("Start");
                Intent i = new Intent(this, TimerInput.class);
                startActivity(i);
                break;
            case R.id.bAddTimer:
                s+=addTime;
                checkTime();
                setTime();
                //Intent i2 = new Intent(this, Prefs.class);
                //startActivity(i2);
                break;
            case R.id.action_settings:
                Intent i2 = new Intent(this, Prefs.class);
                startActivity(i2);
                break;
        }
    }
    //Implementation of Runnable, runs in a separate thread
    public void run(){
        // TODO Auto-generated method stub
        //Running Thread
        while (running == 1) {
            Log.d("Check", "Running is =1");
            try {
                s--;
                if (h == 0 && m == 0 && s == -1) {
                    //Default value
                    String alarmSound = getPrefs.getString("list", "Uninitialised");
                    String time = getPrefs.getString("pause", "1500");
                    int iTime = Integer.parseInt(time);

                    Log.d("Check", "alarmSound = " + alarmSound);
                    Log.d("Check", "addTime = " + addTime);
                    //Set alarm sound
                    if (alarmSound.equals("1")) {
                        alarmMusic = MediaPlayer.create(this,
                                R.raw.alarmsound);
                    } else if (alarmSound.equals("2")) {
                        alarmMusic = MediaPlayer.create(this,
                                R.raw.alarmsound3);
                    } else {
                        alarmMusic = MediaPlayer.create(this,
                                R.raw.alarmsound);
                    }

                    //Start alarm
                    alarmMusic.start();
                    Thread.sleep(iTime);
                    alarmMusic.reset();

                    running = -1;
                    h = memH;
                    m = memM;
                    s = memS;
                    //handler
                    mHandler.sendEmptyMessage(0);
                }

                if (s == -1) {
                    if (m > 0) {
                        m--;
                        s = 59;
                    } else if (m == 0) {
                        if (h > 0) {
                            h--;
                            m = 59;
                            s = 59;
                        }
                    }
                }
                //handler
                mHandler.sendEmptyMessage(0);
                Thread.sleep(1000);

            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        }
    }

    @Override
    //Gets time when activity resumes
    protected void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
        // get information from extra method
        boolean active = th.getActive();
        if (active) {
            h = memH = th.getHour();
            m = memM = th.getMin();
            s = memS = th.getSec();
            th.setActive(false);

            checkTime();
        }
        setTime();
        setAddTime();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()){
            case R.id.action_settings:
                Intent i3 = new Intent(this, Prefs.class);
                startActivity(i3);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
    public void setAddTime(){
        addTime = Integer.parseInt(getPrefs.getString("pref_addTime","10"));
        bAddTime.setText("+"+addTime+"s");

    }
}
