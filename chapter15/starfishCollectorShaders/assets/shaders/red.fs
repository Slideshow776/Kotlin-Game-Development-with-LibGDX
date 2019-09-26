varying vec4 v_color;
varying vec2 v_texCoords;

uniform sampler2D u_texture;

void main()
{
    vec4 color = texture2D(u_texture, v_texCoords);
    gl_FragColor = vec4(1.0, 0.0, 0.0, color.a);
}