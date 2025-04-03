package com.enderyasli.retrofitcoroutines.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.enderyasli.retrofitcoroutines.adapter.PostRecyclerAdapter.PostViewHolder
import com.enderyasli.retrofitcoroutines.data.Post
import com.enderyasli.retrofitcoroutines.databinding.ItemPostBinding

class PostRecyclerAdapter : RecyclerView.Adapter<PostViewHolder>() {

    inner class PostViewHolder(val binding: ItemPostBinding) :
        RecyclerView.ViewHolder(binding.root)

    private val diffCallBack = object : DiffUtil.ItemCallback<Post>() {
        override fun areItemsTheSame(
            oldItem: Post,
            newItem: Post
        ): Boolean {
            return oldItem.id == newItem.id

        }

        override fun areContentsTheSame(
            oldItem: Post,
            newItem: Post
        ): Boolean {
            return oldItem.id == newItem.id
        }

    }

    val differ = AsyncListDiffer<Post>(this, diffCallBack)
    override fun getItemCount(): Int {

        return differ.currentList.size
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PostViewHolder {

        val binding = ItemPostBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PostViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {

        val post = differ.currentList[position]

        holder.binding.apply {
            tvUserId.text = post.userId.toString()
            tvId.text = post.id.toString()
            tvTitle.text = post.title
            tvBody.text = post.body
        }
    }


}