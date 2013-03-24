/**
 * 
 */
package org.appfuse.webapp.client.requests;

import java.util.List;

import org.appfuse.service.PersonManager;
import org.appfuse.webapp.client.proxies.PersonProxy;
import org.appfuse.webapp.server.GwtServiceLocator;

import com.google.web.bindery.requestfactory.shared.Request;
import com.google.web.bindery.requestfactory.shared.RequestContext;
import com.google.web.bindery.requestfactory.shared.Service;

/**
 * @author ivangsa
 *
 */
@Service(value=PersonManager.class, locator=GwtServiceLocator.class)
public interface PersonRequest extends RequestContext {

    Request<PersonProxy> get(Long id);

    Request<PersonProxy> save(PersonProxy object);

    Request<Void> remove(Long id);
	
    Request<Long> count(String searchTerm);
    
    Request<List<PersonProxy>> search(String searchTerm, Integer firstResult,Integer maxResults);
}
