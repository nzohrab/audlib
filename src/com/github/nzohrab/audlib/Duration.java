package com.github.nzohrab.audlib;

public class Duration {
    private int sampleRate;
    public Duration(int sampleRate) {
        this.sampleRate = sampleRate;
    }

    public int ofSamples(int numberOfSamples) {
        return numberOfSamples;
    }

    public int ofSeconds(int numberOfSeconds) {
        return sampleRate * numberOfSeconds;
    }

    public int ofMillis(int numberOfMillis) {
        return sampleRate * numberOfMillis / 1000;
    }

    public int ofMinutes(int numberOfMinutes) {
        return 60 * sampleRate * numberOfMinutes;
    }
}
