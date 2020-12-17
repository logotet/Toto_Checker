package com.logotet.totochecker.adapters;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.logotet.totochecker.data.local.NumbersEntity;

import java.util.List;

public class RecyclerViewInitializer {

    public static void initBallRecView(RecyclerView recyclerView, List<String> winningNumbers) {
        BallAdapter adapter = new BallAdapter(winningNumbers);
        recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext(), RecyclerView.HORIZONTAL, false));
        recyclerView.setAdapter(adapter);
    }


    public static void initCategoryRecView(RecyclerView recyclerView, List<NumbersEntity> entities, CategoryAdapter.CategoryHolder.OnItemsClickListener listener){
        CategoryAdapter adapter = new CategoryAdapter(entities, listener);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext()));
    }

    public static void initCategoryRecView(List<NumbersEntity> entities, CategoryAdapter adapter, RecyclerView recyclerView, CategoryAdapter.CategoryHolder.OnItemsClickListener listener) {
        adapter = new CategoryAdapter(entities, listener);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext()));
    }
}
