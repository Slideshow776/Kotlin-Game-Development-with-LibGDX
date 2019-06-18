# Audio
This chapter teaches how to add sounds and music to a game.

## Starfish Collector
Audio and music was added, as well as a mute button.

As suggested by the book the following features were added:
* A trumpet sound plays when the player wins the game.
* Background music to the cutscenes.
* Background music to the menu.

![image](https://user-images.githubusercontent.com/4059636/59179995-08138c00-8b64-11e9-9ade-a9f2d0389682.png)

## Rythm Tapper
Rhythm tapper requires a JavaFX setup in the project.

As suggested by the book the following features were added:
* An applause sound effect when the player finishes the game
* A label that shows the players stats at the end of the game
* A label that shows the game _percent_ score

![rythmTapper](https://user-images.githubusercontent.com/4059636/59593223-d5ccd600-90f1-11e9-9cd9-712fd5e23c02.png)


## Space Rocks
As suggested by the book the following features were added:
* Sound effects
* Music

![Chapter 4 gameplay](https://user-images.githubusercontent.com/4059636/57120333-ff63b500-6d70-11e9-84cb-241bb2e39472.PNG)


## Sounds
Suitable for small audio files played at discrete events, no larger than 1MB.
```
Sound effect = Gdx.audio.newSound(Gdx.files.internal("beep.wav")); // load the sound
effect.play(1); // play the sound, the optional float [0-1] is the sound volume
```
## Music
The music interface is purposed for longer audio sequences such as music or ambient sounds. Music obejcts are streamed
```
Music song = Gdx.audio.newSound(Gdx.files.internal("song.mp3")); // load the music
song.setLooping(true);
song.setVolume(1);
song.play();
```

## Actions
Both `Actions.parallel()` and `Actions.sequence()` were used in this project, the table below describes them.

|  action  |  description  |
|:-------------:|:----------------|
|  Parallel  | execute given actions in parallel - all actions at once |
|  Sequence  | execute given actions in sequence - one after another |

## New Imports

**import com.badlogic.gdx.Sound** - A Sound is a short audio clip that can be played numerous times in parallel. It's completely loaded into memory so only load small audio files. Call the dispose() method when you're done using the Sound.

Sound instances are created via a call to Audio.newSound(FileHandle).

Calling the play() or play(float) method will return a long which is an id to that instance of the sound. You can use this id to modify the playback of that sound instance.

Note: any values provided will not be clamped, it is the developer's responsibility to do so.

**import com.badlogic.gdx.Music** - A Music instance represents a streamed audio file. The interface supports pausing, resuming and so on. When you are done using the Music instance you have to dispose it via the dispose() method.

Music instances are created via Audio.newMusic(FileHandle).

Music instances are automatically paused and resumed when an Application is paused or resumed. See ApplicationListener.

Note: any values provided will not be clamped, it is the developer's responsibility to do so.
