package com.richard.postsapp.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.richard.postsapp.R
import com.richard.postsapp.data.Post
import java.text.SimpleDateFormat
import java.util.*

class PostAdapter: RecyclerView.Adapter<PostAdapter.PostViewHolder>() {

    private val TAG = "PostAdapter"

    inner class PostViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)

    private val diffCallback = object: DiffUtil.ItemCallback<Post>() {
        override fun areItemsTheSame(oldItem: Post, newItem: Post): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Post, newItem: Post): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }
    }

    private val differ = AsyncListDiffer(this, diffCallback)

    fun submitList(list: List<Post>) = differ.submitList(list)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        return PostViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_post,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        val post = differ.currentList[position]
        val view = holder.itemView
        val tvTitle = view.findViewById<TextView>(R.id.tvTitle)
        val title = post.title
        tvTitle.text = title
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }
}