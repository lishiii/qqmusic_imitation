package cn.ink.music_7.service;

public interface Iservice {
    //把想暴露的方法都定义在接口中
    public void callPlayMusic(String songUrl);

    public void callPauseMusic();

    public void callRePlayMusic();

    public void callSeekTo(int position);

}
