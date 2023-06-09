package com.example.grouptimer.Adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.grouptimer.Activity.GroupTimeTableActivity;
import com.example.grouptimer.Adapter.Holder.GroupChattingRecyclerViewHolder;
import com.example.grouptimer.Common.DefineValue;
import com.example.grouptimer.Object.GroupChatRecyclerViewItem;
import com.example.grouptimer.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Map;

public class GroupChattingRecyclerViewAdapter extends RecyclerView.Adapter<GroupChattingRecyclerViewHolder>
{
    public ArrayList<GroupChatRecyclerViewItem> AdapterChatList;

    public ViewGroup parent;

    int indiceCnt;


    public GroupChattingRecyclerViewAdapter(ArrayList<GroupChatRecyclerViewItem> chatList)
    {
        this.AdapterChatList = chatList;
    }


    @NonNull
    @Override
    public GroupChattingRecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View view;
        GroupChattingRecyclerViewHolder viewHolder = null;


        this.parent = parent;


        if(viewType == DefineValue.Chat_Left)
        {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.group_chatting_left_recyclerview_item, parent, false);

            viewHolder = new GroupChattingRecyclerViewHolder(view);
        }
        else if(viewType == DefineValue.Chat_Right)
        {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.group_chatting_right_recyclerview_item, parent, false);

            viewHolder = new GroupChattingRecyclerViewHolder(view);

            viewHolder.ChatSenderName.setVisibility(View.GONE);
        }


        return viewHolder;
    }


    @Override
    public void onBindViewHolder(@NonNull GroupChattingRecyclerViewHolder viewHolder, int position)
    {
        String message;

        String sendTime;
        String year;
        String month;
        String day;
        String date;
        String hour;
        String minute;
        String time;

        String senderUID;
        String senderName;


        Map<String, Boolean> memberIndice;


        memberIndice = this.AdapterChatList.get(position).MemberIndice;
        message = this.AdapterChatList.get(position).Message;
        sendTime = Long.toString(this.AdapterChatList.get(position).SendTime);
        senderUID = this.AdapterChatList.get(position).SenderUID;


        if(message == null)
        {
            year = sendTime.substring(0, 4);
            month = sendTime.substring(4, 6);
            day = sendTime.substring(6, 8);

            date = year + "년  " + month + "월  " + day +"일";

            viewHolder.ChatDate.setText(date);

            viewHolder.DateLinearLayout.setVisibility(View.VISIBLE);
            viewHolder.ChatLinearLayout.setVisibility(View.GONE);

            return;
        }
        else
        {
            viewHolder.DateLinearLayout.setVisibility(View.GONE);
            viewHolder.ChatLinearLayout.setVisibility(View.VISIBLE);
        }


        hour = sendTime.substring(8, 10);
        minute = sendTime.substring(10, 12);


        time = hour + ":" + minute;


        senderName = GroupTimeTableActivity.MemberInfo.get(senderUID);


        indiceCnt = 0;
        for(int i = 0; i < GroupTimeTableActivity.MemberIDList.size(); i++)
        {
            String memberUID;


            if(GroupTimeTableActivity.MemberIDList.get(i) != null)
            {
                memberUID = GroupTimeTableActivity.MemberIDList.get(i);

                if(memberIndice.get(memberUID) != null)
                {
                    if(memberIndice.get(memberUID) == false)
                    {
                        indiceCnt++;

                        Log.d("GT", "Indice : " + memberUID + ", " + message + ", " + indiceCnt);
                    }
                }
            }
        }


        viewHolder.ChatText.setText(message);
        viewHolder.ChatTime.setText(time);


        FirebaseDatabase.getInstance().getReference().child("Users").child(senderUID).child("userName").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                String value = dataSnapshot.getValue(String.class);


                viewHolder.ChatSenderName.setText(value);


                if(indiceCnt > 0)
                {
                    viewHolder.ChatConfirm.setText(Integer.toString(indiceCnt));
                }
                else
                {
                    viewHolder.ChatConfirm.setText("");
                }
            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }


    @Override
    public int getItemCount()
    {
        return this.AdapterChatList.size();
    }


    @Override
    public int getItemViewType(int position) {
        return this.AdapterChatList.get(position).Get_ViewType();
    }
}
