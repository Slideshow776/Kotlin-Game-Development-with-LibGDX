# Tilemaps
This chapter teaches how to use [Tiled](https://www.mapeditor.org) to simplify the level design.

Managing level design entails the creation of game assets, more easily done using a level or map editor rather than plotting each value manually by trial and error. LibGDX offers [_Tile Maps_](https://github.com/libgdx/libgdx/wiki/Tile-maps) which works well with Tiled, a free and simple level editor.

> A tile-based game lays out tiles in order to create each level. --<cite>[gamedevelopment.tutsplus.com](https://gamedevelopment.tutsplus.com/tutorials/an-introduction-to-creating-a-tile-map-engine--gamedev-10900)</cite>

> A tile-based video game is a type of video or video game where the playing area consists of a small square (or, much less often, rectangular, parallelogram, or hexagonal) graphic images referred to as tiles laid out in a grid. 
>
> Tile-based games usually simulate a top-down, side view, or 2.5D view of the playing area, and are almost always two-dimensional. --<cite>[wikipedia.org](https://en.wikipedia.org/wiki/Tile-based_video_game)</cite>

Tiled is used to create levels. The figure below shows the level design of the game Rectangle Destroyer.
![image](https://user-images.githubusercontent.com/4059636/62021560-2131d400-b1c8-11e9-91d4-a9f4d0acf993.png)

The figure below shows the tileset used.

![image](https://user-images.githubusercontent.com/4059636/62021622-7a016c80-b1c8-11e9-9c1e-4459386b5cdb.png)


## Implementing tilemaps
Tile stores map files using eXtensible Markup Language (XML), which is a text-based file format that can be read by LibGDX and implemented into a game.

A new actor `TileMapActor` was created which applies Tiled map parsing with the game.
In the end, the level designed can be loaded as simply as shown in the algorithm below.
```
val tma = TilemapActor("assets/map.tmx", mainStage)

for (obj in tma.getTileList("Starfish")) {
    val props = obj.properties
    Starfish(props.get("x") as Float, props.get("y") as Float, mainStage)
}
```

## New Imports
**import com.badlogic.gdx.graphics.camera** - Base class for OrthographicCamera and PerspectiveCamera.

**import com.badlogic.gdx.graphics.OrthographicCamera** - A camera with orthographic projection.

**import com.badlogic.gdx.maps.MapLayer** - Map layer containing a set of objects and properties

**import com.badlogic.gdx.maps.MapObject** - Generic Map entity with basic attributes like name, opacity, color

**import com.badlogic.gdx.maps.MapProperties** - Creates an empty properties set

**import com.badlogic.gdx.maps.objects.RectangleMapObject** - Creates a Rectangle object with the given X and Y coordinates along with a given width and height.

**import com.badlogic.gdx.maps.tiled.TiledMap** - Creates an empty TiledMap.

**import com.badlogic.gdx.maps.tiled.TmxMapLoader** - Synchronous loader for TMX maps created with the Tiled tool

**import com.badlogic.gdx.maps.tiled.TiledMapTile** - Generalises the concept of tile in a TiledMap

**import com.badlogic.gdx.maps.tiled.objects.TiledMapTileMapObject** - A MapObject with a TiledMapTile. Can be both StaticTiledMapTile or AnimatedTiledMapTile. 

**import com.badlogic.gdx.maps.tiled.renderers.OrthoCachedTiledMapRenderer** - Renders ortho tiles by caching geometry on the GPU.