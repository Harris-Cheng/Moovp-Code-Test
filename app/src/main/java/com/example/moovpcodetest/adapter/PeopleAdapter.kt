package com.example.moovpcodetest.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.moovpcodetest.databinding.ItemDetailPeopleBinding
import com.example.moovpcodetest.databinding.ItemPeopleBinding
import com.example.moovpcodetest.model.People
import com.example.moovpcodetest.model.ui.PeopleUIModel

interface ListItemClickListener {
    fun onItemClick(item: People)
}

class PeopleAdapter(
    private val onListItemClickListener: ListItemClickListener,
): ListAdapter<PeopleUIModel, RecyclerView.ViewHolder>(PeopleDiffCallBack()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == 0) {
            PeopleDetailViewHolder(
                ItemDetailPeopleBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
                )
            )
        } else {
            PeopleListViewHolder(
                ItemPeopleBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
                )
            )
        }

    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is PeopleDetailViewHolder) {
            holder.bind(getItem(position))
        } else if (holder is PeopleListViewHolder) {
            holder.bind(getItem(position))
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (getItem(position) is PeopleUIModel.DetailItem) {
            0
        } else {
            1
        }
    }

    inner class PeopleListViewHolder(
        private val binding: ItemPeopleBinding,
    ): RecyclerView.ViewHolder(binding.root) {
        fun bind(item: PeopleUIModel) {
            val mItem = item as PeopleUIModel.ListItem
            binding.ivImage.load(mItem.item.image)
            binding.tvName.text = mItem.item.fullName

            binding.root.setOnClickListener {
                onListItemClickListener.onItemClick(
                    mItem.item
                )
            }
        }
    }

    inner class PeopleDetailViewHolder(
        private val binding: ItemDetailPeopleBinding,
    ): RecyclerView.ViewHolder(binding.root) {
        fun bind(item: PeopleUIModel) {
            val mItem = item as PeopleUIModel.DetailItem
            binding.ivImage.load(mItem.item.image)
            binding.tvName.text = mItem.item.fullName
            binding.tvEmail.text = mItem.item.email
        }
    }

}

class PeopleDiffCallBack: DiffUtil.ItemCallback<PeopleUIModel>() {
    override fun areItemsTheSame(oldItem: PeopleUIModel, newItem: PeopleUIModel): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: PeopleUIModel, newItem: PeopleUIModel): Boolean {
        return oldItem == newItem
    }

}