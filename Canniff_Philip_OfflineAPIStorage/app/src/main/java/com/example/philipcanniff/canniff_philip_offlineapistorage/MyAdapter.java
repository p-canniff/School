package com.example.philipcanniff.canniff_philip_offlineapistorage;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by philipcanniff on 8/31/15.
 */
public class MyAdapter extends BaseAdapter {


    String authorString;
    Context mContext;
    ArrayList<PostData> mClass;


    public MyAdapter(Context c, ArrayList<PostData> m) {
        this.mContext = c;
        this.mClass = m;
    }

    static class ViewHolder {
        public TextView memberFirstName;
        public TextView memberLastName;

        public ViewHolder(View v) {
            memberFirstName = (TextView) v.findViewById(R.id.title);
            memberLastName = (TextView) v.findViewById(R.id.count);

        }

    }

    @Override
    public int getCount() {
        if (mClass != null) {
            return mClass.size();
        } else {
            return 0;
        }
    }

    @Override
    public Object getItem(int position) {
        if (mClass != null && position < mClass.size() && position >= 0) {

            return mClass.get(position);

        }
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        // If convertView doesn't already exist....

        if (convertView == null) {

            //Create ConvertView from the row layout
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_layout, parent, false);


            //Assign convertView toViewHolder
            holder = new ViewHolder(convertView);

            //Set Tag for ViewHolder
            convertView.setTag(holder);
        } else {

            // Otherwise return existing convertView
            holder = (ViewHolder) convertView.getTag();
        }

        //Assign Crew Member Details to row
        PostData member = (PostData) getItem(position);

        holder.memberFirstName.setText(member.getArticleTitle());
        holder.memberLastName.setText(member.getCount());

        return convertView;
    }

}

