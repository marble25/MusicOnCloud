package org.iptime.mascore.musiconcloud;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Owner on 2017-03-01.
 */

public class DJListViewAdapter extends BaseAdapter {

    private ArrayList<DJListViewItem> listViewItemDJList = new ArrayList<>();

    public DJListViewAdapter() {

    }
    @Override
    public int getCount() {
        return listViewItemDJList.size();
    }

    @Override
    public Object getItem(int position) {
        return listViewItemDJList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final int pos = position;
        final Context context = parent.getContext();

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.list_dj, parent, false);
        }

        TextView djTextView = (TextView) convertView.findViewById(R.id.text_dj);
        DJListViewItem djListViewItem = listViewItemDJList.get(position);

        djTextView.setText(djListViewItem.getName());

        return convertView;
    }

    public void addItem(String name) {
        DJListViewItem djListViewItem = new DJListViewItem();

        djListViewItem.setName(name);

        listViewItemDJList.add(djListViewItem);
    }
}
