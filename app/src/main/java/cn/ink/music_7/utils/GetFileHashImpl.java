package cn.ink.music_7.utils;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.GetMethod;

import java.io.InputStream;

/**
 * @author heven
 */
public class GetFileHashImpl implements GetFileHash {

    @Override
    public String FileHash(String starName) throws Exception {
        //http://songsearch.kugou.com/song_search_v2?callback=jQuery191034642999175022426_1489023388639&keyword={seve}&page=1&pagesize=30&userid=-1&clientver=&platform=WebFilter&tag=em&filter=2&iscorrection=1&privilege_filter=0&_=1489023388641
        String jsonURL = "http://songsearch.kugou.com/song_search_v2?"
                + "callback=jQuery191034642999175022426_1489023388639"
                + "&keyword={" + starName + "}&page=1&pagesize=30&userid=-1"
                + "&clientver=&platform=WebFilter&tag=em&filter=2"
                + "&iscorrection=1&privilege_filter=0&_=1489023388641";

        String contentbuf = null;
        String fileHash = null;
        HttpClient client = new HttpClient();
        HttpMethod method = new GetMethod(jsonURL);
        client.executeMethod(method);
        int status = method.getStatusLine().getStatusCode();
        if (status == HttpStatus.SC_OK) {
            InputStream in = method.getResponseBodyAsStream();
            contentbuf = StreanUtil.readStream(in);
        }
        String buf = contentbuf.toString();
        int index1 = buf.indexOf("{");
        int index11 = buf.indexOf("{", index1 + 1);

        int index2 = buf.lastIndexOf("}");
        // 得到json字符串
        String jsonString = buf.substring(index11, index2);
        JSONObject json = JSONObject.fromObject(jsonString);
        JSONArray result = json.getJSONArray("lists");
        for (int i = 0; i < result.size(); i++) {
            fileHash = result.getJSONObject(i).getString("FileHash");
            System.out.println("FileHash:" + fileHash);
            //找到第一个FileHash
            break;
            //--------------------------
//			JSONArray Grp = result.getJSONObject(i).getJSONArray("Grp");
//			for (int j = 0; j < Grp.size(); j++) {
//				String FileHash = Grp.getJSONObject(j).getString("FileHash");
//				System.out.println("FileHash:" + FileHash);
//			}
        }
        return fileHash;
    }
}
