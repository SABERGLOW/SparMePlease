package com.example.sparmeplease.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.sparmeplease.ItemDetails
import com.example.sparmeplease.MainActivity
import com.example.sparmeplease.R
import com.example.sparmeplease.data.AppDatabase
import com.example.sparmeplease.data.Item
import com.example.sparmeplease.databinding.ItemRowBinding


class ItemAdapter : RecyclerView.Adapter<ItemAdapter.ViewHolder>
{

    var items = mutableListOf<Item>()
    val context: Context

    constructor(context: Context, listItems: List<Item>)
    {
        this.context = context
        items.addAll(listItems)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder
    {
        return ViewHolder(ItemRowBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun getItemCount(): Int
    {
        return items.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int)
    {
        val currentItem = items[position]

        holder.binding.itemName.text = currentItem.name
        holder.binding.itemPrice.text = currentItem.price.toString()+" ft"
        holder.binding.cbDone.isChecked = currentItem.status

        holder.binding.btnDelete.setOnClickListener{
            deleteItem(holder.adapterPosition)
        }

        holder.binding.cbDone.setOnClickListener {
            items[holder.adapterPosition].status = holder.binding.cbDone.isChecked
            Thread{
                AppDatabase.getInstance(context).itemDao().updateItem(items[holder.adapterPosition])
            }.start()
        }

        holder.binding.btnEdit.setOnClickListener{
            var intent = Intent()
            intent.setClass(context, ItemDetails::class.java)
            intent.putExtra("itemId",items[holder.adapterPosition])
            startActivity(context,intent,null)
        }

        //... categories and their logos
        if (items[holder.adapterPosition].category == 0) // games
        {
            holder.binding.ivIcon.setImageResource(R.drawable.game)
        }
        else if (items[holder.adapterPosition].category == 1) // food
        {
            holder.binding.ivIcon.setImageResource(R.drawable.food)
        }
        else if (items[holder.adapterPosition].category == 2) // drinks
        {
            holder.binding.ivIcon.setImageResource(R.drawable.drink)
        }
        else if (items[holder.adapterPosition].category == 3) // books
        {
            holder.binding.ivIcon.setImageResource(R.drawable.book)
        }
        else if (items[holder.adapterPosition].category == 4) // everything else
        {
            holder.binding.ivIcon.setImageResource(R.drawable.misc)
        }
    }

    public fun addItem(item: Item)
    {
        items.add(item)
        notifyItemInserted(items.lastIndex)
    }

    private fun deleteItem(position: Int)
    {
        Thread{
            AppDatabase.getInstance(context).itemDao().deleteItem(items.get(position))
            (context as MainActivity).runOnUiThread {
                items.removeAt(position)
                notifyItemRemoved(position)
            }
        }.start()
    }

    public fun deleteAllItems()
    {
        items.removeAll(items)
        notifyDataSetChanged()
    }

    inner class ViewHolder(val binding: ItemRowBinding) : RecyclerView.ViewHolder(binding.root)
    {
    }


}