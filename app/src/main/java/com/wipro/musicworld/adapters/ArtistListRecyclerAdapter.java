package com.wipro.musicworld.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.wipro.musicworld.R;
import com.wipro.musicworld.databinding.ArtistListItemBinding;
import com.wipro.musicworld.entities.Artist;
import com.wipro.musicworld.interfaces.OnItemClickListener;

import java.util.ArrayList;

public class ArtistListRecyclerAdapter extends RecyclerView.Adapter<ArtistListRecyclerAdapter.MyViewHolder>  {

    private ArrayList<Artist> artistDataList=null;
    private Context mContext;
    private OnItemClickListener onItemClickListener;
    public ArtistListRecyclerAdapter(Context mCon,ArrayList<Artist> artistArrayList,OnItemClickListener listener) {
        this.mContext=mCon;
        this.artistDataList= artistArrayList;
        this.onItemClickListener = listener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       LayoutInflater inflater= LayoutInflater.from(parent.getContext());
        return new MyViewHolder(ArtistListItemBinding.inflate(inflater, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.binding.getRoot().setOnClickListener(view -> onItemClickListener.OnItemClick(position));
        holder.binding.name.setText(artistDataList.get(position).getName());
        Glide.with(mContext).load(artistDataList.get(position).getImage().get(1).getText()).centerCrop()
                .placeholder(R.mipmap.person).into(holder.binding.image);
    }


    @Override
    public int getItemCount() {
        return artistDataList.size();
    }


    static class  MyViewHolder extends RecyclerView.ViewHolder{

        ArtistListItemBinding binding;
        public MyViewHolder(ArtistListItemBinding binding) {
            super(binding.getRoot());
            this.binding=binding;
        }
    }
}
