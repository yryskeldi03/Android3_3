package com.geek.android3_3.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.geek.android3_3.data.models.Post;
import com.geek.android3_3.databinding.ItemPostBinding;

import java.util.LinkedList;
import java.util.List;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.ViewHolder> {
    private List<Post> list = new LinkedList<>();
    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public void setList(List<Post> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    public Post getItem(int position) {
        return list.get(position);
    }

    public void addItem(Post post) {
        this.list.add(post);
        notifyDataSetChanged();
    }

    public void deletePost(int pos){
        this.list.remove(pos);
        notifyItemRemoved(pos);
    }

    public void updatePost(Post post, int pos){
        this.list.set(pos, post);
        notifyItemChanged(pos);
    }

    @NonNull
    @Override
    public PostAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(ItemPostBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull PostAdapter.ViewHolder holder, int position) {
        holder.onBind(list.get(position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ItemPostBinding binding;

        public ViewHolder(ItemPostBinding itemView) {
            super(itemView.getRoot());
            binding = itemView;
        }

        public void onBind(Post post) {
            binding.tvTitle.setText(post.getTitle());
            binding.tvContent.setText(post.getContent());
            binding.tvGroup.setText(String.valueOf(post.getGroup()));
            binding.tvUser.setText(String.valueOf(post.getUser()));

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    onItemClickListener.onLongClick(post.getId(), getAdapterPosition());
                    return true;
                }
            });

            itemView.setOnClickListener(v -> onItemClickListener.onClick(post, getAdapterPosition()));
        }
    }

    public interface OnItemClickListener {
        void onLongClick(String id, int position);

        void onClick(Post post, int position);
    }
}
