package com.ebicep.undeadstorm;

import java.applet.Applet;
import java.applet.AudioClip;

public class GameAudio {

    private AudioClip allSounds[];

    public GameAudio() {
        allSounds = new AudioClip[11];
        allSounds[0] = Applet.newAudioClip(this.getClass().getResource("/sounds/menuScreenSound.wav"));
        allSounds[1] = Applet.newAudioClip(this.getClass().getResource("/sounds/clickedSound.wav"));
        allSounds[2] = Applet.newAudioClip(this.getClass().getResource("/sounds/grenadeBlowupSound.wav"));
        allSounds[3] = Applet.newAudioClip(this.getClass().getResource("/sounds/zombieHitSound.wav"));
        allSounds[4] = Applet.newAudioClip(this.getClass().getResource("/sounds/playerHitSound.wav"));
        allSounds[5] = Applet.newAudioClip(this.getClass().getResource("/sounds/shootingSound.wav"));
        allSounds[6] = Applet.newAudioClip(this.getClass().getResource("/sounds/gunEmptySound.wav"));
        allSounds[7] = Applet.newAudioClip(this.getClass().getResource("/sounds/lowHealthSound.wav"));
        allSounds[8] = Applet.newAudioClip(this.getClass().getResource("/sounds/onFireSound.wav"));
        allSounds[9] = Applet.newAudioClip(this.getClass().getResource("/sounds/powerUpSound.wav"));
        allSounds[10] = Applet.newAudioClip(this.getClass().getResource("/sounds/autoFireSound.wav"));

    }

    public void play(int i) {
        allSounds[i].play();
    }

    public void loop(int i) {
        allSounds[i].loop();
    }

    public void stop(int i) {
        allSounds[i].stop();
    }

    public AudioClip[] getAllSounds() {
        return allSounds;
    }
}
