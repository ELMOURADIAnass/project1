package com.ocp.interventionapp.data.repository

import android.content.ContentValues
import com.ocp.interventionapp.data.database.DatabaseHelper
import com.ocp.interventionapp.data.model.Equipement

/**
 * Repository class for Equipment operations
 */
class EquipementRepository(private val dbHelper: DatabaseHelper) {

    fun getAllEquipements(): List<Equipement> {
        val equipements = mutableListOf<Equipement>()
        val db = dbHelper.readableDatabase
        val cursor = db.query(
            DatabaseHelper.TABLE_EQUIPEMENTS,
            null,
            null,
            null,
            null,
            null,
            "${DatabaseHelper.COLUMN_EQUIPEMENT_NAME} ASC"
        )

        cursor?.use {
            while (it.moveToNext()) {
                val equipement = Equipement(
                    id = it.getInt(it.getColumnIndexOrThrow(DatabaseHelper.COLUMN_EQUIPEMENT_ID)),
                    name = it.getString(it.getColumnIndexOrThrow(DatabaseHelper.COLUMN_EQUIPEMENT_NAME)),
                    type = it.getString(it.getColumnIndexOrThrow(DatabaseHelper.COLUMN_EQUIPEMENT_TYPE)) ?: "",
                    serialNumber = it.getString(it.getColumnIndexOrThrow(DatabaseHelper.COLUMN_EQUIPEMENT_SERIAL)) ?: "",
                    location = it.getString(it.getColumnIndexOrThrow(DatabaseHelper.COLUMN_EQUIPEMENT_LOCATION)) ?: "",
                    status = Equipement.EquipmentStatus.valueOf(
                        it.getString(it.getColumnIndexOrThrow(DatabaseHelper.COLUMN_EQUIPEMENT_STATUS))
                    ),
                    purchaseDate = it.getString(it.getColumnIndexOrThrow(DatabaseHelper.COLUMN_EQUIPEMENT_PURCHASE_DATE)) ?: "",
                    notes = it.getString(it.getColumnIndexOrThrow(DatabaseHelper.COLUMN_EQUIPEMENT_NOTES)) ?: ""
                )
                equipements.add(equipement)
            }
        }
        return equipements
    }

    fun getEquipementById(id: Int): Equipement? {
        val db = dbHelper.readableDatabase
        val cursor = db.query(
            DatabaseHelper.TABLE_EQUIPEMENTS,
            null,
            "${DatabaseHelper.COLUMN_EQUIPEMENT_ID} = ?",
            arrayOf(id.toString()),
            null,
            null,
            null
        )

        var equipement: Equipement? = null
        cursor?.use {
            if (it.moveToFirst()) {
                equipement = Equipement(
                    id = it.getInt(it.getColumnIndexOrThrow(DatabaseHelper.COLUMN_EQUIPEMENT_ID)),
                    name = it.getString(it.getColumnIndexOrThrow(DatabaseHelper.COLUMN_EQUIPEMENT_NAME)),
                    type = it.getString(it.getColumnIndexOrThrow(DatabaseHelper.COLUMN_EQUIPEMENT_TYPE)) ?: "",
                    serialNumber = it.getString(it.getColumnIndexOrThrow(DatabaseHelper.COLUMN_EQUIPEMENT_SERIAL)) ?: "",
                    location = it.getString(it.getColumnIndexOrThrow(DatabaseHelper.COLUMN_EQUIPEMENT_LOCATION)) ?: "",
                    status = Equipement.EquipmentStatus.valueOf(
                        it.getString(it.getColumnIndexOrThrow(DatabaseHelper.COLUMN_EQUIPEMENT_STATUS))
                    ),
                    purchaseDate = it.getString(it.getColumnIndexOrThrow(DatabaseHelper.COLUMN_EQUIPEMENT_PURCHASE_DATE)) ?: "",
                    notes = it.getString(it.getColumnIndexOrThrow(DatabaseHelper.COLUMN_EQUIPEMENT_NOTES)) ?: ""
                )
            }
        }
        return equipement
    }

    fun insertEquipement(equipement: Equipement): Long {
        val db = dbHelper.writableDatabase
        val values = ContentValues().apply {
            put(DatabaseHelper.COLUMN_EQUIPEMENT_NAME, equipement.name)
            put(DatabaseHelper.COLUMN_EQUIPEMENT_TYPE, equipement.type)
            put(DatabaseHelper.COLUMN_EQUIPEMENT_SERIAL, equipement.serialNumber)
            put(DatabaseHelper.COLUMN_EQUIPEMENT_LOCATION, equipement.location)
            put(DatabaseHelper.COLUMN_EQUIPEMENT_STATUS, equipement.status.name)
            put(DatabaseHelper.COLUMN_EQUIPEMENT_PURCHASE_DATE, equipement.purchaseDate)
            put(DatabaseHelper.COLUMN_EQUIPEMENT_NOTES, equipement.notes)
        }
        return db.insert(DatabaseHelper.TABLE_EQUIPEMENTS, null, values)
    }

    fun updateEquipement(equipement: Equipement): Int {
        val db = dbHelper.writableDatabase
        val values = ContentValues().apply {
            put(DatabaseHelper.COLUMN_EQUIPEMENT_NAME, equipement.name)
            put(DatabaseHelper.COLUMN_EQUIPEMENT_TYPE, equipement.type)
            put(DatabaseHelper.COLUMN_EQUIPEMENT_SERIAL, equipement.serialNumber)
            put(DatabaseHelper.COLUMN_EQUIPEMENT_LOCATION, equipement.location)
            put(DatabaseHelper.COLUMN_EQUIPEMENT_STATUS, equipement.status.name)
            put(DatabaseHelper.COLUMN_EQUIPEMENT_PURCHASE_DATE, equipement.purchaseDate)
            put(DatabaseHelper.COLUMN_EQUIPEMENT_NOTES, equipement.notes)
        }
        return db.update(
            DatabaseHelper.TABLE_EQUIPEMENTS,
            values,
            "${DatabaseHelper.COLUMN_EQUIPEMENT_ID} = ?",
            arrayOf(equipement.id.toString())
        )
    }

    fun deleteEquipement(id: Int): Int {
        val db = dbHelper.writableDatabase
        return db.delete(
            DatabaseHelper.TABLE_EQUIPEMENTS,
            "${DatabaseHelper.COLUMN_EQUIPEMENT_ID} = ?",
            arrayOf(id.toString())
        )
    }

    fun getAvailableEquipements(): List<Equipement> {
        return getAllEquipements().filter { 
            it.status == Equipement.EquipmentStatus.AVAILABLE || 
            it.status == Equipement.EquipmentStatus.IN_USE 
        }
    }
}
