/**
 * 
 */
package org.appfuse.webapp.client.ui.users.search;

import java.util.List;

import org.appfuse.webapp.client.application.Application;
import org.appfuse.webapp.client.application.base.activity.AbstractProxySearchActivity;
import org.appfuse.webapp.client.application.base.place.EntitySearchPlace;
import org.appfuse.webapp.client.application.base.view.ProxySearchView;
import org.appfuse.webapp.client.application.utils.tables.CustomColumn;
import org.appfuse.webapp.client.application.utils.tables.LocalColumnSortHandler;
import org.appfuse.webapp.client.proxies.UserProxy;
import org.appfuse.webapp.client.proxies.UsersSearchCriteriaProxy;
import org.appfuse.webapp.client.requests.UserRequest;

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
public class UsersSearchActivity extends AbstractProxySearchActivity<UserProxy, UsersSearchCriteriaProxy> {

	private Handler sortHandler;
	
	public UsersSearchActivity(EntitySearchPlace currentPlace, Application application) {
		super(application, UsersSearchCriteriaProxy.class);
		setTitle(i18n.userList_title());
	}


	@Override
	protected ProxySearchView<UserProxy, UsersSearchCriteriaProxy> createView() {
		final UsersSearchView view = viewFactory.getView(UsersSearchView.class);
		view.setDelegate(this);
		
		// Configure local/remote sorting
		//sortHandler = createLocalColumnSortHandler(view.asHasData());
        sortHandler = new ColumnSortEvent.AsyncHandler(view.asHasData());
        
        view.addColumnSortHandler(sortHandler);
		return view;
	}


	/**
	 * @param hasData
	 */
	private Handler createLocalColumnSortHandler(final HasData hasData) {
		return new LocalColumnSortHandler<UserProxy>(hasData) {
			@Override
			public List<UserProxy> getList() {
				return (List<UserProxy>) hasData.getVisibleItems();
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
		return requests.userRequest();
	}

	@Override
	protected Request<Long> createCountRequest(RequestContext requestContext, UsersSearchCriteriaProxy searchCriteria) {
		return ((UserRequest) requestContext).countUsers(searchCriteria);
	}
	
	@Override
	protected Request<List<UserProxy>> createSearchRequest(
			RequestContext requestContext, UsersSearchCriteriaProxy searchCriteria, 
			Range range, ColumnSortList columnSortList) {

		String sortProperty = null;
		boolean ascending = true;
		if(columnSortList.size() > 0) {
			Column sortColumn = columnSortList.get(0).getColumn();
			sortProperty = getPropertyNameForColumn(sortColumn);
			ascending = columnSortList.get(0).isAscending();
		}
		
		return ((UserRequest) requestContext).searchUsers(searchCriteria, 
				range.getStart(), range.getLength(), 
				sortProperty, ascending);
	}
	
	@Override
	public void onStop() {
		//XXX view.removeColumnSortHandler(sortHandle);
		sortHandler = null;
		super.onStop();
	}
}
