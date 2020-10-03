package com.github.nzohrab.audlib;
import javax.sound.sampled.*;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

public class Track{
    private List<AudioClip> clips;
    private int sampleRate;
    private int bitDepth;
    private AudioFormat audioFormat;

    public Track(int sampleRate, int bitDepth){
        this.sampleRate = sampleRate;
        this.bitDepth = bitDepth;
        clips = new ArrayList<AudioClip>();
        audioFormat = new AudioFormat(sampleRate, bitDepth, 1, true, true);
    }

    public void append(AudioClip c) {
        clips.add(c);
    }

    public List<AudioClip> getClips() {
        return clips;
    }

    public void save() {
        byte[] bytes = generatePCMArray();

        AudioInputStream ais = new AudioInputStream(
            new ByteArrayInputStream(bytes),
                audioFormat,
                bytes.length / audioFormat.getFrameSize()
        );

        try {
            AudioSystem.write(ais, AudioFileFormat.Type.WAVE, new
                    File("test.wav")
            );
        }
        catch(Exception e) {
            e.printStackTrace();
        }

    }

    public byte[] generatePCMArray() {
        int nBytes = 0;
        for(AudioClip c : clips) {
            nBytes += c.getValues().size() * (bitDepth / 8);
        }

        int bi = 0;
        byte[] bytes = new byte[nBytes];
        for(AudioClip c : clips) {
            for(Double s : c.getValues()) {
                long f = (long)(s * (Math.pow(2,bitDepth) / 2)) - 1;

                int fLengthInBytes = bitDepth / 8;
                for(int i = 0; i < fLengthInBytes; i++) {
                    bytes[bi++] = (byte) ((f >> ((fLengthInBytes-i-1)*8)) & (0xFF));
                }
            }
        }

        return bytes;
    }
    public void play() {
        byte[] bytes = generatePCMArray();
        CountDownLatch syncLatch = new CountDownLatch(1);

        try {
            Clip clip = AudioSystem.getClip();

            // Listener which allow method return once sound is completed
            clip.addLineListener(e -> {
                if (e.getType() == LineEvent.Type.STOP) {
                    syncLatch.countDown();
                }
            });
            clip.open(audioFormat, bytes, 0, bytes.length);
            clip.start();
            syncLatch.await();
        } catch (LineUnavailableException e) {
            System.out.println(e.toString());
        } catch (InterruptedException e) {
            System.out.println(e.toString());
        }

    }
}