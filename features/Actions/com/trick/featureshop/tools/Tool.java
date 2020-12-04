import com.trick.featureshop.actions.Action;
import java.util.ArrayList;
import java.util.List; 

public abstract class Tool {
	protected final List<Action> actions = new ArrayList<Action>();
	
	public List<Action> getActions() {
		return actions;
	}
}