package com.example.firebaserealtimedatabase.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.firebaserealtimedatabase.EmployeeModel
import com.example.firebaserealtimedatabase.R
import kotlinx.android.synthetic.main.emp_list_item.view.*

class empAdapter(private var ds:ArrayList<EmployeeModel>):RecyclerView.Adapter<empAdapter.viewHolder>() {
    private lateinit var empListener:OnItemClickListener
    interface OnItemClickListener{
        fun onItemClick(position: Int)
    }
    fun setOnItemclickListener (clickListener: OnItemClickListener){
        empListener = clickListener
    }


    class viewHolder(itemView: View,clickListener: OnItemClickListener) :RecyclerView.ViewHolder(itemView){
        init {
            itemView.setOnClickListener{
                clickListener.onItemClick(adapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewHolder {
     val itemView = LayoutInflater.from(parent.context).inflate(R.layout.emp_list_item,parent,false)
        return viewHolder(itemView, empListener)
    }

    override fun onBindViewHolder(holder: viewHolder, position: Int) {
        holder.itemView.apply {
            tvEmpName.text = ds[position].empName
        }
    }

    override fun getItemCount(): Int {
        return ds.size
    }
}