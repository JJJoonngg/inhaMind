package com.example.inhamind.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.inhamind.Models.Post;
import com.example.inhamind.R;

import java.util.List;

public class PostAdapters extends RecyclerView.Adapter<PostAdapters.PostViewHolder> {

    private List<Post> datas;

    public PostAdapters(List<Post> datas) {
        this.datas = datas;
    }

    @NonNull
    @Override
    public PostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new PostViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_post, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull PostViewHolder holder, int position) {
        Post data = datas.get(position);
        holder.title.setText(data.getTitle());
        holder.contents.setText(data.getContents());
        holder.studentID.setText("학번 : "+ data.getStudentID());

    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    class PostViewHolder extends RecyclerView.ViewHolder {

        private TextView title;
        private TextView contents;
        private TextView studentID;

        public PostViewHolder(@NonNull View itemview) {
            super(itemview);

            title = itemview.findViewById(R.id.item_post_title);
            contents = itemview.findViewById(R.id.item_post_contents);
            studentID = itemview.findViewById(R.id.item_post_student_number);
        }
    }
}
