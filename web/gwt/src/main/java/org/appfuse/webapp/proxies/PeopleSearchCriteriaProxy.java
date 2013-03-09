/**
 * 
 */
package org.appfuse.webapp.proxies;

import org.appfuse.model.PeopleSearchCriteria;

import com.google.web.bindery.requestfactory.shared.ProxyFor;
import com.google.web.bindery.requestfactory.shared.ValueProxy;

/**
 * @author ivangsa
 *
 */
@ProxyFor(PeopleSearchCriteria.class)
public interface PeopleSearchCriteriaProxy extends ValueProxy {

	String getSearchTerm();

	void setSearchTerm(String searchTerm);
}
