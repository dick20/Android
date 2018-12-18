package com.example2.asus.web_api;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class GithubRecyclerViewAdapter<T> extends RecyclerView.Adapter<MyViewHolder>{
    private List<Repo> data;
    private Context context;
    private int layoutId;
    private OnItemClickListener onItemClickListener;
    //点击事件的接口
    public interface OnItemClickListener{
        void onClick(int position);
        void onLongClick(int position);
    }

    //删除数据
    public void deleteData(int position){
        data.remove(position);
    }

    public GithubRecyclerViewAdapter(Context _context, int _layoutId, List<Repo> _data){
        context = _context;
        layoutId = _layoutId;
        data = _data;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MyViewHolder holder = new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.repo_item, parent, false));
        return holder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        //convert
        Log.i("onBindViewHolder","notifyDataChange");
        ((TextView)holder.getView(R.id.repo_id)).setText(data.get(position).getId());
        ((TextView)holder.getView(R.id.title_github)).setText(data.get(position).getName());
        ((TextView)holder.getView(R.id.description)).setText(data.get(position).getDescription());
        if (data.get(position).getHas_issues()){
            ((TextView)holder.getView(R.id.issue)).setText(String.valueOf(data.get(position).getOpen_issues()));
        }
        else {
            ((TextView)holder.getView(R.id.issue)).setText("0");
        }

        if (onItemClickListener != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onItemClickListener.onClick(holder.getAdapterPosition());
                }
            });
            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    onItemClickListener.onLongClick(holder.getAdapterPosition());
                    return false;
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        if(!data.isEmpty())
            return data.size();
        return 0;
    }

    public void setOnItemClickListener(OnItemClickListener _onItemClickListener) {
        this.onItemClickListener = _onItemClickListener;
    }

}