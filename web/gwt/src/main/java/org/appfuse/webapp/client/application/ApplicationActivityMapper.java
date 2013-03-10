/**
 * 
 */
package org.appfuse.webapp.client.application;

import org.appfuse.webapp.client.application.base.activity.AbstractBaseActivity;
import org.appfuse.webapp.client.application.base.place.EntityProxyPlace;
import org.appfuse.webapp.client.application.base.place.EntitySearchPlace;
import org.appfuse.webapp.client.ui.login.LoginActivity;
import org.appfuse.webapp.client.ui.login.LoginPlace;
import org.appfuse.webapp.client.ui.logout.LogoutActivity;
import org.appfuse.webapp.client.ui.logout.LogoutPlace;
import org.appfuse.webapp.client.ui.mainMenu.MainMenuActivity;
import org.appfuse.webapp.client.ui.mainMenu.MainMenuPlace;
import org.appfuse.webapp.client.ui.people.edit.EditPersonActivity;
import org.appfuse.webapp.client.ui.people.list.PeopleSearchActivity;
import org.appfuse.webapp.client.ui.reloadOptions.ReloadOptionsActivity;
import org.appfuse.webapp.client.ui.reloadOptions.ReloadOptionsPlace;
import org.appfuse.webapp.client.ui.upload.FileUploadActivity;
import org.appfuse.webapp.client.ui.upload.FileUploadPlace;
import org.appfuse.webapp.client.ui.users.active.ActiveUsersActivity;
import org.appfuse.webapp.client.ui.users.active.ActiveUsersPlace;
import org.appfuse.webapp.client.ui.users.editProfile.EditProfileActivity;
import org.appfuse.webapp.client.ui.users.editProfile.EditProfilePlace;
import org.appfuse.webapp.client.ui.users.editUser.EditUserActivity;
import org.appfuse.webapp.client.ui.users.search.UsersSearchActivity;
import org.appfuse.webapp.client.ui.users.signUp.SignUpActivity;
import org.appfuse.webapp.client.ui.users.signUp.SignUpPlace;
import org.appfuse.webapp.proxies.PersonProxy;
import org.appfuse.webapp.proxies.UserProxy;

import com.google.gwt.activity.shared.Activity;
import com.google.gwt.activity.shared.ActivityMapper;
import com.google.gwt.dom.client.Document;
import com.google.gwt.place.shared.Place;
import com.google.inject.Inject;

/**
 * @author ivangsa
 *
 */
public class ApplicationActivityMapper implements ActivityMapper {

    protected final Application application;


    /**
     * @param application
     */
	@Inject
	public ApplicationActivityMapper(Application application) {
		super();
		this.application = application;
	}



	@Override
	public Activity getActivity(Place place) {
		Activity activity = null;
		
		if(place instanceof LoginPlace) {
			activity = new LoginActivity(application);
		}
		else if(place instanceof LogoutPlace) {
			activity = new LogoutActivity(application);
		}
		else if(place instanceof SignUpPlace) {
			activity = new SignUpActivity(application);
		}
		else if(place instanceof MainMenuPlace) {
			activity = new MainMenuActivity(application);
		}
		else if(place instanceof EditProfilePlace) {
			activity = new EditProfileActivity(application);
		}
		else if(place instanceof ActiveUsersPlace) {
			activity = new ActiveUsersActivity(application);
		}		
		else if(place instanceof FileUploadPlace) {
			activity = new FileUploadActivity(application);
		}
		else if(place instanceof ReloadOptionsPlace) {
			activity = new ReloadOptionsActivity(application);
		}		
		else if(place instanceof EntityProxyPlace) {
			EntityProxyPlace proxyPlace = (EntityProxyPlace) place;
			if(UserProxy.class.equals(proxyPlace.getProxyClass())) {
				activity = new EditUserActivity(application);
			}
			else if(PersonProxy.class.equals(proxyPlace.getProxyClass())) {
				activity = new EditPersonActivity(application);
			}
		}
		else if(place instanceof EntitySearchPlace) {
			EntitySearchPlace listPlace = (EntitySearchPlace) place;
			if(UserProxy.class.equals(listPlace.getProxyClass())) {
				activity = new UsersSearchActivity(listPlace, application);
			}
			else if(PersonProxy.class.equals(listPlace.getProxyClass())) {
				activity = new PeopleSearchActivity(listPlace, application);
			}
		}
		
		if(activity instanceof AbstractBaseActivity) {
			decorateDocument((AbstractBaseActivity) activity);

			
		}
		
		return activity;
	}
	
	/**
	 * Sets document title, and body class and id attributes.
	 * @param baseActivity
	 */
	private void decorateDocument(AbstractBaseActivity baseActivity) {
		
		if(baseActivity.getTitle() != null) {
			Document.get().setTitle(baseActivity.getTitle() + " | " + application.getI18n().webapp_name());
		} else {
			Document.get().setTitle(application.getI18n().webapp_name());
		}
		
		if(baseActivity.getBodyId() != null) {
			Document.get().getBody().setId(baseActivity.getBodyId());
		}else {
			Document.get().getBody().removeAttribute("id");
		}
		
		if(baseActivity.getBodyClassName() != null) {
			Document.get().getBody().setClassName(baseActivity.getBodyClassName());
		} else {
			Document.get().getBody().removeAttribute("class");
		}
	}

}
