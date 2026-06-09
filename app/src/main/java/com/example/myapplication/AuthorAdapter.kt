package com.example.myapplication

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class AuthorAdapter(
    private var authors: List<Author>,
    private val onFollowClick: (Author) -> Unit
) : RecyclerView.Adapter<AuthorAdapter.AuthorViewHolder>() {

    fun updateData(newAuthors: List<Author>) {
        authors = newAuthors
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AuthorViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_author, parent, false)
        return AuthorViewHolder(view)
    }

    override fun onBindViewHolder(holder: AuthorViewHolder, position: Int) {
        holder.bind(authors[position])
    }

    override fun getItemCount(): Int = authors.size

    inner class AuthorViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val authorAvatar: ImageView = itemView.findViewById(R.id.authorAvatar)
        private val authorName: TextView = itemView.findViewById(R.id.authorName)
        private val authorBio: TextView = itemView.findViewById(R.id.authorBio)
        private val authorFollowerCount: TextView = itemView.findViewById(R.id.authorFollowerCount)
        private val authorArticleCount: TextView = itemView.findViewById(R.id.authorArticleCount)
        private val followButton: Button = itemView.findViewById(R.id.followButton)

        fun bind(author: Author) {
            Glide.with(itemView.context)
                .load(author.avatar)
                .placeholder(R.drawable.ic_home)
                .into(authorAvatar)

            authorName.text = author.name
            authorBio.text = author.bio
            authorFollowerCount.text = "${author.followerCount} 粉丝"
            authorArticleCount.text = "${author.articleCount} 文章"

            if (author.isFollowed) {
                followButton.text = "已关注"
                followButton.setBackgroundColor(itemView.context.resources.getColor(R.color.gray))
            } else {
                followButton.text = "关注"
                followButton.setBackgroundColor(itemView.context.resources.getColor(R.color.colorPrimary))
            }

            followButton.setOnClickListener {
                onFollowClick(author)
            }
        }
    }
}
