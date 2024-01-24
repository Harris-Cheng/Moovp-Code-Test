package com.example.moovpcodetest.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.moovpcodetest.databinding.ItemPeopleBinding
import com.example.moovpcodetest.model.People

class PeopleAdapter: ListAdapter<People, PeopleViewHolder>(PeopleDiffCallBack()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PeopleViewHolder {
        return PeopleViewHolder(
            ItemPeopleBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: PeopleViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

}

class PeopleViewHolder(
    private val binding: ItemPeopleBinding,
): RecyclerView.ViewHolder(binding.root) {
    fun bind(item: People) {
        binding.ivImage.load(item.image)
        binding.tvName.text = "${item.lastName} ${item.firstName}"
        binding.tvEmail.text = item.email
    }
}

class PeopleDiffCallBack: DiffUtil.ItemCallback<People>() {
    override fun areItemsTheSame(oldItem: People, newItem: People): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: People, newItem: People): Boolean {
        return oldItem == newItem
    }

}