package com.tork;

import android.app.Activity;
import android.content.SharedPreferences;

/**
 * Created by steve on 10/13/16.
 */
public class AccountPreferences {
    Activity activity;

    public AccountPreferences(Activity activity) {
        this.activity = activity;
    }

    public Boolean getLoggedIn() {
        SharedPreferences spref2 = activity.getSharedPreferences("ACCOUNT", activity.MODE_PRIVATE);
        Boolean logged = spref2.getBoolean("logged", false);
        return logged;
    }

    public void setLoggedIn(Boolean logged) {
        SharedPreferences spref2 = activity.getSharedPreferences("ACCOUNT", activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = spref2
                .edit();
        editor.putBoolean("logged", logged);
        editor.apply();
    }

    public String getUserName() {
        SharedPreferences spref2 = activity.getSharedPreferences("ACCOUNT", activity.MODE_PRIVATE);
        String user_name = spref2.getString("user_name", "");
        return user_name;
    }

    public void setUserName(String user_name) {
        SharedPreferences spref2 = activity.getSharedPreferences("ACCOUNT", activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = spref2
                .edit();
        editor.putString("user_name", user_name);
        editor.apply();
    }

    public String getImageUrl() {
        SharedPreferences spref2 = activity.getSharedPreferences("ACCOUNT", activity.MODE_PRIVATE);
        String image_url = spref2.getString("image_url", "");
        return image_url;
    }

    public void setImageUrl(String image_url) {
        SharedPreferences spref2 = activity.getSharedPreferences("ACCOUNT", activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = spref2
                .edit();
        editor.putString("image_url", image_url);
        editor.apply();
    }

    public String getEmail() {
        SharedPreferences spref2 = activity.getSharedPreferences("ACCOUNT", activity.MODE_PRIVATE);
        String email = spref2.getString("email", "");
        return email;
    }

    public void setEmail(String email) {
        SharedPreferences spref2 = activity.getSharedPreferences("ACCOUNT", activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = spref2
                .edit();
        editor.putString("email", email);
        editor.apply();
    }
}
