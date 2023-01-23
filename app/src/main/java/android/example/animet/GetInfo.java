package android.example.animet;

import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.jsoup.select.Selector;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GetInfo {

    String Title;

    public List<Anime> fAnimeList = new ArrayList<>();


//TODO: Write function to get episode list



    public List<Anime> GetLatestAnimeEpisodes(String url)
    {

       /* Document doc = null;
        try {
            doc = Jsoup.connect("https://anitube.cz/").get();
        } catch (IOException e) {
            e.printStackTrace();
        }*/
        Document doc = Jsoup.parse(url);
        doc.setBaseUri("https://anitube.cx/");
        List<Anime> aniList = new ArrayList<>();

        Elements anime =doc.select("div.col-sm-6.col-md-4.col-lg-4>div");


            for (Element ani : anime) {

                String anime_url = "", image_url = "", title = "";
                if (ani.className().equals("well well-sm")) {
                    anime_url = ani.child(0).attr("abs:href");
                    image_url = ani.child(0).child(0).child(0).attr("src");
                    title = ani.child(0).child(0).child(0).attr("title");
                }
                aniList.add(new Anime(title, image_url, anime_url));

            }
            return aniList;


    }
    public List<Anime> GetAnime(String url)
    {

       /* Document doc = null;
        try {
            doc = Jsoup.connect("https://anitube.cz/").get();
        } catch (IOException e) {
            e.printStackTrace();
        }*/
        Document doc = Jsoup.parse(url);
        doc.setBaseUri("https://anitube.cx/");
        List<Anime> aniList = new ArrayList<>();
        Elements anime =doc.select("div[class=imagePlace]");

        for (Element ani : anime) {

            String anime_url = "", image_url = "", title = "";

            anime_url = ani.child(0).attr("abs:href");
            image_url = ani.child(0).child(0).attr("src");
            title = ani.child(0).child(0).attr("alt");



            aniList.add(new Anime(title, image_url, anime_url));

        }
        return aniList;
    }




    public int GetLastPageNumber(String url)
    {
        int value = 0;
        String string;
        String name = "";
        Document doc = Jsoup.parse(url);
        Elements anime =doc.select("div[class=hidden-xs center m-b--15]");



        for(Element an : anime)
        {
            name = an.child(0).children().last().child(0).attr("href");
        }

        string = name.substring(name.lastIndexOf("/"));
        value = Integer.parseInt(string.replaceAll("[\\D]", ""));


        return value;
    }

    public List<Anime> GetAllAnime()
    {
        List<Anime> aniList = new ArrayList<>();
        String url = "https://anitube.cz/anime/page/";

        int i = 1;
        Document doc = null;
        while(true)
        {
            try {
                doc = Jsoup.connect(url + i).get();

            } catch (IOException e) {
                e.printStackTrace();
            }

            doc.setBaseUri("https://anitube.cz/");

            if (!(doc.select("div.col-lg-3.col-md-4.col-sm-6.col-xs-12>div").size()>1))
                break;

            Elements anime = doc.select("div[class=imagePlace]");

            for (Element ani : anime)
            {

                String anime_url = "", image_url = "", title = "";

                anime_url = ani.child(0).attr("abs:href");
                image_url = ani.child(0).child(0).attr("src");
                title = ani.child(0).child(0).attr("alt");


                aniList.add(new Anime(title, image_url, anime_url));

            }
            i++;
        }
        return aniList;


    }


    public List<String> GetVideoUrls(String url)
    {
        String vidurl = "";
        List<String> javascp = new ArrayList<>();
       Document doc = Jsoup.parse(url);
       Elements elementscript = doc.getElementsByTag("script");

for(Element ele : elementscript)
{

        if(ele.data().contains("var urlasdf"))
        {
           vidurl = ele.html();
           Log.d("Whatsup", vidurl);
        }

}

        String regex = "(?!.*(?:\\.jpe?g|\\.gif|\\.png))\\b(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]";
        Pattern p = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
        Matcher m = p.matcher(vidurl);
        while(m.find()) {
            String urlStr = m.group();

            if (urlStr.contains("server"))
            {
                Log.d("serverurl", urlStr);
                javascp.add(urlStr);
            }

        }

            return javascp;
    }



}
