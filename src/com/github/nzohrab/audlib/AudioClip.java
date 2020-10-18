package com.github.nzohrab.audlib;

import java.util.ArrayList;
import java.util.List;

public class AudioClip {
    private List<Double> values;
    public AudioClip(List<Double> values) {
        this.values = values;
    }
    public AudioClip() {
        this.values = new ArrayList<Double>();
    }

    public List<Double> getValues() {
        return values;
    }


}