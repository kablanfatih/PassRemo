package com.kablanfatih.passremo;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.MyViewHolder> {

    private ArrayList<ListPassword> mDataList;
    private LayoutInflater inflater;
    private ClipboardManager myClipBoard;
    private ClipData myClip;


    ListAdapter(Context context, ArrayList<ListPassword> data) {

        inflater = LayoutInflater.from(context);
        this.mDataList = data;
        myClipBoard = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view = inflater.inflate(R.layout.list_password, viewGroup, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int position) {

        ListPassword listPassword = mDataList.get(position);
        myViewHolder.setData(listPassword, position);
    }

    private String getRecordId(int position) {

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
        int clickedCardPosition;

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
                    new AlertDialog.Builder(itemView.getContext())
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .setMessage("Parolanızı Siliyorsunuz?")
                            .setPositiveButton("Evet", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    String uuid = getRecordId(clickedCardPosition);
                                    UserActivity record = new UserActivity();
                                    record.deleteRecord(uuid);
                                }
                            })
                            .setNegativeButton("Hayır", null)
                            .show();
                }
            });
            copyPassword.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    myClip = ClipData.newPlainText("parola", password.getText());
                    myClipBoard.setPrimaryClip(myClip);
                    Toast.makeText(itemView.getContext(), "Parola Kopyalandı", Toast.LENGTH_SHORT).show();
                }
            });

            updateRecord.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String uuid = getRecordId(clickedCardPosition);
                    Intent intent = new Intent(itemView.getContext(), UpdateRecord.class);
                    intent.putExtra("recordId", uuid);
                    itemView.getContext().startActivity(intent);
                }
            });
        }

        void setData(ListPassword listPassword, int position) {

            this.title.setText(listPassword.getTitle());
            this.name.setText(listPassword.getName());
            this.password.setText(listPassword.getPassword());
            this.clickedCardPosition = position;
        }
    }
}
