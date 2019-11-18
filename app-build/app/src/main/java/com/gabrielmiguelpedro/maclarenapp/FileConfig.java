package com.gabrielmiguelpedro.maclarenapp;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class FileConfig implements Serializable {

    protected static final String FILE_NAME = "users.dat";
    protected static File f;

    public static User readUser() {
        User temp = null;
        try {
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(f.getAbsolutePath()));
            temp = (User) ois.readObject();
            ois.close();
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            return temp;
        }
    }

    public static void saveUser(User user) {
        System.out.println("a salvar...");
        try {
            FileOutputStream out = new FileOutputStream(FileConfig.f.getAbsolutePath());
            ObjectOutputStream oout = new ObjectOutputStream(out);
            oout.writeObject(user);
            System.out.println("File created");
            oout.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
