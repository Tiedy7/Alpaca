package core;

public class Functions {

	
	public Functions() {
		
	}
	
	public static float scaleX(float x) {
		return (x * Engine.RESOLUTION_X) / 1920;
	}
	
	public static float scaleY(float y) {
		return (y * Engine.RESOLUTION_Y)/ 1080; 
	}
}
