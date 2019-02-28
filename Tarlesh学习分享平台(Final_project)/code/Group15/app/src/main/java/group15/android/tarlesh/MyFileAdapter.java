package group15.android.tarlesh;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

public class MyFileAdapter extends RecyclerView.Adapter<MyFileAdapter.ViewHolder> {
    private List<File> list;

    public MyFileAdapter(List<File> list) {
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
    public MyFileAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.file_item, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyFileAdapter.ViewHolder viewHolder, int i) {
        System.out.println("In File Adapter " + i);
        final File file = list.get(i);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private String filename;
        private String description;
        private String createTime;
        private String owner;

        public ViewHolder(View itemView) {
            super(itemView);

        }
    }
}
