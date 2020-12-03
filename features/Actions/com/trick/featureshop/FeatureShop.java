import com.trick.featureshop.actions.Action;

public class FeatureShop {
	private Actionbar actionbar = null;
	
	private final Toolbar.ToolbarListener listener = new Toolbar.ToolbarListener() {
        @Override
        public void selected(Tool tool) {
            if (actionbar != null) frame.remove(actionbar);

            ArrayList<Action> actions = tool.getActions();
            Actionbar replacement = actions.size() > 0 ? new Actionbar(actions) : null;

            if (replacement != null) frame.add(replacement, BorderLayout.PAGE_START);

            actionbar = replacement;
            frame.revalidate();
        }
    };
}