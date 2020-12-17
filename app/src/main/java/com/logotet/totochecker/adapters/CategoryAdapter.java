package com.logotet.totochecker.adapters;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.logotet.totochecker.R;
import com.logotet.totochecker.data.local.NumbersEntity;

import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryHolder> {

    List<NumbersEntity> numbersEntities;
    CategoryHolder.OnItemsClickListener listener;

    public CategoryAdapter(List<NumbersEntity> numbersEntities, CategoryHolder.OnItemsClickListener listener) {
        this.numbersEntities = numbersEntities;
        this.listener = listener;
    }

    @NonNull
    @Override
    public CategoryHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.category_view, parent, false);
        return new CategoryHolder(view, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryHolder holder, int position) {
        BallAdapter ballAdapter = new BallAdapter(numbersEntities.get(position).getValues());
        holder.recyclerViewCategory.setAdapter(ballAdapter);
        holder.recyclerViewCategory.setLayoutManager(new LinearLayoutManager(holder.recyclerViewCategory.getContext(), LinearLayoutManager.HORIZONTAL, false));
        NumbersEntity entity = numbersEntities.get(position);
        holder.setNumbersEntity(entity);
        holder.setPosition(position);
    }

    @Override
    public int getItemCount() {
        return numbersEntities.size();
    }

    public static class CategoryHolder extends RecyclerView.ViewHolder {

        RecyclerView recyclerViewCategory;
        ImageView imgDelete, imgEdit;
        NumbersEntity numbersEntity;
        int position;

        @SuppressLint("ClickableViewAccessibility")
        public CategoryHolder(@NonNull View itemView, OnItemsClickListener listener) {
            super(itemView);
            recyclerViewCategory = itemView.findViewById(R.id.rec_view_category_list);
            imgDelete = itemView.findViewById(R.id.img_delete);
            imgEdit = itemView.findViewById(R.id.img_edit);
            recyclerViewCategory.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View view, MotionEvent motionEvent) {
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    toggleVisibility();
                    return false;
                }
            });
            imgDelete.setOnClickListener(view -> listener.onDeleteClicked(numbersEntity));
            imgEdit.setOnClickListener(view -> listener.onEditClicked(numbersEntity ,position));
        }

        private void toggleVisibility() {
            if(imgDelete.getVisibility() == View.GONE && imgEdit.getVisibility() == View.GONE){
                imgDelete.setVisibility(View.VISIBLE);
                imgEdit.setVisibility(View.VISIBLE);
            }else {
                imgDelete.setVisibility(View.GONE);
                imgEdit.setVisibility(View.GONE);
            }
        }

        public void setNumbersEntity(NumbersEntity numbersEntity) {
            this.numbersEntity = numbersEntity;
        }

        public void setPosition(int position) {
            this.position = position;
        }

        public interface OnItemsClickListener {
            void onDeleteClicked(NumbersEntity entity);

            void onEditClicked(NumbersEntity entity, int position);
        }


    }
}
