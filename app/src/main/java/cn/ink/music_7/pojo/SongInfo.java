package cn.ink.music_7.pojo;

public class SongInfo {
    //    private int id;  //自增长ID
    private String songName;         // 歌名
    private String singer;          //  歌手名
    private String album;          //   专辑名
    private String lyrics;        //    歌词
    private String img;          //     图片地址url
    private String play_url;    //      播放地址url

//    public int getId() {
//        return id;
//    }
//
//    public void setId(int id) {
//        this.id = id;
//    }

    public String getSongName() {
        return songName;
    }

    public void setSongName(String songName) {
        this.songName = songName;
    }

    public String getSinger() {
        return singer;
    }

    public void setSinger(String singer) {
        this.singer = singer;
    }

    public String getAlbum() {
        return album;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    public String getLyrics() {
        return lyrics;
    }

    public void setLyrics(String lyrics) {
        this.lyrics = lyrics;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getPlay_url() {
        return play_url;
    }

    public void setPlay_url(String play_url) {
        this.play_url = play_url;
    }

    @Override
    public String toString() {
        return "SongInfo{" +
                "songName='" + songName + '\'' +
                ", singer='" + singer + '\'' +
                ", album='" + album + '\'' +
                ", lyrics='" + lyrics + '\'' +
                ", img='" + img + '\'' +
                ", play_url='" + play_url + '\'' +
                '}';
    }
}
