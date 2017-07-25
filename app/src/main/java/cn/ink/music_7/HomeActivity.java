package cn.ink.music_7;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import cn.ink.music_7.sql.MyOpenHelper;

public class HomeActivity extends AppCompatActivity {
    private MyOpenHelper myOpenHelper;
    // 定义控件
    private Button btn_search, btn_radio;
    private TextView tv_username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        tv_username = (TextView) findViewById(R.id.tv_username);
        myOpenHelper = new MyOpenHelper(getApplicationContext());
        //新页面接收数据
//        Bundle bundle = this.getIntent().getExtras();
        //接收name值
//        String name = bundle.getString("name");
        String name_login = get_name();
//        tv_username.setText(name_login + "  欢迎您回来！");
      tv_username.setText("123  欢迎您回来！");

        //控制图标大小_1
        btn_search = (Button) findViewById(R.id.btn_search);
        Drawable find = getResources().getDrawable(R.drawable.find);
        find.setBounds(0, 0, 40, 40);
        btn_search.setCompoundDrawables(find, null, null, null);
        //控制图标大小_2
        btn_radio = (Button) findViewById(R.id.btn_radio);
        Drawable radio_station = getResources().getDrawable(R.drawable.radio_station);
        radio_station.setBounds(0, 0, 40, 40);
        btn_radio.setCompoundDrawables(radio_station, null, null, null);
    }

    private String get_name() {
        SQLiteDatabase db = myOpenHelper.getWritableDatabase();
        String sql = "select name from user where is_login = 1";
        Cursor cursor = db.rawQuery(sql, null);
        String name = null;
        while (cursor.moveToNext()) {
            name = cursor.getString(cursor.getColumnIndex("name"));
            break;
        }
        cursor.close();
        db.close();
        return name;
    }

    public void more(View view) {
        Intent intent = new Intent(this, MoreActivity.class);
        startActivity(intent);
    }


    public void musicshop(View view) {
        Intent intent = new Intent(this, MusicShopActivity.class);
        startActivity(intent);
    }

    public void find(View view) {
        Intent intent = new Intent(this, FindActivity.class);
        startActivity(intent);
    }


}

