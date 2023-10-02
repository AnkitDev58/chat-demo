package com.example.chatdemo.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.chatdemo.databinding.ItemAllUserBinding
import com.example.chatdemo.model.UserDetailsModel

class AllUserAdapter : ListAdapter<UserDetailsModel, AllUserAdapter.ViewHolder>(ALlUserDiff()) {
    inner class ViewHolder(private val binding: ItemAllUserBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun setData(model: UserDetailsModel?) {
            binding.txtName.text = "${model?.name}"
            binding.txtLastMsg.text = "${model?.phoneNumber}"
        }

        init {

            binding.root.setOnClickListener {
                val position = layoutPosition
                if (position == RecyclerView.NO_POSITION) return@setOnClickListener
                val item = getItem(position)
                onUserClick?.invoke(item)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemAllUserBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val model = getItem(position)
        holder.setData(model)
    }

    var onUserClick: ((UserDetailsModel) -> Unit)? = null

}

class ALlUserDiff : DiffUtil.ItemCallback<UserDetailsModel>() {
    override fun areItemsTheSame(oldItem: UserDetailsModel, newItem: UserDetailsModel): Boolean {
        return oldItem.uid == newItem.uid && oldItem.lastOnlineMili == newItem.lastOnlineMili
    }

    override fun areContentsTheSame(oldItem: UserDetailsModel, newItem: UserDetailsModel): Boolean {
        return oldItem == newItem
    }

}