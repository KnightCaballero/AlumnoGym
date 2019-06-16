package com.examen.alumnogym;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.examen.alumnogym.database.AlumnoBaseHelper;
import com.examen.alumnogym.database.AlumnoCursorWrapper;
import com.examen.alumnogym.database.AlumnoDbSchema.AlumnoTable;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static com.examen.alumnogym.database.AlumnoDbSchema.AlumnoTable.Cols.*;

public class AlumnoLab {
    private static AlumnoLab sAlumnoLab;
    private Context mContext;
    private SQLiteDatabase mDatabase;

    public static AlumnoLab get(Context context) {
        if (sAlumnoLab == null) {
            sAlumnoLab = new AlumnoLab(context);
        }

        return sAlumnoLab;
    }

    private AlumnoLab(Context context) {
        mContext = context.getApplicationContext();
        mDatabase = new AlumnoBaseHelper(mContext)
                .getWritableDatabase();

    }

    public void addAlumno(Alumno c) {
        ContentValues values = getContentValues(c);
        mDatabase.insert(AlumnoTable.NAME, null, values);
    }

    public List<Alumno> getAlumno() {
        List<Alumno> alumnos = new ArrayList<>();
        AlumnoCursorWrapper cursor = queryAlumnos(null, null);
        try {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                alumnos.add(cursor.getAlumno());
                cursor.moveToNext();
            }
        } finally {
            cursor.close();
        }
        return alumnos;
    }

    public Alumno getAlumno(UUID id) {
        AlumnoCursorWrapper cursor = queryAlumnos(
                AlumnoTable.Cols.UUID + " = ?",
                new String[]{id.toString()}
        );
        try {
            if (cursor.getCount() == 0) {
                return null;
            }
            cursor.moveToFirst();
            return cursor.getAlumno();
        } finally {
            cursor.close();
        }
    }

    public File getPhotoFile(Alumno alumno) {
        File filesDir = mContext.getFilesDir();
        return new File(filesDir, alumno.getPhotoFilename());
    }

    public void updateAlumno(Alumno alumno) {
        String uuidString = alumno.getId().toString();
        ContentValues values = getContentValues(alumno);
        mDatabase.update(AlumnoTable.NAME, values,
                AlumnoTable.Cols.UUID + " = ?",
                new String[]{uuidString});
    }

    private AlumnoCursorWrapper queryAlumnos(String whereClause, String[] whereArgs) {
        Cursor cursor = mDatabase.query(
                AlumnoTable.NAME,
                null, // Columns - null selects all columns
                whereClause,
                whereArgs,
                null, // groupBy
                null, // having
                null  // orderBy
        );
        return new AlumnoCursorWrapper(cursor);
    }

    private static ContentValues getContentValues(Alumno alumno) {
        ContentValues values = new ContentValues();
        values.put(UUID, alumno.getId().toString());
        values.put(TITLE, alumno.getTitle());
        values.put(EMAIL, alumno.getEmail());
        values.put(YEAR, alumno.getEdad());
        values.put(DATE, alumno.getDate().getTime());
        values.put(SOLVED, alumno.isSolved() ? 1 : 0);
        values.put(AlumnoTable.Cols.SUSPECT, alumno.getSuspect());

        return values;
    }
}
