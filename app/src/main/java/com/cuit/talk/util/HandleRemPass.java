package com.cuit.talk.util;

import android.content.Context;
import android.content.SharedPreferences;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.FileHandler;

/**
 * Created by rice on 16-10-14.
 */
public class HandleRemPass {
    private Context context;
    private static final String FILE_NAME = "UserData";
    private FileOutputStream outputStream = null;
    private FileInputStream inputStream = null;

    /**
     * @return 一个HandleRemPass实例对象
     */
    public static HandleRemPass getInstance(Context context) {
        return new HandleRemPass(context);
    }

    private HandleRemPass(Context context) {
        this.context = context;
    }

    /**
     * @return 返回所有记住密码的用户数据
     */
    public String getAllData() {
        StringBuilder builder = new StringBuilder();
        try {
            inputStream = context.openFileInput(FILE_NAME);
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            String line = "";
            while ((line=reader.readLine())!=null){
                builder.append(line);
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return builder.toString();
    }

    /**
     *
     * @param userPass 用户帐号以及密码
     */
    public void saveUserPass(String userPass){
        String string = userPass + ";";
        try {
            outputStream = context.openFileOutput(FILE_NAME,Context.MODE_APPEND);
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outputStream));
            writer.write(string);
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     *
     * @param oldPass 用户久的密码
     * @param newPass 用户新的密码
     */
    public void updateUserPass(String oldPass,String newPass){
        String users = getAllData();
        String s = users.replaceAll(oldPass,newPass);
        try {
            outputStream = context.openFileOutput(FILE_NAME,Context.MODE_PRIVATE);
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outputStream));
            writer.write(s);
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
