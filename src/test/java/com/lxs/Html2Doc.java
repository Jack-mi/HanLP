package com.lxs;
import java.io.IOException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Html2Doc {

    public static String Get_Content(String url) {
        StringBuffer content = new StringBuffer();
        try {
            Document doc = Jsoup.connect(url).get();
            Elements body = doc.getElementsByTag("body");
            for (Element t1 : body) {
                String text = t1.text();
                for(int i=1;i<text.length();i++) {
                    char cur = text.charAt(i);
                    content.append(cur);
                }
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        // 确定text的范围
        String Content = new String(content);
        int start = Content.indexOf("供稿")+7;
        if (Content.charAt(start) != ' ')
            start += 4;
        int end = Content.indexOf("Expires");
        Content = Content.substring(start, end);
        return Content;
    }

    public static void main(String[] args) {
        String url ="http://sharepoint/Pressroom/Lists/Announcements/DispForm.aspx?ID=881&Source=http%3A%2F%2Fsharepoint%2FPressroom%2FLists%2FAnnouncements%2FAllItems%2Easpx";
        Get_Content(url);
    }
}