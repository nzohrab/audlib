package com.github.nzohrab.audlib;

import java.util.ArrayList;
import java.util.List;

public class Effects {
    private int sampleRate;
    private int bitDepth;

    Effects(int sampleRate, int bitDepth) {
        this.sampleRate = sampleRate;
        this.bitDepth = bitDepth;
    }

    private AudioClip joinTrack(Track track) {
        List<Double> values = new ArrayList<Double>();
        for(AudioClip c : track.getClips()) {
            for(Double s : c.getValues()) {
                values.add(s);
            }
        }
        return new AudioClip(values);
    }


    private List<AudioClip> joinTracks(Track... tracks) {
        List<AudioClip> joinedTracks = new ArrayList<AudioClip>();
        for(Track track : tracks) {
            joinedTracks.add(joinTrack(track));
        }
        return joinedTracks;
    }

    private AudioClip getLongestAudioClip(List<AudioClip> clips) {
        int maxClipLength = 0;
        AudioClip longestClip = null;
        for(AudioClip clip : clips) {
            if(clip.getValues().size() > maxClipLength) {
                maxClipLength = clip.getValues().size();
                longestClip = clip;
            }
        }
        return longestClip;
    }

    public AudioClip mix(List<AudioClip> clips) {

        List<Double> values = new ArrayList<Double>();
        int maxTrackLength = getLongestAudioClip(clips).getValues().size();
        for(int i = 0; i < maxTrackLength; i++) {
            double sampleSum = 0;
            for(AudioClip clip : clips) {
                if(i < clip.getValues().size()) {
                    sampleSum += clip.getValues().get(i);
                }
            }
            values.add(sampleSum / clips.size());
        }
        return new AudioClip(values);
    }

    public AudioClip clip(AudioClip clip, int t1, int t2) {
        List<Double> values = new ArrayList<Double>();
        for(int i = 0; i < clip.getValues().size(); i++) {
            if((i >= sampleRate * t1) && (i < sampleRate * t2)) {
                values.add(clip.getValues().get(i));
            }
        }
        return new AudioClip(values);
    }

    private AudioClip fadeIn(AudioClip clip, double exponent) {
        List<Double> values = new ArrayList<Double>();
        for(int i = 0; i < clip.getValues().size(); i++) {
            double mult = ((double)(i)/clip.getValues().size());
            values.add(clip.getValues().get(i) * Math.pow(mult,exponent));
        }
        return new AudioClip(values);

    }

    public AudioClip fadeIn(AudioClip clip) {
        return fadeIn(clip, 1);
    }

    public AudioClip expFadeIn(AudioClip clip) {
        return fadeIn(clip, 2);
    }

    private AudioClip fadeOut(AudioClip clip, double exponent) {
        List<Double> values = new ArrayList<Double>();
        for(int i = 0; i < clip.getValues().size(); i++) {
            double mult = (1-((double)(i)/clip.getValues().size()));
            values.add(clip.getValues().get(i) * Math.pow(mult, exponent));
        }
        return new AudioClip(values);
    }

    public AudioClip fadeOut(AudioClip clip) {
        return fadeOut(clip, 1);
    }

    public AudioClip expFadeOut(AudioClip clip) {
        return fadeOut(clip, 2);
    }

    public AudioClip joinClips(List<AudioClip> clips) {
        List<Double> values = new ArrayList<Double>();
        for(AudioClip clip : clips) {
            for(Double d : clip.getValues()) {
                values.add(d);
            }
        }
        return new AudioClip(values);
    }

}