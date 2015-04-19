package apps.mgapcdev.com.nicetimer_v2;

/**
 * Created by Mathias on 28/08/2014.
 */
public class TempHolder {
    public static int h1, m1, s1 = 0;
    public static boolean active1 = false;

    public static void setValues(int h, int m, int s, boolean active){
        h1 = h;
        m1 = m;
        s1 = s;
        active1 = active;
    }
    public static void setActive(boolean active) {
        active1 = active;
    }
    public static int getHour(){
        return h1;
    }
    public static int getMin(){
        return m1;
    }
    public static int getSec(){
        return s1;
    }
    public static boolean getActive(){
        return active1;
    }

}
