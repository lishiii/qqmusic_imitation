package cn.ink.music_7;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import cn.ink.music_7.sql.MyOpenHelper;

public class AdActivity extends AppCompatActivity {

    private MyOpenHelper myOpenHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ad);

        myOpenHelper = new MyOpenHelper(getApplicationContext());
        new Thread(new Runnable() {
            public void run() {
                try {
                    if (check_use_login()) {
                        Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                        Thread.sleep(2000);
//                        System.out.println("==================MainActivity==============");
                        startActivity(intent);
                    } else {
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        Thread.sleep(2000);
//                        System.out.println("==================HomeActivity==============");
                        startActivity(intent);
                    }

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    /**
     * 判断是否登录了用户
     */
    private boolean check_use_login() {
        SQLiteDatabase db = myOpenHelper.getWritableDatabase();
//        System.out.println("==================check_use_login==============");
        String sql = "select * from user where is_login = 1";
        Cursor cursor = db.rawQuery(sql, null);
//        System.out.println("==================cursor==============");
        if (cursor != null) {
            while (cursor.moveToNext()) {
                String value = cursor.getString(cursor.getColumnIndex("name"));
//                System.out.println("==================name==============" + value);
                cursor.close();
                db.close();
                return true;
            }
        }

        cursor.close();
        db.close();
        return false;
    }


    protected void onPause() {
        super.onPause();
        finish();
    }

    public void tiao(View v) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
