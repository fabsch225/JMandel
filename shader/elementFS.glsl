#version 460

uniform float offset;
out vec4 color;

void drawOneColor();
void drawMultipleColors();
float getColorDelimiter( float );

void main( ) { 
   drawOneColor();
   //drawMultipleColors();
}

void drawOneColor() {
    color = vec4( 0.0, 1.0, 0.0, 1.0 );
}

void drawMultipleColors() {
    if( gl_FragCoord.x < getColorDelimiter( offset ) )
        color = vec4( 1.0, 0.0, 0.0, 1.0 );
    else if( gl_FragCoord.x < getColorDelimiter( offset ) + 50 )
        color = vec4( 0.0, 1.0, 0.0, 1.0 );
    else
        color = vec4( 0.0, 0.0, 1.0, 1.0 );
}

float getColorDelimiter( float offset ) {
    return offset*400 + 370;
}
