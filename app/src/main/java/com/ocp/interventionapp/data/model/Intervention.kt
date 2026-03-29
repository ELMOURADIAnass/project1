package com.ocp.interventionapp.data.model

/**
 * Model class representing an Intervention
 */
data class Intervention(
    val id: Int = 0,
    val title: String,
    val description: String,
    val technicianId: Int,
    val technicianName: String,
    val equipmentId: Int,
    val equipmentName: String,
    val date: String,
    val priority: Priority,
    val status: Status,
    val createdAt: String = System.currentTimeMillis().toString()
) {
    enum class Priority {
        LOW, MEDIUM, HIGH, URGENT
    }

    enum class Status {
        PENDING, IN_PROGRESS, COMPLETED
    }

    fun getPriorityColor(): Int {
        return when (priority) {
            Priority.LOW -> 0xFF4CAF50.toInt() // Green
            Priority.MEDIUM -> 0xFFFFC107.toInt() // Amber
            Priority.HIGH -> 0xFFFF9800.toInt() // Orange
            Priority.URGENT -> 0xFFF44336.toInt() // Red
        }
    }

    fun getStatusColor(): Int {
        return when (status) {
            Status.PENDING -> 0xFFFFC107.toInt() // Amber
            Status.IN_PROGRESS -> 0xFF2196F3.toInt() // Blue
            Status.COMPLETED -> 0xFF4CAF50.toInt() // Green
        }
    }
}
