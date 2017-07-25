package cn.ink.music_7;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

import cn.ink.music_7.pojo.SongInfo;
import cn.ink.music_7.service.Iservice;
import cn.ink.music_7.service.MusicService;
import cn.ink.music_7.utils.GetFileHashImpl;
import cn.ink.music_7.utils.GetSongInfoImpl;
import cn.ink.music_7.view.CircleImageView;

public class FindActivity extends AppCompatActivity {


    private static int SUCCESS = 1;
    private Iservice iservice; //这个是我们定义的中间人对象
    private MyConn conn;
    private EditText songName; //查询的歌曲名称或者其他
    private SongInfo songInfo;    //songInfo对象
    private String songsURL = "error";
    private TextView sing_name; //tv_歌曲名
    private TextView singerName;    //tv_歌手
    private ListView lv_songs; // listview 歌曲列表
    private CircleImageView roundImg; //图片
    private static SeekBar sbar; //进度条

    public static Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            //获取我们携带的数据
            Bundle data = msg.getData();
            //获取歌曲的总时长 和 当前进度
            int duration = data.getInt("duration");
            int currentPosition = data.getInt("currentPosition");

            //设置seekbar 的进度
            sbar.setMax(duration);
            sbar.setProgress(currentPosition);
        }

        ;
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find);
        Button button1 = (Button) findViewById(R.id.b1);
        lv_songs = (ListView) findViewById(R.id.lv_songs);
        lv_songs.setAdapter(new MyAdpter());

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(FindActivity.this, "这是歌手按钮", Toast.LENGTH_SHORT).show();
            }
        });
        Button button2 = (Button) findViewById(R.id.b2);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(FindActivity.this, "这是音乐圈", Toast.LENGTH_SHORT).show();
            }
        });
        Button button3 = (Button) findViewById(R.id.b3);
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(FindActivity.this, "这是歌词海报", Toast.LENGTH_SHORT).show();
            }
        });

        /**
         * 歌曲播放、进度条处理
         */
        sing_name = (TextView) findViewById(R.id.sing_name);
        singerName = (TextView) findViewById(R.id.singer);
        roundImg = (CircleImageView) findViewById(R.id.roundImg);


        //混合方式开启服务
        //[1] 先调用startservice 目的是可以保证服务在后台长期运行
        Intent intent = new Intent(this, MusicService.class);
        startService(intent);
        //[2]调用bindservice 目的为了获取我们定义的中间人对象  就可以间接的调用服务里面的方法了
        conn = new MyConn();
        bindService(intent, conn, BIND_AUTO_CREATE);

        //[3]找到seekbar   设置进度
        sbar = (SeekBar) findViewById(R.id.seekBar1);

        //[4]给seekbar设置监听事件
        sbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            //当拖动停止的时候调用
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                iservice.callSeekTo(seekBar.getProgress());

            }

            //当开始拖动
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            //当进度改变
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress,
                                          boolean fromUser) {

            }
        });
    }

    @Override
    protected void onDestroy() {
        //当Activity销毁的时候 解绑服务   目的是为了不报红色日志
        unbindService(conn);

        super.onDestroy();
    }


    // 点击按钮 播放音乐
    public void start_music(View v) {
        new Thread() {
            public void run() {
                try {
                    iservice.callRePlayMusic();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();

    }

    // 点击按钮 暂停音乐
    public void stop_music(View v) {

        //调用暂停音乐的方法
        iservice.callPauseMusic();
    }

//    // 点击按钮 继续播放音乐
//    public void click3(View v) {
//
//        //调用继续播放音乐的方法
//        iservice.callRePlayMusic();
//    }


    /*
     *  搜索歌曲btn
     */

    public void btn_search_music(View v) {

//        System.out.println("==========btn=======btn=============");
        new Thread() {
            public void run() {
                // 找到搜索歌曲名及其URL
                songName = (EditText) findViewById(R.id.et_search);
                String search_song = songName.getText().toString();
                String search_song_encode = URLEncoder.encode(search_song);
                GetFileHashImpl getFileHash = new GetFileHashImpl();
                GetSongInfoImpl getSongInfo = new GetSongInfoImpl();
                String fileHash = null;
                //--------------
                try {
                    fileHash = getFileHash.FileHash(search_song_encode);
                    if (fileHash != null && fileHash.length() > 0) {
                        songInfo = getSongInfo.SongsURL(fileHash);
                        songsURL = songInfo.getPlay_url();
//                        System.out.println("==========songsURL=======" + songsURL);
                        iservice.callPlayMusic(songsURL);
                        sing_name.setText(songInfo.getSongName());
                        // 设置添加布局信息
//                        if (songInfo != null) {
//                            Message msg = new Message();
//                            msg.what = SUCCESS;
//                            msg.obj = songInfo;
//                            handler2.sendMessage(msg);
//                            sing_name.setText(songInfo.getSongName());
//                            singerName.setText(songInfo.getSinger());
//                            String ImgUrl = songInfo.getImg();
//                            roundImg.setImageBitmap(returnBitMap(ImgUrl));
//                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    //监听服务的状态
    private class MyConn implements ServiceConnection {

        //当服务连接成功
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            //获取我们定义的中间人对象

            iservice = (Iservice) service;

        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }

    }


    /**
     * 网络图面获取
     *
     * @param url
     * @return
     */
    public Bitmap returnBitMap(String url) {

        URL myFileUrl = null;
        Bitmap bitmap = null;
        try {
            myFileUrl = new URL(url);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        try {
            HttpURLConnection conn = (HttpURLConnection) myFileUrl
                    .openConnection();
            conn.setDoInput(true);
            conn.connect();
            InputStream is = conn.getInputStream();
            bitmap = BitmapFactory.decodeStream(is);
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bitmap;
    }

    public void more(View view) {
        Intent intent = new Intent(this, MoreActivity.class);
        startActivity(intent);
    }

    public void my(View view) {
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
    }

    public void musicshop(View view) {
        Intent intent = new Intent(this, MusicShopActivity.class);
        startActivity(intent);
    }

    private class MyAdpter extends BaseAdapter {

        @Override
        public int getCount() {
            return 20;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view;
            if (convertView == null) {
                // [1]打气筒服务
//                view = View.inflate(MainActivity.this, R.layout.layout, null);
                // [2]打气筒服务
//                view = LayoutInflater.from(getApplicationContext()).inflate(R.layout.layout, null);
                // [3]打气筒服务
                LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
                view = inflater.inflate(R.layout.music_list,null);
            } else {
                view = convertView;
            }
            return view;
        }
    }

}
