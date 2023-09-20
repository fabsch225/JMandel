#version 460

out vec4 colorOut;
uniform double screen_ratio;
uniform dvec2 screen_size;
uniform highp dvec2 center;
uniform highp double zoom;
uniform int itr;

vec4 color(float t) {
    float r = 12.5 * (1.0 - t) * t * t * t;
    float g = 15.0 * (1.0 - t) * (1.0 - t) * t * t;
    float b = 11.5 * (1.0 - t) * (1.0 - t) * (1.0 - t) * t;

    return vec4(r, g, b, 1.0);
}

void main()
{
	highp dvec2 a, c;
    c.x = screen_ratio * (gl_FragCoord.x / screen_size.x - 0.5);
    c.y = (gl_FragCoord.y / screen_size.y - 0.5);

    c.x /= zoom;
    c.y /= zoom;

    c.x += center.x;
    c.y += center.y;

    int i;
    for(i = 0; i < itr; i++) {//
    	highp double x = (a.x * a.x - a.y * a.y) + c.x;//R
    	highp double y = (a.y * a.x + a.x * a.y) + c.y;//I

		if((x * x + y * y) > 2.0) {
			break;
		}
		a.x = x;
		a.y = y;
    }
    if (i == itr) {
    	colorOut = vec4(1.0, 1.0, 1.0, 1.0);
    }
    else {
		double t = double(i) / double(itr);

		colorOut = color(float(t));
    }
}
