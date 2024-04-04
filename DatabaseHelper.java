package com.example.esemeny;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class DatabaseHelper extends SQLiteOpenHelper {

    // Adatbázis verzió és név
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "Naplo.db";
    private final Context context;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Itt hozhatod létre az adatbázis tábláit
    }
    // Beolvasás a Dokumentumok mappábból a program megnyitásakor
    public void importDatabaseFromDocuments() {
        File externalFilesDir = context.getExternalFilesDir(null);
        if (externalFilesDir != null) {
            // Az adatbázis útvonala az alkalmazásban
            String appDbPath = context.getDatabasePath(DATABASE_NAME).getPath();

            // Az adatbázis útvonala a Dokumentumok mappában
            String documentsPath = externalFilesDir.getParentFile().getPath() + "/Documents/" + DATABASE_NAME;

            InputStream inputStream = null;
            OutputStream outputStream = null;

            try {
                File inputFile = new File(documentsPath);
                if (!inputFile.exists()) {
                    throw new FileNotFoundException("A bemeneti fájl nem található: " + documentsPath);
                }

                File outputFile = new File(appDbPath);
                if (outputFile.exists()) {
                    outputFile.delete();
                }

                inputStream = new FileInputStream(inputFile);
                outputStream = new FileOutputStream(outputFile);

                byte[] buffer = new byte[1024];
                int length;
                while ((length = inputStream.read(buffer)) > 0) {
                    outputStream.write(buffer, 0, length);
                }

                outputStream.flush();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (inputStream != null) {
                        inputStream.close();
                    }
                    if (outputStream != null) {
                        outputStream.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } else {
            // Kezeld a hibát, például dobhatsz egy kivételt vagy logolhatod a hibát
        }
    }

    public void exportDatabaseToDocuments() {
        File externalFilesDir = context.getExternalFilesDir(null);
        if (externalFilesDir != null) {
            // Az adatbázis útvonala az alkalmazásban
            String appDbPath = context.getDatabasePath(DATABASE_NAME).getPath();

            // Az adatbázis útvonala a Dokumentumok mappában
            String documentsPath = externalFilesDir.getParentFile().getPath() + "/Documents/" + DATABASE_NAME;

            InputStream inputStream = null;
            OutputStream outputStream = null;

            try {
                File file = new File(documentsPath);
                if (file.exists()) {
                    file.delete();
                }

                inputStream = new FileInputStream(appDbPath);
                outputStream = new FileOutputStream(documentsPath);

                byte[] buffer = new byte[1024];
                int length;
                while ((length = inputStream.read(buffer)) > 0) {
                    outputStream.write(buffer, 0, length);
                }

                outputStream.flush();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (inputStream != null) {
                        inputStream.close();
                    }
                    if (outputStream != null) {
                        outputStream.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } else {
            // Kezeld a hibát, például dobhatsz egy kivételt vagy logolhatod a hibát
        }
    }    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Törli az összes táblát
        // TODO: Adj hozzá kódot az összes táblád törléséhez
        // db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);

        // Újra létrehozza az adatbázist
        onCreate(db);
    }
}