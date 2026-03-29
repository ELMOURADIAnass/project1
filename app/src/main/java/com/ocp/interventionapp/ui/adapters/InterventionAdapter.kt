package com.ocp.interventionapp.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.ocp.interventionapp.R
import com.ocp.interventionapp.data.model.Intervention
import java.util.Locale

/**
 * RecyclerView Adapter for displaying Interventions list
 */
class InterventionAdapter(
    private var interventions: List<Intervention>,
    private val onItemClick: (Intervention) -> Unit,
    private val onEditClick: (Intervention) -> Unit,
    private val onDeleteClick: (Intervention) -> Unit
) : RecyclerView.Adapter<InterventionAdapter.InterventionViewHolder>() {

    inner class InterventionViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val cardTitle: TextView = itemView.findViewById(R.id.cardTitle)
        private val cardDescription: TextView = itemView.findViewById(R.id.cardDescription)
        private val cardTechnician: TextView = itemView.findViewById(R.id.cardTechnician)
        private val cardEquipment: TextView = itemView.findViewById(R.id.cardEquipment)
        private val cardDate: TextView = itemView.findViewById(R.id.cardDate)
        private val cardPriority: TextView = itemView.findViewById(R.id.cardPriority)
        private val cardStatus: TextView = itemView.findViewById(R.id.cardStatus)
        private val cardView: CardView = itemView.findViewById(R.id.cardView)

        fun bind(intervention: Intervention) {
            cardTitle.text = intervention.title
            cardDescription.text = intervention.description
            cardTechnician.text = "Technicien: ${intervention.technicianName}"
            cardEquipment.text = "Équipement: ${intervention.equipmentName}"
            cardDate.text = "Date: ${intervention.date}"
            cardPriority.text = intervention.priority.name.lowercase(Locale.getDefault()).capitalize(Locale.getDefault())
            cardStatus.text = intervention.status.name.lowercase(Locale.getDefault()).capitalize(Locale.getDefault())

            // Set priority color
            cardPriority.setTextColor(intervention.getPriorityColor())
            
            // Set status color
            cardStatus.setTextColor(intervention.getStatusColor())

            // Set card click listener
            cardView.setOnClickListener { onItemClick(intervention) }
            
            // Set edit button listener (using long click for edit)
            cardView.setOnLongClickListener { 
                onEditClick(intervention)
                true
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InterventionViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_intervention, parent, false)
        return InterventionViewHolder(view)
    }

    override fun onBindViewHolder(holder: InterventionViewHolder, position: Int) {
        holder.bind(interventions[position])
    }

    override fun getItemCount(): Int = interventions.size

    fun updateInterventions(newInterventions: List<Intervention>) {
        val diffCallback = InterventionDiffCallback(interventions, newInterventions)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        interventions = newInterventions
        diffResult.dispatchUpdatesTo(this)
    }

    class InterventionDiffCallback(
        private val oldList: List<Intervention>,
        private val newList: List<Intervention>
    ) : DiffUtil.Callback() {
        override fun getOldListSize(): Int = oldList.size
        override fun getNewListSize(): Int = newList.size
        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition].id == newList[newItemPosition].id
        }
        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition] == newList[newItemPosition]
        }
    }
}
