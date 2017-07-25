package cn.ink.music_7.utils;


import java.io.ByteArrayOutputStream;
import java.io.InputStream;

public class StreanUtil {
    public static String readStream(InputStream in) throws Exception {

        // 定义一个内存输出流
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        int len = -1;
        byte[] buffer = new byte[1024];
        while ((len = in.read(buffer)) != -1) {
            baos.write(buffer, 0, len);
        }
        in.close();
        String content = new String(baos.toByteArray(),"utf-8");
        return content;
    }
}
