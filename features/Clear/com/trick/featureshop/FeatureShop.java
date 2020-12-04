import com.trick.featureshop.plugins.Clear;


public class FeatureShop {
	
	public List<Plugin> getPlugins() {
		List<Plugin> plugins = original();
		plugins.add(new Clear());
		return plugins;
	}
	
}