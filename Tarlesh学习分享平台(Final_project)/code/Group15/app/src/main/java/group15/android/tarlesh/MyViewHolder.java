package group15.android.tarlesh;

import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.View;

public class MyViewHolder extends RecyclerView.ViewHolder {
    private SparseArray<View> views;
    private View view;


    public MyViewHolder(View _view) {
        super(_view);
        view = _view;
        views = new SparseArray<View>();
    }

    public <T extends View> T getView(int _viewId) {
        View _view = views.get(_viewId);
        if (_view == null) {
            //创建view
            _view = view.findViewById(_viewId);
            //将view存入views
            views.put(_viewId, _view);
        }
        return (T) _view;
    }
}