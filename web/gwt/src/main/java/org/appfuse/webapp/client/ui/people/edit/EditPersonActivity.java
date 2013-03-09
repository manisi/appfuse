/**
 * 
 */
package org.appfuse.webapp.client.ui.people.edit;

import org.appfuse.webapp.client.application.Application;
import org.appfuse.webapp.client.application.base.activity.AbstractProxyEditActivity;
import org.appfuse.webapp.client.application.base.place.EntitySearchPlace;
import org.appfuse.webapp.client.application.base.view.ProxyEditView;
import org.appfuse.webapp.client.ui.mainMenu.MainMenuPlace;
import org.appfuse.webapp.proxies.PersonProxy;
import org.appfuse.webapp.requests.PersonRequest;

import com.google.gwt.place.shared.Place;
import com.google.web.bindery.requestfactory.shared.Request;
import com.google.web.bindery.requestfactory.shared.RequestContext;

/**
 * @author ivangsa
 *
 */
public class EditPersonActivity extends AbstractProxyEditActivity<PersonProxy> implements EditPersonView.Delegate {

	
	public EditPersonActivity(Application application) {
		super(application);
	}
	
	@Override
	protected ProxyEditView<PersonProxy, ?> createView(Place place) {
		return viewFactory.getView(EditPersonView.class);
	}

	@Override
	protected RequestContext createProxyRequest() {
		return requests.personRequest();
	}
	
	@Override
	protected Request<PersonProxy> loadProxyRequest(RequestContext requestContext, String entityId) {
		return ((PersonRequest) requestContext).get(Long.parseLong(entityId));
	}
	

	@Override
	protected RequestContext saveOrUpdateRequest(RequestContext requestContext, PersonProxy proxy) {
		((PersonRequest) requestContext).save(proxy);
		return requestContext;
	}	
	
	@Override
	protected RequestContext deleteRequest(RequestContext requestContext, PersonProxy proxy) {
		((PersonRequest) requestContext).remove(proxy.getId());
		return requestContext;
	}

	@Override
	protected Place previousPlace() {
		return new EntitySearchPlace(PersonProxy.class);
	}
	
	@Override
	protected Place nextPlace(boolean saved) {
		if(saved) {
			return new EntitySearchPlace(PersonProxy.class);
		} else { // deleted
			return new MainMenuPlace();
		}
	}

}
