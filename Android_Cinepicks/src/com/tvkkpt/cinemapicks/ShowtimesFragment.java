package com.tvkkpt.cinemapicks;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.tvkkpt.cinemapicks.adapters.ShowtimeGridAdapter;
import com.tvkkpt.cinemapicks.models.CinemaItemModel;
import com.tvkkpt.cinemapicks.models.MovieItemModel;
import com.tvkkpt.cinemapicks.models.ViewModel;
import com.tvkkpt.cinemapicks.utils.ExpandableHeightGridView;
import com.tvkkpt.cinemapicks.utils.FontUtils;
import com.tvkkpt.cinemapicks.utils.LogUtils;
import com.tvkkpt.cinemapicks.utils.RobotoFontFragment;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Copyright © Smart Cows Inc. All rights reserved.
 * Author: tvkkpt
 * Date: 12/26/12
 * Time: 9:39 PM
 *
 */
public class ShowtimesFragment extends RobotoFontFragment implements AdapterView.OnItemSelectedListener {

    private MovieItemModel movie;
    private List<CinemaItemModel> cinemas;

    private LayoutInflater inflater;
    private Activity activity;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (container == null) {
            return null;
        }

        int movieType = getArguments().getInt("movie_type");
        int moviePosition = getArguments().getInt("movie_position");
        movie = ViewModel.getInstance().getMovieListByType(movieType).get(moviePosition);

        this.inflater = inflater;
        this.activity = getActivity();
        View view = inflater.inflate(R.layout.fragment_showtimes, container, false);

        Spinner spinner = (Spinner)view.findViewById(R.id.spinner);
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(activity,
                android.R.layout.simple_spinner_item, ViewModel.getInstance().getCinemaTitles());
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(dataAdapter);
        spinner.setOnItemSelectedListener(this);

        cinemas = ViewModel.getInstance().getCinemaItems();

        return view;
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        CinemaItemModel cinema = cinemas.get(i);
        TextView address = (TextView)activity.findViewById(R.id.cinema_address);
        TextView phone = (TextView)activity.findViewById(R.id.cinema_phone);

        address.setText("Địa chỉ: " + cinema.getCinemaAddress());
        phone.setText("Điện thoại: " + cinema.getCinemaPhone());

        // url to get session times
        String GET_SESSION_TIMES_URL = "http://megastar.vn/megastarXMLData.aspx?RequestType=GetSessionTimes";
        String url = GET_SESSION_TIMES_URL + "&&MovieName=" + movie.getMovieNameVar() + "&&CinemaID="
                + cinema.getCinemaId();
        new GetSessionTimesTask().execute(url);
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    private class GetSessionTimesTask extends AsyncTask<String, Integer, String> {

        private class DateSession {
            private String date;
            private List<String> sessions;

            public String getDate() {
                return date;
            }

            public void setDate(String date) {
                this.date = date;
            }

            public List<String> getSessions() {
                return sessions;
            }

            public void setSessions(List<String> sessions) {
                this.sessions = sessions;
            }
        }

        private ArrayList<DateSession> dateSessions;
        private ViewGroup showtimesContainer;
        private ProgressBar progressBar;
        private TextView textResult;

        @Override
        protected void onPreExecute() {
            showtimesContainer = (ViewGroup)activity.findViewById(R.id.showtimes_container);
            showtimesContainer.removeAllViews();
            progressBar = (ProgressBar)activity.findViewById(R.id.progressBar);
            progressBar.setVisibility(ProgressBar.VISIBLE);
            textResult = (TextView)activity.findViewById(R.id.textResult);
            textResult.setVisibility(TextView.GONE);
        }

        @Override
        protected String doInBackground(String... params) {
            String tag = "cpicks";
            String url = params[0];
            final Pattern pattern =  Pattern.compile("<a[^>]*>(.*?)</a>", Pattern.DOTALL | Pattern.CASE_INSENSITIVE);

            dateSessions = new ArrayList<DateSession>();

            try {
                InputStream in = new URL(url).openConnection().getInputStream();

                DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
                Document doc = builder.parse(in);
                XPath xPath = XPathFactory.newInstance().newXPath();

                NodeList dates = (NodeList)xPath.evaluate("/movies/date", doc, XPathConstants.NODESET);
                if (dates.getLength() == 0) return "no showtime";

                for (int i = 0; i < dates.getLength(); i++) {
                    Element d = (Element)dates.item(i);
                    String dateStr = d.getAttribute("name");

                    DateSession ds = new DateSession();
                    ds.setDate(dateStr);

                    List<String> ss = new ArrayList<String>();
                    NodeList times = (NodeList)xPath.evaluate("time", d, XPathConstants.NODESET);
                    for (int j = 0; j < times.getLength(); j++) {
                        Element t = (Element)times.item(j);
                        String timeStr = t.getElementsByTagName("value").item(0).getTextContent();
                        Matcher matcher = pattern.matcher(timeStr);
                        if (matcher.find()) {
                            ss.add(matcher.group(1));
                        }
                    }
                    ds.setSessions(ss);

                    dateSessions.add(ds);
                }

            } catch (MalformedURLException e) {
                Log.e(tag, LogUtils.getStackTrace(e));
                Log.d(tag, "wrong url syntax");
                return "wrong url syntax";
            } catch (IOException e) {
                Log.e(tag, LogUtils.getStackTrace(e));
                Log.d(tag, "io exception");
                return "io exception";
            } catch (ParserConfigurationException e) {
                Log.e(tag, LogUtils.getStackTrace(e));
                Log.d(tag, "parse xml exception");
                return "parse xml exception";
            } catch (SAXException e) {
                Log.e(tag, LogUtils.getStackTrace(e));
                Log.d(tag, "sax exception");
                return "sax exception";
            } catch (XPathExpressionException e) {
                Log.e(tag, LogUtils.getStackTrace(e));
                Log.d(tag, "xpath expression exception");
                return "xpath exception";
            }

            return "successful";
        }

        @Override
        protected void onPostExecute(String result) {
            if (result.equals("successful")) {
                for (DateSession ds : dateSessions) {
                    View v = inflater.inflate(R.layout.showtime_el, null);

                    TextView dateTitle = (TextView)v.findViewById(R.id.date_str);
                    dateTitle.setText(ds.getDate());
                    FontUtils.setRobotoFont(activity, dateTitle);

                    ExpandableHeightGridView grid = (ExpandableHeightGridView)v.findViewById(R.id.showtimes_grid);
                    grid.setAdapter(new ShowtimeGridAdapter(activity, ds.getSessions()));
                    grid.setExpanded(true);

                    showtimesContainer.addView(v);
                }
            } else if (result.equals("no showtime")) {
                textResult.setText(getString(R.string.no_showtime_result));
                textResult.setVisibility(TextView.VISIBLE);
            } else {
                textResult.setText(getString(R.string.get_showtime_error));
                textResult.setVisibility(TextView.VISIBLE);
            }

            progressBar.setVisibility(ProgressBar.GONE);
        }
    }
}
