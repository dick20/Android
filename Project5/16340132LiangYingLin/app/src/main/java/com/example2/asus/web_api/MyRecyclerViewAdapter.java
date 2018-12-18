package com.example2.asus.web_api;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


public class MyRecyclerViewAdapter<T> extends RecyclerView.Adapter<MyViewHolder>{
    private List<RecyclerObj> data;
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

    public MyRecyclerViewAdapter(Context _context, int _layoutId, List<RecyclerObj> _data){
        context = _context;
        layoutId = _layoutId;
        data = _data;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MyViewHolder holder = new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.item, parent, false));
        return holder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        // 隐藏加载条
        if (data.get(position).getData().getCover_image() != null){
            ((ProgressBar)holder.getView(R.id.processBar)).setVisibility(View.INVISIBLE);
        }
        // 初始化seekBar
        if(data.get(position).getPieces()==null){
            ((SeekBar)holder.getView(R.id.seekBar)).setEnabled(false);
        }
        else {
            ((SeekBar)holder.getView(R.id.seekBar)).setEnabled(true);
            String timeStr = data.get(position).getData().getDuration();
            String[] timeArray = new String[2];
            timeArray = timeStr.split(":");
            int minute = Integer.valueOf(timeArray[0]);
            int second = Integer.valueOf(timeArray[1]);
            int time = minute * 60 + second;
            Log.i("时间长度",time+"");
            ((SeekBar)holder.getView(R.id.seekBar)).setMax(time);
            ((SeekBar)holder.getView(R.id.seekBar)).setProgress(0);
            // 设置监听器
            ((SeekBar)holder.getView(R.id.seekBar)).setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                    ArrayList<RecyclerObj.ImagePiece> pieces =  data.get(position).getPieces();
                    Log.i("时间长度",progress+"");
                    for (int i = 0; i < pieces.size(); i++){
                        if (pieces.get(i).getIndex() == progress){
                            Bitmap bitmap = pieces.get(i).getBitmap();
                            ((ImageView)holder.getView(R.id.web_image)).setImageBitmap(bitmap);
                            break;
                        }
                    }
                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {

                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {
                    // 设置回初始状态
                    seekBar.setProgress(0);
                    ((ImageView)holder.getView(R.id.web_image)).setImageBitmap(data.get(position).getData().getCover_image());
                }
            });
        }
        ((ImageView)holder.getView(R.id.web_image)).setImageBitmap(data.get(position).getData().getCover_image());
        ((TextView)holder.getView(R.id.play_amount)).setText(data.get(position).getData().getPlay());
        ((TextView)holder.getView(R.id.comment_amount)).setText(data.get(position).getData().getVideo_review());
        ((TextView)holder.getView(R.id.video_time)).setText(data.get(position).getData().getDuration());
        ((TextView)holder.getView(R.id.create_time)).setText(data.get(position).getData().getCreate());

        ((TextView)holder.getView(R.id.video_title)).setText(data.get(position).getData().getTitle());
        ((TextView)holder.getView(R.id.introduction)).setText(data.get(position).getData().getContent());

    }

    @Override
    public int getItemCount() {
        if(!data.isEmpty())
            return data.size();
        return 0;
    }

}
