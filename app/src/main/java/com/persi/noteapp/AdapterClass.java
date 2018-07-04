package com.persi.noteapp;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class AdapterClass extends RecyclerView.Adapter<AdapterClass.ListViewHolder>{
    private ArrayList<NoteDataModel > mNotes;
    private Context mContext;

    public AdapterClass(ArrayList<NoteDataModel> notes, Context context) {
        mNotes = notes;
        mContext = context;
    }

    @NonNull
    @Override
    public ListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(mContext).inflate(R.layout.list_item,parent,false);
        ListViewHolder listViewHolder=new ListViewHolder(v);
        return listViewHolder;

    }

    @Override
    public void onBindViewHolder(ListViewHolder holder, final int position) {

        holder.title.setText(mNotes.get(position).getTitle());
        if(mNotes.get(position).isCompleted()){
            holder.statusImage.setImageResource(android.R.drawable.presence_online);
        }else{
            holder.statusImage.setImageResource(android.R.drawable.presence_invisible);
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(mContext,"Working",Toast.LENGTH_SHORT).show();
                Intent newIntent=new Intent(mContext,DetailNote.class);
                newIntent.putExtra("userId",mNotes.get(position).getUserId());
                newIntent.putExtra("noteId",mNotes.get(position).getId());
                newIntent.putExtra("title",mNotes.get(position).getTitle());
                newIntent.putExtra("status",mNotes.get(position).isCompleted());
                mContext.startActivity(newIntent);

            }
        });

    }




    @Override
    public int getItemCount() {
        return mNotes.size();
    }

    public class ListViewHolder extends RecyclerView.ViewHolder{
        TextView title;
        ImageView statusImage;
        public ListViewHolder(View itemView) {
            super(itemView);
            title=itemView.findViewById(R.id.note_text);
            statusImage=itemView.findViewById(R.id.note_status);
        }
    }
}
