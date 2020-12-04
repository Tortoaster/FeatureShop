import com.trick.featureshop.plugins.Save;


public class FeatureShop {
	
	public List<Plugin> getPlugins() {
		List<Plugin> plugins = original();
		plugins.add(new Save());
		return plugins;
	}
	
}