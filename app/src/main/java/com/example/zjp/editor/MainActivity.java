package com.example.zjp.editor;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    CheckBox pass;
    EditText user;
    SharedPreferences pref;
    private Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        //requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);


        /*toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setLogo(R.drawable.logo);
        setSupportActionBar(toolbar);
        android.support.v7.app.ActionBar actionbar = getSupportActionBar();
        Drawable resID = getResources().getDrawable(R.drawable.logo);
        actionbar.setLogo(resID);
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar .setDisplayUseLogoEnabled( false );
        actionbar.setTitle("我是主标题");actionbar.setSubtitle("我是副标题");*/

        pref= PreferenceManager.getDefaultSharedPreferences(this);
        user=(EditText)findViewById(R.id.username_edit);
        final EditText password1=(EditText)findViewById(R.id.password_edit);
        pass=(CheckBox)findViewById(R.id.rembmberCheckBox);
        boolean isremember=pref.getBoolean("rembmbered",false);
        if(isremember){
            String account=pref.getString("account","");
            String password=pref.getString("password","");
            user.setText(account);
            password1.setText(password);
            pass.setChecked(true);
        }
        Button button=(Button)findViewById(R.id.signin_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String account=user.getText().toString();
                String password=password1.getText().toString();
                if(account.equals("admin")&&password.equals("123")){
                    SharedPreferences.Editor editor=pref.edit();
                    if(pass.isChecked()){
                        editor.putBoolean("rembmbered",true);
                        editor.putString("account", account);
                        editor.putString("password",password);
                    }
                    else{
                        editor.clear();
                    }
                    editor.commit();
                    Toast.makeText(MainActivity.this,"yes",Toast.LENGTH_SHORT).show();
                    Intent intent1=new Intent(MainActivity.this,ListActivity.class);
                    startActivity(intent1);
                }
                else{
                    Toast.makeText(MainActivity.this,"用户名或密码错误",Toast.LENGTH_SHORT).show();
                    user.setText("");
                    password1.setText("");
                }


            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        /*if (id == R.id.action_settings) {
            return true;
        }*/

        return super.onOptionsItemSelected(item);
    }
}
