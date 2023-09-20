package mandel;

import utils.ShaderUtils;

import java.awt.MouseInfo;
import java.awt.Point;
import java.util.HashMap;

import javax.swing.JFrame;

import com.jogamp.opengl.GL4;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.awt.GLJPanel;
import com.jogamp.opengl.util.Animator;

import jogamp.opengl.egl.EGLContext;


public class shaderDisplay  extends JFrame implements GLEventListener {

	private GLJPanel glJPanel;

    private int renderingProgram;
    private int vao[] = new int[ 1 ];
    private int size;
    private double zoom = 0.25;
    private double zoomIncrement = 0.03;
    private boolean autozoom;
    
    private String vectorShader;
    private String fractionShader;
    
    private double x = -1.5;
    private double y = 0.0;
    
    private long lastRenderTime = 0l;

	private boolean renderM;
    
    public shaderDisplay(String title, int size, String vs, String fs, boolean autozoom) {
    	vectorShader = vs;
    	fractionShader = fs;
    	this.size = size;
    	
        this.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
        this.setTitle(title);
        this.setSize(size, size);
        this.setLocation(0, 0);
        this.autozoom = autozoom;
        this.renderingProgram = 0;
        this.renderM = true;
        
        glJPanel = new GLJPanel();
        glJPanel.addGLEventListener( this );

        this.add(glJPanel);
        this.setVisible( true );

        Animator animator = new Animator( glJPanel );
        animator.start();
    }
	
	public void init(GLAutoDrawable  drawable) {
        GL4 gl = (GL4) drawable.getGL();
        
        this.renderingProgram = ShaderUtils.createShaderProgram( 
        		gl, vectorShader, fractionShader);
   
    } 

    public void display(GLAutoDrawable  drawable) {
        GL4 gl = (GL4) drawable.getGL();
        if (autozoom) {
	        if( System.currentTimeMillis() > lastRenderTime + 200 ) {
	            this.clearScreenBuffers( gl );
	            
	            Point p = MouseInfo.getPointerInfo().getLocation();
	            System.out.println(p.toString());
	            x += (p.x - size / 2) * 0.0001 / zoom;
	            y -= (p.y - size / 2) * 0.0001 / zoom;
	
	            zoom += zoomIncrement;
	            zoomIncrement *= 1.09;
	            
	            printMandelbrot(gl);
	            
	            lastRenderTime = System.currentTimeMillis();
	        }
        }
	    
	    else {
	    	if (renderM) {
	    	    this.renderM =  false;
				this.clearScreenBuffers( gl );
				
				Point p = MouseInfo.getPointerInfo().getLocation();
				System.out.println(p.toString());
				x += (p.x - size / 2) * 0.0001 / zoom;
				y -= (p.y - size / 2) * 0.0001 / zoom;
				
				zoom += zoomIncrement;
				zoomIncrement *= 1.09;
				
				printMandelbrot(gl);
	    	}
	    }
	}
    
    public void printMandelbrot(GL4 gl) {
    	gl.glUseProgram( this.renderingProgram );
    	gl.glClearColor(1f, 1f, 1f, 0f);
    	
    	int Reference = gl.glGetUniformLocation( renderingProgram, "screen_ratio" );
        gl.glProgramUniform1d(this.renderingProgram, Reference, 1.0);
        Reference = gl.glGetUniformLocation( renderingProgram, "screen_size" );
        gl.glProgramUniform2d(renderingProgram,Reference, (double) size, (double) size);
        Reference = gl.glGetUniformLocation( renderingProgram, "center" );
        gl.glProgramUniform2d(renderingProgram,Reference, x, y);
        Reference = gl.glGetUniformLocation( renderingProgram, "zoom" );
        gl.glProgramUniform1d(renderingProgram,Reference, zoom);
        Reference = gl.glGetUniformLocation( renderingProgram, "itr" );
        gl.glProgramUniform1i(renderingProgram,Reference,5500);
    	
    	gl.glDrawArrays(GL4.GL_TRIANGLE_STRIP, 0, 4);
    }
    
    
    public void dispose( GLAutoDrawable  drawable ) { }
    public void reshape( GLAutoDrawable  drawable, int x, int y, int width, int height ) { }
    
    private void clearScreenBuffers( GL4 gl ) {
        gl.glClear( GL4.GL_DEPTH_BUFFER_BIT );
        gl.glClear( GL4.GL_COLOR_BUFFER_BIT );
    }
}
