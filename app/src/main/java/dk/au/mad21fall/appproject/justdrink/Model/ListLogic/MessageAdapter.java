package dk.au.mad21fall.appproject.justdrink.Model.ListLogic;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import dk.au.mad21fall.appproject.justdrink.Model.Message;
import dk.au.mad21fall.appproject.justdrink.R;

public class MessageAdapter extends RecyclerView.Adapter {

    private ArrayList<Message> _messageList;
    private Context _context;


    public MessageAdapter(Context context) {
        _messageList = new ArrayList<Message>();
        _context = context;
    }

    //assign new movies on list
    public void addMessage(Message msg) {
        _messageList.add(msg);
        notifyDataSetChanged();
    }

    //Make Viewholder to define appearance of each movieItem
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //inspired by example
        //source: https://www.javatpoint.com/android-recyclerview-list-example
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem;
        if(viewType==1) {
            listItem= layoutInflater.inflate(R.layout.my_message, parent, false);
        } else {
            listItem= layoutInflater.inflate(R.layout.their_message, parent, false);
        }

        MessageViewHolder viewHolder = new MessageViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public int getItemViewType(int position) {

        if(_messageList.get(position).isBelongsToCurrentUser()) {
            return 1;
        } else {
            return 0;
        }
    }

    //assign a movieItem's values to widgets in ViewHolder
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        Message msg = _messageList.get(position);
        if(msg.isBelongsToCurrentUser()) {
            ((MessageViewHolder) holder)._body.setText(_messageList.get(position).getText());
        } else {
            ((MessageViewHolder) holder).bindForTheirMessage();
            ((MessageViewHolder) holder)._body.setText(_messageList.get(position).getText());
            ((MessageViewHolder) holder)._name.setText(_messageList.get(position).getMemberData().getName());
            //((MessageViewHolder) holder)._image.setImageResource(_messageList.get(position).);
        }

    }

    //return size of list
    @Override
    public int getItemCount() {
        if(_messageList==null)
            return 0;
        return _messageList.size();
    }
}
