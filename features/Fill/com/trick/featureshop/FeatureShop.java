import java.util.ArrayList;

import com.trick.featureshop.tools.Tool;

public class FeatureShop {
	private static ArrayList<Tool> getTools() {
    	ArrayList<Tool> tools = original();
    	
    	tools.add(new Fill());
    	
    	return tools;
    }
}
