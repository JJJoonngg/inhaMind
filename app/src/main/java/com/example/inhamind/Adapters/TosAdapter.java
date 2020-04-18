package com.example.inhamind.Adapters;

import android.animation.ValueAnimator;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.inhamind.Models.TosData;
import com.example.inhamind.R;

import java.util.ArrayList;


public class TosAdapter extends RecyclerView.Adapter<TosAdapter.ItemViewHolder> {
    public TosAdapter(ArrayList<TosData> dataArrayList) {
        this.dataArrayList = dataArrayList;
    }
    private ArrayList<TosData> dataArrayList;

    //Item의 클릭 상태를 저장할 array 객체
    //선택한 position에 대한 정보를 보관하는 객체
    //get() 메소드를 호출하여 선택된 position 값을 구함
    //get()은 position의 값이 존재하면 true를 반환
    private SparseBooleanArray selectedItems = new SparseBooleanArray();

    // 직전에 클릭됐던 Item의 position
    private int prePosition = -1;

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // =View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_tos,parent,false);
        // LayoutInflater = xml에 미리 정의해둔 틀을 실제 메모리에 올려주는 역할
        //                 즉,xml에 정의된 resource를 View 객체로 반환해주는 역할
        //from을 사용해 쉽게 layoutInflater를 얻는다
        //inflater를 통해 사전에 미리 선언해뒀던 parent라는 레이아웃에 작성했던 xml의 메모리객체가 삽입되게 된다.
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.item_tos, parent, false);
        //객체화하고픈 xml파일, 객체화한 뷰를 넣을 부모 레이아웃, 바로 인플레이션 하고자 하는지
        return new ItemViewHolder(v);
    }

    //각 뷰홀더에 데이터들을 바인딩(연관)하는 역할 (바인딩 : 함수 호출과 실제 함수를 연결하는 방법
    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        holder.onBind(dataArrayList.get(position), position);
    }

    @Override
    public int getItemCount() {
        return dataArrayList.size();
    }

    public void addItem(TosData data) {
        // 외부에서 item을 추가시킬 함수입니다.
        dataArrayList.add(data);
    }

    // 뷰 재활용을 위한 뷰홀더 : 뷰들을 홀더에 꼽아놓듯이 보관하는 객체
    // inflate를 최소화하기 위해서 뷰를 재활용 하는데, 이 때 각 뷰의 내용을 업데이트하기 위해
    // findViewById 매번 호출해야함, 이로인해 성능저하가 일어나서
    // ItemvView의 각 요소를 바로 엑세스 할 수 있도록 저장해두고 사용하기 위한 객체
    class ItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView titleText;
        TextView contentText;
        private TosData data;
        private int position;

        ItemViewHolder(View view) {
            super(view);

            titleText = view.findViewById(R.id.tos_title);
            contentText = view.findViewById(R.id.tos_content);

        }

        void onBind(TosData data, int position) {
            this.data = data;
            this.position = position;

            titleText.setText(data.getTitle());
            contentText.setText(data.getContent());

            changeVisibility(selectedItems.get(position));

            itemView.setOnClickListener(this);
        }

        private void changeVisibility(final boolean isExpanded) {
            int height = contentText.getMaxHeight();

            // ValueAnimator.ofInt(int... values)는 View가 변할 값을 지정, 인자는 int 배열
            ValueAnimator va = isExpanded ? ValueAnimator.ofInt(0, height) : ValueAnimator.ofInt(height, 0);
            // Animation이 실행되는 시간, n/1000초
            va.setDuration(100);
            va.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    // value는 height 값
                    int value = (int) animation.getAnimatedValue();
                    // imageView의 높이 변경
                    contentText.getLayoutParams().height = value;
                    contentText.requestLayout();
                    // imageView가 실제로 사라지게하는 부분
                    contentText.setVisibility(isExpanded ? View.VISIBLE : View.GONE);
                }
            });
            // Animation start
            va.start();

        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.linearItem:
                    if (selectedItems.get(position)) {
                        // 펼쳐진 Item을 클릭 시
                        selectedItems.delete(position);
                    } else {
                        // 직전의 클릭됐던 Item의 클릭상태를 지움
                        selectedItems.delete(prePosition);
                        // 클릭한 Item의 position을 저장
                        selectedItems.put(position, true);
                    }
                    // 해당 포지션의 변화를 알림
                    if (prePosition != -1) notifyItemChanged(prePosition);
                    notifyItemChanged(position);
                    // 클릭된 position 저장
                    prePosition = position;
                    break;
            }
        }

    }
}