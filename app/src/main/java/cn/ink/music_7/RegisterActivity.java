package cn.ink.music_7;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import cn.ink.music_7.sql.MyOpenHelper;


public class RegisterActivity extends AppCompatActivity {

    private MyOpenHelper myOpenHelper;

    //定义控件
    private EditText et_zc_email, et_zc_repwd, et_zc_pwd, et_zc_username;
    //定义接受变量
    private String username, pwd, repwd, email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        myOpenHelper = new MyOpenHelper(getApplicationContext());
    }

    public void Fanhui(View v) {
        et_zc_username = (EditText) findViewById(R.id.et_zc_username);
        et_zc_pwd = (EditText) findViewById(R.id.et_zc_pwd);
        et_zc_repwd = (EditText) findViewById(R.id.et_zc_repwd);
        et_zc_email = (EditText) findViewById(R.id.et_zc_email);
        username = et_zc_username.getText().toString().trim();
        pwd = et_zc_pwd.getText().toString().trim();
        repwd = et_zc_repwd.getText().toString().trim();
        email = et_zc_email.getText().toString().trim();

        // TODO 各种校验
        // TODO 添加进入服务器数据库中

        /**
         *  添加到本地数据库
         *  如果添加到服务器数据库，则添加到本地数据库
         */
        if (true) {
            add_user(username, email);
        } else {
            Toast.makeText(getApplicationContext(), "服务器忙，请稍后..", Toast.LENGTH_LONG).show();
        }

        // 返回登录界面
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);

    }

    /**
     * 添加数据到本地数据库
     */
    private void add_user(String username, String email) {
        SQLiteDatabase db = myOpenHelper.getWritableDatabase();
        System.out.println("===============" + username + "====+++++++=====" + email + "========");
        db.execSQL("insert into user(name,email,is_login) values(?,?,?)",
                new Object[]{username, email, 0});
        db.close();
    }


    /**
     * 验证用户名是否规范
     */
    private boolean TestUsername(String username) {
        return true;
    }

    /**
     * 验证用户名是否存在
     */
    private boolean TestUsernameExist(String username) {
        return true;
    }

    /**
     * 验证密码格式是否正确
     */
    private boolean TestPassword(String pwd) {
        return true;
    }

    /**
     * 验证密码和再次输入的密码是否相同
     */
    private boolean TestPasswordSame(String pwd, String repwd) {
        return true;
    }

    /**
     * 验证邮箱格式是否正确
     */
    private boolean TestEmail(String email) {
        return true;
    }

}
