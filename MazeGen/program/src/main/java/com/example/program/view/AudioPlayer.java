package com.example.program.view;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

/**
 * @author André Eklund
 * @edit Viktor Näslund
 */

public class AudioPlayer {

    private MediaPlayer diamondPlayer;
    private MediaPlayer deathPlayer;
    private MediaPlayer startPlayer;
    private MediaPlayer goalPlayer;
    private MediaPlayer heartPlayer;
    private MediaPlayer breakableWallPlayer;
    private MediaPlayer currentSongPlayer;
    private MediaPlayer pickAxeMediaPlayer;
    private MediaPlayer gameOverMediaPlayer;
    private MediaPlayer buttonClickedMediaPlayer;
    private MediaPlayer introMediaPlayer;
    private MediaPlayer worldIntroMediaPlayer;
    private MediaPlayer timeLeftMediaPlayer;
    private MediaPlayer mobSoundMediaPlayer;
    private final String MUSIC_BASE_PATH = "/com/example/program/files/music/";

    /**
     * Kör metoden som instansierar ljudfilerna.
     */
    public AudioPlayer() {
        setupAudioFiles();
    }

    /**
     * Instansierar alla ljudfiler.
     */
    public void setupAudioFiles() {

        // Sound players
        String SOUND_BASE_PATH = "/com/example/program/files/sounds/";
        diamondPlayer = new MediaPlayer(new Media(AudioPlayer.class.getResource(SOUND_BASE_PATH + "Diamond1.mp3").toString()));
        deathPlayer = new MediaPlayer(new Media(AudioPlayer.class.getResource(SOUND_BASE_PATH + "MazegenDeath.mp3").toString()));
        startPlayer = new MediaPlayer(new Media(AudioPlayer.class.getResource(SOUND_BASE_PATH + "MazegenStart.mp3").toString()));
        goalPlayer = new MediaPlayer(new Media(AudioPlayer.class.getResource(SOUND_BASE_PATH + "MazegenGoal.mp3").toString()));
        heartPlayer = new MediaPlayer(new Media(AudioPlayer.class.getResource(SOUND_BASE_PATH + "Heart.mp3").toString()));
        breakableWallPlayer = new MediaPlayer(new Media(AudioPlayer.class.getResource(SOUND_BASE_PATH + "AxeUsed.mp3").toString()));
        pickAxeMediaPlayer = new MediaPlayer(new Media(AudioPlayer.class.getResource(SOUND_BASE_PATH + "pickaxe.mp3").toString()));
        gameOverMediaPlayer = new MediaPlayer(new Media(AudioPlayer.class.getResource(SOUND_BASE_PATH + "gameover.mp3").toString()));
        buttonClickedMediaPlayer = new MediaPlayer(new Media(AudioPlayer.class.getResource(SOUND_BASE_PATH + "menubuttons.mp3").toString()));
        introMediaPlayer = new MediaPlayer(new Media(AudioPlayer.class.getResource(SOUND_BASE_PATH + "introsound.mp3").toString()));
        worldIntroMediaPlayer = new MediaPlayer(new Media(AudioPlayer.class.getResource(SOUND_BASE_PATH + "nextworld.mp3").toString()));

        // Music player
        currentSongPlayer = new MediaPlayer(new Media(AudioPlayer.class.getResource(MUSIC_BASE_PATH + "forest.mp3").toString()));

        // Additional sound players
        timeLeftMediaPlayer = new MediaPlayer(new Media(AudioPlayer.class.getResource(SOUND_BASE_PATH + "timeLeft.mp3").toString()));
        mobSoundMediaPlayer = new MediaPlayer(new Media(AudioPlayer.class.getResource(SOUND_BASE_PATH + "mobsound.mp3").toString()));

        // Other methods of your class


    }

    /**
     * Spelar musik baserad på given input.
     *
     * @param songToPlay Låten som ska spelas.
     */
    public void playLevelMusic(String songToPlay) {

        // Check if currentSongPlayer is already playing and stop it
        if (currentSongPlayer != null && currentSongPlayer.getStatus() == MediaPlayer.Status.PLAYING) {
            currentSongPlayer.stop();
        }

        String songPath = MUSIC_BASE_PATH + songToPlay + ".mp3";
        Media songMedia = new Media(AudioPlayer.class.getResource(songPath).toString());
        currentSongPlayer = new MediaPlayer(songMedia);

        currentSongPlayer.setOnEndOfMedia(() -> currentSongPlayer.seek(Duration.ZERO));

        currentSongPlayer.play();
    }

