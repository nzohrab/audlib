# audlib - Multi-track audio generation and mixing in Java
Generate audio samples, mix tracks and play back tracks, and export results as Wav files using Java.

# Documentation
Documentation can be viewed **here** //TODO

# Examples
## Shepard scale
```java
int projectBitDepth = 32;
int projectSampleRate = 48000;
int lowOctave = 3;
int midOctave = 4;
int highOctave = 5;
int nRepeats = 10;

AudioProject proj = new AudioProject(projectSampleRate, projectBitDepth);
Track track = proj.addTrack();

int toneLength = proj.duration.ofMillis(300);
AudioClip silence = proj.generate.silence(proj.duration.ofMillis(100));

AudioClip rise = new AudioClip();
AudioClip mid = new AudioClip();
AudioClip fall = new AudioClip();

for(int note = 1; note < 13; note++) {
    AudioClip tone = proj.generate.note(note, lowOctave, toneLength);
    tone = proj.effects.fadeIn(tone);
    tone = proj.effects.fadeOut(tone);
    rise.append(proj.effects.joinClips(Arrays.asList(tone, silence)));

    tone = proj.generate.note(note, midOctave, toneLength);
    tone = proj.effects.fadeIn(tone);
    tone = proj.effects.fadeOut(tone);
    mid.append(proj.effects.joinClips(Arrays.asList(tone, silence)));

    tone = proj.generate.note(note, highOctave, toneLength);
    tone = proj.effects.fadeIn(tone);
    tone = proj.effects.fadeOut(tone);
    fall.append(proj.effects.joinClips(Arrays.asList(tone, silence)));
}

rise = proj.effects.expFadeIn(rise);
fall = proj.effects.expFadeOut(fall);

AudioClip fin = proj.effects.mix(Arrays.asList(rise, mid, fall));

for(int i = 0; i < nRepeats; i++) {
    track.append(fin);
}
track.play();
```

## Shepard-Risset Glissandro
```java
int projectBitDepth = 32;
int projectSampleRate = 48000;
int nLayers = 6;
int chirpSeconds = 60;
int overlapSeconds = chirpSeconds / nLayers;
int nLoops = 5;
int freq1 = 30;
int freq2 = 3840;

AudioProject proj = new AudioProject(projectSampleRate, projectBitDepth);
Track track = proj.addTrack();

AudioClip chirp = proj.generate.logChirp(
        freq1,
        freq2,
        proj.duration.ofSeconds(chirpSeconds)
);

chirp = proj.effects.expFadeIn(chirp);
chirp = proj.effects.expFadeOut(chirp);

List<AudioClip> layers = new ArrayList<AudioClip>();
for (int i = 0; i < nLayers; i++) {
    List<AudioClip> layerClips = new ArrayList<AudioClip>();
    layerClips.add(proj.generate.silence(proj.duration.ofSeconds(overlapSeconds * i)));
    layerClips.add(chirp);
    layers.add(proj.effects.joinClips(layerClips));
}

AudioClip shepardSample = proj.effects.clip(
        proj.effects.mix(layers),
        overlapSeconds * (nLayers - 1),
        chirpSeconds
);

for (int i = 0; i < nLoops; i++) {
    track.append(shepardSample);
}

track.play();
```
