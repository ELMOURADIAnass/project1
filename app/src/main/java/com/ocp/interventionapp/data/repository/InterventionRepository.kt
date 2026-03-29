package com.ocp.interventionapp.data.repository

import android.content.ContentValues
import com.ocp.interventionapp.data.database.DatabaseHelper
import com.ocp.interventionapp.data.model.Intervention

/**
 * Repository class for Intervention operations
 */
class InterventionRepository(private val dbHelper: DatabaseHelper) {

    fun getAllInterventions(): List<Intervention> {
        val interventions = mutableListOf<Intervention>()
        val db = dbHelper.readableDatabase
        val cursor = db.query(
            DatabaseHelper.TABLE_INTERVENTIONS,
            null,
            null,
            null,
            null,
            null,
            "${DatabaseHelper.COLUMN_INTERVENTION_DATE} DESC"
        )

        cursor?.use {
            while (it.moveToNext()) {
                val intervention = Intervention(
                    id = it.getInt(it.getColumnIndexOrThrow(DatabaseHelper.COLUMN_INTERVENTION_ID)),
                    title = it.getString(it.getColumnIndexOrThrow(DatabaseHelper.COLUMN_INTERVENTION_TITLE)) ?: "",
                    description = it.getString(it.getColumnIndexOrThrow(DatabaseHelper.COLUMN_INTERVENTION_DESCRIPTION)) ?: "",
                    technicianId = it.getInt(it.getColumnIndexOrThrow(DatabaseHelper.COLUMN_INTERVENTION_TECHNICIAN_ID)),
                    technicianName = it.getString(it.getColumnIndexOrThrow(DatabaseHelper.COLUMN_INTERVENTION_TECHNICIAN_NAME)) ?: "",
                    equipmentId = it.getInt(it.getColumnIndexOrThrow(DatabaseHelper.COLUMN_INTERVENTION_EQUIPEMENT_ID)),
                    equipmentName = it.getString(it.getColumnIndexOrThrow(DatabaseHelper.COLUMN_INTERVENTION_EQUIPEMENT_NAME)) ?: "",
                    date = it.getString(it.getColumnIndexOrThrow(DatabaseHelper.COLUMN_INTERVENTION_DATE)) ?: "",
                    priority = Intervention.Priority.valueOf(
                        it.getString(it.getColumnIndexOrThrow(DatabaseHelper.COLUMN_INTERVENTION_PRIORITY)) ?: "MEDIUM"
                    ),
                    status = Intervention.Status.valueOf(
                        it.getString(it.getColumnIndexOrThrow(DatabaseHelper.COLUMN_INTERVENTION_STATUS)) ?: "PENDING"
                    ),
                    createdAt = it.getString(it.getColumnIndexOrThrow(DatabaseHelper.COLUMN_INTERVENTION_CREATED_AT)) ?: ""
                )
                interventions.add(intervention)
            }
        }
        return interventions
    }

    fun getInterventionById(id: Int): Intervention? {
        val db = dbHelper.readableDatabase
        val cursor = db.query(
            DatabaseHelper.TABLE_INTERVENTIONS,
            null,
            "${DatabaseHelper.COLUMN_INTERVENTION_ID} = ?",
            arrayOf(id.toString()),
            null,
            null,
            null
        )

        var intervention: Intervention? = null
        cursor?.use {
            if (it.moveToFirst()) {
                intervention = Intervention(
                    id = it.getInt(it.getColumnIndexOrThrow(DatabaseHelper.COLUMN_INTERVENTION_ID)),
                    title = it.getString(it.getColumnIndexOrThrow(DatabaseHelper.COLUMN_INTERVENTION_TITLE)) ?: "",
                    description = it.getString(it.getColumnIndexOrThrow(DatabaseHelper.COLUMN_INTERVENTION_DESCRIPTION)) ?: "",
                    technicianId = it.getInt(it.getColumnIndexOrThrow(DatabaseHelper.COLUMN_INTERVENTION_TECHNICIAN_ID)),
                    technicianName = it.getString(it.getColumnIndexOrThrow(DatabaseHelper.COLUMN_INTERVENTION_TECHNICIAN_NAME)) ?: "",
                    equipmentId = it.getInt(it.getColumnIndexOrThrow(DatabaseHelper.COLUMN_INTERVENTION_EQUIPEMENT_ID)),
                    equipmentName = it.getString(it.getColumnIndexOrThrow(DatabaseHelper.COLUMN_INTERVENTION_EQUIPEMENT_NAME)) ?: "",
                    date = it.getString(it.getColumnIndexOrThrow(DatabaseHelper.COLUMN_INTERVENTION_DATE)) ?: "",
                    priority = Intervention.Priority.valueOf(
                        it.getString(it.getColumnIndexOrThrow(DatabaseHelper.COLUMN_INTERVENTION_PRIORITY)) ?: "MEDIUM"
                    ),
                    status = Intervention.Status.valueOf(
                        it.getString(it.getColumnIndexOrThrow(DatabaseHelper.COLUMN_INTERVENTION_STATUS)) ?: "PENDING"
                    ),
                    createdAt = it.getString(it.getColumnIndexOrThrow(DatabaseHelper.COLUMN_INTERVENTION_CREATED_AT)) ?: ""
                )
            }
        }
        return intervention
    }

