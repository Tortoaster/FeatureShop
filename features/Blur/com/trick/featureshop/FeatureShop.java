import com.trick.featureshop.plugins.Blur;


public class FeatureShop {
	
	public List<Plugin> getPlugins() {
		List<Plugin> plugins = original();
		plugins.add(new Blur());
		return plugins;
	}
	
}