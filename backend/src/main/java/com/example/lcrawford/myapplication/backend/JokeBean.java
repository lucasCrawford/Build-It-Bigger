package com.example.lcrawford.myapplication.backend;

import com.example.Jokester;

/**
 * The object model for the data we are sending through endpoints
 */
public class JokeBean {
    private String mJoke;

    public String getJoke() {
        Jokester jokester = new Jokester();
        return jokester.getJoke();
    }

    public void setJoke(String data) {
        mJoke = data;
    }
}