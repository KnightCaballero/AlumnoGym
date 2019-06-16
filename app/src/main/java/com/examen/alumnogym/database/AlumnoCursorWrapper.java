package com.examen.alumnogym.database;

import android.database.Cursor;
import android.database.CursorWrapper;

import com.examen.alumnogym.Alumno;
import com.examen.alumnogym.database.AlumnoDbSchema.AlumnoTable;

import java.util.Date;
import java.util.UUID;

import static com.examen.alumnogym.database.AlumnoDbSchema.AlumnoTable.*;

public class AlumnoCursorWrapper extends CursorWrapper {

    public AlumnoCursorWrapper(Cursor cursor) {
        super(cursor);
    }

    public Alumno getAlumno() {
        String uuidString = getString(getColumnIndex(Cols.UUID));
        String title = getString(getColumnIndex(Cols.TITLE));
        String email = getString(getColumnIndex(Cols.EMAIL));
        String year = getString(getColumnIndex(Cols.YEAR));
        long date = getLong(getColumnIndex(Cols.DATE));
        int isSolved = getInt(getColumnIndex(Cols.SOLVED));
        String suspect = getString(getColumnIndex(AlumnoTable.Cols.SUSPECT));


        Alumno alumno = new Alumno(UUID.fromString(uuidString));
        alumno.setTitle(title);
        alumno.setEmail(email);
        alumno.setEdad(year);
        //alumno.setEdad(edad);
        alumno.setDate(new Date(date));
        alumno.setSolved(isSolved != 0);
        alumno.setSuspect(suspect);

        return alumno;
    }
}
