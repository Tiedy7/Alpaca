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
	
	public static float minimapScaleX(float x, float minimapW, float levelW) {
		return (x * minimapW) / levelW;
	}
	
	public static float minimapScaleY(float y, float minimapH, float levelH) {
		return (y * minimapH) / levelH;
	}
}
