package com.example.zjp.editor;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.zjp.editor.R;

import java.util.ArrayList;

/**
 * Created by zjp on 15-10-10.
 */
public class EDITOR extends AppCompatActivity {
    private Database dbHelper;
    String pretitle;
    String data;
    int num;
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.editor);
            Intent intent=getIntent();
            data=intent.getStringExtra("class");
            pretitle=intent.getStringExtra("title");
            String precontent=intent.getStringExtra("content");
            num=intent.getIntExtra("num",0);
            final EditText title=(EditText)findViewById(R.id.title);
            final EditText message=(EditText)findViewById(R.id.message);
            switch (data){
                case "listshow":{
                    title.setText(pretitle);
                    message.setText(precontent);
                }
            }
            Log.d("df", "fdgfd");
            dbHelper =new Database(this,"Listdb.db",null,1);
            Button bb=(Button)findViewById(R.id.save);


            Toolbar toolbar=(Toolbar)findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);
            ActionBar actionbar = getSupportActionBar();
            actionbar.setDisplayHomeAsUpEnabled(true);


            Log.d("df", "1");
            SQLiteDatabase db= dbHelper.getWritableDatabase();
            final Cursor cursor=db.query("book", null, null, null, null, null, null);
            bb.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SQLiteDatabase db = dbHelper.getWritableDatabase();
                    ContentValues values = new ContentValues();
                    String t = title.getText().toString();
                    String m = message.getText().toString();


                    if(data.equals("list")){
                        values.put("title", t);
                        values.put("message", m);
                        db.insert("book", null, values);

                    }
                    else {
                        /*String sql = "update book set title = "+t+",message="+m+" where num = "+num;

                        //执行SQL
                        db.execSQL(sql);*/
                        ContentValues cv = new ContentValues();
                        cv.put("title", t);
                        cv.put("message", m);
                        //更新数据
                        db.update("book", cv, "id = ?", new String[]{num + ""});
                    }
                    cursor.moveToLast();
                    int insertid=cursor.getInt(0);

                    /*dbHelper.li.add(t);
                    ArrayAdapter<String> adapter;
                    ListView listview=(ListView)findViewById(R.id.list_view);
                    adapter= (ArrayAdapter<String>) listview.getAdapter();
                    Log.d("df","2");
                    listview.setAdapter(adapter);*/
                    Intent intent = new Intent();
                    intent.putExtra("title_return",t);
                    intent.putExtra("id_return",insertid);
                    intent.putExtra("message_return",m);
                    if(data.equals("list"))setResult(1, intent);
                    else setResult(2,intent);
                    finish();
                }
            });

        }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
       // getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (item.getItemId() == android.R.id.home){
            finish();
            return true ;
        }
        //noinspection SimplifiableIfStatement
        /*if (id == R.id.action_settings) {
            return true;
        }*/

        return super.onOptionsItemSelected(item);
    }
}
