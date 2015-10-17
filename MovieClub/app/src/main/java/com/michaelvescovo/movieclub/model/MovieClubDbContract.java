package com.michaelvescovo.movieclub.model;

import android.provider.BaseColumns;

/**
 * Michael Vescovo
 * s3459317
 */
public final class MovieClubDbContract {
    // To prevent accidentally instantiating this contract class, provide an empty constructor
    public MovieClubDbContract() {}

    // Table contents
    public static abstract class MovieEntry implements BaseColumns {
        private static final String TABLE_NAME = "movie";
        private static final String COLUMN_NAME_MOVIE_ID = "movie_id";
        private static final String COLUMN_NAME_TITLE = "title";

        private static final String TEXT_TYPE = " TEXT";
        private static final String COMMA_SEP = ",";
        public static final String SQL_CREATE_ENTRIES =
                "CREATE TABLE " + MovieEntry.TABLE_NAME + " (" +
                        MovieEntry._ID + " INTEGER PRIMARY KEY," + TEXT_TYPE + COMMA_SEP +
                        MovieEntry.COLUMN_NAME_TITLE + TEXT_TYPE + COMMA_SEP + " )";

        private static final String SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS " + MovieEntry.TABLE_NAME;

        public static String getSqlCreateEntries() {
            return SQL_CREATE_ENTRIES;
        }

        public String getSqlDeleteEntries() {
            return SQL_DELETE_ENTRIES;
        }
    }

    // Table contents
    public static abstract class PartyEntry implements BaseColumns {
        private static final String TABLE_MOVIE = "movie";
        private static final String TABLE_PARTY = "party";
        private static final String TABLE_INVITEE = "invitee";
    }


}


//private static final String CREATE_MOVIE_TABLE = "CREATE TABLE movie (movie_id INTEGER PRIMARY KEY, party_id INTEGER REFERENCES party, movie_title TEXT NOT NULL, movie_year TEXT NOT NULL, movie_shortplot TEXT NOT NULL, movie_fullplot TEXT NOT NULL, movie_poster INTEGER NOT NULL);";
//private static final String CREATE_TEST_TABLE = "CREATE TABLE test (test_id INTEGER PRIMARY KEY, testfield1 TEXT NOT NULL);";
//private static final String CREATE_PARTY_TABLE = "CREATE TABLE party (party_id INTEGER PRIMARY KEY, movie_id TEXT NOT NULL REFERENCES movie, party_year INTEGER, party_month INTEGER, party_day INTEGER, party_hour INTEGER, party_minute INTEGER, party_venue TEXT, party_location TEXT);";
//private static final String CREATE_INVITEE_TABLE = "CREATE TABLE invitee (invitee_id INTEGER PRIMARY KEY, invitee_email TEXT);";
//private static final String CREATE_MOVIE_PARTY_BRIDGE_TABLE = "CREATE TABLE movie_party_bridge (movie_id TEXT NOT NULL REFERENCES movie, party_id INTEGER NOT NULL REFERENCES party, PRIMARY KEY( movie_id, party_id ));";
//private static final String CREATE_PARTY_INVITEE_BRIDGE_TABLE = "CREATE TABLE party_invitee_bridge (party_id INTEGER NOT NULL REFERENCES party, invitee_id INTEGER NOT NULL REFERENCES invitee, PRIMARY KEY( party_id, invitee_id ));";

/*public final class FeedReaderContract {
    // To prevent someone from accidentally instantiating the contract class,
    // give it an empty constructor.
    public FeedReaderContract() {}

    *//* Inner class that defines the table contents *//*
    public static abstract class FeedEntry implements BaseColumns {
        public static final String TABLE_NAME = "entry";
        public static final String COLUMN_NAME_ENTRY_ID = "entryid";
        public static final String COLUMN_NAME_TITLE = "title";
        public static final String COLUMN_NAME_SUBTITLE = "subtitle";
        ...
    }
}*/

/*private static final String TEXT_TYPE = " TEXT";
private static final String COMMA_SEP = ",";
private static final String SQL_CREATE_ENTRIES =
        "CREATE TABLE " + FeedEntry.TABLE_NAME + " (" +
                FeedEntry._ID + " INTEGER PRIMARY KEY," +
                FeedEntry.COLUMN_NAME_ENTRY_ID + TEXT_TYPE + COMMA_SEP +
                FeedEntry.COLUMN_NAME_TITLE + TEXT_TYPE + COMMA_SEP +
... // Any other options for the CREATE command
        " )";

private static final String SQL_DELETE_ENTRIES =
        "DROP TABLE IF EXISTS " + FeedEntry.TABLE_NAME;*/
