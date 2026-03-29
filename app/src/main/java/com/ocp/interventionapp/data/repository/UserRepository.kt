package com.ocp.interventionapp.data.repository

import android.content.ContentValues
import android.database.sqlite.SQLiteDatabase
import com.ocp.interventionapp.data.database.DatabaseHelper
import com.ocp.interventionapp.data.model.Intervention
import com.ocp.interventionapp.data.model.Equipement
import com.ocp.interventionapp.data.model.User

/**
 * Repository class for User operations
 */
class UserRepository(private val dbHelper: DatabaseHelper) {

    fun getAllUsers(): List<User> {
        val users = mutableListOf<User>()
        val db = dbHelper.readableDatabase
        val cursor = db.query(
            DatabaseHelper.TABLE_USERS,
            null,
            "${DatabaseHelper.COLUMN_USER_IS_ACTIVE} = 1",
            null,
            null,
            null,
            DatabaseHelper.COLUMN_USER_NAME
        )

        cursor?.use {
            while (it.moveToNext()) {
                val user = User(
                    id = it.getInt(it.getColumnIndexOrThrow(DatabaseHelper.COLUMN_USER_ID)),
                    name = it.getString(it.getColumnIndexOrThrow(DatabaseHelper.COLUMN_USER_NAME)),
                    email = it.getString(it.getColumnIndexOrThrow(DatabaseHelper.COLUMN_USER_EMAIL)) ?: "",
                    phone = it.getString(it.getColumnIndexOrThrow(DatabaseHelper.COLUMN_USER_PHONE)) ?: "",
                    role = User.UserRole.valueOf(it.getString(it.getColumnIndexOrThrow(DatabaseHelper.COLUMN_USER_ROLE))),
                    isActive = it.getInt(it.getColumnIndexOrThrow(DatabaseHelper.COLUMN_USER_IS_ACTIVE)) == 1
                )
                users.add(user)
            }
        }
        return users
    }

    fun getUserById(id: Int): User? {
        val db = dbHelper.readableDatabase
        val cursor = db.query(
            DatabaseHelper.TABLE_USERS,
            null,
            "${DatabaseHelper.COLUMN_USER_ID} = ?",
            arrayOf(id.toString()),
            null,
            null,
            null
        )

        var user: User? = null
        cursor?.use {
            if (it.moveToFirst()) {
                user = User(
                    id = it.getInt(it.getColumnIndexOrThrow(DatabaseHelper.COLUMN_USER_ID)),
                    name = it.getString(it.getColumnIndexOrThrow(DatabaseHelper.COLUMN_USER_NAME)),
                    email = it.getString(it.getColumnIndexOrThrow(DatabaseHelper.COLUMN_USER_EMAIL)) ?: "",
                    phone = it.getString(it.getColumnIndexOrThrow(DatabaseHelper.COLUMN_USER_PHONE)) ?: "",
                    role = User.UserRole.valueOf(it.getString(it.getColumnIndexOrThrow(DatabaseHelper.COLUMN_USER_ROLE))),
                    isActive = it.getInt(it.getColumnIndexOrThrow(DatabaseHelper.COLUMN_USER_IS_ACTIVE)) == 1
                )
            }
        }
        return user
    }

    fun insertUser(user: User): Long {
        val db = dbHelper.writableDatabase
        val values = ContentValues().apply {
            put(DatabaseHelper.COLUMN_USER_NAME, user.name)
            put(DatabaseHelper.COLUMN_USER_EMAIL, user.email)
            put(DatabaseHelper.COLUMN_USER_PHONE, user.phone)
            put(DatabaseHelper.COLUMN_USER_ROLE, user.role.name)
            put(DatabaseHelper.COLUMN_USER_IS_ACTIVE, if (user.isActive) 1 else 0)
        }
        return db.insert(DatabaseHelper.TABLE_USERS, null, values)
    }

    fun updateUser(user: User): Int {
        val db = dbHelper.writableDatabase
        val values = ContentValues().apply {
            put(DatabaseHelper.COLUMN_USER_NAME, user.name)
            put(DatabaseHelper.COLUMN_USER_EMAIL, user.email)
            put(DatabaseHelper.COLUMN_USER_PHONE, user.phone)
            put(DatabaseHelper.COLUMN_USER_ROLE, user.role.name)
            put(DatabaseHelper.COLUMN_USER_IS_ACTIVE, if (user.isActive) 1 else 0)
        }
        return db.update(
            DatabaseHelper.TABLE_USERS,
            values,
            "${DatabaseHelper.COLUMN_USER_ID} = ?",
            arrayOf(user.id.toString())
        )
    }

    fun deleteUser(id: Int): Int {
        val db = dbHelper.writableDatabase
        return db.delete(
            DatabaseHelper.TABLE_USERS,
            "${DatabaseHelper.COLUMN_USER_ID} = ?",
            arrayOf(id.toString())
        )
    }

    fun getTechniciansOnly(): List<User> {
        return getAllUsers().filter { it.role == User.UserRole.TECHNICIAN }
    }
}
