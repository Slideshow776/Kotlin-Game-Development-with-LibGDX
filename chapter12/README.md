# Adventure Games (WIP)

## Controls
* arrows - for movement
* a - for arrow
* s - for sword
* e - for bombs
* b - for buy

As suggested by the book the following features have been added:
* Audio
* A menu
* Bombs
* A restart button
* A new enemy

Bonus features:
* camera movement

## Bugs
* when game is over the Flyers keeps on flying and can move through anything.

## New Imports
No new imports

hero.kt uses https://en.wikipedia.org/wiki/Mipmap

## [Conner Anderson's LibGDX - Camera Effects](https://www.youtube.com/playlist?list=PLD_bW3UTVsEnRf9k3uZI4V0y5Jcfp-0ER)
```
public static void lockToTarget(Camera camera, Vector2 target) {
	Vector3 position = camera.position;
	position.x = target.x;
	position.y = target.y;
	camera.position.set(position);
	camera.update();
}

public static void lerpToTarget(Camera camera, Vector2 target) {
	// a + (b - a) lerp factor
	Vector3 position = camera.position;
	position.x = camera.position.x + (target.x - camera.positionx) * .1f;
	position.y = camera.position.y + (target.y - camera.positionx) * .1f;
	camera.position.set(position);
	camera.update();
}

public static void lockAverageBetweenTargets(Camera camera, Vector2 targetA, Vector2 targetB) {
	public Vector3 position = camera.position;
	position.x = (targetA.x + targetB.x) / 2;
	position.y = (targetA.y + targetB.y) / 2;
	camera.position.set(position);
	camera.update;
}

public static void lerpAverageBetweenTargets(Camera camera, Vector2 targetA, Vector2 targetB) {
	float avgX = (targetA.x + targetB.x) / 2;
	float avgY = (targetA.y + targetB.y) / 2;
	public Vector3 position = camera.position;
	position.x = camera.position.x + (avgX - camera.position.x) * .1f;
	position.y = camera.position.y + (avgY - camera.position.y) * .1f;
	camera.position.set(position);
	camera.update;
}

public static boolean searchFocalPoints(Camera camera, Array<Vector2> focalPoints, Vector2 target, float threshold) {
	for (Vactor2 point : focalPoints) {
		if (target.dst(pont < threshold)) {
			lerpToTarget(camera, point);
			return true;
		}
	}
	return false;
}

public static void boundary(Camera camera, float startX, float startY, float width, float height) {
	Vactor3 position = camera.position;
	
	if (position.x < startX) {
		position.x = startX;
	}
	if (position.y < startY) {
		position.y = startY;
	}
	
	if (position.x > startX + width) {
		position.x = startX + width;
	}
	if (position.x > startY + height) {
		position.y = startY + height;		
	}
	
	camera.position.set(position);
	camera.update();
}
```

public TextureRegion[][] split(int tileWidth,
                               int tileHeight)
Helper function to create tiles out of this TextureRegion starting from the top left corner going to the right and ending at the bottom right corner. Only complete tiles will be returned so if the region's width or height are not a multiple of the tile width and height not all of the region will be used. This will not work on texture regions returned form a TextureAtlas that either have whitespace removed or where flipped before the region is split.
Parameters:
tileWidth - a tile's width in pixels
tileHeight - a tile's height in pixels
Returns:
a 2D array of TextureRegions indexed by [row][column].