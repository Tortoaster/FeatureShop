import java.util.ArrayList;

public class FeatureShop {
	private static ArrayList<Tool> getTools() {
    	ArrayList<Tool> tools = original();
    	
    	tools.add(new Line());
    	
    	return tools;
    }
}
