import java.util.ArrayList;

import com.trick.featureshop.tools.Rectangle;

public class FeatureShop {
	private static ArrayList<Tool> getTools() {
    	ArrayList<Tool> tools = original();
    	
    	tools.add(new Rectangle());
    	
    	return tools;
    }
}
