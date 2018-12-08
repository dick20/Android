package adapter;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.util.ArrayList;

public abstract class MyRecyclerViewAdapter<T> extends RecyclerView.Adapter {
    ArrayList<T> data;
    int view_item;
    public abstract void convert(MyViewHolder holder, T t);

    public MyRecyclerViewAdapter(int _layoutId, ArrayList _data) {
        data = _data;
        view_item = _layoutId;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MyViewHolder holder = MyViewHolder.get(parent.getContext(), parent ,view_item);
        return holder;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        convert((MyViewHolder) holder, data.get(position)); // convert函数需要重写，下面会
    }

    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size();
    }

    public void delete(T item) { data.remove(item);}
}
