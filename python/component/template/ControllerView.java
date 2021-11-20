package ${PACKAGE};

import androidx.annotation.NonNull;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.thornbirds.component.IParams;
import com.thornbirds.frameworkext.component.ControllerView;

${IMPORT}

/**
 * Created by ${USER} on ${DATE}.
 *
 * Generated using ${COMMAND}
 *
 * @module [TODO-COMPONENT add module]
 */
public class ${NAME1} extends ControllerView<RelativeLayout, ${NAME2}> {

    @Override
    protected final String getTAG() {
        return "${NAME1}";
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
    public final boolean onEvent(int event, IParams params) {
        return false;
    }
}