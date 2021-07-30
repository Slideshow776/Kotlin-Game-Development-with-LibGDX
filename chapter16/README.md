# Introduction to 3D Graphics and Games
This chapter examines how to develop 3D games.

As suggested by the book the following features have been added:
* Rectangle Destroyer 3D

## 3 Dimensional Considerations
As it turns out, all previous games are created in a 3D space. This may be hard to realise because of the z-axis
is pointing toward the camera/player, perpendicular to the xy plane (i.e a bird's view).

![The xyz plane](https://upload.wikimedia.org/wikipedia/commons/thumb/8/83/Coord_planes_color.svg/300px-Coord_planes_color.svg.png)

![Orthographic projections](http://www.mbsoftworks.sk/tutorials/opengl4/009-orthographic-2D-projection/ortho_example.png)

* Orthogonal Projection: If the edges of an object have the same length, then they will be drawn as having the same length in the projection, regardless of their distance from the viewer. 
* Perspective Projection: An edge farther away from the viewer will appear shorter.

![Orthogonal vs. Perspective projection](https://answers.unity.com/storage/temp/74544-temp.png)

If two edges of an object are parallel, then they remain parallel in an orthographic projection, but they appear to converge in a perspective projection.

![A vanishing point](https://upload.wikimedia.org/wikipedia/commons/thumb/9/91/Railroad_in_Northumberland_County%2C_Pennsylvania.JPG/182px-Railroad_in_Northumberland_County%2C_Pennsylvania.JPG)

A `PerspectiveCamera` requires a visible region defined.
```
camera = PerspectiveCamera(67f, Gdx.graphics.width.toFloat(), Gdx.graphics.height.toFloat())
camera.near = .1f
camera.far = 1000f
camera.position.set(10f, 10f, 10f)
camera.lookAt(0f, 0f, 0f)
camera.update()
```
![libGDX's perspective camera](https://habrastorage.org/files/1fd/6b3/eaf/1fd6b3eafd8b4f1d8941ea3609a3781b.png)

Instead of a `SpriteBatch` renders 2-dimensional objects, a `ModelBatch` is used to render 3-dimensional objects.
Data needed to draw an object is contained in a `ModelObject`, which consists of a _Mesh_ and a _Material_.
The mesh is a collection of vertices, edges, and triangular faces, and together define the shape of an object.
A material contains the color or texture data applied to the mesh.

![mesh vs. material](http://15418.courses.cs.cmu.edu/spring2016content/lectures/05_gpuarch/images/slide_006.jpg)

Lights provide a way to view 3-dimensional objects. The two kinds used in this project are `AmbientLight` and `DirectionalLight`.
![Different kinds of light](https://i.stack.imgur.com/3udUJ.gif)

## Creating a Minimal 3D Demo
A minimal demo of the LibGDX's 3D capabilities is found in the package `demoCube`. Coincidentally [Xoppa's tutorial](https://xoppa.github.io/blog/basic-3d-using-libgdx/) explains the same steps as the book does.
 
![image](https://user-images.githubusercontent.com/4059636/66450952-b79d3400-ea5a-11e9-9121-c9532c63f7b0.png)

## Creating an Interactive 3D Demo
Further, the book explains how to make a more interactive demo. View the `README.md` for the controls.

![image](https://user-images.githubusercontent.com/4059636/67067226-c8922780-f175-11e9-8a81-5b79af1fb1af.png)

## Starfish Collector 3D
The project describes how to implement the Starfish Collector 3D game.

![image](https://user-images.githubusercontent.com/4059636/67091489-065e7280-f1ad-11e9-9e99-a3d343db3606.png)

## Rectangle Destroyer 3D
As suggested by the book the Rectangle Destroyer 3D game was created. Although minimalistic, it proves the point and is surprisingly impressive.

![image](https://user-images.githubusercontent.com/4059636/67302242-e999a600-f4f0-11e9-9434-cc2180a21ea3.png)

## Other Tutorials
* [Basic 3D using libGDX -by Xoppa](https://xoppa.github.io/blog/basic-3d-using-libgdx/)


## New Imports
**import com.badlogic.gdx.graphics.PerspectiveCamera** - A Camera with perspective projection.

**import com.badlogic.gdx.graphics.VertexAttributes.Usage** - The usage of a vertex attribute.
 
**import com.badlogic.gdx.graphics.g3d.Environment** - The 3D environment which contains everything
 
**import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute** - Color attributes
 
**import com.badlogic.gdx.graphics.g3d.environment.DirectionalLight** - A light that gets emitted in a specific direction.
 
**import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder** -
 
**import com.badlogic.gdx.graphics.g3d.Model** - Helper class to create Models from code.

**import com.badlogic.gdx.graphics.g3d.ModelBatch** - Batches Renderable instances, fetches Shaders for them, sorts them and then renders them.
 
**import com.badlogic.gdx.graphics.g3d.ModelInstance** - An instance of a Model, allows to specify global transform and modify the materials, as it has a copy of the model's materials.
 
**import com.badlogic.gdx.graphics.g3d.Material** - A renderable material

**import com.badlogic.gdx.graphics.g3d.loader.ObjLoader** - ModelLoader to load Wavefront OBJ files.
 
**import com.badlogic.gdx.math.Vector3** - Encapsulates a 3D vector. Allows chaining operations by returning a reference to itself in all modification methods.
 
**import com.badlogic.gdx.math.Quaternion** - A simple quaternion class.

**import com.badlogic.gdx.math.Matrix4** - Encapsulates a column major 4 by 4 matrix. Like the Vector3 class it allows the chaining of methods by returning a reference to itself. For example:
                                            Matrix4 mat = new Matrix4().trn(position).mul(camera.combined);
