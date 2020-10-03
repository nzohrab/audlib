package com.github.nzohrab.audlib;

import java.util.List;

public class AudioClip {
    private List<Double> values;
    public AudioClip(List<Double> values) {
        this.values = values;
    }

    public List<Double> getValues() {
        return values;
    }


}