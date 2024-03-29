package ${PACKAGE};

import androidx.annotation.NonNull;
import android.util.Log;

import com.thornbirds.component.IEventController;
import com.thornbirds.component.IParams;
import com.thornbirds.component.presenter.ComponentPresenter;

${IMPORT}

/**
 * Created by ${USER} on ${DATE}.
 *
 * Generated using ${COMMAND}
 *
 * @module [TODO-COMPONENT add module]
 */
public class ${NAME1} extends ComponentPresenter<${NAME2}.IView>
		implements ${NAME2}.IPresenter {

    @Override
    protected final String getTAG() {
        return "${NAME1}";
    }

    public ${NAME1}(@NonNull IEventController controller) {
        super(controller);
    }

    @Override
    public final boolean onEvent(int event, IParams params) {
        if (mView == null) {
            Log.e(TAG, "onAction but mView is null, event=" + event);
            return false;
        }
        switch (event) {
            default:
                break;
        }
        return false;
    }
}
