package chapter16.rectangleDestroyer3D

import com.badlogic.gdx.maps.MapLayer
import com.badlogic.gdx.maps.MapObject
import com.badlogic.gdx.maps.objects.RectangleMapObject
import com.badlogic.gdx.maps.tiled.TiledMap
import com.badlogic.gdx.maps.tiled.TmxMapLoader
import com.badlogic.gdx.maps.tiled.objects.TiledMapTileMapObject
import java.util.ArrayList

class TilemapActor(filename: String) {

    private var tiledMap: TiledMap = TmxMapLoader().load(filename)

    fun getRectangleList(propertyName: String): ArrayList<MapObject> {
        val list = ArrayList<MapObject>()
        for (layer in tiledMap.layers) {
            for (obj in layer.objects) {
                if (obj !is RectangleMapObject)
                    continue

                val props = obj.getProperties()
                if (props.containsKey("name") && props.get("name") == propertyName)
                    list.add(obj)
            }
        }
        return list
    }

    fun getTileList(propertyName: String):ArrayList<MapObject> {
        val list = ArrayList<MapObject>()

        for (layer: MapLayer in tiledMap.layers) {
            for (obj: MapObject in layer.objects) {
                if (obj !is TiledMapTileMapObject)
                    continue

                val props = obj.properties

                // Default MapProperties are stored within associated Tile object
                // Instance-specific overrides are stored in MapObject

                val tmtmo:TiledMapTileMapObject = obj
                val t = tmtmo.tile
                val defaultProps = t.properties

                if (defaultProps.containsKey("name") && defaultProps.get("name") == propertyName)
                    list.add(obj)

                // get list of default property keys
                val propertyKeys = defaultProps.keys

                // iterate over keys; copy default values into props if needed
                while (propertyKeys.hasNext()) {
                    val key = propertyKeys.next()

                    // check if value already exists; if not, create property with default value
                    if (props.containsKey(key)) {
                        continue
                    } else {
                        val value = defaultProps.get(key)
                        props.put(key, value)
                    }
                }
            }
        }
        return list
    }
}
