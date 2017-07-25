package cn.ink.music_7.utils;


import net.sf.json.JSONObject;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.GetMethod;

import java.io.InputStream;

import cn.ink.music_7.pojo.SongInfo;


/**
 * @author Heven
 */
public class GetSongInfoImpl implements GetSongInfo {

    @Override
    public SongInfo SongsURL(String fileHash) throws Exception {
        SongInfo songInfo = new SongInfo();

//      http://www.kugou.com/yy/index.php?r=play/getdata&hash=34A3007B3A279D78372CA182171A32CE
        String songsURL = "http://www.kugou.com/yy/index.php?r=play/getdata&hash=" + fileHash + "";
        String resultJson = null;
        HttpClient client = new HttpClient();
        HttpMethod method = new GetMethod(songsURL);
        client.executeMethod(method);
        int status = method.getStatusLine().getStatusCode();
        if (status == HttpStatus.SC_OK) {
            InputStream in = method.getResponseBodyAsStream();
            resultJson = StreanUtil.readStream(in);
        }
        //================
        String buf = resultJson.toString();
        int index1 = buf.indexOf("{");
        int index11 = buf.indexOf("{", index1 + 1);
        int index2 = buf.lastIndexOf("}");
        String jsonString = buf.substring(index11, index2);
        JSONObject json = JSONObject.fromObject(jsonString);

        //------获取歌曲名-------
        String song_name = json.getString("song_name");//包含了歌曲名和专辑名
        //解析歌曲名
        songInfo.setSongName(song_name);
        //------获取歌手名-------
        String singer = json.getString("author_name");
        songInfo.setSinger(singer);
        //------获取专辑名-------
        String Album = json.getString("album_name");
        songInfo.setAlbum(Album);
        //------获取歌词-------
        String lyrics = json.getString("lyrics");
        songInfo.setLyrics(lyrics);
        //------获取图片地址url-------
        String img = json.getString("img");
        songInfo.setImg(img);
        //------获取播放地址url-------
        String play_url = json.getString("play_url");
        songInfo.setPlay_url(play_url);
//        System.out.println("=======play_url===========play_url=========" + play_url);
        return songInfo;
    }
}
