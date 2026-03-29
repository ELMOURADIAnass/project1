package com.ocp.interventionapp.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.ocp.interventionapp.R
import com.ocp.interventionapp.data.model.Equipement
import java.util.Locale

/**
 * RecyclerView Adapter for displaying Equipment list
 */
class EquipmentAdapter(
    private var equipments: List<Equipement>,
    private val onItemClick: (Equipement) -> Unit,
    private val onEditClick: (Equipement) -> Unit,
    private val onDeleteClick: (Equipement) -> Unit
) : RecyclerView.Adapter<EquipmentAdapter.EquipmentViewHolder>() {

    inner class EquipmentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val cardName: TextView = itemView.findViewById(R.id.cardName)
        private val cardType: TextView = itemView.findViewById(R.id.cardType)
        private val cardSerial: TextView = itemView.findViewById(R.id.cardSerial)
        private val cardLocation: TextView = itemView.findViewById(R.id.cardLocation)
        private val cardStatus: TextView = itemView.findViewById(R.id.cardStatus)
        private val cardView: CardView = itemView.findViewById(R.id.cardView)

        fun bind(equipment: Equipement) {
            cardName.text = equipment.name
            cardType.text = "Type: ${equipment.type}"
            cardSerial.text = "S/N: ${equipment.serialNumber}"
            cardLocation.text = "Location: ${equipment.location}"
            cardStatus.text = equipment.status.name.lowercase(Locale.getDefault()).capitalize(Locale.getDefault())
            cardStatus.setTextColor(equipment.getStatusColor())

            cardView.setOnClickListener { onItemClick(equipment) }
            cardView.setOnLongClickListener { 
                onEditClick(equipment)
                true
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EquipmentViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_equipment, parent, false)
        return EquipmentViewHolder(view)
    }

    override fun onBindViewHolder(holder: EquipmentViewHolder, position: Int) {
        holder.bind(equipments[position])
    }

    override fun getItemCount(): Int = equipments.size

    fun updateEquipments(newEquipments: List<Equipement>) {
        val diffCallback = EquipmentDiffCallback(equipments, newEquipments)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        equipments = newEquipments
        diffResult.dispatchUpdatesTo(this)
    }

    class EquipmentDiffCallback(
        private val oldList: List<Equipement>,
        private val newList: List<Equipement>
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
