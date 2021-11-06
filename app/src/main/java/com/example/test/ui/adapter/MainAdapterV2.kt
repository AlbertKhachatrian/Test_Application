package com.example.test.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.domain.model.Petrol
import com.example.test.R
import com.example.test.databinding.ItemPetrolBinding

class MainAdapterV2 : RecyclerView.Adapter<MainAdapterV2.MyViewHolder>() {

    private var oldItemPos: Int? = null
    var items = mutableListOf<Petrol>()
    var selectedItem: Int? = null
    lateinit var onItemChanged: (Int, Int) -> Unit

    inner class MyViewHolder(private val binding: ItemPetrolBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Petrol) {
            with(binding) {
                petrolTitle.text = item.name
                petrolPrice.text = root.context.getString(R.string.x_dollar, item.price.toString())
                petrolImage.setImageDrawable(
                    item.image?.let {
                        ResourcesCompat.getDrawable(
                            root.context.resources,
                            it,
                            null
                        )
                    }
                )
                root.isSelected = item.isSelected
                if(item.isSelected) {
                    selectedItem = layoutPosition
                    oldItemPos = layoutPosition
                }
                containerView.setOnClickListener {
                    items[layoutPosition].isSelected = true
                    if (oldItemPos!=null && oldItemPos!=layoutPosition){
                        items[oldItemPos!!].isSelected = false
                        onItemChanged.invoke(oldItemPos!!, layoutPosition)
                    }
                    notifyItemChanged(layoutPosition)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            ItemPetrolBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(items[position])
    }

    fun setData(list: List<Petrol>) {
        if(items.isEmpty()) {
            items = list.map { it.copy(it.name, it.price, it.image, it.isSelected) }.toMutableList()
        }else{
            for(i in items.indices){
                items[i] = list[i].copy(list[i].name, list[i].price, list[i].image, items[i].isSelected)
            }
        }
    }

    override fun getItemCount(): Int = items.size
}