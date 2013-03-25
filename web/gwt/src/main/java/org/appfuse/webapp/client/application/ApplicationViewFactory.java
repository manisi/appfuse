/**
 * 
 */
package org.appfuse.webapp.client.application;

import java.util.HashMap;
import java.util.Map;

import org.appfuse.webapp.client.ui.login.LoginView;
import org.appfuse.webapp.client.ui.login.LoginViewImpl;
import org.appfuse.webapp.client.ui.mainMenu.MainMenuView;
import org.appfuse.webapp.client.ui.mainMenu.MainMenuViewDesktop;
import org.appfuse.webapp.client.ui.people.edit.EditPersonView;
import org.appfuse.webapp.client.ui.people.edit.EditPersonViewImpl;
import org.appfuse.webapp.client.ui.people.list.PeopleSearchView;
import org.appfuse.webapp.client.ui.people.list.PeopleSearchViewImpl;
import org.appfuse.webapp.client.ui.upload.views.FileUploadView;
import org.appfuse.webapp.client.ui.upload.views.FileUploadViewImpl;
import org.appfuse.webapp.client.ui.upload.views.UploadedFileView;
import org.appfuse.webapp.client.ui.upload.views.UploadedFileViewImpl;
import org.appfuse.webapp.client.ui.users.active.ActiveUsersView;
import org.appfuse.webapp.client.ui.users.active.ActiveUsersViewImpl;
import org.appfuse.webapp.client.ui.users.editProfile.EditProfileViewImpl;
import org.appfuse.webapp.client.ui.users.editUser.EditUserView;
import org.appfuse.webapp.client.ui.users.editUser.EditUserViewImpl;
import org.appfuse.webapp.client.ui.users.search.UsersSearchView;
import org.appfuse.webapp.client.ui.users.search.UsersSearchViewImpl;
import org.appfuse.webapp.client.ui.users.signUp.SignUpViewImpl;

import com.google.gwt.core.shared.GWT;
import com.google.gwt.user.client.ui.IsWidget;

/**
 * @author ivangsa
 *
 */
public class ApplicationViewFactory {

	private static final Map<Class<? extends IsWidget>, IsWidget> reusableViews = new HashMap<Class<? extends IsWidget>, IsWidget>();
	

	public <T extends IsWidget> T getView(Class<T> viewClass) {
		T view = (T) reusableViews.get(viewClass);
		
		if(view == null) {
			view = instantiateView(viewClass);
		}
		
		return view;
	}
	
	protected <T extends IsWidget> T instantiateView(Class<T> viewClass) {
		T view = null;
		
		if(LoginView.class.equals(viewClass)) {
			view = GWT.create(LoginViewImpl.class);
			reusableViews.put(viewClass, view);
		}
		else if(MainMenuView.class.equals(viewClass)) {
			view = GWT.create(MainMenuViewDesktop.class);
			reusableViews.put(viewClass, view);
		}
		else if(SignUpViewImpl.class.equals(viewClass)) {
			view = GWT.create(SignUpViewImpl.class);
			reusableViews.put(viewClass, view);
		}
		else if(EditProfileViewImpl.class.equals(viewClass)) {
			view = GWT.create(EditProfileViewImpl.class);
			reusableViews.put(viewClass, view);
		}
		else if(ActiveUsersView.class.equals(viewClass)) {
			view = GWT.create(ActiveUsersViewImpl.class);
			reusableViews.put(viewClass, view);
		} 		
		else if(FileUploadView.class.equals(viewClass)) {
			view = GWT.create(FileUploadViewImpl.class);
			//reusableViews.put(viewClass, view); //no way to clear FileUpload
		}		
		else if(UploadedFileView.class.equals(viewClass)) {
			view = GWT.create(UploadedFileViewImpl.class);
			reusableViews.put(viewClass, view);
		}		
		else if(EditUserView.class.equals(viewClass)) {
			view = GWT.create(EditUserViewImpl.class);
			reusableViews.put(viewClass, view);
		}
		else if(UsersSearchView.class.equals(viewClass)) {
			view = GWT.create(UsersSearchViewImpl.class);
			reusableViews.put(viewClass, view);
		}
		else if(PeopleSearchView.class.equals(viewClass)) {
			view = GWT.create(PeopleSearchViewImpl.class);
			reusableViews.put(viewClass, view);
		}
		else if(EditPersonView.class.equals(viewClass)) {
			view = GWT.create(EditPersonViewImpl.class);
			reusableViews.put(viewClass, view);
		}		
		return view;
	}
}
