package dk.au.mad21fall.appproject.justdrink.Model.PlaceListLogic;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import dk.au.mad21fall.appproject.justdrink.R;

public class PlaceViewHolder extends RecyclerView.ViewHolder {

    //instantiate widgets of the ViewHolder
    ImageView _image;
    TextView _name;
    TextView _distanceKm;
    TextView _rating;

    public PlaceViewHolder(@NonNull View itemView) {
        super(itemView);

        //bind widgets on movie_item.xml to the viewHolder in ObjectOrientated world
        _name = itemView.findViewById(R.id.title1);
        _distanceKm = itemView.findViewById(R.id.distance);
        _rating = itemView.findViewById(R.id.rating1);
        _image = itemView.findViewById(R.id.barImage);

    }
}