    fun getInterventionsByStatus(status: Intervention.Status): List<Intervention> {
        return getAllInterventions().filter { it.status == status }
    }

    fun getInterventionsByTechnician(technicianId: Int): List<Intervention> {
        return getAllInterventions().filter { it.technicianId == technicianId }
    }

    fun insertIntervention(intervention: Intervention): Long {
        val db = dbHelper.writableDatabase
        val values = ContentValues().apply {
            put(DatabaseHelper.COLUMN_INTERVENTION_TITLE, intervention.title)
            put(DatabaseHelper.COLUMN_INTERVENTION_DESCRIPTION, intervention.description)
            put(DatabaseHelper.COLUMN_INTERVENTION_TECHNICIAN_ID, intervention.technicianId)
            put(DatabaseHelper.COLUMN_INTERVENTION_TECHNICIAN_NAME, intervention.technicianName)
            put(DatabaseHelper.COLUMN_INTERVENTION_EQUIPEMENT_ID, intervention.equipmentId)
            put(DatabaseHelper.COLUMN_INTERVENTION_EQUIPEMENT_NAME, intervention.equipmentName)
            put(DatabaseHelper.COLUMN_INTERVENTION_DATE, intervention.date)
            put(DatabaseHelper.COLUMN_INTERVENTION_PRIORITY, intervention.priority.name)
            put(DatabaseHelper.COLUMN_INTERVENTION_STATUS, intervention.status.name)
            put(DatabaseHelper.COLUMN_INTERVENTION_CREATED_AT, intervention.createdAt)
        }
        return db.insert(DatabaseHelper.TABLE_INTERVENTIONS, null, values)
    }

    fun updateIntervention(intervention: Intervention): Int {
        val db = dbHelper.writableDatabase
        val values = ContentValues().apply {
            put(DatabaseHelper.COLUMN_INTERVENTION_TITLE, intervention.title)
            put(DatabaseHelper.COLUMN_INTERVENTION_DESCRIPTION, intervention.description)
            put(DatabaseHelper.COLUMN_INTERVENTION_TECHNICIAN_ID, intervention.technicianId)
            put(DatabaseHelper.COLUMN_INTERVENTION_TECHNICIAN_NAME, intervention.technicianName)
            put(DatabaseHelper.COLUMN_INTERVENTION_EQUIPEMENT_ID, intervention.equipmentId)
            put(DatabaseHelper.COLUMN_INTERVENTION_EQUIPEMENT_NAME, intervention.equipmentName)
            put(DatabaseHelper.COLUMN_INTERVENTION_DATE, intervention.date)
            put(DatabaseHelper.COLUMN_INTERVENTION_PRIORITY, intervention.priority.name)
            put(DatabaseHelper.COLUMN_INTERVENTION_STATUS, intervention.status.name)
        }
        return db.update(
            DatabaseHelper.TABLE_INTERVENTIONS,
            values,
            "${DatabaseHelper.COLUMN_INTERVENTION_ID} = ?",
            arrayOf(intervention.id.toString())
        )
    }

    fun deleteIntervention(id: Int): Int {
        val db = dbHelper.writableDatabase
        return db.delete(
            DatabaseHelper.TABLE_INTERVENTIONS,
            "${DatabaseHelper.COLUMN_INTERVENTION_ID} = ?",
            arrayOf(id.toString())
        )
    }

    fun getTotalInterventionsCount(): Int {
        return getAllInterventions().size
    }

    fun getCompletedInterventionsCount(): Int {
        return getInterventionsByStatus(Intervention.Status.COMPLETED).size
    }

    fun getPendingInterventionsCount(): Int {
        return getInterventionsByStatus(Intervention.Status.PENDING).size
    }

    fun getInProgressInterventionsCount(): Int {
        return getInterventionsByStatus(Intervention.Status.IN_PROGRESS).size
    }
}
