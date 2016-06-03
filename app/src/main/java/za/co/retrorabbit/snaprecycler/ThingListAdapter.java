package za.co.retrorabbit.snaprecycler;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;

/**
 * Created by wsche on 2016/06/03.
 */
public class ThingListAdapter extends ArrayAdapter<Thing> {
    HashMap<Thing, Integer> mIdMap = new HashMap<Thing, Integer>();

    public ThingListAdapter(Context context, int textViewResourceId,
                            List<Thing> objects) {
        super(context, textViewResourceId, objects);
        for (int i = 0; i < objects.size(); ++i) {
            mIdMap.put(objects.get(i), i);
        }
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ThingViewHolder holder;
        if (convertView == null) {
            holder = new ThingViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, null, false));
            convertView = holder.itemView;
            convertView.setTag(holder);
        } else {
            holder = (ThingViewHolder) convertView.getTag();
            convertView = holder.itemView;
        }
        holder.populate(getItem(position));
        return convertView;
    }

    @Override
    public long getItemId(int position) {
        Thing item = getItem(position);
        return mIdMap.get(item);
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    public class ThingViewHolder {

        ImageView imageView;
        TextView titleTextView;
        public View itemView;

        public ThingViewHolder(View itemView) {
            this.itemView = itemView;
            imageView = (ImageView) itemView.findViewById(R.id.imageview);
            titleTextView = (TextView) itemView.findViewById(R.id.textview);
        }

        public void populate(Thing item) {
            imageView.setImageResource(item.imageResource);
            titleTextView.setText(item.text);
        }
    }
}
