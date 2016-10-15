package com.cuit.talk.util;

import android.content.Context;
import android.content.SharedPreferences;

import com.cuit.talk.entity.Person;
import com.cuit.talk.entity.PersonSimple;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.FileHandler;
import java.util.zip.InflaterInputStream;

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
     * @return 返回所有用户信息
     */
    public List<PersonSimple> getAllData() {
        ArrayList<PersonSimple> list = new ArrayList<PersonSimple>();
        try {
            inputStream = context.openFileInput(FILE_NAME);
            ObjectInputStream readOb = new ObjectInputStream(inputStream);
            list = (ArrayList<PersonSimple>) readOb.readObject();
        } catch (FileNotFoundException e) {
            return list;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }


    /**
     * 该方法是保存用户信息
     * @param personSimple 用户信息
     */
    public void saveUserPass(PersonSimple personSimple) {
        ArrayList<PersonSimple> list = null;
        try {
            outputStream = context.openFileOutput(FILE_NAME, Context.MODE_PRIVATE);
            ObjectOutputStream writer = new ObjectOutputStream(outputStream);
            list = (ArrayList<PersonSimple>) getAllData();
            list.add(personSimple);
            writer.writeObject(list);
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 该方法用户更新用户的信息
     * @param personSimple 用户新的信息
     */
    public void updateUserPass(PersonSimple personSimple) {
        ArrayList<PersonSimple> list = null;
        try {
            outputStream = context.openFileOutput(FILE_NAME, Context.MODE_PRIVATE);
            ObjectOutputStream writer = new ObjectOutputStream(outputStream);
            list = (ArrayList<PersonSimple>) getAllData();
            for (int i = 0; i < list.size(); i++) {
                if(list.get(i).getNumber().equals(personSimple.getNumber())){
                    list.get(i).setPassword(personSimple.getPassword());
                }
            }
            writer.writeObject(list);
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
