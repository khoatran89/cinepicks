package com.tvkkpt.cinemapicks.models;

import android.util.Log;
import com.tvkkpt.cinemapicks.utils.LogUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class ViewModel {
    /// Singleton pattern

    private static ViewModel instance;

    public static ViewModel getInstance() {
        if (instance == null) {
            instance = new ViewModel();
        }
        return instance;
    }

    private ViewModel() {
        setNowShowingItems(new ArrayList<MovieItemModel>());
        setUpcomingItems(new ArrayList<MovieItemModel>());
        setCinemaItems(new ArrayList<CinemaItemModel>());
    }

    // Actual class definition
    public static final int NOW_SHOWING_MOVIES = 1;
    public static final int UPCOMING_MOVIES = 2;

    private List<MovieItemModel> nowShowingItems;
    private List<MovieItemModel> upcomingItems;
    private List<CinemaItemModel> cinemaItems;

    public List<MovieItemModel> getNowShowingItems() {
        return nowShowingItems;
    }

    protected void setNowShowingItems(List<MovieItemModel> nowShowingItems) {
        this.nowShowingItems = nowShowingItems;
    }

    public List<MovieItemModel> getUpcomingItems() {
        return upcomingItems;
    }

    protected void setUpcomingItems(List<MovieItemModel> upcomingItems) {
        this.upcomingItems = upcomingItems;
    }

    public List<CinemaItemModel> getCinemaItems() {
        return cinemaItems;
    }

    public List<String> getCinemaTitles() {
        List<String> list = new ArrayList<String>();

        for (CinemaItemModel item : cinemaItems) {
            list.add(item.getCinemaName());
        }

        return list;
    }

    protected void setCinemaItems(List<CinemaItemModel> cinemaItems) {
        this.cinemaItems = cinemaItems;
    }

    private boolean isDataLoaded;

    public boolean isDataLoaded() {
        return isDataLoaded;
    }

    protected void setDataLoaded(boolean isDataLoaded) {
        this.isDataLoaded = isDataLoaded;
    }

    public List<MovieItemModel> getMovieListByType(int type) {
        switch (type) {
            case NOW_SHOWING_MOVIES:
                return getNowShowingItems();
            case UPCOMING_MOVIES:
                return getUpcomingItems();
            default:
                return null;
        }
    }

    private static String BASE_URL = "http://www.megastar.vn";
    private static String NOW_SHOWING_URL = "http://www.megastar.vn/megastarXMLData.aspx?RequestType=GetMovieListByMode&&visMode=NowShowing&&visLang=1";
    private static String UPCOMING_URL = "http://www.megastar.vn/megastarXMLData.aspx?RequestType=GetMovieListByMode&&visMode=ComingSoon&&visLang=1";
    private static String CINEMA_URL = "http://www.megastar.vn/megastarXMLData.aspx?RequestType=GetCinemaList&&visLang=1";

    public void loadData() {
        boolean loaded;
        setDataLoaded(false);

        loaded = this.updateMovieList(NOW_SHOWING_URL, nowShowingItems);
        loaded = loaded && this.updateMovieList(UPCOMING_URL, upcomingItems);
        loaded = loaded && this.updateCinemaList(CINEMA_URL, cinemaItems);

        setDataLoaded(loaded);
    }

    private boolean updateMovieList(String url, List<MovieItemModel> list) {
        String tag = "cpicks";

        try {
            Log.d(tag, "begin update movie");
            InputStream in = new URL(url).openConnection().getInputStream();
            Log.d(tag, "got input stream");

            DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document doc = builder.parse(in);
            Log.d(tag, "parsed document successfully");

            NodeList movies = doc.getElementsByTagName("movie");
            for (int i = 0; i < movies.getLength(); i++) {
                MovieItemModel mim = new MovieItemModel();
                Element movie = (Element) movies.item(i);

                mim.setMovieId(movie.getElementsByTagName("MovieId").item(0).getTextContent());
                mim.setMovieNameVar(movie.getElementsByTagName("MovieNameVar").item(0).getTextContent());
                mim.setMovieTitle(movie.getElementsByTagName("MovieName").item(0).getTextContent());
                mim.setMovieShortTitle(movie.getElementsByTagName("MovieNameShort").item(0).getTextContent());
                mim.setThumbUrl(BASE_URL + movie.getElementsByTagName("ImageUrlLargeNew").item(0).getTextContent());
                mim.setMovieDescription(movie.getElementsByTagName("ShortDesc").item(0).getTextContent());
                mim.setVideoUrl(movie.getElementsByTagName("YoutubeUrl").item(0).getTextContent());
                mim.setDirector("Đạo diễn: " + movie.getElementsByTagName("Director").item(0).getTextContent());
                mim.setCasting("Diễn viên: " + movie.getElementsByTagName("Cast").item(0).getTextContent());
                mim.setGenre("Thể loại: " + movie.getElementsByTagName("Genre").item(0).getTextContent());
                mim.setDuration("Thời lượng: " + movie.getElementsByTagName("RunningTime").item(0).getTextContent());

                if (movie.getElementsByTagName("ReleaseDate").getLength() > 0) {
                    mim.setReleaseDate("Khởi chiếu: " + movie.getElementsByTagName("ReleaseDate").item(0).getTextContent()
                            .replace("<br>", " ").replace("<br/>", " "));
                }

                list.add(mim);
            }

            return true;

        } catch (MalformedURLException e) {
            Log.e(tag, LogUtils.getStackTrace(e));
            Log.d(tag, "wrong url syntax");
            return false;
        } catch (IOException e) {
            Log.e(tag, LogUtils.getStackTrace(e));
            Log.d(tag, "io exception");
            return false;
        } catch (ParserConfigurationException e) {
            Log.e(tag, LogUtils.getStackTrace(e));
            Log.d(tag, "parse xml exception");
            return false;
        } catch (SAXException e) {
            Log.e(tag, LogUtils.getStackTrace(e));
            Log.d(tag, "sax exception");
            return false;
        }

    }

    private boolean updateCinemaList(String url, List<CinemaItemModel> list) {
        String tag = "cpicks";

        try {
            Log.d(tag, "begin update cinema");
            InputStream in = new URL(url).openConnection().getInputStream();
            Log.d(tag, "got input stream");

            DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document doc = builder.parse(in);
            Log.d(tag, "parsed document successfully");

            NodeList cinemas = doc.getElementsByTagName("Cinema");
            for (int i = 0; i < cinemas.getLength(); i++) {
                CinemaItemModel item = new CinemaItemModel();
                Element c = (Element)cinemas.item(i);

                item.setCinemaId(c.getElementsByTagName("Cinema_strID").item(0).getTextContent());
                item.setCinemaName(c.getElementsByTagName("Cinema_strDisplayName").item(0).getTextContent());
                item.setCinemaPhone(c.getElementsByTagName("Cinema_strPhone").item(0).getTextContent());

                String address = c.getElementsByTagName("Cinema_strAddress").item(0).getTextContent();
                if (address.startsWith("<br/>")) address = address.substring(5);
                item.setCinemaAddress(address.replace("<br/>", ", ").replace(",,", ","));

                list.add(item);
            }

            return true;

        } catch (MalformedURLException e) {
            Log.e(tag, LogUtils.getStackTrace(e));
            Log.d(tag, "wrong url syntax");
            return false;
        } catch (IOException e) {
            Log.e(tag, LogUtils.getStackTrace(e));
            Log.d(tag, "io exception");
            return false;
        } catch (ParserConfigurationException e) {
            Log.e(tag, LogUtils.getStackTrace(e));
            Log.d(tag, "parse xml exception");
            return false;
        } catch (SAXException e) {
            Log.e(tag, LogUtils.getStackTrace(e));
            Log.d(tag, "sax exception");
            return false;
        }
    }

}
