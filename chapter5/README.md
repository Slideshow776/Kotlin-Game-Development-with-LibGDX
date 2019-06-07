# Text and User Interfaces

This chapter teaches how to use labels, image and text-based buttons. A custom bitmap font was created, lambda expression were introduced, as well as using tables. Lastly we added logic to display dialog boxes and creating cutscenes.

This chapter contains two games; StarfishCollector and The Missing Homework

## Starfish Collector
As suggested by the book the following features were added:
* A pause button
* Count down timer to end the game
* Two cutscenes displaying the respective game ending conditions

![StarfishCollector](https://user-images.githubusercontent.com/4059636/59086522-4447b200-8902-11e9-9501-fc6dcfb2c068.png)

## The Missing Homework
TODO

## Displaying Text
A text may be displayed using a label. Labels are initialized with a String and a LabelStyle object.
It is best to initialize this often used asset in the Game object as a public static variable.

### Bitmap Fonts
A computer-generated font is either stored as a set of mathematical curves and formulas or as a set of images.
Bitmap fonts are a set of images and are used in a LabelStyle. 

To create a BitmapFont object two pieces of data are needed: an image containing all the characters, and an associated data file (.fnt) which lists the regions.

A bitmap font may be created _externally_ or _class-based_.
Using an external application enables viewing and adjusting letter dimensions immediately, but requires additional runtime and storage.
The class-based approach makes it easier to change font parameters but is not viewable until the game is running.

[Hiero](https://libgdx.badlogicgames.com/tools.html) is a tool created by LibGDX which may be used to create bitmap font data using fonts.

To create a bitmapfont class-based in code a TrueType Font file (.ttf) must be supplied as an asset.
 
## Buttons
### Functional Interfaces and Lambda Expressions
#### Functional Interface
Some languages may store methods in variables, others may not. A substitute for this lack of flexibility is a _functional interface_: an interface that consists of a single method.
When storage of a method is required rather implement the interface and then specify the function as needed.
For example, to configure a button to exit a program the following class may be created:
```
public class QuitFunction implements Function {
    public void run() {
        System.exit(0);
    }
}
```

A button class is needed as well:
```
public class Button {
    private Function clickFunction;

    public void setFunction(Function f) {
        clickFunction = f;
    }
}
```

The application's implementation would be:

```
Button button = new Button();
button.setFunction(new QuitFunction());
```

In this instance `QuitFunction()` is an _inner class_. 
An _anonymous inner class_ may be created by writing the class definition as the argument passed to the method, as follows: 
```
button.setFunction() {
    new Function() {
        public void run() {
            System.exit();
        }
    }
}
```
#### Lambda Expressions
Lambda expressions are compact syntax for creating anonymous inner classes for functional interfaces. This may save a great deal of time and pain when writing code.
```
button.setFunction(
    () -> { System.exit(); }
)
```

### Image-Based Buttons

In a nutshell:
```
// Get the images
Texture buttonTexture = new Texture(Gdx.files.internal("button.png"));
TextureRegion buttonRegion = new TextureRegion(buttonTexture);

// Apply the images
ButtonStyle buttonStyle = new ButtonStyle();
buttonStyle.up = new TextureRegionDrawable(buttonRegion);

// Add the button
Button quitButton = new Button(buttonStyle);
quitButton.setPosition(200, 200);

// Add a listener
quitButton.addListener(
    (Event e) -> {
        if (!(e instanceOf InputEvent) ||
        !((InputEvent)e).getType().equals(Type.touchDown))
        return false;

        System.exit(0);
        return false;
    }
)

// Apply the listener
Gdx.input.setInputProcessor(quitButton);
```

#### Nine Patch
Text inside a texture without overflowing, and without stretching the corners of the image, thus reducing image distortion.
```
NinePatch np = new NinePatch(texture, left, right, top, bottom);
```
The integers provided are measurements in pixel. 

![Dividing a texture into nine regions](https://user-images.githubusercontent.com/4059636/58396391-aecc3880-804c-11e9-84f7-17cc47830345.png)


### Text-Based Buttons
In a nutshell:
```
// Style setup
TextButtonStyle textButtonsStyle = new TextButtonStyle(Gdx.files.internal("button.png"));
NinePatch buttonPatch = new NinePatch(buttonTex, 16, 16, 16, 16);
textButtonStyle.up = new NinePatchDrawable(buttonPatch);
textButtonStyle.font = someFont;

// create the button
TextButton quitButton = new TextButton("Quit", textButtonStyle);
quitButton.setPosition(150, 150);

// Apply the listener
Gdx.input.setInputProcessor(quitButton);
```

## Organizing Layouts with Tables
Setting the GUI elements' position manually is often unnecessary. With LibGDX's Table this is simplified. Table is a subclass of Actor and can be added to Stage objects. Table is also a subclass of Group which means objects may be added to a Table additionally.
Tables consist of Cell objects ordered in rows and columns, each Cell containing an Actor.

The following code results in a table as shown in the figure below. 
```
Table t = new Table();
t.add(a);
t.add(b);
t.row();
t.add(c);
t.add(d);
```
![table](https://user-images.githubusercontent.com/4059636/58312344-64a84480-7e0b-11e9-84f3-7d89dcede23a.png)


## Determine if two actors are close
To determine if two actors/entities are _close_ to each other the following algorithm suffices.
* Temporarily scale up the acting actors bounding rectangle/polygon,
* Check if new bounding rectangle/polygon overlaps the other actor

Note: Scaling happens from the center of the actor if a specific detection distance is required this needs to be multiplied by two.

## Creating Cutscenes
To create cutscenes you need a _SceneSegment_ class which binds an actor to an action and a _Scene_ class to handle the list of SceneSegments which also automatically plays the next SceneSegment. 

In addition, in this chapter, two new Action classes (_SetTextAction_ and _SceneActions_) have been defined to give more custom needed actions.

## New Imports

**import com.badlogic.gdx.graphics.g2d.BitmapFont** - Renders bitmap fonts. The font consists of 2 files: an image file or TextureRegion containing the glyphs and a file in the AngleCode BMFont text format that describes where each glyph is on the image.

**import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle** - The style for a label, see Label.

**import com.badlogic.gdx.graphics.g2d.freetype.FreetypeFontGenerator** - Generates BitmapFont and BitmapFontData instances from TrueType, OTF, and other FreeType supported fonts.

**import com.badlogic.gdx.graphics.g2d.freetype.FreetypeFontGenerator.FreeTypeFontParameter**
