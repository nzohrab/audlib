# audlib - Multi-track audio generation and mixing in Java
Generate audio samples, mix tracks and play back tracks, and export results as Wav files using Java.

# Documentation
Documentation can be viewed **here** //TODO

# Examples
Shepard-Risset Glissandro
```java
AudioProject proj = new AudioProject(48000, 32);
Track track = proj.addTrack();

int nLayers = 6;
int chirpSeconds = 60;
int overlapSeconds = chirpSeconds / nLayers;
int nLoops = 5;

AudioClip chirp = proj.generate.logChirp(
        30,
        3840,
        proj.duration.ofSeconds(60)
);

chirp = proj.effects.expFadeIn(chirp);
chirp = proj.effects.expFadeOut(chirp);

List<AudioClip> layers = new ArrayList<AudioClip>();
for (int i = 0; i < nLayers; i++) {
    layers.add(proj.effects.joinClips(
            Arrays.asList(proj.generate.silence(proj.duration.ofSeconds(overlapSeconds * i)),
                    chirp)
    ));
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

