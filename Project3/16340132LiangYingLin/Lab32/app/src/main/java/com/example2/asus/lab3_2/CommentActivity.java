package com.example2.asus.lab3_2;

import android.content.DialogInterface;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class CommentActivity extends AppCompatActivity {
    private int history_comment_num;
    private List<Comment> mList;
    private ArrayList<Integer> like_comment;
    private Mydb mydb;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);
        mydb = new Mydb(this);
        like_comment = new ArrayList<>();

        history_comment_num = mydb.getAllCommentCount();
        // 拿到登陆的用户
        Bundle bundle=this.getIntent().getExtras();
        String str_username = bundle.getString("user");
        user = mydb.queryUserByUsername(str_username);
        Log.i("userdeArraylist",user.getLike_comment().size()+"");

        final EditText comment = findViewById(R.id.comment);
        final Button send = findViewById(R.id.send);

        mList = new ArrayList<>();
        if(mydb.getAllComment() != null){
            mList = mydb.getAllComment();
        }

        ListView listView = findViewById(R.id.listview);
        final MyAdapter adapter = new MyAdapter(CommentActivity.this, mList, user);
        listView.setAdapter(adapter);
        //ListView item的点击事件
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(CommentActivity.this);
                dialog.setTitle("info");
                String username = ((Comment)adapter.getItem(position)).getUsername();
                Cursor cursor = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,null,
                        ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + " = \"" + username + "\"", null, null);
                Boolean is_finded = false;
                if(cursor.moveToFirst() && cursor.getCount() != 0) {
                    is_finded = true;
                }
                if(is_finded){
                    cursor.moveToFirst();
                    String number = "\nPhone: ";
                    do {
                        number += cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)) + "";
                    } while (cursor.moveToNext());

                    dialog.setMessage("Username: " + username + number);
                }
                else {
                    dialog.setMessage("Username: " + username + "\nPhone number not exist");
                }
                dialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                });
                dialog.show();
            }
        });
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                if (((Comment)adapter.getItem(position)).getUsername().equals(user.getUsername())){
                    AlertDialog.Builder dialog = new AlertDialog.Builder(CommentActivity.this);
                    dialog.setMessage("Delete or not?");
                    dialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            mydb.deleteCommentByCid(((Comment)adapter.getItem(position)).getCid());
                            mList.remove(position);
                            adapter.notifyDataSetChanged();
                        }
                    });
                    dialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                        }
                    });
                    dialog.show();
                }
                else{
                    AlertDialog.Builder dialog = new AlertDialog.Builder(CommentActivity.this);
                    dialog.setMessage("Report or not?");
                    dialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Toast.makeText(CommentActivity.this, "You have reported this comment.", Toast.LENGTH_SHORT).show();
                        }
                    });
                    dialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                        }
                    });
                    dialog.show();
                }
                return true;
            }
        });

        //ListView item 中的点赞按钮的点击事件
        adapter.setOnItemLikeClickListener(new MyAdapter.onItemLikeListener() {
            @Override
            public void onLikeClick(int i) {
                Boolean is_liked = false;
                int item = -1;
                like_comment = user.getLike_comment();
                for(int ii = like_comment.size() - 1; ii >= 0; ii--){
                    item = like_comment.get(ii);
                    if(mList.get(i).getCid() == item){
                        is_liked = true;
                    }
                }
                int old_num = mList.get(i).getLike_num();
                if(is_liked) {
                    mList.get(i).setLike_num(old_num - 1);
                    mydb.updateComment(mList.get(i));
                    for(int ii = like_comment.size() - 1; ii >= 0; ii--){
                        item = like_comment.get(ii);
                        if(mList.get(i).getCid() == item){
                            like_comment.remove(ii);
                        }
                    }
                    user.setLike_comment(like_comment);
                    mydb.updateUser(user);
                }
                else{
                    mList.get(i).setLike_num(old_num + 1);
                    mydb.updateComment(mList.get(i));
                    like_comment.add(mList.get(i).getCid());
                    user.setLike_comment(like_comment);
                    mydb.updateUser(user);
                }
                adapter.notifyDataSetChanged();
            }
        });

        // 发送评论
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String content = comment.getText().toString();
                if (content.isEmpty()){
                    Toast.makeText(CommentActivity.this, "Comment cannot be empty.", Toast.LENGTH_SHORT).show();
                    return;
                }
                int cid = history_comment_num+2;
                history_comment_num++;
                // 临时从resources获取头像，后要改成读取图库
                // Bitmap head =  ((BitmapDrawable) getResources().getDrawable(R.mipmap.me)).getBitmap();
                Bitmap head = user.getHead();
                Date now = new Date( );
                SimpleDateFormat ft = new SimpleDateFormat ("yyyy.MM.dd hh:mm:ss");
                Log.i("timestamp",ft.format(now));
                Comment new_comment = new Comment(cid,user.getUsername(),head,ft.format(now),content,0,false);
                mydb.insertComment(new_comment);
                mList.add(new_comment);
                adapter.notifyDataSetChanged();
                comment.setText("");
            }
        });
    }
}
