package com.research.andrade.andar;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Onyok on 3/2/2017.
 */

public class CustomBaseAdapter extends BaseAdapter {
    private static ArrayList<MessageResults> searchArrayList;

    private LayoutInflater mInflater;

    public CustomBaseAdapter(Context context, ArrayList<MessageResults> results) {
        searchArrayList = results;
        mInflater = LayoutInflater.from(context);
    }

    public int getCount() {
        return searchArrayList.size();
    }

    public Object getItem(int position) {
        return searchArrayList.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.custom_row_layout, null);
            holder = new ViewHolder();
            holder.txtReceiver = (TextView) convertView.findViewById(R.id.txtReceiver);
            holder.txtBody = (TextView) convertView
                    .findViewById(R.id.txtBody);
            holder.txtDay = (TextView) convertView.findViewById(R.id.txtDay);
            holder.txtTime = (TextView) convertView.findViewById(R.id.txtTime);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.txtReceiver.setText(searchArrayList.get(position).getReceiver());
        holder.txtBody.setText(searchArrayList.get(position)
                .getBody());
        holder.txtTime.setText(searchArrayList.get(position).getTime());
        holder.txtDay.setText(searchArrayList.get(position).getDay());

        return convertView;
    }

    static class ViewHolder {
        TextView txtReceiver;
        TextView txtBody;
        TextView txtDay;
        TextView txtTime;
    }
}