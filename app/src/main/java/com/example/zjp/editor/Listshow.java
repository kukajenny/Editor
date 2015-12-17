package com.example.zjp.editor;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ActionMenuView;
import android.widget.TextView;
import android.widget.Toast;

import com.yalantis.contextmenu.lib.ContextMenuDialogFragment;
import com.yalantis.contextmenu.lib.MenuObject;
import com.yalantis.contextmenu.lib.MenuParams;
import com.yalantis.contextmenu.lib.interfaces.OnMenuItemClickListener;
import com.yalantis.contextmenu.lib.interfaces.OnMenuItemLongClickListener;

import java.util.ArrayList;

/**
 * Created by zjp on 15-10-13.
 */
public class Listshow extends ToolBarActivity implements OnMenuItemClickListener ,OnMenuItemLongClickListener{
    private Database dbHelper;
    ContextMenuDialogFragment mMenuDialogFragment;
    private FragmentManager fragmentManager;
    String title,content;
    int data;
    TextView title_textview;
    TextView content_textview;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listshow);
        title_textview=(TextView)findViewById(R.id.listshow_title);
        content_textview=(TextView)findViewById(R.id.listshow_content);


        Intent intent=getIntent();
        data=intent.getIntExtra("extra_data",0);
        //Toast.makeText(Listshow.this, ""+data+"", Toast.LENGTH_SHORT).show();
        Log.d("data1", data + "");


        String sql ="SELECT * FROM book WHERE num = ?";
        dbHelper =new Database(this,"Listdb.db",null,1);

        android.support.v7.app.ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);

        SQLiteDatabase db= dbHelper.getWritableDatabase();
        String[] args={data+""};
        //Cursor cursor=db.query("book", null, null, null, null, null, null);
        /*Cursor cursor=db.rawQuery(
                "SELECT id FROM book WHERE id='"+data+"'", null);*/
        //cursor.moveToPosition(1);
        //Log.d("dfd", cursor.getPosition() + "dgd" + data);
        //Cursor cursor=db.query("book", null, "id = \'"+data+"\'", null, null, null, null);
        /*Cursor cursor=db.query("book", null, null, null, null, null, null);
        cursor.moveToFirst();*/
        Cursor cursor=db.rawQuery("select * from book where id=?", new String[]{data+""});//Cursor 游标和 ResultSet 很像
        cursor.moveToFirst();
        title=cursor.getString(cursor.getColumnIndex("title"));
        content=cursor.getString(cursor.getColumnIndex("message"));
        cursor.close();
        title_textview.setText(title);
        content_textview.setText(content);


        fragmentManager = getSupportFragmentManager();
        /*MenuObject addFr = new MenuObject("Add to friends");
        BitmapDrawable bd = new BitmapDrawable(getResources(),
                BitmapFactory.decodeResource(getResources(), R.drawable.logo));
        addFr.setDrawable(bd);
        addFr.setMenuTextAppearanceStyle(R.style.TextViewStyle);*/


        MenuParams menuParams = new MenuParams();
        menuParams.setActionBarSize((int) getResources().getDimension(R.dimen.tool_bar_height));
        menuParams.setMenuObjects(getMenuObjects());
        menuParams.setClosableOutside(true);
        // set other settings to meet your needs
        mMenuDialogFragment= ContextMenuDialogFragment.newInstance(menuParams);
        Log.d("dfs",mMenuDialogFragment+"");

    }

    private java.util.List<MenuObject> getMenuObjects() {
        // You can use any [resource, bitmap, drawable, color] as image:
        // item.setResource(...)
        // item.setBitmap(...)
        // item.setDrawable(...)
        // item.setColor(...)
        // You can set image ScaleType:
        // item.setScaleType(ScaleType.FIT_XY)
        // You can use any [resource, drawable, color] as background:
        // item.setBgResource(...)
        // item.setBgDrawable(...)
        // item.setBgColor(...)
        // You can use any [color] as text color:
        // item.setTextColor(...)
        // You can set any [color] as divider color:
        // item.setDividerColor(...)

        java.util.List<MenuObject> menuObjects = new ArrayList<>();

        MenuObject close = new MenuObject();
        close.setResource(R.drawable.icn_close);

        MenuObject send = new MenuObject("Edit text");
        send.setResource(R.drawable.logo);

        MenuObject like = new MenuObject("Like profile");
        Bitmap b = BitmapFactory.decodeResource(getResources(), R.drawable.icn_2);
        like.setBitmap(b);

        MenuObject addFr = new MenuObject("Add to friends");
        BitmapDrawable bd = new BitmapDrawable(getResources(),
                BitmapFactory.decodeResource(getResources(), R.drawable.icn_3));
        addFr.setDrawable(bd);

        MenuObject addFav = new MenuObject("Add to favorites");
        addFav.setResource(R.drawable.icn_4);

        MenuObject block = new MenuObject("Block user");
        block.setResource(R.drawable.icn_5);

        menuObjects.add(close);
        menuObjects.add(send);
        menuObjects.add(like);
        menuObjects.add(addFr);
        menuObjects.add(addFav);
        menuObjects.add(block);
        return menuObjects;
    }


    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.context_menu:
                mMenuDialogFragment
                        .show(fragmentManager, "ContextMenuDialogFragment");
                break;
        }
        return super.onOptionsItemSelected(item);
    }



    @Override
    public void onMenuItemClick(View view, int i) {
        switch(i){
            case 1:
                Intent intent1=new Intent(Listshow.this,EDITOR.class);
                intent1.putExtra("class","listshow");
                intent1.putExtra("title",title);
                intent1.putExtra("content",content);
                intent1.putExtra("num",data);
                startActivityForResult(intent1, 2);
        }
    }

    @Override
    public void onMenuItemLongClick(View view, int i) {

    }

    @Override//重写onActivityResult
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);//有参数的super必须重写
        Log.d("fdf", "dg");
        if(resultCode == 2){
            String returntitle=data.getStringExtra("title_return");
            String returnmessage=data.getStringExtra("message_return");
            title_textview.setText(returntitle);
            content_textview.setText(returnmessage);
            Intent intent=new Intent();
            intent.putExtra("title_return",returntitle);
            setResult(2,intent);
        }
    }
}
