package de.kanyuji.lobby.utils;

import java.util.*;

public class Dice {

    private Integer min;
    private Integer max;
    private Integer result;

    boolean running = false;

    public Dice(Integer min, Integer max) {
        this.min = min;
        this.max = max;
    }

    public Dice() { }

    public static Integer generateNumberBetween(Integer min, Integer max) {
        return (int) (Math.random()*(max-min+1)+min);
    }

    public Integer roll() {
        if(min == null || max == null) {
            throw new MissingResourceException("Missing max or min", "roll()", "");
        }
        if(min > max) {
            throw new IllegalArgumentException("Min can't be higher than the max value");
        }
        result = (int) (Math.random()*(max-min+1)+min);
        return result;
    }


    public Integer getResult() {
        if(result == null) {
            throw new NullPointerException("Dice hasn't been rolled yet");
        }
        return result;
    }

    public Dice setMin(Integer min) {
        this.min = min;
        return this;
    }

    public Dice setMax(Integer max) {
        this.max = max;
        return this;
    }
}