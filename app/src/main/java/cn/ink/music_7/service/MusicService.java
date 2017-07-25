package cn.ink.music_7.service;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Message;

import java.util.Timer;
import java.util.TimerTask;

import cn.ink.music_7.FindActivity;

public class MusicService extends Service {

    private MediaPlayer mediaPlayer;

    @Override
    public IBinder onBind(Intent intent) {
        return new MyBinder();
    }

    @Override
    public void onCreate() {
        //  初始化mediaplayer
        mediaPlayer = new MediaPlayer();
        super.onCreate();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    //实现指定播放器位置
    public void seekTo(int position) {
        mediaPlayer.seekTo(position);
    }

    //播放音乐的方法
    public void playMusic(String songUrl) {
        System.out.println("============11111===================" + songUrl);
        try {
            mediaPlayer.reset();
            if (songUrl == "error") {
                songUrl = "http://192.168.10.103:8080/music.mp3";
            }
            System.out.println("=========22222======================" + songUrl);
            mediaPlayer.setDataSource(songUrl);
            mediaPlayer.prepare();
            mediaPlayer.start();
            //更新进度条
            updateSeekBar();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //更新进度条的方法
    private void updateSeekBar() {

        //[1]获取到当前播放的总长度
        final int duration = mediaPlayer.getDuration();

        //[2]使用Timer 定时器去定时获取当前进度
        final Timer timer = new Timer();
        final TimerTask task = new TimerTask() {

            @Override
            public void run() {
                //[3]一秒钟获取一次当前进度
                int currentPosition = mediaPlayer.getCurrentPosition();

                //[4]拿着我们在MainActivity 创建的handler 发消息 消息就可以携带数据

                Message msg = Message.obtain();
                Bundle bundle = new Bundle(); //map
                bundle.putInt("duration", duration);
                bundle.putInt("currentPosition", currentPosition);
                msg.setData(bundle);
                //发送一条消息  mainActivity里面的handlemessage方法就会执行
                FindActivity.handler.sendMessage(msg);

            }
        };
        //100 毫秒后 每隔1秒执行一次run方法
        timer.schedule(task, 100, 1000);
        //当歌曲执行完毕后 把timer 和 timertask取消
        //设置播放完成的监听

        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {

            @Override
            public void onCompletion(MediaPlayer mp) {
                //把timer 和 timertast 取消
                timer.cancel();
                task.cancel();
            }
        });
    }

    // 暂停音乐的方法
    public void pauseMusic() {
        //暂停音乐
        mediaPlayer.pause();
    }

    // 继续播放音乐的方法
    public void rePlayMusic() {
        mediaPlayer.start();
    }

    private class MyBinder extends Binder implements Iservice {

        //调用播放音乐的方法
        @Override
        public void callPlayMusic(String songUrl) {

            playMusic(songUrl);
        }

        //调用暂停音乐的方法
        @Override
        public void callPauseMusic() {

            pauseMusic();
        }

        @Override
        public void callRePlayMusic() {
            rePlayMusic();
        }

        //调用继续播放音乐的方法
//        @Override
//        public void callRePlayMusic() {
//            rePlayMusic();
//        }

        @Override
        public void callSeekTo(int position) {
            seekTo(position);
        }
    }
}
