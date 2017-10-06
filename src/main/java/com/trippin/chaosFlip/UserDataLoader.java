package com.trippin.chaosFlip;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;

import com.google.gson.Gson;
import com.trippin.chasoFlip.model.UserData;

public class UserDataLoader {

    private static String path = "chaosFlipUserData.txt";
    private Gson gson = new Gson();

    public static void init(String path) {
        UserDataLoader.path = path;
    }

    public UserData load()
        throws IOException {

        if (!new File(path).exists())
            return new UserData();

        try (
            Reader reader = new InputStreamReader(new FileInputStream(path));
        ) {

            return gson.fromJson(reader, UserData.class);
        }
    }

    public void save(UserData userData)
        throws IOException {

        try (
            OutputStreamWriter out = new OutputStreamWriter(new FileOutputStream(path));
        ) {
            gson.toJson(userData, out);
        }
    }
}
