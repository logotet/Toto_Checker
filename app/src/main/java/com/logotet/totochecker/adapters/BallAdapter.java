package com.logotet.totochecker.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.logotet.totochecker.R;

import java.util.List;

public class BallAdapter extends RecyclerView.Adapter<BallAdapter.BallHolder> {

    List<String> numbers;


    public BallAdapter(List<String> numbers) {
        this.numbers = numbers;
    }

    @NonNull
    @Override
    public BallHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.ball_view, parent, false);
        return new BallHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BallHolder holder, int position) {
        holder.txtBallValue.setText(numbers.get(position));
    }

    @Override
    public int getItemCount() {
        return numbers.size();
    }

    public class BallHolder extends RecyclerView.ViewHolder {
        TextView txtBallValue;

        public BallHolder(@NonNull View itemView) {
            super(itemView);
            txtBallValue = itemView.findViewById(R.id.txt_ball_value);
        }
    }
}
