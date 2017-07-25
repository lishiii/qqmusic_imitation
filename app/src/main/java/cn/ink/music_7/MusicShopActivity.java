package cn.ink.music_7;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class MusicShopActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music_shop);
    }

    public void more(View view){
        Intent intent =new Intent(this,MoreActivity.class);
        startActivity(intent);
    }
    public void my(View view){
        Intent intent =new Intent(this,HomeActivity.class);
        startActivity(intent);
    }
    public void find(View view){
        Intent intent =new Intent(this,FindActivity.class);
        startActivity(intent);
    }
}
