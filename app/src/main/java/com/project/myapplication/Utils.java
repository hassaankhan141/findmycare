package com.project.myapplication;

import static android.content.Context.CLIPBOARD_SERVICE;
import static android.content.Context.MODE_PRIVATE;
import static java.lang.Integer.parseInt;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.text.Spannable;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.RelativeSizeSpan;
import android.util.Log;
import android.view.inputmethod.InputMethodManager;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;

import java.lang.reflect.Method;
import java.math.BigInteger;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Utils {
    public  static Context context;
    public static User userModel;
    public static ProgressDialog progressDialog;
    public  static String Server_Key="Key=AAAAM02K19Q:APA91bGFR_rXhK6ZtP-Ix4YGkqlij_YCPaLC57GALcg2JV3jPs1NtJRQGl9PGEmcJX897IxRtTjePSgQx2_T4aEm34BIixATH65S_88d28trZX2eOfR6IRPlQiLoew6Oz4n8yu_ptIK0";
    public Utils(Context context) {
        this.context=context;
    }
    public static void setToast(Activity context, String text){
        Toast.makeText(context,text,Toast.LENGTH_SHORT).show();
    }
    public static boolean isDark(Activity context){
        boolean is=context.getSharedPreferences("dark", MODE_PRIVATE)
                .getBoolean("isdark",false);
        return is;
    }
    public static void setDark(Activity context,boolean is){
        context.getSharedPreferences("dark", MODE_PRIVATE).edit()
                .putBoolean("isdark",is).commit();

    }
    public static boolean checkPermission(Activity activity){
        String callread= Manifest.permission.CALL_PHONE;

        if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(activity, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                ||ActivityCompat.checkSelfPermission(activity,callread) != PackageManager.PERMISSION_GRANTED
        ||ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED
        ||ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_NETWORK_STATE) != PackageManager.PERMISSION_GRANTED) {
            //  Toast.makeText(this, "false", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            //  Toast.makeText(this, "true", Toast.LENGTH_SHORT).show();
            return true;
        }
    }
    public static void getpermission(Activity activity){
        String exread= Manifest.permission.READ_EXTERNAL_STORAGE;
        String exwrite=Manifest.permission.WRITE_EXTERNAL_STORAGE;
        String callread= Manifest.permission.CALL_PHONE;
        String fineLocation = Manifest.permission.ACCESS_FINE_LOCATION;
        String coarseLocation = Manifest.permission.ACCESS_COARSE_LOCATION;
        String networkstateLocation = Manifest.permission.ACCESS_NETWORK_STATE;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            activity.requestPermissions(new String[]{fineLocation, coarseLocation, networkstateLocation,callread,exread,exwrite}, 200);
        }
    }


    public static void increaseFontSizeForPath(Spannable spannable, String path, float increaseTime) {
        int startIndexOfPath = spannable.toString().indexOf(path);
        spannable.setSpan(new RelativeSizeSpan(increaseTime), startIndexOfPath,
                startIndexOfPath + path.length(), 0);
    }
    public static void setFontSizeForPath(Spannable spannable, String path, int fontSizeInPixel) {
        int startIndexOfPath = spannable.toString().indexOf(path);
        spannable.setSpan(new AbsoluteSizeSpan(fontSizeInPixel), startIndexOfPath,
                startIndexOfPath + path.length(), 0);
    }
    public static String getTextSize(String text,int size) {
        return "<font size="+size+">"+text+" </font>";
       // return "<span style=\"size:"+size+"\" >"+text+"</span>";
    }
    public static void StoreString(Activity activity,String key,String value) {
        SharedPreferences mStudentPref=activity.getSharedPreferences("Userpref",MODE_PRIVATE);
        SharedPreferences.Editor prefsEditor = mStudentPref.edit();
        prefsEditor.putString(key, value);
        prefsEditor.commit();
    }
    public static String GetString(Activity activity,String key) {
        String json=activity.getSharedPreferences("Userpref",MODE_PRIVATE).getString(key,"");
        return json;
    }
    public static void StoreInPref(Activity activity) {
        SharedPreferences mStudentPref=activity.getSharedPreferences("Userpref",MODE_PRIVATE);
        SharedPreferences.Editor prefsEditor = mStudentPref.edit();
        prefsEditor.putString("MyObject", "true");
        prefsEditor.commit();
    }
    public static boolean GetStoreInPref(Activity activity) {
        String json=activity.getSharedPreferences("Userpref",MODE_PRIVATE).getString("MyObject","false");
        if (json.equals("false")){
            return false;
        }else {
            return  true;
        }
    }
    public static void ClearStoreInPref(Activity activity) {
        activity.getSharedPreferences("Userpref",MODE_PRIVATE).edit().clear().commit();
    }

    public static void showKeyboard(Activity activity){
        InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
    }

    public static void closeKeyboard(Activity activity){
        InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
    }

    public static String printDifference(Date startDate, Date endDate) {
        //milliseconds
        long different = endDate.getTime() - startDate.getTime();

        System.out.println("startDate : " + startDate);
        System.out.println("endDate : "+ endDate);
        System.out.println("different : " + different);

        long secondsInMilli = 1000;
        long minutesInMilli = secondsInMilli * 60;
        long hoursInMilli = minutesInMilli * 60;
        long daysInMilli = hoursInMilli * 24;

        long elapsedDays = different / daysInMilli;
        different = different % daysInMilli;

        long elapsedHours = different / hoursInMilli;
        different = different % hoursInMilli;

        long elapsedMinutes = different / minutesInMilli;
        different = different % minutesInMilli;

        long elapsedSeconds = different / secondsInMilli;

        System.out.printf(
                "%d days, %d hours, %d minutes, %d seconds%n",
                elapsedDays, elapsedHours, elapsedMinutes, elapsedSeconds);
        return ""+elapsedDays;
    }
    public static boolean checkTime(String Start_Time,String End_Time) {
        SimpleDateFormat dateFormat=new SimpleDateFormat("HHmm");
        Date date=null;
        String time="";
        try {
            date=dateFormat.parse(dateFormat.format(new Date()));
            long l=date.getTime();
            l=date.getTime()+(30*60*1000);
            String dateString = dateFormat.format(new Date(l));
            time=dateString;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        time = new SimpleDateFormat("HHmm").format(new Date());
        Log.e("cccc",time);
        String start=Start_Time;
        String end=End_Time;
        int dbstarttime=parseInt(start.substring(0,2)+start.substring(3,5));
        int dbendtime=parseInt(end.substring(0,2)+end.substring(3,5));
        int curtime = parseInt(time);

        if(dbstarttime > dbendtime) { if(curtime >= dbstarttime || curtime < dbendtime){ return true; } }
        else if (curtime >= dbstarttime && curtime < dbendtime) { return true; }

        return false;

    }
    public static boolean isStringInt(String s)
    {
        try
        {
            parseInt(s);
            return true;
        } catch (NumberFormatException ex)
        {
            return false;
        }
    }
    public static boolean isStringBigInteger(String s)
    {

        try
        {
            BigInteger bigInteger=new BigInteger(s);
            return true;
        } catch (NumberFormatException ex)
        {
            return false;
        }
    }
    public static void hideSpinnerDropDown(Spinner spinner) {
        try {
            Method method = Spinner.class.getDeclaredMethod("onDetachedFromWindow");
            method.setAccessible(true);
            method.invoke(spinner);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String cmp2(String i){
        String am="AM"; int hour=parseInt(i.substring(0,2));
        if(parseInt(i.substring(0,2))>11) { am="PM"; hour-=12;}
        if(hour==0) { hour=12;}
        return hour+i.substring(2,5)+" "+am;
    }
    public static void cmp2(String start,String end,Activity activity){
        @SuppressLint("SimpleDateFormat") String time = new SimpleDateFormat("HHmm").format(new Date());
        int dbstarttime=parseInt(start.substring(0,2)+start.substring(3,5));
        int dbendtime=parseInt(end.substring(0,2)+end.substring(3,5));
        int curtime = parseInt(time);

        if(dbstarttime > dbendtime) { if(curtime >= dbstarttime || curtime < dbendtime){  } }
        else if (curtime >= dbstarttime && curtime < dbendtime) {  }
        else { activity.finish(); }
    }

    public static String cmp1(String start,String end,String time){
        int dbstarttime=parseInt(start.substring(0,2)+start.substring(3,5));
        int dbendtime=parseInt(end.substring(0,2)+end.substring(3,5));
        int curtime = parseInt(time);

        if(dbstarttime > dbendtime) { if(curtime >= dbstarttime || curtime < dbendtime){ return "PLAY GAME"; } }
        else if (curtime >= dbstarttime && curtime < dbendtime) { return "PLAY GAME"; }
        return "TIME OVER";
    }
    public static void Copy_Content(Activity context, String content) {
        ClipboardManager clipboard = (ClipboardManager) context.getSystemService(CLIPBOARD_SERVICE);
        ClipData clip = ClipData.newPlainText("label",content);
        clipboard.setPrimaryClip(clip);
        Toast.makeText(context, "Copied Successfully", Toast.LENGTH_SHORT).show();
    }
    public static void Open_WhatsApp(Activity activity,String text){
        String url = "https://wa.me/92"+"3477490664"+"?text=Hi,";
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(url));
        activity.startActivity(i);
    }
}
