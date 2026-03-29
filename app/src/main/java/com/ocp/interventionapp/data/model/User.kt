package com.ocp.interventionapp.data.model

/**
 * Model class representing a User (Technician)
 */
data class User(
    val id: Int = 0,
    val name: String,
    val email: String,
    val phone: String,
    val role: UserRole = UserRole.TECHNICIAN,
    val isActive: Boolean = true
) {
    enum class UserRole {
        ADMIN, TECHNICIAN, SUPERVISOR
    }
}
