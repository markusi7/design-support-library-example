package com.imarkusi.design_support_library_example.adapters;

import com.imarkusi.design_support_library_example.R;
import com.imarkusi.design_support_library_example.listener.ItemClickListener;
import com.imarkusi.design_support_library_example.models.ListItem;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created on 11/10/15.
 *
 * @author markusi
 */
public class ListItemAdapter extends RecyclerView.Adapter<ListItemAdapter.ViewHolder> {

    private List<ListItem> items;

    private ItemClickListener<ListItem> itemClickListener;

    public ListItemAdapter(List<ListItem> items, ItemClickListener<ListItem> itemClickListener) {
        this.items = items;
        this.itemClickListener = itemClickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final ListItem item = items.get(position);
        holder.title.setText(item.getTitle());
        holder.subtitle.setText(item.getSubtitle());
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.title)
        TextView title;

        @Bind(R.id.subtitle)
        TextView subtitle;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
