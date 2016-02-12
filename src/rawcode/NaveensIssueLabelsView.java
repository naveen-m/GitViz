package rawcode;

import com.naveen.eclipse.gva.dataviews.files.FileHistoryDataView;
import com.naveen.eclipse.gva.personalization.PersonalizationEngineManagerComponent;

public class NaveensIssueLabelsView {
	public PersonalizationEngineManagerComponent peManager;
	public FileHistoryDataView mFileHistoryDataView;

	public NaveensIssueLabelsView(
			PersonalizationEngineManagerComponent peManager,
			FileHistoryDataView mFileHistoryDataView) {
		this.peManager = peManager;
		this.mFileHistoryDataView = mFileHistoryDataView;
	}
}