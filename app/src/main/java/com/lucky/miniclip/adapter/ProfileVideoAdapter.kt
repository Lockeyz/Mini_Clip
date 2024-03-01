package com.lucky.miniclip.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.lucky.miniclip.databinding.ActivityProfileBinding
import com.lucky.miniclip.databinding.ProfileVideoItemRowBinding
import com.lucky.miniclip.model.VideoModel

class ProfileVideoAdapter(options: FirestoreRecyclerOptions<VideoModel>)
    : FirestoreRecyclerAdapter<VideoModel,ProfileVideoAdapter.VideoViewHolder>(options){
    inner class VideoViewHolder(private var binding: ProfileVideoItemRowBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(video : VideoModel){
            Glide.with(binding.thumbnailImageView)
                .load(video.url)
                .into(binding.thumbnailImageView)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideoViewHolder {
        val binding = ProfileVideoItemRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return VideoViewHolder(binding)
    }

    override fun onBindViewHolder(holder: VideoViewHolder, position: Int, model: VideoModel) {
        holder.bind(model)
    }
}