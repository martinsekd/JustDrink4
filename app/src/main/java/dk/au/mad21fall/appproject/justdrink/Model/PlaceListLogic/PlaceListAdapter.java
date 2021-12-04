package dk.au.mad21fall.appproject.justdrink.Model.PlaceListLogic;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Response;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import dk.au.mad21fall.appproject.justdrink.Model.Location;
import dk.au.mad21fall.appproject.justdrink.Model.Repository;
import dk.au.mad21fall.appproject.justdrink.R;
import dk.au.mad21fall.appproject.justdrink.View.MainActivity;

public class PlaceListAdapter extends RecyclerView.Adapter {

    private ArrayList<Location> _placeList;
    private Context _context;
    private String imUrl = "https://images.evendo.com/cdn-cgi/image/f=auto,width=1386,quality=75/images/d2faa0c0b80d45a8b87ef3a8da5c8e9d.png";

    public PlaceListAdapter(Context context) {
        _context = context;
    }

    //assign new movies on list
    public void setMovieList(List<Location> list) {
        _placeList = (ArrayList<Location>) list;
        notifyDataSetChanged();
    }

    //Make Viewholder to define appearance of each movieItem
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //inspired by example
        //source: https://www.javatpoint.com/android-recyclerview-list-example
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View placeItem= layoutInflater.inflate(R.layout.placeitem, parent, false);
        PlaceViewHolder viewHolder = new PlaceViewHolder(placeItem);
        return viewHolder;
    }

    //assign a movieItem's values to widgets in ViewHolder
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((PlaceViewHolder)holder)._name.setText(_placeList.get(position).name);
        ((PlaceViewHolder)holder)._rating.setText(_placeList.get(position).rating+"");
        Repository.getInstance(_context).getDistanceFromPlace(_placeList.get(position).lat, _placeList.get(position).long1, new Response.Listener<Float>() {
            @Override
            public void onResponse(Float response) {
                ((PlaceViewHolder)holder)._distanceKm.setText(String.valueOf(response)+" km væk");
            }
        });

        Glide.with(_context)
                .load(imUrl)
                .fitCenter()
                .into(((PlaceViewHolder) holder)._image);

        //onClick handler for pressing on a item in the list
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity)_context).onItemClick(holder.getAdapterPosition());
            }
        });

    }

    //return size of list
    @Override
    public int getItemCount() {
        if(_placeList==null)
            return 0;
        return _placeList.size();
    }
}
