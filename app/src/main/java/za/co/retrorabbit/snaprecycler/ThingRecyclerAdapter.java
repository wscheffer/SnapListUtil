package za.co.retrorabbit.snaprecycler;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wsche on 2016/06/03.
 */
public class ThingRecyclerAdapter extends RecyclerView.Adapter<ThingRecyclerAdapter.ThingHolder> {

    List<Thing> things;

    public ThingRecyclerAdapter(List<Thing> things) {
        this.things = things;
    }

    @Override
    public ThingHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        return new ThingHolder(view);
    }

    @Override
    public void onBindViewHolder(ThingHolder holder, int position) {
        holder.populate(this.things.get(position));
    }

    @Override
    public int getItemCount() {
        return this.things.size();
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    @Override
    public long getItemId(int position) {
        return things.get(position).id;
    }

    public class ThingHolder extends RecyclerView.ViewHolder {

        ImageView imageView;
        TextView titleTextView;

        public ThingHolder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.imageview);
            titleTextView = (TextView) itemView.findViewById(R.id.textview);
        }

        public void populate(Thing item) {
            imageView.setImageResource(item.imageResource);
            titleTextView.setText(item.text);
        }
    }
}
