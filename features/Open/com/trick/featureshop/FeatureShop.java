import com.trick.featureshop.plugins.Open;


public class FeatureShop {
	
	public List<Plugin> getPlugins() {
		List<Plugin> plugins = original();
		plugins.add(new Open());
		return plugins;
	}
	
}