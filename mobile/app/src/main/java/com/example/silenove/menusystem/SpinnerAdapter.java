package com.example.silenove.menusystem;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by silenove on 2015/12/3.
 */
public class SpinnerAdapter extends BaseAdapter {
    private List<SpinnerInfo> dList;
    private LayoutInflater dInflater;

    public SpinnerAdapter(List<SpinnerInfo> dList, Context context){
        this.dList = dList;
        dInflater = LayoutInflater.from(context);
    }
    @Override
    public int getCount() {
        return dList.size();
    }

    @Override
    public Object getItem(int position) {
        return dList.get(position);
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
            convertView = dInflater.inflate(R.layout.view_spinner,null);
            viewHolder.image = (ImageView) convertView.findViewById(R.id.id_spinner_image);
            viewHolder.tab = (TextView) convertView.findViewById(R.id.id_spinner_text);
            convertView.setTag(viewHolder);

        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }
        SpinnerInfo item = dList.get(position);
        viewHolder.image.setImageResource(item.getImage());
        viewHolder.tab.setText(item.getTab());
        return convertView;
    }



    class ViewHolder{
        public ImageView image;
        public TextView tab;
    }


}
