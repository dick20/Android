package com.example2.asus.lab3_2;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

public class MyAdapter extends BaseAdapter {
    private Context mContext;
    private List<Comment> mList = new ArrayList<>();
    private User mUser;

    public MyAdapter(Context context, List<Comment> list, User user) {
        mContext = context;
        mList = list;
        mUser = user;
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int i) {
        return mList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder = null;
        if (view == null) {
            viewHolder = new ViewHolder();
            view = LayoutInflater.from(mContext).inflate(R.layout.item, null);
            viewHolder.head = view.findViewById(R.id.head);
            viewHolder.name = view.findViewById(R.id.name);
            viewHolder.time = view.findViewById(R.id.time);
            viewHolder.content = view.findViewById(R.id.content);
            viewHolder.num = view.findViewById(R.id.num);
            viewHolder.like_img = view.findViewById(R.id.like_img);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        viewHolder.head.setImageBitmap(mList.get(i).getHead());
        viewHolder.name.setText(mList.get(i).getUsername());
        viewHolder.time.setText(mList.get(i).getTimestamp());
        viewHolder.content.setText(mList.get(i).getContent());
        viewHolder.num.setText(mList.get(i).getLike_num()+"");
        ArrayList<Integer> arrayList = mUser.getLike_comment();
        viewHolder.like_img.setImageResource(R.mipmap.white);

        // Log.i("已收藏",arrayList.size()+"");
        for(int j = 0; j < arrayList.size(); j++){
            if(arrayList.get(j) == mList.get(i).getCid()){
                viewHolder.like_img.setImageResource(R.mipmap.red);
            }
        }
        viewHolder.like_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnItemLikeListener.onLikeClick(i);
            }
        });
        return view;
    }

    /**
     * 点赞按钮的监听接口
     */
    public interface onItemLikeListener {
        void onLikeClick(int i);
    }

    private onItemLikeListener mOnItemLikeListener;

    public void setOnItemLikeClickListener(onItemLikeListener mOnItemLikeListener) {
        this.mOnItemLikeListener = mOnItemLikeListener;
    }

    class ViewHolder {
        ImageView head;
        TextView name;
        TextView time;
        TextView content;
        TextView num;
        ImageView like_img;
    }

}
