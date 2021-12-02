package dk.au.mad21fall.appproject.justdrink.Model.ListLogic;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import dk.au.mad21fall.appproject.justdrink.R;

public class MessageViewHolder extends RecyclerView.ViewHolder {

    //instantiate widgets of the ViewHolder
    public ImageView _image;
    public TextView _name;
    public TextView _body;

    public MessageViewHolder(@NonNull View itemView) {
        super(itemView);
        //bind widgets on movie_item.xml to the viewHolder in ObjectOrientated world
        _body = itemView.findViewById(R.id.messageBody);
    }

    public void bindForTheirMessage() {
        _name = itemView.findViewById(R.id.name);
        _image = itemView.findViewById(R.id.avatar);
    }
}
