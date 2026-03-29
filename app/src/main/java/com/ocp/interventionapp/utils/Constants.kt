package com.ocp.interventionapp.utils

object Constants {
    // Request codes for activities
    const val REQUEST_CODE_ADD_INTERVENTION = 1001
    const val REQUEST_CODE_EDIT_INTERVENTION = 1002
    const val REQUEST_CODE_ADD_EQUIPMENT = 1003
    const val REQUEST_CODE_EDIT_EQUIPMENT = 1004
    
    // Fragment tags
    const val FRAGMENT_DASHBOARD = "dashboard"
    const val FRAGMENT_INTERVENTIONS = "interventions"
    const val FRAGMENT_EQUIPMENTS = "equipments"
    const val FRAGMENT_STATISTICS = "statistics"
    
    // Intent extras
    const val EXTRA_INTERVENTION_ID = "extra_intervention_id"
    const val EXTRA_INTERVENTION = "extra_intervention"
    const val EXTRA_EQUIPMENT_ID = "extra_equipment_id"
    const val EXTRA_EQUIPMENT = "extra_equipment"
    
    // Shared Preferences
    const val PREF_NAME = "intervention_app_prefs"
    const val PREF_SELECTED_TAB = "selected_tab"
}
