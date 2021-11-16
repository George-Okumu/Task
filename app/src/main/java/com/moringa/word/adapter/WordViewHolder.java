package com.moringa.word.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.moringa.word.R;

public class WordViewHolder extends RecyclerView.ViewHolder {
    private TextView wordTetView;

    private WordViewHolder(View itemView){
        super(itemView);
        wordTetView = itemView.findViewById(R.id.textview);
    }

    public void bind(String text){
        wordTetView.setText(text);
    }

    static WordViewHolder create(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recyclerview_item, parent, false);
        return new WordViewHolder(view);
    }

}
