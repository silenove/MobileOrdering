package com.example.silenove.menusystem;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by silenove on 2015/12/16.
 */
public class SelectedAdapter extends BaseAdapter {

    private List<SelectedInfo> list;
    private LayoutInflater inflater;

    public SelectedAdapter(List<SelectedInfo> list,Context context){
        this.list = list;
        inflater = LayoutInflater.from(context);
    }
    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if(convertView == null){
            viewHolder = new ViewHolder();
            convertView = inflater.inflate(R.layout.selected_items,null);
            viewHolder.image = (ImageView) convertView.findViewById(R.id.id_selected_image);
            viewHolder.info = (TextView) convertView.findViewById(R.id.id_selected_info);
            viewHolder.number = (TextView) convertView.findViewById(R.id.id_selected_number);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }
        SelectedInfo sInfo = list.get(position);
        viewHolder.image.setImageResource(sInfo.getImage());
        viewHolder.info.setText(sInfo.getInfo());
        viewHolder.number.setText("X"+sInfo.getNumber());
        return convertView;
    }

    class ViewHolder{
        public ImageView image;
        public TextView info;
        public TextView number;
    }
}
