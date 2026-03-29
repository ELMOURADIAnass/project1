package com.ocp.interventionapp.data.database

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.ocp.interventionapp.data.model.Intervention
import com.ocp.interventionapp.data.model.Equipement
import com.ocp.interventionapp.data.model.User

/**
 * SQLite Database Helper class for Intervention Management App
 * Manages database creation and version management
 */
class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "intervention_db"
        private const val DATABASE_VERSION = 1

        // Table names
        const val TABLE_USERS = "users"
        const val TABLE_EQUIPEMENTS = "equipements"
        const val TABLE_INTERVENTIONS = "interventions"

        // Users table columns
        const val COLUMN_USER_ID = "id"
        const val COLUMN_USER_NAME = "name"
        const val COLUMN_USER_EMAIL = "email"
        const val COLUMN_USER_PHONE = "phone"
        const val COLUMN_USER_ROLE = "role"
        const val COLUMN_USER_IS_ACTIVE = "is_active"

        // Equipments table columns
        const val COLUMN_EQUIPEMENT_ID = "id"
        const val COLUMN_EQUIPEMENT_NAME = "name"
        const val COLUMN_EQUIPEMENT_TYPE = "type"
        const val COLUMN_EQUIPEMENT_SERIAL = "serial_number"
        const val COLUMN_EQUIPEMENT_LOCATION = "location"
        const val COLUMN_EQUIPEMENT_STATUS = "status"
        const val COLUMN_EQUIPEMENT_PURCHASE_DATE = "purchase_date"
        const val COLUMN_EQUIPEMENT_NOTES = "notes"

        // Interventions table columns
        const val COLUMN_INTERVENTION_ID = "id"
        const val COLUMN_INTERVENTION_TITLE = "title"
        const val COLUMN_INTERVENTION_DESCRIPTION = "description"
        const val COLUMN_INTERVENTION_TECHNICIAN_ID = "technician_id"
        const val COLUMN_INTERVENTION_TECHNICIAN_NAME = "technician_name"
        const val COLUMN_INTERVENTION_EQUIPEMENT_ID = "equipment_id"
        const val COLUMN_INTERVENTION_EQUIPEMENT_NAME = "equipment_name"
        const val COLUMN_INTERVENTION_DATE = "date"
        const val COLUMN_INTERVENTION_PRIORITY = "priority"
        const val COLUMN_INTERVENTION_STATUS = "status"
        const val COLUMN_INTERVENTION_CREATED_AT = "created_at"
    }

    override fun onCreate(db: SQLiteDatabase) {
        // Create users table
        val createUserTable = """
            CREATE TABLE $TABLE_USERS (
                $COLUMN_USER_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $COLUMN_USER_NAME TEXT NOT NULL,
                $COLUMN_USER_EMAIL TEXT,
                $COLUMN_USER_PHONE TEXT,
                $COLUMN_USER_ROLE TEXT DEFAULT 'TECHNICIAN',
                $COLUMN_USER_IS_ACTIVE INTEGER DEFAULT 1
            )
        """.trimIndent()
        db.execSQL(createUserTable)

        // Create equipements table
        val createEquipementTable = """
            CREATE TABLE $TABLE_EQUIPEMENTS (
                $COLUMN_EQUIPEMENT_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $COLUMN_EQUIPEMENT_NAME TEXT NOT NULL,
                $COLUMN_EQUIPEMENT_TYPE TEXT,
                $COLUMN_EQUIPEMENT_SERIAL TEXT,
                $COLUMN_EQUIPEMENT_LOCATION TEXT,
                $COLUMN_EQUIPEMENT_STATUS TEXT DEFAULT 'AVAILABLE',
                $COLUMN_EQUIPEMENT_PURCHASE_DATE TEXT,
                $COLUMN_EQUIPEMENT_NOTES TEXT
            )
        """.trimIndent()
        db.execSQL(createEquipementTable)

        // Create interventions table
        val createInterventionTable = """
            CREATE TABLE $TABLE_INTERVENTIONS (
                $COLUMN_INTERVENTION_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $COLUMN_INTERVENTION_TITLE TEXT NOT NULL,
                $COLUMN_INTERVENTION_DESCRIPTION TEXT,
                $COLUMN_INTERVENTION_TECHNICIAN_ID INTEGER,
                $COLUMN_INTERVENTION_TECHNICIAN_NAME TEXT,
                $COLUMN_INTERVENTION_EQUIPEMENT_ID INTEGER,
                $COLUMN_INTERVENTION_EQUIPEMENT_NAME TEXT,
                $COLUMN_INTERVENTION_DATE TEXT,
                $COLUMN_INTERVENTION_PRIORITY TEXT DEFAULT 'MEDIUM',
                $COLUMN_INTERVENTION_STATUS TEXT DEFAULT 'PENDING',
                $COLUMN_INTERVENTION_CREATED_AT TEXT,
                FOREIGN KEY ($COLUMN_INTERVENTION_TECHNICIAN_ID) REFERENCES $TABLE_USERS($COLUMN_USER_ID),
                FOREIGN KEY ($COLUMN_INTERVENTION_EQUIPEMENT_ID) REFERENCES $TABLE_EQUIPEMENTS($COLUMN_EQUIPEMENT_ID)
            )
        """.trimIndent()
        db.execSQL(createInterventionTable)

        // Insert default technicians
        insertDefaultUsers(db)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_INTERVENTIONS")
        db.execSQL("DROP TABLE IF EXISTS $TABLE_EQUIPEMENTS")
        db.execSQL("DROP TABLE IF EXISTS $TABLE_USERS")
        onCreate(db)
    }

    private fun insertDefaultUsers(db: SQLiteDatabase) {
        val users = listOf(
            arrayOf("Ahmed Bennani", "ahmed.bennani@ocp.ma", "0661234567", "TECHNICIAN"),
            arrayOf("Fatima Zahra", "fatima.zahra@ocp.ma", "0662345678", "TECHNICIAN"),
            arrayOf("Mohammed Alami", "mohammed.alami@ocp.ma", "0663456789", "TECHNICIAN"),
            arrayOf("Khadija Mansouri", "khadija.mansouri@ocp.ma", "0664567890", "SUPERVISOR"),
            arrayOf("Omar Tazi", "omar.tazi@ocp.ma", "0665678901", "ADMIN")
        )

        for (user in users) {
            val values = ContentValues().apply {
                put(COLUMN_USER_NAME, user[0])
                put(COLUMN_USER_EMAIL, user[1])
                put(COLUMN_USER_PHONE, user[2])
                put(COLUMN_USER_ROLE, user[3])
                put(COLUMN_USER_IS_ACTIVE, 1)
            }
            db.insert(TABLE_USERS, null, values)
        }
    }
}
