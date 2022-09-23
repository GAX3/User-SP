package com.example.user_sp

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.user_sp.databinding.ItemUserBinding

class UserAdapter(private val users: List<User>, private val listener: OnClickListener): RecyclerView.Adapter<UserAdapter.ViewHolder>() {

    private lateinit var context: Context

    inner class ViewHolder(view: View): RecyclerView.ViewHolder(view){
        val binding = ItemUserBinding.bind(view)

        fun setListener(user: User, position: Int){
            binding.root.setOnClickListener{
                listener.onClick(user, position)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context
        val view = LayoutInflater.from(context).inflate(R.layout.item_user, parent, false)
        return ViewHolder(view)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val user = users[position]
        with(holder) {
            setListener(user,position+1)
            binding.tvOrder.text = (position+1).toString()
            binding.tvName.text = user.getFullName()
            Glide.with(context)
                    .load(user.url)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .centerCrop()
                    .circleCrop()
                    .into(binding.imgPhoto)
        }
    }

    override fun getItemCount(): Int = users.size
}