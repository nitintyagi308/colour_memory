package colormemory.test.com.colourmemory.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import colormemory.test.com.colourmemory.data.ListItemBean;

/**
 * Created by nitin.tyagi on 1/16/2017.
 */

    public class DBHelper extends SQLiteOpenHelper {


    /*
     * Database tables
     */
        public static final String TABLE_SCORE = "tb_score";

        /*
         * Database Name
         */
        private static final String DATABASE_NAME = "colour_memory.db";
        /*
         * Database Version
         */
        private static final int DATABASE_VERSION = 1;
        private static final String TAG = "DBHelper";


    /*
     * CREATE TABLE query for TABLE_SCORE
     */

        private final String createScoreTable = "CREATE TABLE " + TABLE_SCORE
                + "("
                + TableScores.ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + TableScores.NAME + " TEXT, "
                + TableScores.SCORE + " INTEGER NOT NULL )";

        public DBHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(createScoreTable);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        }

        /**
         * To get all the rows in the db in a descending order.
         *
         * @return {@link List} of {@link ListItemBean}
         */
        public List<ListItemBean> getScoreList() {

            Cursor cursor = null;
            SQLiteDatabase database = null;
            List<ListItemBean> list = new ArrayList<>();
            try {
                database = getReadableDatabase();
                cursor = database.rawQuery("SELECT * FROM " + DBHelper.TABLE_SCORE + " ORDER BY " + TableScores.SCORE + " DESC", null);
                int rank = 1;
                cursor.moveToFirst();
                while (!cursor.isAfterLast()) {
                    ListItemBean item = new ListItemBean();
                    item.setName(cursor.getString(cursor.getColumnIndex(TableScores.NAME)));
                    item.setScore(cursor.getInt(cursor.getColumnIndex(TableScores.SCORE)));
                    item.setRank(rank++);
                    list.add(item);
                    cursor.moveToNext();
                }

            } finally {
                if (cursor != null) {
                    cursor.close();
                }
                if (database != null) {
                    database.close();
                }
            }

            return list;
        }

        /**
         * Inserts a new score row in to th db
         *
         * @param score input score
         * @return insert id of the new row created.
         */
        public long insertScore(int score) {
            SQLiteDatabase database = null;
            long insertId;
            ContentValues cv = new ContentValues();
            cv.put(TableScores.SCORE, score);

            try {
                database = this.getWritableDatabase();
                insertId = database.insert(DBHelper.TABLE_SCORE, null, cv);
            } finally {
                if (database != null)
                    database.close();
            }
            return insertId;
        }

        /**
         * Gets the rank for the supplied insertId
         *
         * @param insertId insert id on which the rank is to be found out
         * @return rank
         */
        public int getRankForInsertId(long insertId) {
            Cursor cursor = null;
            SQLiteDatabase database = null;
            int rank = 1;
            try {
                database = this.getReadableDatabase();
                cursor = database.rawQuery("SELECT * FROM " + DBHelper.TABLE_SCORE + " ORDER BY " + TableScores.SCORE + " DESC", null);
                cursor.moveToFirst();
                rank = 1;
                while (!cursor.isAfterLast()) {
                    if (insertId == cursor.getInt(cursor.getColumnIndex(TableScores.ID))) {
                        break;
                    }
                    rank++;
                    cursor.moveToNext();
                }
            } finally {
                if (cursor != null) {
                    cursor.close();
                }
                if (database != null) {
                    database.close();
                }
            }
            return rank;
        }

        /**
         * This is called when user enters her name in the dialog and press save button.
         *
         * @param name     The name
         * @param insertId id of the row in the db where the corresponding score is stored
         */
        public void updateNameInDB(String name, long insertId) {
            SQLiteDatabase database = this.getWritableDatabase();
            ContentValues cv = new ContentValues();
            cv.put(TableScores.NAME, name);
            int noOfRowsAffected = database.update(DBHelper.TABLE_SCORE, cv, TableScores.ID + "=" + insertId, null);
            if (noOfRowsAffected > 0) {
                Log.d(TAG, "Name updated");
            } else {
                Log.d(TAG, "failed to update name");
            }
            database.close();
        }

}
