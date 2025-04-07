package com.example.myandroidapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class ItemAdapter(
    private var items: List<Item>,
    private val onClick: (Item) -> Unit
) : RecyclerView.Adapter<ItemAdapter.ItemViewHolder>(), Filterable {

    private var filteredItems: List<Item> = items

    inner class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val imageView: ImageView = itemView.findViewById(R.id.ivItemImage)
        private val nameTextView: TextView = itemView.findViewById(R.id.tvItemName)
        private val categoryTextView: TextView = itemView.findViewById(R.id.tvCategory)

        fun bind(item: Item) {
            Glide.with(itemView.context)
                .load(item.imageUrl)
                .into(imageView)
            
            nameTextView.text = item.name
            categoryTextView.text = item.category
            
            itemView.setOnClickListener { onClick(item) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_row, parent, false)
        return ItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bind(filteredItems[position])
    }

    override fun getItemCount(): Int = filteredItems.size

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val query = constraint?.toString()?.lowercase() ?: ""
                val results = FilterResults()
                
                results.values = if (query.isEmpty()) {
                    items
                } else {
                    items.filter {
                        it.name.lowercase().contains(query) ||
                        it.category.lowercase().contains(query)
                    }
                }
                return results
            }

            @Suppress("UNCHECKED_CAST")
            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                filteredItems = results?.values as? List<Item> ?: emptyList()
                notifyDataSetChanged()
            }
        }
    }

    fun updateItems(newItems: List<Item>) {
        items = newItems
        filteredItems = newItems
        notifyDataSetChanged()
    }
}