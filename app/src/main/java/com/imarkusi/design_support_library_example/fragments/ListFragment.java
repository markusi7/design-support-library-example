package com.imarkusi.design_support_library_example.fragments;

import com.imarkusi.design_support_library_example.R;
import com.imarkusi.design_support_library_example.adapters.ListItemAdapter;
import com.imarkusi.design_support_library_example.factories.ListItemFactory;
import com.imarkusi.design_support_library_example.listener.ItemClickListener;
import com.imarkusi.design_support_library_example.models.ListItem;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created on 11/10/15.
 *
 * @author markusi
 */
public class ListFragment extends Fragment {

    private static final int LIST_SIZE = 20;

    @Bind(R.id.recycler_view)
    RecyclerView recyclerView;

    private ItemClickListener<ListItem> itemClickListener = new ItemClickListener<ListItem>() {
        @Override
        public void onItemClick(ListItem item) {
            //TODO start details activity;
        }
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list, container, false);
        ButterKnife.bind(this, view);

        populateList();

        return view;
    }

    private void populateList() {
        List<ListItem> items = new ArrayList<>();
        for (int i = 0; i < LIST_SIZE; i++) {
            items.add(ListItemFactory.createListItem(i + 1));
        }
        ListItemAdapter adapter = new ListItemAdapter(items, itemClickListener);
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
    }
}
