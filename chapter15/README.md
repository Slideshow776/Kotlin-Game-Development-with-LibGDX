# Advanced 2D Graphics
This chapter presents two advanced and powerful techniques to improve a game's graphics, namely particle effects, and shader programs.

## Particle Systems
>A particle system is a technique in-game physics, motion graphics, and computer graphics that uses many minute sprites, 3D models, or other graphic objects to simulate certain kinds of "fuzzy" phenomena, which are otherwise very hard to reproduce with conventional rendering techniques - usually highly chaotic systems, natural phenomena, or processes caused by chemical reactions. --<cite>[wikipedia](https://en.wikipedia.org/wiki/Particle_system)</cite>

Simply put a particle effect is a collection of images/sprites put together to resemble a wanted animation. Animations such as fire, smoke, explosions, electrical sparks, rain, snow, droplets.

### The LibGDX Particle Editor
LibGDX's [particle editor](https://github.com/libgdx/libgdx/wiki/2D-Particle-Editor) is included in this chapter's project. To run the latest version of the particle editor download a [nightly version ](https://libgdx.badlogicgames.com/ci/nightlies/) and in the top level directory, together with the main .jar's create and run this .bat script:
```
java -cp gdx.jar;gdx-natives.jar;gdx-backend-lwjgl.jar;gdx-backend-lwjgl-natives.jar;extensions\gdx-tools\gdx-tools.jar com.badlogic.gdx.tools.particleeditor.ParticleEditor
```

#### Tutorials
For tutorials on how to use the particle editor recommendations go to:
* one of LibGDX's developers [Nate's tutorial](https://www.youtube.com/watch?v=w8xkf3O4nho)
* another good one by [Robin Stumm](https://www.youtube.com/watch?v=LCLa-rgR_MA)
* a shorter but more practical video by [Kyle Dencker](https://www.youtube.com/watch?v=520vA2elV4M)

The `ParticleActor` class is created to support additional features such as rotation and scaling.

As explained by LibGDX's [wiki](https://github.com/libgdx/libgdx/wiki/2D-ParticleEffects#basic-particleeffect-usage) a basic example looks like this:
```
TextureAtlas particleAtlas; //<-load some atlas with your particle assets in
ParticleEffect effect = new ParticleEffect();
effect.load(Gdx.files.internal("myparticle.p"), particleAtlas);
effect.start();

//Setting the position of the ParticleEffect
effect.setPosition(x, y);

//Updating and Drawing the particle effect
//Delta being the time to progress the particle effect by, usually you pass in Gdx.graphics.getDeltaTime();
effect.draw(batch, delta);
```
The wiki suggests further increase to the performance by using an atlas, pooling and batching.

Also make sure the particle editor is of the same version as the rest of your project, as it may be subject to breaking changes.

### Space Rocks with Particles
The project features an upgrade to the game Space Rocks, by adding particle effects to the thruster and the explosions, and dramatically enhances the visual effects.
![image](https://user-images.githubusercontent.com/4059636/66285066-411dfc00-e8cb-11e9-8a11-f393b7895bbc.png)

## Shader Programming
Shaders are advanced value-based animations, as displayed best on [libgdx.info/shaders](https://libgdx.info/shaders/).

Shader programming uses OpenGL Shading Language (GLSL), and are a set of instructions run on every single pixel of the game on the screen, simultaneously.
> "Typically, when rendering something in OpenGL ES 2.0 the data will be sent through the Vertex shader first and then through the Fragment shader." --<cite>[LibGDX's wiki](https://github.com/libgdx/libgdx/wiki/Shaders)</cite>

This simple shader program which renders everything in a solid red consists of two files: the _vertex shader_ and the _fragment shader_

The vertex shader:
```
attribute vec4 a_position;

uniform mat4 u_projectionViewMatrix;

void main()
{
    gl_Position =  u_projectionViewMatrix * a_position;
} 
```

The fragment shader:
```
void main()
{
    gl_FragColor = vec4(1.0, 0.0, 0.0, 1.0);
}
```

The shader program is usually store as two files in an _assets folder_, and is loaded into the game's app like this:
```
vertexShaderCode = Gdx.files.internal("assets/shaders/default.vs").readString()
        fragmenterShaderCode = Gdx.files.internal("assets/shaders/default.fs").readString()
        shaderProgram = ShaderProgram(vertexShaderCode, fragmenterShaderCode)
        if (!shaderProgram.isCompiled)
            println("Shader compile error: " + shaderProgram.log)
```

Then the shader program needs to be drawn:
```
override fun draw(batch: Batch, parentAlpha: Float) {
        batch.shader = shaderProgram
        super.draw(batch, parentAlpha)
        batch.shader = null
    }
```

### Starfish Collector with shaders
The project features an upgrade to the game Starfish Collector, by adding shader effects to the starfishes (a pulsing glow), and the background (wave-like effect), and also some extra effects to the menu and transitions of screens.
![image](https://user-images.githubusercontent.com/4059636/66285729-534d6980-e8ce-11e9-82b6-d3dea2332fd0.png)

### Further reading and tutorials
This topic is far too broad and advanced to be covered in a chapter's summary. The following are recommendations for further interests as used in this project:
* Matt DesLauriers's [Intro to Shaders](https://github.com/mattdesl/lwjgl-basics/wiki/Shaders)
* [The Book of Shaders](https://thebookofshaders.com)
* LibGDX's [wiki](https://github.com/libgdx/libgdx/wiki/Shaders)
* [Libgdx.info](https://github.com/libgdx/libgdx/wiki/Shaders)
* Robin Stumm's [youtube tutorials](https://www.youtube.com/watch?v=1mIdNru2VO0)
* And of course this project's [book](https://www.apress.com/gp/book/9781484233238)

## New Imports

**import com.badlogic.gdx.graphics.g2d.ParticleEffect** - Main particle effect library
**import com.badlogic.gdx.graphics.g2d.ParticleEmitter** - Emitter library
**import com.badlogic.gdx.graphics.glutils.ShaderProgram** - A shader program encapsulates a vertex and fragment shader pair linked to form a shader program 
