package com.example;

/**
 * Java class that provides random jokes.
 */
public class Jokester {

    public String getJoke(){
        return Constants.JOKES[ (int) (Math.random() * Constants.JOKE_COUNT)];
    }
}
