package com.examen.alumnogym.database;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.examen.alumnogym.database.AlumnoDbSchema.AlumnoTable;

public class AlumnoBaseHelper extends SQLiteOpenHelper {
    private static final int VERSION = 1;
    private static final String DATABASE_NAME = "alumnoBase.db";

    public AlumnoBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + AlumnoTable.NAME + "(" +
                " _id integer primary key autoincrement, " +
                AlumnoTable.Cols.UUID + ", " +
                AlumnoTable.Cols.TITLE + ", " +
                AlumnoTable.Cols.EMAIL + ", " +
                AlumnoTable.Cols.YEAR + ", " +
                AlumnoTable.Cols.DATE + ", " +
                AlumnoTable.Cols.SOLVED + ", " +
                AlumnoTable.Cols.SUSPECT +

                ")"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
}