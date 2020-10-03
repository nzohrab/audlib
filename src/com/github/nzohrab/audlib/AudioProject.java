package com.github.nzohrab.audlib;

public class AudioProject {
    private int sampleRate;
    private int bitDepth;

    public ClipGenerator generate;
    public Effects effects;
    public Duration duration;
    public AudioProject(int sampleRate, int bitDepth) {
        this.sampleRate = sampleRate;
        this.bitDepth = bitDepth;
        generate = new ClipGenerator(sampleRate, bitDepth);
        effects = new Effects(sampleRate, bitDepth);
        duration = new Duration(sampleRate);
    }

    public Track addTrack() {
        return new Track(sampleRate, bitDepth);
    }
}