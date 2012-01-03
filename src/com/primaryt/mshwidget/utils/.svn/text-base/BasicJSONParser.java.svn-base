
package com.primaryt.mshwidget.utils;

import java.io.InputStream;

import org.apache.http.protocol.HTTP;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

public abstract class BasicJSONParser {

    protected XmlPullParser parser;

    public BasicJSONParser(InputStream stream) {
        try {
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            parser = factory.newPullParser();
            parser.setInput(stream, HTTP.UTF_8);
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        }
    }
    
    protected abstract Object parse();
}
