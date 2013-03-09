/**
 * 
 */
package org.appfuse.webapp.client.ui.people.list;

import java.util.List;

import org.appfuse.webapp.client.application.Application;
import org.appfuse.webapp.client.application.base.activity.AbstractProxySearchActivity;
import org.appfuse.webapp.client.application.base.place.EntitySearchPlace;
import org.appfuse.webapp.client.application.base.view.ProxySearchView;
import org.appfuse.webapp.client.application.utils.tables.CustomColumn;
import org.appfuse.webapp.client.application.utils.tables.LocalColumnSortHandler;
import org.appfuse.webapp.proxies.PeopleSearchCriteriaProxy;
import org.appfuse.webapp.proxies.PersonProxy;
import org.appfuse.webapp.requests.PersonRequest;

import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.cellview.client.ColumnSortEvent;
import com.google.gwt.user.cellview.client.ColumnSortEvent.Handler;
import com.google.gwt.user.cellview.client.ColumnSortList;
import com.google.gwt.view.client.HasData;
import com.google.gwt.view.client.Range;
import com.google.web.bindery.requestfactory.shared.Request;
import com.google.web.bindery.requestfactory.shared.RequestContext;

/**
 * @author ivangsa
 *
 */
public class PeopleSearchActivity extends AbstractProxySearchActivity<PersonProxy, PeopleSearchCriteriaProxy> {

	private Handler sortHandler;
	
	public PeopleSearchActivity(EntitySearchPlace currentPlace, Application application) {
		super(application, PeopleSearchCriteriaProxy.class);
		setTitle("People List");
	}


	@Override
	protected ProxySearchView<PersonProxy, PeopleSearchCriteriaProxy> createView() {
		final PeopleSearchView view = viewFactory.getView(PeopleSearchView.class);
		view.setDelegate(this);
		
		// Configure local/remote sorting
		sortHandler = createLocalColumnSortHandler(view.asHasData());
        //sortHandler = new ColumnSortEvent.AsyncHandler(view.asHasData());
        
        view.addColumnSortHandler(sortHandler);
		return view;
	}


	/**
	 * @param hasData
	 */
	private Handler createLocalColumnSortHandler(final HasData hasData) {
		return new LocalColumnSortHandler<PersonProxy>(hasData) {
			@Override
			public List<PersonProxy> getList() {
				return (List<PersonProxy>) hasData.getVisibleItems();
			}
        };
	}
	
	private String getPropertyNameForColumn(Column column) {
		if(column instanceof CustomColumn) {
			return ((CustomColumn) column).getPropertyName();
		}
		return null;
	}

	@Override
	protected RequestContext createRequestContext() {
		return requests.personRequest();
	}

	@Override
	protected Request<Long> createCountRequest(RequestContext requestContext, PeopleSearchCriteriaProxy searchCriteria) {
		return ((PersonRequest) requestContext).countPeople(searchCriteria.getSearchTerm());
	}
	
	@Override
	protected Request<List<PersonProxy>> createSearchRequest(
			RequestContext requestContext, PeopleSearchCriteriaProxy searchCriteria, 
			Range range, ColumnSortList columnSortList) {

		String sortProperty = null;
		boolean ascending = true;
		if(columnSortList.size() > 0) {
			Column sortColumn = columnSortList.get(0).getColumn();
			sortProperty = getPropertyNameForColumn(sortColumn);
			ascending = columnSortList.get(0).isAscending();
		}
		
		return ((PersonRequest) requestContext)
				.getPeople(searchCriteria.getSearchTerm(), 
				range.getStart(), range.getLength());
	}
	
	@Override
	public void onStop() {
		//XXX view.removeColumnSortHandler(sortHandle);
		sortHandler = null;
		super.onStop();
	}
}
