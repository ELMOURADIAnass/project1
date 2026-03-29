package com.ocp.interventionapp.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ocp.interventionapp.data.database.DatabaseHelper
import com.ocp.interventionapp.data.model.Intervention
import com.ocp.interventionapp.data.model.Equipement
import com.ocp.interventionapp.data.model.User
import com.ocp.interventionapp.data.repository.InterventionRepository
import com.ocp.interventionapp.data.repository.EquipementRepository
import com.ocp.interventionapp.data.repository.UserRepository

/**
 * Main ViewModel for the Intervention Management App
 */
class InterventionViewModel : ViewModel() {

    private lateinit var dbHelper: DatabaseHelper
    private lateinit var interventionRepository: InterventionRepository
    private lateinit var equipementRepository: EquipementRepository
    private lateinit var userRepository: UserRepository

    // LiveData for interventions
    private val _interventions = MutableLiveData<List<Intervention>>()
    val interventions: LiveData<List<Intervention>> = _interventions

    // LiveData for equipments
    private val _equipments = MutableLiveData<List<Equipement>>()
    val equipments: LiveData<List<Equipement>> = _equipments

    // LiveData for users/technicians
    private val _technicians = MutableLiveData<List<User>>()
    val technicians: LiveData<List<User>> = _technicians

    // LiveData for selected intervention
    private val _selectedIntervention = MutableLiveData<Intervention?>()
    val selectedIntervention: LiveData<Intervention?> = _selectedIntervention

    // LiveData for dashboard statistics
    private val _totalInterventions = MutableLiveData<Int>()
    val totalInterventions: LiveData<Int> = _totalInterventions

    private val _completedInterventions = MutableLiveData<Int>()
    val completedInterventions: LiveData<Int> = _completedInterventions

    private val _pendingInterventions = MutableLiveData<Int>()
    val pendingInterventions: LiveData<Int> = _pendingInterventions

    private val _inProgressInterventions = MutableLiveData<Int>()
    val inProgressInterventions: LiveData<Int> = _inProgressInterventions

    // Initialize repositories with context (called from Activity)
    fun init(context: android.content.Context) {
        dbHelper = DatabaseHelper(context)
        interventionRepository = InterventionRepository(dbHelper)
        equipementRepository = EquipementRepository(dbHelper)
        userRepository = UserRepository(dbHelper)
    }

    // Intervention operations
    fun loadAllInterventions() {
        _interventions.value = interventionRepository.getAllInterventions()
        updateStatistics()
    }

    fun getInterventionById(id: Int): Intervention? {
        return interventionRepository.getInterventionById(id)
    }

    fun addIntervention(intervention: Intervention) {
        interventionRepository.insertIntervention(intervention)
        loadAllInterventions()
    }

    fun updateIntervention(intervention: Intervention) {
        interventionRepository.updateIntervention(intervention)
        loadAllInterventions()
    }

    fun deleteIntervention(id: Int) {
        interventionRepository.deleteIntervention(id)
        loadAllInterventions()
    }

    fun setSelectedIntervention(intervention: Intervention?) {
        _selectedIntervention.value = intervention
    }

    // Equipment operations
    fun loadAllEquipments() {
        _equipments.value = equipementRepository.getAllEquipements()
    }

    fun getAvailableEquipments(): List<Equipement> {
        return equipementRepository.getAvailableEquipements()
    }

    fun addEquipment(equipment: Equipement) {
        equipementRepository.insertEquipement(equipment)
        loadAllEquipments()
    }

    fun updateEquipment(equipment: Equipement) {
        equipementRepository.updateEquipement(equipment)
        loadAllEquipments()
    }

    fun deleteEquipment(id: Int) {
        equipementRepository.deleteEquipement(id)
        loadAllEquipments()
    }

    // User/Technician operations
    fun loadAllTechnicians() {
        _technicians.value = userRepository.getTechniciansOnly()
    }

    fun getAllUsers(): List<User> {
        return userRepository.getAllUsers()
    }

    // Dashboard statistics
    fun updateStatistics() {
        _totalInterventions.value = interventionRepository.getTotalInterventionsCount()
        _completedInterventions.value = interventionRepository.getCompletedInterventionsCount()
        _pendingInterventions.value = interventionRepository.getPendingInterventionsCount()
        _inProgressInterventions.value = interventionRepository.getInProgressInterventionsCount()
    }

    // Get interventions by status
    fun getInterventionsByStatus(status: Intervention.Status): List<Intervention> {
        return interventionRepository.getInterventionsByStatus(status)
    }

    // Generate report for an intervention
    fun generateReport(intervention: Intervention): String {
        return buildString {
            appendLine("========================================")
            appendLine("       RAPPORT D'INTERVENTION")
            appendLine("========================================")
            appendLine("")
            appendLine("ID Intervention: ${intervention.id}")
            appendLine("Titre: ${intervention.title}")
            appendLine("Description: ${intervention.description}")
            appendLine("")
            appendLine("Technicien: ${intervention.technicianName}")
            appendLine("Équipement: ${intervention.equipmentName}")
            appendLine("Date: ${intervention.date}")
            appendLine("")
            appendLine("Priorité: ${intervention.priority}")
            appendLine("Statut: ${intervention.status}")
            appendLine("")
            appendLine("Créé le: ${intervention.createdAt}")
            appendLine("========================================")
        }
    }

    override fun onCleared() {
        super.onCleared()
        // Close database if needed
    }
}
