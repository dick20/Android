package view;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example2.asus.myapplication.R;

import module.HeroDetail;
import adapter.MyRecyclerViewAdapter;
import adapter.MyViewHolder;
import db.Mydb;

import method.imageDownloader;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class MyFragmentTOCollection extends Fragment {
    private Mydb mydb;

    boolean state = true;

    private RecyclerView mRecyclerView;

    private ArrayList<Integer> totalData;

    private MyRecyclerViewAdapter mAdapter;

    private GridLayoutManager mLayoutManager;

    Spinner selectType;

    Spinner rankType;

    String searchString="";

    private EditText searchInput;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.mainpage, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        initData();
        initView();

        initSearch();
        initSelect();
    }

    public void refresh(){
        initData();
        initView();

        initSearch();
        initSelect();
    }


    private void initData(){
        mLayoutManager = new GridLayoutManager(getContext(),2);
        //new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);

        mydb = new Mydb(getContext());

        totalData= mydb.getAllCollectedHeros();
        if(totalData==null)
            totalData = new ArrayList<>();

        Log.i("hero number","number:"+totalData.size());
        mAdapter  = new MyRecyclerViewAdapter<Integer>(R.layout.main_page_preview_item, totalData) {
            @Override
            public void convert(final MyViewHolder holder, Integer s_id) {
                // Colloction是自定义的一个类，封装了数据信息，也可以直接将数据做成一个Map，那么这里就是Map<String, Object>
                final HeroDetail s = mydb.queryHeroById(s_id);
                final TextView name = holder.getView(R.id.name);
                name.setText(s.getName());

                final ImageView img = holder.getView(R.id.head);
                try {
                    (new imageDownloader(img)).execute(s.getImg_url());
//                    img.setImageBitmap(bmp);
                } catch (Exception e) {
                    Log.i("ERROR",e.toString());
                }

                final TextView type = holder.getView(R.id.role);
                type.setText(s.getPosition());

                RelativeLayout itemWhole = (RelativeLayout) holder.getView(R.id.item);
                itemWhole.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent=new Intent();
                        intent.putExtra("id",s.getHero_id());
                        intent.setClass(getContext(),Detail.class);
                        startActivity(intent);
                    }
                });
            }
        };

    }

    private void initView() {
        mRecyclerView = (RecyclerView) getView().findViewById(R.id.ReView);
        // 设置布局管理器
        mRecyclerView.setLayoutManager(mLayoutManager);
        // 设置adapter
        mRecyclerView.setAdapter(mAdapter);
    }


    private void initSearch(){
        Button search_btn = getView().findViewById(R.id.searchButton);
        searchInput = getView().findViewById(R.id.searchInput);

        search_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    totalData.clear();
                    searchString = searchInput.getText().toString();
                    if (searchString.length() != 0) {
                        HeroDetail detail = mydb.queryHeroByName(searchInput.getText().toString());
                        if (detail != null)
                        {
                            totalData.add(detail.getHero_id());
                        }
                        else{
                            Toast.makeText(getContext(), "无法找到对象！", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        totalData.addAll(mydb.getAllCollectedHeros());
                    }
                    selectType.setSelection(0);
                    rankType.setSelection(0);
                    rankWithType(0);
                }
                catch(Exception E){
                    E.printStackTrace();
                }
            }
        });
    }


    private void initSelect(){
        // set the arrow
        final TextView arrow = (TextView) getView().findViewById(R.id.arrow);
        final Animation rotate1 = AnimationUtils.loadAnimation(getContext(), R.anim.lt_rotation_animation);//创建动画
        rotate1.setInterpolator(new LinearInterpolator());//设置为线性旋转
        rotate1.setFillAfter(true);

        final Animation rotate2 = AnimationUtils.loadAnimation(getContext(), R.anim.lt_rotation_animation2);//创建动画
        rotate2.setInterpolator(new LinearInterpolator());//设置为线性旋转
        rotate2.setFillAfter(true);

        arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(state)
                {
                    arrow.startAnimation(rotate1);
                } else {
                    arrow.startAnimation(rotate2);
                }
                state = !state;
                Collections.reverse(totalData);
                mAdapter.notifyDataSetChanged();
            }
        });


        initSelectType();

        initSelectRanktype();
    }

    private void initSelectType(){

        // set the type select
        selectType = getView().findViewById(R.id.selectType);
        final String[] typeArrary=getResources().getStringArray(R.array.type);

        selectType = (Spinner) getView().findViewById(R.id.selectType);

        //将可选内容与ArrayAdapter连接起来，simple_spinner_item是android系统自带样式
        ArrayAdapter adapter = ArrayAdapter.createFromResource(getContext(),
                R.array.type, android.R.layout.simple_spinner_item);
        //设置下拉列表的风格,simple_spinner_dropdown_item是android系统自带的样式，等会自定义修改
        adapter.setDropDownViewResource(R.layout.spinner_item_style);
        //将adapter 添加到spinner中
        selectType.setAdapter(adapter);

        selectType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.i("select type: ", typeArrary[position]);

                if(searchString.length()!=0){
                    //
                } else {
                    ArrayList<Integer> temp = mydb.getAllCollectedHeros();
                    if(temp != null){
                        totalData.clear();
                        for (int c1=0;c1<temp.size();c1++) {
                            Integer item = temp.get(c1);
                            if(mydb.queryHeroById(item).getPosition().equals(typeArrary[position])|| position == 0) {
                                totalData.add(item);
                            }
                        }
                        rankWithType(rankType.getSelectedItemPosition());
                        mAdapter.notifyDataSetChanged();
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                totalData = mydb.getAllCollectedHeros();
                mAdapter.notifyDataSetChanged();
            }
        });
    }

    private void initSelectRanktype() {
        // set the type select
        rankType = getView().findViewById(R.id.rankType);
        final String[] typeArrary=getResources().getStringArray(R.array.rankType);

        //将可选内容与ArrayAdapter连接起来，simple_spinner_item是android系统自带样式
        ArrayAdapter adapter = ArrayAdapter.createFromResource(getContext(),
                R.array.rankType, android.R.layout.simple_spinner_item);
        //设置下拉列表的风格,simple_spinner_dropdown_item是android系统自带的样式，等会自定义修改
        adapter.setDropDownViewResource(R.layout.spinner_item_style);
        //将adapter 添加到spinner中
        rankType.setAdapter(adapter);

        rankType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                rankWithType(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                totalData.sort(new NameComparator());
                mAdapter.notifyDataSetChanged();
            }
        });
    }

    private void rankWithType(int position) {
        switch (position){
            case 0:
                totalData.sort(new NameComparator());
                break;
            case 1:
                totalData.sort(new DifficultyComparator());
                break;
            default:
                totalData.sort(new NameComparator());
        }
        if(!state)
            Collections.reverse(totalData);
        mAdapter.notifyDataSetChanged();
    }


    class DifficultyComparator implements Comparator<Integer> {
        public int compare(Integer first, Integer second) {
            return Integer.compare(Integer.parseInt(mydb.queryHeroById(first).getDifficulty_value()),Integer.parseInt(mydb.queryHeroById(second).getDifficulty_value()));
        }
    }

    class NameComparator implements Comparator<Integer> {
        public int compare(Integer first, Integer second) {
            return mydb.queryHeroById(first).getName().compareTo(mydb.queryHeroById(second).getName());
        }
    }
}