package com.ocp.interventionapp.data.model

/**
 * Model class representing Equipment
 */
data class Equipement(
    val id: Int = 0,
    val name: String,
    val type: String,
    val serialNumber: String,
    val location: String,
    val status: EquipmentStatus = EquipmentStatus.AVAILABLE,
    val purchaseDate: String = "",
    val notes: String = ""
) {
    enum class EquipmentStatus {
        AVAILABLE, IN_USE, MAINTENANCE, BROKEN
    }

    fun getStatusColor(): Int {
        return when (status) {
            EquipmentStatus.AVAILABLE -> 0xFF4CAF50.toInt() // Green
            EquipmentStatus.IN_USE -> 0xFF2196F3.toInt() // Blue
            EquipmentStatus.MAINTENANCE -> 0xFFFFC107.toInt() // Amber
            EquipmentStatus.BROKEN -> 0xFFF44336.toInt() // Red
        }
    }
}
