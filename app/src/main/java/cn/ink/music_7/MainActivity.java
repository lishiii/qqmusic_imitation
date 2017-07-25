package cn.ink.music_7;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import cn.ink.music_7.sql.MyOpenHelper;


public class MainActivity extends AppCompatActivity {
    private MyOpenHelper myOpenHelper;
    // 具体值
    private String password;
    private String username;
    // 定义控件
    private EditText et_username, et_password;
    private Button btn_user_clear, btn_pwd_eye, btn_pwd_clear, login, pwd_forget, register;
    // 定义静态变量
    public final static int USER_CLEAR = 1;
    public final static int PWD_CLEAR = 2;
    public final static int PWD_EYE = 3;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case USER_CLEAR:
                    et_username.setText("");
                    break;
                case PWD_CLEAR:
                    et_password.setText("");
                    break;
                case PWD_EYE:
                    if (et_password.getInputType() == (InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD)) {
                        et_password.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_NORMAL);
                    } else {
                        et_password.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    }
                    et_password.setSelection(et_password.getText().toString().length());
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myOpenHelper = new MyOpenHelper(getApplicationContext());
        // 从注册界面传输注册成功的用户名过来
//        if (this.getIntent() != null) {
//            Bundle bundle = this.getIntent().getExtras();
//            String username = bundle.getString("username");
//            if (username != null && username.length() > 0) {
//                et_username.setText(username);
//            }
//        }


    }

    //    btn_user_clear, btn_pwd_eye, btn_pwd_clear
    public void user_clear(View v) {
        Message msg = new Message();
        msg.what = USER_CLEAR;
        handler.sendMessage(msg);
    }

    public void pwd_clear(View v) {
        Message msg = new Message();
        msg.what = PWD_CLEAR;
        handler.sendMessage(msg);
    }


    public void search() {
        Intent intent = new Intent(getApplicationContext(), FindActivity.class);
        startActivity(intent);
    }


    public void pwd_eye(View v) {
        Message msg = new Message();
        msg.what = PWD_EYE;
        handler.sendMessage(msg);
    }


    public void login(View v) {
        et_username = (EditText) findViewById(R.id.username);
        et_password = (EditText) findViewById(R.id.password);
        password = et_password.getText().toString().trim();
        username = et_username.getText().toString().trim();
        /**
         * 保存登录状态到数据库
         */
//        change_status(username);
        if (username != null && password != null) {
            Intent intent = new Intent(this, HomeActivity.class);
            //用Bundle携带数据
//        Bundle bundle = new Bundle();
            startActivity(intent);
        } else {
            Toast.makeText(getApplicationContext(), "用户名密码不能为空!", Toast.LENGTH_LONG).show();
        }

    }

    private void change_status(String username) {
        SQLiteDatabase db = myOpenHelper.getWritableDatabase();
        String sql1 = "SELECT * FROM user WHERE name='" + username + "'";
        Cursor cursor = db.rawQuery(sql1, null);
        if (cursor != null) {
            String sql2 = "UPDATE user SET is_login=1 WHERE name=" + username;
            db.execSQL(sql2);
        } else {
            Toast.makeText(getApplicationContext(), "用户名为" + username, Toast.LENGTH_LONG).show();
        }
        cursor.close();
        db.close();
    }

    public void register(View v) {
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }

    public void pwd_forget(View v) {

    }

}
