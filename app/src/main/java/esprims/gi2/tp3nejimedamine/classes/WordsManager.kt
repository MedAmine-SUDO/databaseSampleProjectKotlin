package esprims.gi2.tp3nejimedamine.classes

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException

class WordsManager(context: Context) {
    private var motHelper: MotDbHelper = MotDbHelper(context)
    lateinit var db: SQLiteDatabase

    fun openReadDB(){
        db = motHelper.readableDatabase
    }
    fun openWriteDB(){
        db = motHelper.writableDatabase
    }
    fun closeDB(){
        db.close()
    }

    fun addMot(mot: Mot): Long {
        val contentValues = ContentValues()
        contentValues.put(MotDbHelper.COL_MOT, mot.leMot)
        contentValues.put(MotDbHelper.COL_GENRE, mot.genre)
        contentValues.put(MotDbHelper.COL_TAILLE, mot.taille)
        contentValues.put(MotDbHelper.COL_TYPE, mot.type)

        return motHelper.writableDatabase.insert(MotDbHelper.THE_TABLE, null, contentValues)
    }

    fun cursorToWords(cursor: Cursor): List<Mot> {
        val motList: ArrayList<Mot> = ArrayList()

        var mot: String
        var taille: Int
        var type: String
        var genre: String
        if(cursor.moveToFirst()){
            do{
                mot = cursor.getString(cursor.getColumnIndex("leMot"))
                taille = cursor.getInt(cursor.getColumnIndex("taille"))
                type = cursor.getString(cursor.getColumnIndex("type"))
                genre = cursor.getString(cursor.getColumnIndex("genre"))
                val mt = Mot(mot, taille, type, genre)
                motList.add(mt)
            }while(cursor.moveToNext())
        }
        return motList
    }

    fun getWords(): List<Mot> {
        val selectQuery = "SELECT * FROM "+MotDbHelper.THE_TABLE
        var cursor: Cursor = db.rawQuery(selectQuery, null)

        try {
            cursor = db.rawQuery(selectQuery, null)
        }catch(e: SQLiteException){
            db.execSQL(selectQuery)
        }
        return cursorToWords(cursor)
    }

    fun getWords(t: Int){

    }

    fun deleteWords(mot: String){
        db.delete(MotDbHelper.THE_TABLE, MotDbHelper.COL_MOT+"=?", arrayOf(mot))
    }

    fun updateWord(mot: String, word: Mot){
        val contentValues = ContentValues()
        contentValues.put(MotDbHelper.COL_MOT, word.leMot)
        contentValues.put(MotDbHelper.COL_GENRE, word.genre)
        contentValues.put(MotDbHelper.COL_TAILLE, word.taille)
        contentValues.put(MotDbHelper.COL_TYPE, word.type)

        db.update(MotDbHelper.THE_TABLE, contentValues, "leMot="+"'"+mot+"'", null)
    }

    fun getRandomWords(query: String): String {
        this.openReadDB()
        var cursor: Cursor = db.rawQuery(query, null)
        try {
            cursor = db.rawQuery(query, null)
        }catch(e: SQLiteException){
            db.execSQL(query)
        }
        var mot:String = ""
        if(cursor.moveToFirst()){
            do{
                mot = cursor.getString(cursor.getColumnIndex("leMot"))
            }while(cursor.moveToNext())
        }
        this.closeDB()
        return mot
    }

}