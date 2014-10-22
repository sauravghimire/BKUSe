package com.example.saurav.bkuse;

/**
 * Created by Saurav on 10/22/2014.
 */
public class CustomData {
    private int mBackgroundColor;
    private String mText;

    public CustomData(int backgroundColor, String text) {
        mBackgroundColor = backgroundColor;
        mText = text;
    }

    /**
     * @return the backgroundColor
     */
    public int getBackgroundColor() {
        return mBackgroundColor;
    }

    /**
     * @return the text
     */
    public String getText() {
        return mText;
    }
}