package com.example.inhamind.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.inhamind.Board.MyPostReadActivity;
import com.example.inhamind.Models.DataName;
import com.example.inhamind.Models.Post;
import com.example.inhamind.R;

import java.util.List;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

public class MyPostAdapters extends RecyclerView.Adapter<MyPostAdapters.MyPostViewHolder> {

    private List<Post> datas;
    private Context context;
    private String title, contents, status;
    private Post data;

    public MyPostAdapters(List<Post> datas, Context context) {
        this.datas = datas;
        this.context = context;
    }

    @NonNull
    @Override
    public MyPostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyPostViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.my_item_post, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyPostViewHolder holder, int position) {
        data = datas.get(position);
        title = data.getTitle();
        if (title.length() > 10) title = title.substring(0, 10) + "...";
        contents = data.getContents();
        if (contents.length() > 20) contents = contents.substring(0, 20) + "...";
        holder.title.setText(title);
        holder.contents.setText(contents);
        holder.status.setText(data.getStatus().equals("true") ? "완료" : "미완료");
        holder.cardView.setTag(holder.getAdapterPosition());
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, MyPostReadActivity.class);
                Post cur = datas.get((int) view.getTag());
                intent.putExtra(DataName.data, cur);
                intent.addFlags(FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    class MyPostViewHolder extends RecyclerView.ViewHolder {

        private TextView title, contents, status;
        private CardView cardView;

        public MyPostViewHolder(@NonNull View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.my_item_post_title);
            contents = itemView.findViewById(R.id.my_item_post_contents);
            status = itemView.findViewById(R.id.my_item_post_status);
            cardView = itemView.findViewById(R.id.my_cardview);
        }
    }
}
