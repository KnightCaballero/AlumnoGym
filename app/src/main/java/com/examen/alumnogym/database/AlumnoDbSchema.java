package com.examen.alumnogym.database;

public class AlumnoDbSchema {
    public static final class AlumnoTable {
        public static final String NAME = "alumnos";

        public static final class Cols {
            public static final String UUID = "uuid";
            public static final String TITLE = "title";
            public static final String EMAIL = "email";
            public static final String YEAR = "year";
            public static final String DATE = "date";
            public static final String SOLVED = "solved";
            public static final String SUSPECT = "suspect";
        }
    }
}