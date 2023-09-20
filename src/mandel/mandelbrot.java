package mandel;

public class mandelbrot {
	public static void main(String[] args) {
		shaderDisplay d = new shaderDisplay("Mandelbrot Render", 900, "shader/elementVS.glsl", "shader/elementFS_M.glsl", true);
	}
	
}
