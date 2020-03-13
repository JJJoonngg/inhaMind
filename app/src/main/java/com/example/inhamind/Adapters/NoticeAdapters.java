package com.example.inhamind.Adapters;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.inhamind.Models.DataName;
import com.example.inhamind.Models.Notice;
import com.example.inhamind.Notice.NoticeReadActivity;
import com.example.inhamind.R;

import java.util.List;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

public class NoticeAdapters extends RecyclerView.Adapter<NoticeAdapters.NoticeViewHolder> {

    private List<Notice> datas;
    private Context context;
    private String title, contents, writer;
    private Notice data;

    public NoticeAdapters(List<Notice> datas, Context context) {
        this.datas = datas;
        this.context = context;
    }

    @NonNull
    @Override
    public NoticeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new NoticeViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_notice, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull NoticeViewHolder holder, int position) {
        data = datas.get(position);
        title = data.getTitle();
        if (title.length() > 10) title = title.substring(0, 10) + "...";
        contents = data.getContents();
        if (contents.length() > 20) contents = contents.substring(0, 20) + "...";
        holder.title.setText(title);
        holder.contents.setText(contents);
        holder.cardView.setTag(holder.getAdapterPosition());
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, NoticeReadActivity.class);
                Notice cur = datas.get((int) view.getTag());
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

    public class NoticeViewHolder extends RecyclerView.ViewHolder {


        private TextView title, contents, writer;
        private CardView cardView;

        public NoticeViewHolder(@NonNull View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.item_notice_title);
            contents = itemView.findViewById(R.id.item_notice_contents);
            writer = itemView.findViewById(R.id.item_notice_writer);
            cardView = itemView.findViewById(R.id.cardview);

        }
    }
}
