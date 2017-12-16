package com.karen.asynctask;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by karen on 12/14/17.
 */

public class ParseApplications {
    private static final String TAG = "ParseApplications";

    private List<FeedEntry> applications;

    public ParseApplications() {
        this.applications = new ArrayList<>();
    }

    public List<FeedEntry> getApplications() {
        return applications;
    }

    public boolean parse(String xmlData) {
        boolean status = true;
        FeedEntry currentRecord;
        boolean isEntry = false;
        String textValue = "";

        try {
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            factory.setNamespaceAware(true);
            XmlPullParser xpp = factory.newPullParser();
            xpp.setInput(new StringReader(xmlData));
            int eventType = xpp.getEventType();

            while (eventType != XmlPullParser.END_DOCUMENT) {

            }
        } catch (Exception e) {
            status = false;
            e.printStackTrace();
        }

        return status;
    }
}