    /**
     * Spelar ett ljud när spelaren plockar upp en collectible.
     */
    public void playCollectibleSound() {
        diamondPlayer.play();
        diamondPlayer.seek(Duration.ZERO);
    }

    /**
     * Spelar ett ljud när spelaren dör.
     */
    public void playDeathSound() {
        deathPlayer.play();
        deathPlayer.seek(Duration.ZERO);
    }

    /**
     * Spelar ett ljud när spelrundan startas.
     */
    public void playStartSound() {
        startPlayer.play();
        startPlayer.seek(Duration.ZERO);
    }

    /**
     * Spelar ett ljud när spelaren går i mål.
     */
    public void playGoalSound() {
        goalPlayer.play();
        goalPlayer.seek(Duration.ZERO);
    }

    /**
     * Spelar ett ljud när spelaren plockar upp ett liv.
     */
    public void playHeartSound() {
        heartPlayer.play();
        heartPlayer.seek(Duration.ZERO);
    }

    /**
     * Spelar ett ljud när spelaren förstör en vägg.
     */
    public void playBreakableWallSound() {
        breakableWallPlayer.play();
        breakableWallPlayer.seek(Duration.ZERO);
    }

    /**
     * Spelar ett ljud när spelaren plockar upp en yxa.
     */
    public void playPickAxeSound() {
        pickAxeMediaPlayer.play();
        pickAxeMediaPlayer.seek(Duration.ZERO);
    }

    /**
     * Spelar ett ljud när det blir gameOver.
     */
    public void playGameOverSound() {
        gameOverMediaPlayer.play();
        gameOverMediaPlayer.seek(Duration.ZERO);
    }

    /**
     * En metod som mutear alla speleffekters ljud.
     *
     * @param mute True om ljudet ska vara avstängt och false om ljudet ska vara på.
     */
    public void muteSound(boolean mute) {
        breakableWallPlayer.setMute(mute);
        deathPlayer.setMute(mute);
        heartPlayer.setMute(mute);
        startPlayer.setMute(mute);
        goalPlayer.setMute(mute);
        diamondPlayer.setMute(mute);
        worldIntroMediaPlayer.setMute(mute);
        timeLeftMediaPlayer.setMute(mute);
    }

    /**
     * Spelar ett ljud när spelet startas.
     */
    public void playIntroMusic() {
        introMediaPlayer.play();
        introMediaPlayer.seek(Duration.ZERO);
    }

    /**
     * Stänger av all musik.
     */
    public void stopMusic() {
        currentSongPlayer.stop();
        introMediaPlayer.stop();
    }

    /**
     * Spelar ett ljud vid alla knapptryck i menyerna.
     */
    public void playButtonSound() {
        buttonClickedMediaPlayer.play();
        buttonClickedMediaPlayer.seek(Duration.ZERO);
    }

    /**
     * Spelar ett ljud vid varje ny värld.
     */
    public void playWorldIntroSound() {
        worldIntroMediaPlayer.play();
        worldIntroMediaPlayer.seek(Duration.ZERO);
    }

    /**
     * Spelar ett ljud när det endast är fem sekunder kvar på timern.
     */
    public void playTimeLeftSound() {
        timeLeftMediaPlayer.play();
        timeLeftMediaPlayer.seek(Duration.ZERO);
    }

    /**
     * Spelar ett ljud när spelaren kolliderar med en fiende.
     */
    public void playMobSound() {
        mobSoundMediaPlayer.play();
        mobSoundMediaPlayer.seek(Duration.ZERO);
    }

    /**
     * En metod som mutear all musik.
     *
     * @param mute True om ljudet ska vara avstängt och false om ljudet ska vara på.
     */
    public void muteMusic(boolean mute) {
        currentSongPlayer.setMute(mute);
    }

    /**
     * Stänger av timerns ljud.
     */

    public void stopClockSound() {
        timeLeftMediaPlayer.stop();
    }

    public void setVolume(double volume) {
        currentSongPlayer.setVolume(volume);
        introMediaPlayer.setVolume(volume);
        buttonClickedMediaPlayer.setVolume(volume);
        gameOverMediaPlayer.setVolume(volume);
        pickAxeMediaPlayer.setVolume(volume);
        breakableWallPlayer.setVolume(volume);
        heartPlayer.setVolume(volume);
        goalPlayer.setVolume(volume);
        startPlayer.setVolume(volume);
        deathPlayer.setVolume(volume);
        diamondPlayer.setVolume(volume);
        worldIntroMediaPlayer.setVolume(volume);
        timeLeftMediaPlayer.setVolume(volume);
        mobSoundMediaPlayer.setVolume(volume);
    }
    public double getVolume() {
        return currentSongPlayer.getVolume();
    }
}
