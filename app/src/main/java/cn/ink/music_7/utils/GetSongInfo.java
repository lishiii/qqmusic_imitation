package cn.ink.music_7.utils;


import cn.ink.music_7.pojo.SongInfo;

public interface GetSongInfo {

    public SongInfo SongsURL(String fileHash) throws Exception;
}
