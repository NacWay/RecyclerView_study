package com.study.recyclingview_study.counries

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.study.recyclingview_study.ItemTouchHelperAdapter
import com.study.recyclingview_study.R
import java.util.Collections




class CountriesAdapter
    (
    private val countries: MutableList<String>,
    private val flags: MutableList<String>,
): RecyclerView.Adapter<CountriesAdapter.MyViewHolder>(),
    ItemTouchHelperAdapter
{

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val imageView : ImageView = itemView.findViewById(R.id.imageView)
        val largeTextView: TextView = itemView.findViewById(R.id.textViewLarge)
        val smallTextView: TextView = itemView.findViewById(R.id.textViewSmall)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.country_layout, parent, false)

        return MyViewHolder(itemView)
    }

    override fun getItemCount() = countries.size

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        Glide.with(holder.itemView.getContext())
            .load(flags.get(position))
            .placeholder(android.R.drawable.stat_notify_sync)
            .into(holder.imageView);
        holder.largeTextView.text = countries[position]
        holder.smallTextView.text = "${position + 1} id"

    }

    override fun onItemMove(fromPosition: Int, toPosition: Int) : Boolean{
        if (fromPosition < toPosition) {
            for (i in fromPosition until toPosition) {
                Collections.swap(countries, i, i + 1)
                Collections.swap(flags, i, i + 1)
            }
        } else {
            for (i in fromPosition downTo toPosition + 1) {
                Collections.swap(countries, i, i - 1)
                Collections.swap(flags, i, i - 1)
            }
        }
        notifyItemMoved(fromPosition, toPosition)
        return true
    }

    override fun onItemDismiss(position: Int) {
        countries.removeAt(position)
        flags.removeAt(position)
        notifyItemRemoved(position);
    }
}