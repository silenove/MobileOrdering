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
public class ListAdapter extends BaseAdapter {
    private List<Info> dList;
    private LayoutInflater dInflater;

    public ListAdapter(List<Info> dList, Context context){
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
            convertView = dInflater.inflate(R.layout.view_grid,null);
            viewHolder.image = (ImageView) convertView.findViewById(R.id.id_grid_image);
            viewHolder.price = (TextView) convertView.findViewById(R.id.id_grid_price);
            viewHolder.info = (TextView) convertView.findViewById(R.id.id_grid_info);
            viewHolder.addbtn = (Button) convertView.findViewById(R.id.id_grid_addbtn);
            viewHolder.subbtn = (Button) convertView.findViewById(R.id.id_grid_subbtn);
            viewHolder.number = (TextView) convertView.findViewById(R.id.id_grid_number);
            convertView.setTag(viewHolder);

        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }
        Info item = dList.get(position);
        viewHolder.image.setImageResource(item.getMyImgid());
        viewHolder.price.setText(item.getMyPrice());
        viewHolder.info.setText(item.getMyInfo());
        viewHolder.number.setText("0");
        viewHolder.addbtn.setOnClickListener(new addListener(viewHolder.number));
        viewHolder.subbtn.setOnClickListener(new subListener(viewHolder.number));

        return convertView;
    }



    class ViewHolder{
        public ImageView image;
        public TextView price;
        public TextView info;
        public Button addbtn;
        public Button subbtn;
        public TextView number;
    }

    class addListener implements View.OnClickListener{
        TextView viewnumber;

        public addListener(TextView viewnumber){
            this.viewnumber = viewnumber;
        }

        @Override
        public void onClick(View v) {
            String num = viewnumber.getText().toString();
            Integer number = Integer.parseInt(num.trim());
            number++;
           viewnumber.setText(number.toString());

        }
    }

    class subListener implements View.OnClickListener{
        TextView viewnumber;

        public subListener(TextView viewnumber){
            this.viewnumber = viewnumber;
        }

        @Override
        public void onClick(View v) {
            String num = viewnumber.getText().toString();
            Integer number = Integer.parseInt(num.trim());
            if(number > 0){
                number--;
            }
            viewnumber.setText(number.toString());

        }
    }
}
