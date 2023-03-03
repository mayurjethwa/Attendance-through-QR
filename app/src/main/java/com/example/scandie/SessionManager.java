package com.example.scandie;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.Toast;

import java.util.HashMap;

public class SessionManager {

    SharedPreferences userSession;
    SharedPreferences.Editor editor;
    Context context;

    private static final String IS_LOGIN = "IsLoggedIn";

    public static final String KEY_NAME = "name";
    public static final String KEY_EMAIL = "email";
    public static final String KEY_REGNO = "regno";
    public static final String KEY_ROLLNO = "roll";
    public static final String KEY_PASSWORD = "password";
    public static final String KEY_BRANCH = "branch";
    public static final String KEY_YEAR = "year";


    public SessionManager(Context _context){
        context = _context;
        userSession = _context.getSharedPreferences("userLoginSession",Context.MODE_PRIVATE);
        editor = userSession.edit();
    }

    public void createLoginSession(String name,String email,String regno,String roll,String password, String branch, String year){

        editor.putBoolean(IS_LOGIN,true);

        editor.putString(KEY_NAME,name);
        editor.putString(KEY_EMAIL,email);
        editor.putString(KEY_REGNO,regno);
        editor.putString(KEY_ROLLNO,roll);
        editor.putString(KEY_PASSWORD,password);
        editor.putString(KEY_BRANCH,branch);
        editor.putString(KEY_YEAR,year);

        editor.commit();

    }

    public HashMap<String,String> getUserDetailFromSession(){
        HashMap<String,String> userData = new HashMap<String,String>();

        userData.put(KEY_NAME,userSession.getString(KEY_NAME,null));
        userData.put(KEY_EMAIL,userSession.getString(KEY_EMAIL,null));
        userData.put(KEY_REGNO,userSession.getString(KEY_REGNO,null));
        userData.put(KEY_ROLLNO,userSession.getString(KEY_ROLLNO,null));
        userData.put(KEY_PASSWORD,userSession.getString(KEY_PASSWORD,null));
        userData.put(KEY_BRANCH,userSession.getString(KEY_BRANCH,null));
        userData.put(KEY_YEAR,userSession.getString(KEY_YEAR,null));

        return userData;
    }

    public boolean checkLogin(){
        if(userSession.getBoolean(IS_LOGIN,false)){
            return true;
        }else{
            return false;
        }
    }

    public void logoutUserFromSession(){
        editor.clear();
        editor.commit();
    }


}
