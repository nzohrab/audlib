# audlib - Multi-track audio generation and mixing in Java
Generate audio samples, mix tracks and play back tracks, and export results as Wav files using Java.

# Documentation
Documentation can be viewed **here** //TODO

# Examples
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
