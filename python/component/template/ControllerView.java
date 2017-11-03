package ${PACKAGE};

import android.support.annotation.NonNull;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.thornbirds.component.IParams;
import com.thornbirds.framework.component.ControllerView;

${IMPORT}

/**
 * Created by ${USER} on ${DATE}.
 *
 * Generated using ${COMMAND}
 *
 * @module [TODO-COMPONENT add module]
 */
public class ${NAME1} extends ControllerView<RelativeLayout, ${NAME2}> {
    private static final String TAG = "${NAME1}";

    @Override
    protected String getTAG() {
        return TAG;
    }

    public ${NAME1}(@NonNull ViewGroup parentView, @NonNull ${NAME2} controller) {
        super(parentView, controller);
    }

    @Override
    public void setupView() {
        // [TODO-COMPONENT assign mContentView, e.g. mContentView = $(mParentView, R.id.activity_root);]
        // [TODO-COMPONENT setup all component for this controller view]
    }

    @Override
    public boolean onEvent(int event, IParams params) {
        return false;
    }
}