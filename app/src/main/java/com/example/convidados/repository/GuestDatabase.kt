package com.example.convidados.repository

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.convidados.constants.DatabaseConstants

class GuestDatabase(
    context : Context?
) : SQLiteOpenHelper(context, NAME, null, VERSION) {

    companion object {
        private const val NAME = "guestdb"
        private const val VERSION = 1
    }

    override fun onCreate(db : SQLiteDatabase) {
        db.execSQL(
            "CREATE TABLE " + DatabaseConstants.Guest.TABLE_NAME + " (" +
                    DatabaseConstants.Guest.COLUMNS.ID + " integer PRIMARY KEY AUTOINCREMENT," +
                    DatabaseConstants.Guest.COLUMNS.NAME + " name text," +
                    DatabaseConstants.Guest.COLUMNS.PRESENCE + " presence integer)"
        )
    }

    override fun onUpgrade(db : SQLiteDatabase, oldVersion : Int, newVersion : Int) {
    }
}