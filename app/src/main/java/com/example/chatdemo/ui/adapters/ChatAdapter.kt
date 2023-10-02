package com.example.chatdemo.ui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.example.chatdemo.databinding.ItemLeftBinding
import com.example.chatdemo.databinding.ItemRightBinding
import com.example.chatdemo.model.ChatModel
import com.example.chatdemo.utils.SharePref

class ChatAdapter(val context: Context) :
    ListAdapter<ChatModel, ChatAdapter.ViewHolder>(ChatDiff()) {
    private val sharePref by lazy { SharePref(context) }

    inner class ViewHolder(private val binding: ViewBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun setData(item: ChatModel?) {
            if (binding is ItemLeftBinding) {
                binding.txtMessage.text = "${item?.message}"
            } else if (binding is ItemRightBinding) {
                binding.txtMessage.text = "${item?.message}"
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            if (viewType == 0) ItemLeftBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ) else ItemRightBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.setData(item)
    }

    override fun getItemViewType(position: Int): Int {
        val item = getItem(position)
        return if (item.senderUID == sharePref.userUUID) 1 else 0
    }

}

class ChatDiff() : DiffUtil.ItemCallback<ChatModel>() {
    override fun areItemsTheSame(oldItem: ChatModel, newItem: ChatModel): Boolean {
        return oldItem.senderUID == newItem.senderUID && oldItem.receiverUID == newItem.receiverUID
    }

    override fun areContentsTheSame(oldItem: ChatModel, newItem: ChatModel): Boolean {
        return newItem == oldItem
    }

}