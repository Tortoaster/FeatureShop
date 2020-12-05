import com.trick.featureshop.plugins.Undo;
import com.trick.featureshop.plugins.Redo;


public class FeatureShop {
	
	public List<Plugin> getPlugins() {
		List<Plugin> plugins = original();
		plugins.add(new Undo());
		plugins.add(new Redo());
		return plugins;
	}
	
}