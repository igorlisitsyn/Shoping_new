package com.example.shoping.presentation

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.TextureView
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.shoping.R
import com.example.shoping.domain.ShopItem

class ShopListAdapter : RecyclerView.Adapter<ShopListAdapter.ShopItemViewHolder>() {

    var shoplist = listOf<ShopItem>()
        // @SuppressLint("NotifyDataSetChanged")
        set(value) {
            field = value
            notifyDataSetChanged()
        }

   // var onShopItemLongClick: OnShopItemLongClickListener? = null
    // вариант kotlin через лямбда функцию
    var onShopItemLongClick: ((ShopItem) -> Unit)? = null

    class ShopItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvName = itemView.findViewById<TextView>(R.id.tv_name)
        val tvCount = itemView.findViewById<TextView>(R.id.tv_count)

    }

    /*class ShopItemViewHolder(val view: View): RecyclerView.ViewHolder(view) {
        val tvName = view.findViewById<TextView>(R.id.tv_name)
        val tvCount = view.findViewById<TextView>(R.id.tv_count)
    }*/

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShopItemViewHolder {
        // определяем условие вызова layout
        val layoutGet = when (viewType) {
            VIEW_TYPE_ENABLED -> R.layout.item_shop_enabled
            VIEW_TYPE_DISABLED -> R.layout.item_shop_disabled
            else -> throw RuntimeException("Unknown view type: $viewType")
        }

        val view =
            LayoutInflater.from(parent.context).inflate(layoutGet, parent, false)

        return ShopItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: ShopItemViewHolder, position: Int) {
        val shopItem = shoplist[position]
        holder.tvName.text = shopItem.name
        holder.tvCount.text = shopItem.count.toString()
        holder.itemView.setOnLongClickListener {
           // onShopItemLongClick?.onSopItemLongClick(shopItem)
           onShopItemLongClick?.invoke(shopItem)
            true
        }
        /*val status = if (shopItem.enabled) {
            "Активная запись"
        } else {
            "Пассивная запись"
        }*/

        // recyclerView - использует повторно view поэтому при установке по значению
        // повторно вызываются viewHolder
        // поэтому необходимо восстанавливать предыдущие значения holder

        // пример
        /*if (shopItem.enabled) {
            holder.tvName.text = "${shopItem.name} $status"
            holder.tvCount.text = shopItem.count.toString()
            holder.tvName.setTextColor(ContextCompat.getColor(holder.itemView.context, android.R.color.holo_blue_light))
        } else {
            holder.tvName.text = "${shopItem.name} $status"
            holder.tvCount.text = shopItem.count.toString()
            holder.tvName.setTextColor(ContextCompat.getColor(holder.itemView.context, android.R.color.white))
        }*/

    }


    override fun getItemCount(): Int {
        return shoplist.size
    }


    // позволяет использовать viewType в ViewHolder
    // реализуем логику возврата значения viewType в ViewHolder
    // количество обращений для создания нового ViewHolder не уменьшается
    override fun getItemViewType(position: Int): Int {
        val iten = shoplist[position]
        return if (iten.enabled) {
            VIEW_TYPE_ENABLED
        } else {
            VIEW_TYPE_DISABLED
        }
    }
    // определяем константы
    companion object {
        const val VIEW_TYPE_ENABLED = 0
        const val VIEW_TYPE_DISABLED = 1

        const val MAX_POOL_SIZE = 15 // количество ViewHolder созданных заранее
    }

    //так как из адаптера не можем работать с ViewModel
    // реализация осуществляется через интерфейс
    interface OnShopItemLongClickListener {

        fun onSopItemLongClick(shopItem: ShopItem)

    }

}