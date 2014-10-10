package com.example.saurav.bkuse;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by saurav on 9/29/14.
 */
public class ItemListAdapter extends BaseAdapter {
    private Context context;
    private List<ItemDto> itemDtoList;

    public ItemListAdapter(Context context, List<ItemDto> itemDtoList) {
        this.context = context;
        this.itemDtoList = itemDtoList;
    }

    @Override
    public int getCount() {
        return itemDtoList.size();
    }

    @Override
    public Object getItem(int i) {
        return itemDtoList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if(view==null){
            view = inflater.inflate(R.layout.layout_single_row, viewGroup, false);
            holder = new ViewHolder(view);
            view.setTag(holder);
        }else{
            holder = (ViewHolder) view.getTag();
        }
        holder.name.setText(itemDtoList.get(i).getName());
        holder.gender.setText(itemDtoList.get(i).getGender());
        holder.address.setText(itemDtoList.get(i).getAddress());
        return view;
    }

    static class ViewHolder{
        @InjectView(R.id.name)
        TextView name;
        @InjectView(R.id.gender)
        TextView gender;
        @InjectView(R.id.address)
        TextView address;
        public ViewHolder(View view){
            ButterKnife.inject(this,view);
        }
    }

}
