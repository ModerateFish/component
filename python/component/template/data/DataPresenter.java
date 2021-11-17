package ${PACKAGE};

import androidx.annotation.NonNull;

import com.thornbirds.component.IEventController;
import com.thornbirds.component.IParams;
import com.thornbirds.component.presenter.ComponentPresenter;

${IMPORT}

/**
 * Created by ${USER} on ${DATE}.
 * <p>
 * Generated using ${COMMAND}
 *
 * @module [TODO-COMPONENT add module]
 */
public class ${NAME1} extends ComponentPresenter<${NAME2}> {

	@Override
	protected final String getTAG() {
		return "${NAME1}";
	}

	public ${NAME1}(@NonNull IEventController controller) {
		super(controller);
	}

	@Override
	public final boolean onEvent(int event, IParams params) {
		return false;
	}
}
