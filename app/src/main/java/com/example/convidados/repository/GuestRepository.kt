package com.example.convidados.repository

import android.content.ContentValues
import android.content.Context
import com.example.convidados.constants.DatabaseConstants
import com.example.convidados.model.GuestModel

class GuestRepository private constructor(context : Context) {

    private val guestDatabase = GuestDatabase(context)

    companion object {
        private lateinit var repository : GuestRepository

        fun getInstance(context : Context) : GuestRepository {
            if (!Companion::repository.isInitialized) {
                repository = GuestRepository(context)
            }
            return repository
        }
    }

    fun insert(guest : GuestModel) {
        try {
            val db = guestDatabase.writableDatabase

            val presence = if (guest.presence) 1 else 0

            val values = ContentValues()
            values.put(DatabaseConstants.Guest.COLUMNS.NAME, guest.name)
            values.put(DatabaseConstants.Guest.COLUMNS.PRESENCE, presence)

            db.insert(DatabaseConstants.Guest.TABLE_NAME, null, values)

            true
        } catch (e : Exception) {
            false
        }
    }

    fun update(guest : GuestModel) {
        try {
            val db = guestDatabase.writableDatabase

            val values = ContentValues()
            values.put(DatabaseConstants.Guest.COLUMNS.NAME, guest.name)
            val presence = if (guest.presence) 1 else 0
            values.put(DatabaseConstants.Guest.COLUMNS.PRESENCE, presence)
            val selection = DatabaseConstants.Guest.COLUMNS.ID + " = ?"
            val args = arrayOf(guest.id.toString())

            db.update(DatabaseConstants.Guest.TABLE_NAME, values, selection, args)
            true
        } catch (e : Exception) {
            false
        }
    }

    fun delete(id : Int) {
        try {
            val db = guestDatabase.writableDatabase

            val selection = DatabaseConstants.Guest.COLUMNS.ID + " = ?"
            val args = arrayOf(id.toString())

            db.delete(DatabaseConstants.Guest.TABLE_NAME, selection, args)
            true
        } catch (e : Exception) {
            false
        }
    }

    fun getAll() : List<GuestModel> {
        val list = mutableListOf<GuestModel>()
        try {
            val db = guestDatabase.readableDatabase

            val projection = arrayOf(
                DatabaseConstants.Guest.COLUMNS.ID,
                DatabaseConstants.Guest.COLUMNS.NAME,
                DatabaseConstants.Guest.COLUMNS.PRESENCE
            )
            val cursor = db.query(
                DatabaseConstants.Guest.TABLE_NAME,
                projection,
                null,
                null,
                null,
                null,
                null
            )

            if (cursor !== null && cursor.count > 0) {
                while (cursor.moveToNext()) {
                    val id =
                        cursor.getInt(cursor.getColumnIndex(DatabaseConstants.Guest.COLUMNS.ID))
                    val name =
                        cursor.getString(cursor.getColumnIndex(DatabaseConstants.Guest.COLUMNS.NAME))
                    val presence =
                        cursor.getInt(cursor.getColumnIndex(DatabaseConstants.Guest.COLUMNS.PRESENCE))

                    list.add(GuestModel(id, name, presence == 1))
                }
            }

            cursor.close()
        } catch (e : Exception) {
            return list
        }
        return list
    }

    fun getPresent() : List<GuestModel> {
        val list = mutableListOf<GuestModel>()
        try {
            val db = guestDatabase.readableDatabase

            val cursor = db.rawQuery("SELECT * FROM Guest WHERE presence = 1", null)

            if (cursor !== null && cursor.count > 0) {
                while (cursor.moveToNext()) {
                    val id =
                        cursor.getInt(cursor.getColumnIndex(DatabaseConstants.Guest.COLUMNS.ID))
                    val name =
                        cursor.getString(cursor.getColumnIndex(DatabaseConstants.Guest.COLUMNS.NAME))
                    val presence =
                        cursor.getInt(cursor.getColumnIndex(DatabaseConstants.Guest.COLUMNS.PRESENCE))

                    list.add(GuestModel(id, name, presence == 1))
                }
            }

            cursor.close()
        } catch (e : Exception) {
            return list
        }
        return list
    }

    fun getAbsent() : List<GuestModel> {
        val list = mutableListOf<GuestModel>()
        try {
            val db = guestDatabase.readableDatabase

            val cursor = db.rawQuery("SELECT * FROM Guest WHERE presence = 0", null)

            if (cursor !== null && cursor.count > 0) {
                while (cursor.moveToNext()) {
                    val id =
                        cursor.getInt(cursor.getColumnIndex(DatabaseConstants.Guest.COLUMNS.ID))
                    val name =
                        cursor.getString(cursor.getColumnIndex(DatabaseConstants.Guest.COLUMNS.NAME))
                    val presence =
                        cursor.getInt(cursor.getColumnIndex(DatabaseConstants.Guest.COLUMNS.PRESENCE))

                    list.add(GuestModel(id, name, presence == 1))
                }
            }

            cursor.close()
        } catch (e : Exception) {
            return list
        }
        return list
    }
}
