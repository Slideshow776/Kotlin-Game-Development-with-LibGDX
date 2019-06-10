# Audio
This chapter teaches hw to add sounds and music to a game.

![image](https://user-images.githubusercontent.com/4059636/59179995-08138c00-8b64-11e9-9ade-a9f2d0389682.png)


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

## New Imports

**import com.badlogic.gdx.Sound** - A Sound is a short audio clip that can be played numerous times in parallel. It's completely loaded into memory so only load small audio files. Call the dispose() method when you're done using the Sound.

Sound instances are created via a call to Audio.newSound(FileHandle).

Calling the play() or play(float) method will return a long which is an id to that instance of the sound. You can use this id to modify the playback of that sound instance.

Note: any values provided will not be clamped, it is the developer's responsibility to do so.

**import com.badlogic.gdx.Music** - A Music instance represents a streamed audio file. The interface supports pausing, resuming and so on. When you are done with using the Music instance you have to dispose it via the dispose() method.

Music instances are created via Audio.newMusic(FileHandle).

Music instances are automatically paused and resumed when an Application is paused or resumed. See ApplicationListener.

Note: any values provided will not be clamped, it is the developer's responsibility to do so.