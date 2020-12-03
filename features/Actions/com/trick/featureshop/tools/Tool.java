import com.trick.featureshop.actions.Action;
import java.util.ArrayList;

public abstract class Tool {
	public ArrayList<Action> getActions() {
		return new ArrayList<Action>();
	}
}