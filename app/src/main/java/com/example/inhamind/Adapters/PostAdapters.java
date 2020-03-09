package com.example.inhamind.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.inhamind.Board.PostReadActivity;
import com.example.inhamind.Models.Post;
import com.example.inhamind.R;

import java.util.List;

public class PostAdapters extends RecyclerView.Adapter<PostAdapters.PostViewHolder> {

    private List<Post> datas;
    private Context context;
    private String title, contents, studentID;

    public PostAdapters(Context context, List<Post> datas) {
        this.context = context;
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
        title = data.getTitle();
        if (title.length() > 10) title = title.substring(0, 10) + "...";
        contents = data.getContents();
        if (contents.length() > 20) contents = contents.substring(0, 20) + "...";
        studentID = data.getStudentID();
        holder.title.setText(title);
        holder.contents.setText(contents);
        holder.studentID.setText("학번 : " + studentID);

    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    class PostViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView title;
        private TextView contents;
        private TextView studentID;

        public PostViewHolder(@NonNull View itemview) {
            super(itemview);

            title = itemview.findViewById(R.id.item_post_title);
            contents = itemview.findViewById(R.id.item_post_contents);
            studentID = itemview.findViewById(R.id.item_post_student_number);
            itemview.findViewById(R.id.cardview).setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            Intent intent = new Intent(context, PostReadActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        }
    }
}
