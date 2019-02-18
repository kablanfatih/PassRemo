package com.kablanfatih.passremo;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.MyViewHolder> {

    private ArrayList<ListPassword> mDataList;
    private LayoutInflater inflater;

    ListAdapter(Context context, ArrayList<ListPassword> data) {

        inflater = LayoutInflater.from(context);
        this.mDataList = data;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view = inflater.inflate(R.layout.list_password, viewGroup, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int position) {

        ListPassword tiklanilanManzara = mDataList.get(position);
        myViewHolder.setData(tiklanilanManzara, position);
    }

    public String getRecordId(int position){

        return mDataList.get(position).getRecordId();
    }

    @Override
    public int getItemCount() {
        return mDataList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView title, name;
        ImageView deleteRecord, updateRecord, copyPassword;
        EditText password;
        int tiklanilanOgeninPozisyonu;

        MyViewHolder(@NonNull final View itemView) {
            super(itemView);

            title = (TextView) itemView.findViewById(R.id.title);
            name = (TextView) itemView.findViewById(R.id.name);
            password = (EditText) itemView.findViewById(R.id.password);
            deleteRecord = (ImageView) itemView.findViewById(R.id.delete_button);
            updateRecord = (ImageView) itemView.findViewById(R.id.update_button);
            copyPassword = (ImageView) itemView.findViewById(R.id.copy_password);

            deleteRecord.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    String uuid = getRecordId(tiklanilanOgeninPozisyonu);

                    UserActivity record = new UserActivity();
                    record.deleteRecord(uuid);
                }
            });
        }

        void setData(ListPassword tiklanilanManzara, int position) {

            this.title.setText(tiklanilanManzara.getTitle());
            this.name.setText(tiklanilanManzara.getName());
            this.password.setText(tiklanilanManzara.getPassword());
            this.tiklanilanOgeninPozisyonu = position;
        }
    }
}
