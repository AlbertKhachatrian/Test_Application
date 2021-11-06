package com.example.test.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.DiffUtil
import com.example.base_mvvm.adapter.BaseAdapter
import com.example.base_mvvm.adapter.BaseViewHolder
import com.example.domain.model.Petrol
import com.example.test.R
import com.example.test.databinding.ItemPetrolBinding

class MainAdapter :
    BaseAdapter<ItemPetrolBinding, Petrol, MainAdapter.MainViewHolder>(diffCallback) {

    private var oldBinding: ItemPetrolBinding? = null
    private var oldItem: Petrol? = null

    var selectedItemPosition: Int? = null
    var selectedItem: Petrol? = null

    inner class MainViewHolder(private val binding: ItemPetrolBinding) :
        BaseViewHolder<Petrol, ItemPetrolBinding>(binding) {

        override fun bind(item: Petrol) {
            item.image?.let {
                binding.petrolImage.setImageDrawable(
                    ResourcesCompat.getDrawable(
                        binding.root.context.resources,
                        it, null
                    )
                )
            }
            with(binding) {
                if(oldItem != selectedItem) {
                    oldBinding?.let { it.root.isSelected = false }
                    oldItem?.let { it.isSelected = false }
                }
                petrolTitle.text = item.name
                petrolPrice.text = root.context.getString(R.string.x_dollar, item.price.toString())
                root.isSelected = item.isSelected
            }
        }

        override fun onItemClick(item: Petrol) {
            super.onItemClick(item)
            selectedItem = item
            selectedItemPosition = layoutPosition
            item.isSelected = !item.isSelected
            bind(item)
            if(oldItem != selectedItem){
                oldBinding = binding
                oldItem = item
            }
        }

    }

    companion object {
        val diffCallback by lazy {
            object : DiffUtil.ItemCallback<Petrol>() {
                override fun areItemsTheSame(oldItem: Petrol, newItem: Petrol): Boolean {
                    return oldItem == newItem
                }

                override fun areContentsTheSame(oldItem: Petrol, newItem: Petrol): Boolean {
                    return oldItem.price == newItem.price
                }

            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder =
        MainViewHolder(
            ItemPetrolBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )

}