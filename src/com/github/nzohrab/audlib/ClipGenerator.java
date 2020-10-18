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

    public AudioClip tone(double freq, int numberOfSamples) {
        List<Double> values = new ArrayList<Double>();
        for(int i = 0; i < numberOfSamples; i++) {
            values.add(Math.sin(2 * Math.PI * i / (sampleRate/freq)));
        }
        return new AudioClip(values);
    }

    public AudioClip note(int note, int octave, int numberOfSamples) {
        if(note < 1 || note > 12) {
            throw new IllegalArgumentException("Note must be 1 and 12 inclusive");
        }
        if(octave < 0 || octave > 8) {
            throw new IllegalArgumentException("Octave must be 0 and 8 inclusive");
        }

        double f_zero = 16.352; //C0
        int halfStepsAway = (octave * 12) + note - 1;

        double freq = f_zero * Math.pow(2.0, halfStepsAway/12.0);
        System.out.println(freq);
        return tone(freq, numberOfSamples);
    }

    public AudioClip chirp(double freq1, double freq2, int numberOfSamples) {
        List<Double> values = new ArrayList<Double>();
        double lengthInSeconds = (double)numberOfSamples / (double)sampleRate;
        double c = (freq2-freq1)/lengthInSeconds;
        double dt = 1.0 / (sampleRate);
        for(int i = 0; i < numberOfSamples; i++) {
            double t = i*dt;
            values.add(Math.sin(2*Math.PI*((c*t/2) + freq1)*t));
        }
        return new AudioClip(values);
    }

    public AudioClip logChirp(double freq1, double freq2, int numberOfSamples) {
        List<Double> values = new ArrayList<Double>();
        double dt = 1.0 / (sampleRate);
        double lengthInSeconds = (double)numberOfSamples / (double)sampleRate;
        double k = Math.pow(freq2/freq1, (1.0/lengthInSeconds));
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