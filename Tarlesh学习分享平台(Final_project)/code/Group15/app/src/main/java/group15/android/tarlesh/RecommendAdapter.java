package group15.android.tarlesh;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class RecommendAdapter<T> extends RecyclerView.Adapter<MyViewHolder>{
    private List<File> data;
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

    public RecommendAdapter(Context _context, int _layoutId, List<File> _data){
        context = _context;
        layoutId = _layoutId;
        data = _data;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MyViewHolder holder = new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.file_item, parent, false));
        return holder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        //convert
        Log.i("onBindViewHolder","notifyDataChange");
        ((TextView)holder.getView(R.id.name)).setText(data.get(position).getFilename());
        ((TextView)holder.getView(R.id.uploader)).setText("上传者： "+data.get(position).getOwner());
        ((TextView)holder.getView(R.id.download_count)).setText("下载次数： "+data.get(position).getDownload_count());
        if(data.get(position).getDownload_count() == null){
            ((TextView)holder.getView(R.id.download_count)).setText("下载次数： 0");
        }
        ((TextView)holder.getView(R.id.star)).setText("评价： "+String.valueOf(data.get(position).getStar_count())+"/5");
        if(String.valueOf(data.get(position).getStar_count()) == "null"){
            ((TextView)holder.getView(R.id.star)).setText("评价：3/5");
        }
        ((TextView)holder.getView(R.id.create_time)).setText("创建时间： " + data.get(position).getCreateTime());
        ((TextView)holder.getView(R.id.file_description)).setText("文件描述： " + String.valueOf(data.get(position).getDescription()));

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