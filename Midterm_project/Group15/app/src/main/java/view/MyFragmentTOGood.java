package view;

import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
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
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import module.Equip;
import adapter.MyRecyclerViewAdapter;
import adapter.MyViewHolder;

import db.Mydb;
import com.example2.asus.myapplication.R;
import method.imageDownloader;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class MyFragmentTOGood extends Fragment {
    private PopupWindow popupWindow; // 装备详情框
    private Mydb mydb;

    boolean state = true;

    private RecyclerView mRecyclerView;

    private ArrayList<Equip> totalData;
    private ArrayList<String> cdata;

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

        initSelect();
        initSearch();
    }

    private void initData(){
        mLayoutManager = new GridLayoutManager(getContext(),2);
        //new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);

        mydb = new Mydb(getContext());
        totalData= mydb.getAllEquips();
        Log.i("hero number","number:"+totalData.size());
        mAdapter  = new MyRecyclerViewAdapter<Equip>(R.layout.main_page_preview_item, totalData) {
            @Override
            public void convert(final MyViewHolder holder, Equip s) {
                // Colloction是自定义的一个类，封装了数据信息，也可以直接将数据做成一个Map，那么这里就是Map<String, Object>
                final TextView name = holder.getView(R.id.name);
                name.setText(s.getEquip_name());

                final ImageView img = holder.getView(R.id.head);
                try {
                    (new imageDownloader(img)).execute(s.getImg_url());
                } catch (Exception e) {
                    Log.i("ERROR",e.toString());
                }

                final TextView type = holder.getView(R.id.role);
                type.setText("$"+s.getBuying_price());

                RelativeLayout itemWhole = (RelativeLayout) holder.getView(R.id.item);
                final Equip eq = s;
                itemWhole.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showPopupWindow(v,eq);
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
                totalData.clear();
                searchString =searchInput.getText().toString();
                if(searchString.length()!=0){
                    Equip detail = mydb.queryEquipByName(searchString);
                    if(detail!=null){
                        totalData.add(detail);
                    }
                    else{
                        Toast.makeText(getContext(), "无法找到对象！", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    totalData.addAll(mydb.getAllEquips());
                }
                selectType.setSelection(0);
                rankType.setSelection(0);
                rankWithType(0);
                mAdapter.notifyDataSetChanged();
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
        final String[] typeArrary=getResources().getStringArray(R.array.equipType);

        selectType = (Spinner) getView().findViewById(R.id.selectType);

        //将可选内容与ArrayAdapter连接起来，simple_spinner_item是android系统自带样式
        ArrayAdapter adapter = ArrayAdapter.createFromResource(getContext(),
                R.array.equipType, android.R.layout.simple_spinner_item);
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
                    ArrayList<Equip> temp = mydb.getAllEquips();
                    totalData.clear();
                    for (int c1=0;c1<temp.size();c1++) {
                        Equip item = temp.get(c1);
                        if(item.getAttr().equals(typeArrary[position])|| position == 0) {
                            totalData.add(item);
                        }
                    }
                    rankWithType(rankType.getSelectedItemPosition());
                    mAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                totalData = mydb.getAllEquips();
                rankWithType(rankType.getSelectedItemPosition());
                mAdapter.notifyDataSetChanged();
            }
        });
    }

    private void initSelectRanktype() {
        // set the type select
        rankType = getView().findViewById(R.id.rankType);
        final String[] typeArrary=getResources().getStringArray(R.array.equip_RankType);

        //将可选内容与ArrayAdapter连接起来，simple_spinner_item是android系统自带样式
        ArrayAdapter adapter = ArrayAdapter.createFromResource(getContext(),
                R.array.equip_RankType, android.R.layout.simple_spinner_item);
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
                totalData.sort(new priceComparator());
                for (Equip eq :
                        totalData) {
                    Log.i(eq.getEquip_name()+"'s money",eq.getBuying_price()+"");
                }
                break;
            default:
                totalData.sort(new priceComparator());
        }
        
        mAdapter.notifyDataSetChanged();
    }


    class priceComparator implements Comparator<Equip> {
        public int compare(Equip first, Equip second) {
            return Integer.compare(first.getBuying_price(),second.getBuying_price());
        }
    }

    class NameComparator implements Comparator<Equip> {
        public int compare(Equip first, Equip second) {
            return first.getEquip_name().compareTo(second.getEquip_name());
        }
    }

    // 生成PopupWindow
    private void showPopupWindow(View button, Equip equip) {
        View contentView = LayoutInflater.from(getContext()).inflate(R.layout.popuplayout, null);
        // 新建popupWindow
        popupWindow = new PopupWindow(contentView);
        popupWindow.setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
        popupWindow.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        popupWindow.setContentView(contentView);

        // 设置popupwindow内容
        ImageView equipImg = (ImageView)contentView.findViewById(R.id.equipImg);
        TextView equipName = (TextView)contentView.findViewById(R.id.equipname);
        TextView sellPrice = (TextView)contentView.findViewById(R.id.sellPrice);
        TextView buyPrice = (TextView)contentView.findViewById(R.id.buyPrice);
        TextView equipDetail = (TextView)contentView.findViewById(R.id.equipdetail);
        TextView uniquepassive = (TextView)contentView.findViewById(R.id.uniquepassive);

        // 绑定页面数据
        // 装备图片
        //equipImg.setImageDrawable(getResources().getDrawable(R.drawable.arrow));
        (new imageDownloader(equipImg)).execute(equip.getImg_url());
        equipName.setText(equip.getEquip_name());
        sellPrice.setText("售价" + equip.getSelling_price() + "");
        buyPrice.setText("总价" + equip.getBuying_price() + "");
        equipDetail.setText(equip.getDetail());
        if (equip.getUnique_passive().equals("NULL")) {
            uniquepassive.setText("无");
        }
        else {
            uniquepassive.setText(equip.getUnique_passive());
        }

        // 允许点击外部消失
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        popupWindow.setOutsideTouchable(true);
        popupWindow.setFocusable(true);

        // 获取自身的长宽
        int popupHeight = contentView.getMeasuredHeight();
        int popupWidth = contentView.getMeasuredWidth();

        // 在控件附近显示信息
        int []location = new int[2];
        button.getLocationOnScreen(location);
        //View rootview = LayoutInflater.from(getContext()).inflate(R.layout.fragment2, null);
        popupWindow.showAtLocation(button, Gravity.NO_GRAVITY, (location[0] + button.getWidth() / 2) - popupWidth / 2, location[1] - popupHeight-70);
    }

}