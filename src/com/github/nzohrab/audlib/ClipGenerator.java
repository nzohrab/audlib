package com.github.nzohrab.audlib;
import java.util.ArrayList;
import java.util.List;

public class ClipGenerator {
    private int sampleRate;
    private int bitDepth;

    ClipGenerator(int sampleRate, int bitDepth) {
        this.sampleRate = sampleRate;
        this.bitDepth = bitDepth;
    }

    public AudioClip tone(int freq, int length) {
        List<Double> values = new ArrayList<Double>();
        for(int i = 0; i < sampleRate * length; i++) {
            values.add(Math.sin(2 * Math.PI * i / (sampleRate/freq)));
        }

        return new AudioClip(values);
    }


    public AudioClip chirp(int freq1, int freq2, int numberOfSamples) {
        List<Double> values = new ArrayList<Double>();
        double c = (freq2-freq1)/(numberOfSamples / sampleRate);
        double dt = 1.0 / (sampleRate);
        for(int i = 0; i < numberOfSamples; i++) {
            double t = i*dt;
            values.add(Math.sin(2*Math.PI*((c*t/2) + freq1)*t));
        }

        return new AudioClip(values);
    }

    public AudioClip logChirp(int freq1, int freq2, int numberOfSamples) {
        List<Double> values = new ArrayList<Double>();
        double dt = 1.0 / (sampleRate);
        double k = Math.pow((double)freq2/(double)freq1, (1.0/(numberOfSamples / sampleRate)));
        for(int i = 0; i < numberOfSamples; i++) {
            double t = i*dt;

            values.add(Math.sin(2*Math.PI*freq1* ((Math.pow(k,t) - 1)/Math.log(k))));
        }

        return new AudioClip(values);
    }

    public AudioClip silence(int numberOfSamples) {
        List<Double> values = new ArrayList<Double>();
        for(int i = 0; i < numberOfSamples; i++) {
            values.add(0d);
        }

        return new AudioClip(values);
    }
}