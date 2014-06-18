import org.lwjgl.Sys;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;

import static org.lwjgl.opengl.GL11.*;

public class FPSFoo {

	float rotation = 0.0f;
	float x = 400;
	float y = 300;
	float last_frame;
	int fps;
	long last_fps;
	
	public static void main(String[] args) {
		try
		{
			LibraryLoader.loadNativeLibraries();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		System.out.println("Lol what happened why did it work");
		
		FPSFoo f = new FPSFoo();
		f.start();
	}

	public void start(){
		
		try
		{
			Display.setDisplayMode(new DisplayMode(800,600));
			Display.create();
		}
		catch(Exception e)
		{
			System.out.println("Lol genji doesn't know how to code");
			e.printStackTrace();
			System.exit(0);
		}
		
		initGL();
		getDelta();
		last_fps = getTime();
		
		while(!Display.isCloseRequested())
		{
			int delta = getDelta();
			
			update(delta);
			renderGL();
			
			Display.update();
			
			Display.sync(60);
			
		}
		
		Display.destroy();
		
		System.exit(0);	
	}
	
	public void update(int delta)
	{
		rotation -= 0.1f*delta;
		
		if(Keyboard.isKeyDown(Keyboard.KEY_A)){
			x -= 0.35f * delta;
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_D)){
			x += 0.35f * delta;
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_S)){
			y -= 0.35f * delta;
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_W)){
			y += 0.35f * delta;
		}
		if(x<0){ x=0; }
		if(x>800){ x=800; }
		if(y<0){ y=0; }
		if(y>600){ y=600; }
		
		updateFPS();
		
	}
	
	public int getDelta()
	{
		long time = getTime();
		int delta = (int) (time - last_frame);
		last_frame = time;
		
		return delta;
	}
	
	public long getTime()
	{
		return (Sys.getTime() * 1000) / Sys.getTimerResolution();
	}
	
	public void updateFPS()
	{
		if(getTime() - last_fps > 1000)
		{
			Display.setTitle("FPS: " + fps);
			fps = 0;
			last_fps += 1000;
		}
		fps++;
	}
	
	public void initGL()
	{
		glMatrixMode(GL_PROJECTION);
		glLoadIdentity();
		glOrtho(0,800,0,600,-1,1);
		glMatrixMode(GL_MODELVIEW);
	}
	
	public void renderGL()
	{
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
		
		//glClearColor(1.0f,1.0f,0.0f,0.0f);
		
		glColor3f(1.0f,1.0f,0.0f);
		
		glPushMatrix();
		
		glTranslatef(x,y,0);
		glRotatef(rotation,0f,0f,1f);
		//glTranslatef(-x,-y,0);
		
		
		
		/*
		glVertex2f(x,y);
		glVertex2f(x-50,y-50);
		
		glVertex2f(x+50,y-50);
		
		glVertex2f(x+50,y+50);
		glVertex2f(x-50,y+50);
		
		glEnd();
		*/
		
		glBegin(GL_POLYGON);
		
		
		float res = 10f;
		float size = 100f;
		double angle;
		
		
		glBegin(GL_POLYGON);
		for(int i=0;i<res;i++){
			angle = i * 2*Math.PI / res;
			glVertex2f(size*(float)Math.cos(angle),size*(float)Math.sin(angle));
		}
		/*
		glVertex2f(x + size*(float)Math.cos(0),y + size*(float)Math.sin(0));
		glVertex2f(x + size*(float)Math.cos(Math.PI/2),y + size*(float)Math.sin(Math.PI/2));
		glVertex2f(x + size*(float)Math.cos(Math.PI),y + size*(float)Math.sin(Math.PI));
		glVertex2f(x + size*(float)Math.cos(3*Math.PI/2),y + size*(float)Math.sin(Math.PI * 3 / 2));
		*/
		glEnd();
		glPopMatrix();
		
	}
	
}