package com.example.inhamind.Adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.inhamind.Board.MyPostReadActivity;
import com.example.inhamind.Models.DataName;
import com.example.inhamind.Models.Post;
import com.example.inhamind.R;

import java.util.List;

public class MainMyPostAdapters extends RecyclerView.Adapter<MainMyPostAdapters.MainMyPostViewHolder> {

    private List<Post> datas;
    private Context context;
    private String title, status;
    private Post data;

    public MainMyPostAdapters(List<Post> datas, Context context) {
        this.datas = datas;
        this.context = context;
    }

    @NonNull
    @Override
    public MainMyPostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MainMyPostViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.my_item_main_post, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MainMyPostViewHolder holder, int position) {
        data = datas.get(position);
        if (data.getTitle() == null) {
            holder.title.setText("글이 없습니다.");
            holder.title.setTextColor(Color.GRAY);
            holder.title.setTextSize(12);
            holder.title.setTypeface(holder.title.getTypeface(), Typeface.NORMAL);
            holder.status.setText(" ");

        } else {
            title = data.getTitle();
            if (title.length() > 10) title = title.substring(0, 10) + "...";
            status = data.getStudentID();
            holder.title.setText(title);
            holder.status.setText(data.getStatus().equals("true") ? "완료" : "미완료");
            holder.layout.setTag(holder.getAdapterPosition());
            holder.layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, MyPostReadActivity.class);
                    Post cur = datas.get((int) view.getTag());
                    intent.putExtra(DataName.data, cur);
                    context.startActivity(intent);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    class MainMyPostViewHolder extends RecyclerView.ViewHolder {

        private TextView title, status;
        private LinearLayout layout;

        public MainMyPostViewHolder(@NonNull View itemView) {
            super(itemView);

            layout = itemView.findViewById(R.id.layout);
            title = itemView.findViewById(R.id.my_item_post_title);
            status = itemView.findViewById(R.id.my_item_post_status);
        }
    }
}
