uniform sampler2D sceneTex; // 0
uniform vec2 center; // Mouse position
uniform float time; // effect elapsed time
uniform vec3 shockParams; // 10.0, 0.8, 0.1
 
varying vec2 v_texCoords;
 
void main() 
{ 
	// get pixel coordinates
	vec2 l_texCoords = v_texCoords;
	
	//get distance from center
	float distance = distance(v_texCoords, center);
	
	if ( (distance <= (time + shockParams.z)) && (distance >= (time - shockParams.z)) ) {
    	float diff = (distance - time); 
    	float powDiff = 1.0 - pow(abs(diff*shockParams.x), shockParams.y); 
    	float diffTime = diff  * powDiff; 
    	vec2 diffUV = normalize(v_texCoords-center); 
    	l_texCoords = v_texCoords + (diffUV * diffTime);
	}
	gl_FragColor = texture2D(sceneTex, l_texCoords);
}