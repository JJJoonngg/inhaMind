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

import com.example.inhamind.Models.DataName;
import com.example.inhamind.Models.Notice;
import com.example.inhamind.Notice.NoticeReadActivity;
import com.example.inhamind.R;

import java.util.List;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

public class MainNoticeAdapters extends RecyclerView.Adapter<MainNoticeAdapters.MainNoticeViewHolder> {

    private List<Notice> datas;
    private Context context;
    private String title, contents, writer;
    private Notice data;

    public MainNoticeAdapters(List<Notice> datas, Context context) {
        this.datas = datas;
        this.context = context;
    }

    @NonNull
    @Override
    public MainNoticeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MainNoticeViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_notice_main, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MainNoticeViewHolder holder, int position) {
        data = datas.get(position);
        if (data.getTitle() == null) {
            holder.title.setText("공지사항이 없습니다.");
            holder.title.setTextColor(Color.GRAY);
            holder.title.setTextSize(12);
            holder.title.setTypeface(holder.title.getTypeface(), Typeface.NORMAL);
        } else {
            title = data.getTitle();
            if (title.length() > 10) title = title.substring(0, 10) + "...";
            writer = data.getName();

            holder.title.setText(title);
            holder.writer.setText(writer);

            holder.layout.setTag(holder.getAdapterPosition());
            holder.layout.setOnClickListener(new View.OnClickListener() {
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
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    public class MainNoticeViewHolder extends RecyclerView.ViewHolder {

        private TextView title, writer;
        private LinearLayout layout;

        public MainNoticeViewHolder(@NonNull View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.item_notice_title);
            writer = itemView.findViewById(R.id.item_notice_writer);
            layout = itemView.findViewById(R.id.layout);
        }
    }
}
