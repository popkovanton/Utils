package com.popkovanton.utilssample;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SoundListAdapter extends RecyclerView.Adapter<SoundListAdapter.ElementsViewHolder> {
    private ISoundList iSoundList;
    private Context context;
    private ArrayList<Integer> musicList;

    public interface ISoundList {
        void onElementClick(int resId);
    }

    public SoundListAdapter(Context context, ArrayList<Integer> musicList, ISoundList iSoundList) {
        this.context = context;
        this.musicList = musicList;
        this.iSoundList = iSoundList;
    }

    @NonNull
    @Override
    public ElementsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context)
                .inflate(R.layout.sound_list_element, parent, false);
        ElementsViewHolder viewHolder = new ElementsViewHolder(itemView);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (iSoundList != null) {
                    iSoundList.onElementClick(musicList.get(viewHolder.getLayoutPosition()));
                }
            }
        });
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ElementsViewHolder holder, int position) {
        holder.musicNameTV.setText(context.getResources().getResourceEntryName(musicList.get(position)));
    }

    @Override
    public int getItemCount() {
        return musicList.size();
    }

    class ElementsViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.musicName)
        TextView musicNameTV;

        ElementsViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
