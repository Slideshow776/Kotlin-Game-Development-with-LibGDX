varying vec4 v_color;
varying vec2 v_texCoords;

uniform sampler2D u_texture;

uniform vec2 u_imageSize;
uniform vec4 u_borderColor;
uniform float u_borderSize;

void main()
{
    vec4 color = texture2D(u_texture, v_texCoords);
    vec2 pixelToTextureCoords = 1.0 / u_imageSize;

    bool isInteriorPoint = true;
    bool isExteriorPoint = true;

    for (float dx = -u_borderSize; dx < u_borderSize; dx++)
    {
        for (float dy = -u_borderSize; dy < u_borderSize; dy++)
        {
            vec2 point = v_texCoords + vec2(dx, dy) * pixelToTextureCoords;
            float alpha = texture2D(u_texture, point).a;

            if ( alpha < .5 )
                isInteriorPoint = false;
            if ( alpha > .5 )
                isExteriorPoint = false;
        }
    }

    if (!isInteriorPoint && !isExteriorPoint && color.a < .5)
        gl_FragColor = u_borderColor;
    else
        gl_FragColor = v_color * color;
}
