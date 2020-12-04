import com.trick.featureshop.plugins.New;


public class FeatureShop {
	
	public List<Plugin> getPlugins() {
		List<Plugin> plugins = original();
		plugins.add(new New());
		return plugins;
	}
	
}