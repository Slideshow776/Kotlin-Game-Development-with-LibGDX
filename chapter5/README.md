# Text and User Interfaces

## Displaying Text
A text may be displayed using a label. Labels are initialized with a String and a LabelStyle object.
It is best to initialize this often used asset in the Game object as a public static variable.

### Bitmap Fonts
A computer-generated font is either stored as a set of mathematical curves and formulas, or as a set of images.
Bitmap fonts is a set of images and is used in a LabelStyle. 

To create a BitmapFont object two pieces of data is needed: an image containing all the characters, and an associated data file (.fnt) which lists the regions.

A bitmap font may be created _externally_ or _class-based_.
Using an external application enables viewing and adjusting letter dimensions immediately, but requires additional runtime and storage.
The class-based approach makes it easier to change font parameters, but is not viewable until the game is running.

[Hiero](https://libgdx.badlogicgames.com/tools.html) is a tool created by LibGDX which may be used to create bitmap font data using fonts.

To create a bitmapfont class-based in code a TrueType Font file (.ttf) must be supplied as an asset.
 



## New Imports

**import com.badlogic.gdx.graphics.g2d.BitmapFont** - Renders bitmap fonts. The font consists of 2 files: an image file or TextureRegion containing the glyphs and a file in the AngleCode BMFont text format that describes where each glyph is on the image.

**import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle** - The style for a label, see Label.

**import com.badlogic.gdx.graphics.g2d.freetype.FreetypeFontGenerator** - Generates BitmapFont and BitmapFontData instances from TrueType, OTF, and other FreeType supported fonts.

**import com.badlogic.gdx.graphics.g2d.freetype.FreetypeFontGenerator.FreeTypeFontParameter**
