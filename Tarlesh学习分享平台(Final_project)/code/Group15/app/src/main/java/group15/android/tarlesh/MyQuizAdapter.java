package group15.android.tarlesh;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class MyQuizAdapter extends RecyclerView.Adapter<MyQuizAdapter.ViewHolder>{
    private List<Quiz> list;

    public MyQuizAdapter(List<Quiz> list) {
        this.list = list;
    }

    @Override
    public int getItemCount() {
        if (list == null) {
            return 0;
        }
        return list.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @NonNull
    @Override
    public MyQuizAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.quiz_item, viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyQuizAdapter.ViewHolder viewHolder, int i) {
        System.out.println("In Quiz Adapter " + i);
        final Quiz quiz = list.get(i);
        // 渲染每个Item的页面数据
        viewHolder.title.setText("Title: " + quiz.getTitle());
        viewHolder.content.setText("Content: " + quiz.getContent());
        viewHolder.createTime.setText("Create Time: " + quiz.getCreateTime());
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView title;
        private TextView content;
        private TextView createTime;

        public ViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
            content = itemView.findViewById(R.id.content);
            createTime = itemView.findViewById(R.id.createTime);
        }
    }
}
