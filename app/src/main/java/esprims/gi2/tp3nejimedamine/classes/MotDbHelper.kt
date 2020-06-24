package esprims.gi2.tp3nejimedamine.classes

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class MotDbHelper(context: Context?): SQLiteOpenHelper(context, "jeuMots.db", null, 1) {
    companion object {
        val THE_TABLE = "TMots"
        val COL_MOT = "leMot"
        val COL_TAILLE = "taille"
        val COL_TYPE = "type"
        val COL_GENRE = "genre"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val CREATE_WORDS_TABLE = ("CREATE TABLE "+ THE_TABLE+"("+ COL_MOT+" TEXT, "+ COL_TAILLE+" INTEGER NOT NULL, "+ COL_TYPE+" TEXT NOT NULL, "+ COL_GENRE+" TEXT NOT NULL"+")")
        db?.execSQL(CREATE_WORDS_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXIST "+ THE_TABLE)
        onCreate(db)
    }
}