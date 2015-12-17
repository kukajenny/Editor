package com.example.zjp.editor;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageButton;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;


/**
 * Created by zjp on 15-10-12.
 */
public class ListActivity extends Activity{
    private Database dbHelper;
    public List<Listitem> li=new ArrayList<Listitem>();
    SwipeMenuListView listview;
    ListAdapter adapter;
    private static final int BTN_ONE = 1;
    int editposition;
    SQLiteDatabase db;

    public void onCreate(Bundle savedInstanceState){
        Log.d("fds", "dff");
        super.onCreate(savedInstanceState);
        //requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.list);
        dbHelper =new Database(this,"Listdb.db",null,1);
        db= dbHelper.getReadableDatabase();
        //db.execSQL("drop table if exists book");
        Cursor cursor=db.query("book", null, null, null, null, null, null);

        if(cursor.moveToFirst()){
            do{
                String title=cursor.getString(cursor.getColumnIndex("title"));
                int id=cursor.getInt(cursor.getColumnIndex("id"));
                Log.d("id2", id + "");
                Listitem item=new Listitem();
                item.setId(id);
                item.setContent(title);
                li.add(item);

            }while(cursor.moveToNext());
        }
         adapter=new ListAdapter(ListActivity.this,R.layout.listitem, li);


        ImageButton image=(ImageButton)findViewById(R.id.edit);
        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1=new Intent(ListActivity.this,EDITOR.class);
                intent1.putExtra("class","list");
                startActivityForResult(intent1,BTN_ONE);
            }
        });

        listview= (SwipeMenuListView) findViewById(R.id.list_view);
        listview.setAdapter(adapter);
        listview.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                editposition=position;
                Listitem listitem=li.get(position);
                int idd=listitem.getId();
                Toast.makeText(ListActivity.this,position+"",Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(ListActivity.this,Listshow.class);
                intent.putExtra("extra_data",idd);
                startActivityForResult(intent, 2);

            }
        });


        SwipeMenuCreator creator = new SwipeMenuCreator() {

            @Override
            public void create(SwipeMenu menu) {
                // create "open" item
                SwipeMenuItem openItem = new SwipeMenuItem(
                        getApplicationContext());
                // set item background
                openItem.setBackground(new ColorDrawable(Color.rgb(0xC9, 0xC9,
                        0xCE)));
                // set item width
                openItem.setWidth(dp2px(90));
                // set item title
                openItem.setTitle("Open");
                // set item title fontsize
                openItem.setTitleSize(25);
                // set item title font color
                openItem.setTitleColor(Color.WHITE);
                // add to menu
                menu.addMenuItem(openItem);

                // create "delete" item
                SwipeMenuItem deleteItem = new SwipeMenuItem(
                        getApplicationContext());
                // set item background
                deleteItem.setBackground(new ColorDrawable(Color.rgb(0xF9,
                        0x3F, 0x25)));
                // set item width
                deleteItem.setWidth(dp2px(90));
                // set a icon
                deleteItem.setIcon(R.drawable.ic_delete);
                // add to menu
                menu.addMenuItem(deleteItem);
            }
        };
        listview.setMenuCreator(creator);
        listview.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
                Listitem listitem=li.get(position);
                int id=listitem.getId();
                switch (index) {
                    case 0:
                        // open
                        //open(item);
                        break;
                    case 1:
                        // delete
//					delete(item);
                        li.remove(listitem);
                        db.delete("book","id=?",new String[] {id+""});
                        Log.d("id2","yes");
                        adapter.notifyDataSetChanged();
                        break;
                }
                return false;
            }
        });
    }

    private int dp2px(int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
                getResources().getDisplayMetrics());
    }
// set creator







    @Override//重写onActivityResult
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);//有参数的super必须重写
        Log.d("fdf", "dg");
        if(resultCode == BTN_ONE){
            Toast.makeText(ListActivity.this,"yes",Toast.LENGTH_SHORT).show();
            String returneddata=data.getStringExtra("title_return");
            int returnid=data.getIntExtra("id_return",0);
            Listitem listitem=new Listitem();
            listitem.setContent(returneddata);
            listitem.setId(returnid);
            li.add(listitem);
            adapter.notifyDataSetChanged();
        }
        if(resultCode == 2){
            Log.d("return2", "yes");
            Toast.makeText(ListActivity.this,"yes11",Toast.LENGTH_SHORT).show();
            String returneddata=data.getStringExtra("title_return");
            Listitem listitem=li.get(editposition);
            listitem.setContent(returneddata);
            li.set(editposition,listitem);
            adapter.notifyDataSetChanged();
        }
    }

}