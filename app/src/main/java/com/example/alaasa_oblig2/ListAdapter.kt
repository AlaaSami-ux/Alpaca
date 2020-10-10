package com.example.alaasa_oblig2

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.android.synthetic.main.element.view.*


class ListAdapter(val context: Context, val brukerList: MutableList<Alpaca>) :
    RecyclerView.Adapter<ListAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v:View = LayoutInflater.from(parent.context).inflate(R.layout.element, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data: Alpaca = brukerList[position]
        holder.fAge.text = data.age
        holder.fName.text = data.name
        holder.fLocation.text = data.location
        Glide.with(context).load(data.imgSrc).into(holder.fImgSrc)

    }

    override fun getItemCount(): Int {
        return brukerList.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val fName: TextView = itemView.name
        val fAge: TextView = itemView.age
        val fLocation: TextView = itemView.location
        val fImgSrc: CircleImageView = itemView.imgSrc

    }
}



