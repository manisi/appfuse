package org.appfuse.webapp.client.ui.people.list;

import java.util.HashSet;
import java.util.Set;

import org.appfuse.webapp.client.application.ApplicationResources;
import org.appfuse.webapp.client.application.base.view.AbstractProxySearchView;
import org.appfuse.webapp.client.application.utils.tables.CustomColumn;
import org.appfuse.webapp.client.proxies.PersonProxy;

import com.github.gwtbootstrap.client.ui.Button;
import com.github.gwtbootstrap.client.ui.TextBox;
import com.google.gwt.cell.client.Cell.Context;
import com.google.gwt.cell.client.FieldUpdater;
import com.google.gwt.core.client.GWT;
import com.google.gwt.editor.client.SimpleBeanEditorDriver;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyDownEvent;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.safehtml.shared.SafeHtmlUtils;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.ColumnSortEvent.Handler;
import com.google.gwt.user.cellview.client.ColumnSortList;
import com.google.gwt.user.cellview.client.HasKeyboardSelectionPolicy.KeyboardSelectionPolicy;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.view.client.HasData;

public class PeopleSearchViewImpl extends AbstractProxySearchView<PersonProxy, String> implements PeopleSearchView {

    interface Binder extends UiBinder<Widget, PeopleSearchViewImpl> { }
    private static final Binder uiBinder = GWT.create(Binder.class);
	
	@UiField(provided=true) ApplicationResources i18n = GWT.create(ApplicationResources.class);
  
    @UiField TextBox searchTerm;
    
    @UiField Button addButton;
    @UiField Button doneButton;
    @UiField com.google.gwt.user.client.ui.Button searchButton;

    @UiField CellTable<PersonProxy> table;
    Set<String> paths = new HashSet<String>();

    public PeopleSearchViewImpl() {
        initWidget(uiBinder.createAndBindUi(this));
        table.setKeyboardSelectionPolicy(KeyboardSelectionPolicy.DISABLED);
        createTableColumns();
    }

    @Override
    public HasData<PersonProxy> asHasData() {
    	return table;
    }
    
    @Override
    public ColumnSortList getColumnSortList() {
    	return table.getColumnSortList();
    }
    
    @Override
    public void addColumnSortHandler(Handler clientSideSortHandler) {
    	table.addColumnSortHandler(clientSideSortHandler);
    }
    
    @Override
    public String[] getPaths() {
    	return paths.toArray(new String[paths.size()]);
    }

    @Override
    public SimpleBeanEditorDriver<String, ?> getEditorDriver() {
    	return null;
    }
    
    @Override
    public void setSearchCriteria(String searchCriteria) {
    	searchTerm.setText(searchCriteria);
    }
    
    @Override
    public String getSearchCriteria() {
    	return searchTerm.getText();
    }
    
    @UiHandler("addButton")
    public void addButtonClicked(ClickEvent event) {
    	delegate.addClicked();
    }

    @UiHandler("doneButton")
    public void doneClicked(ClickEvent event) {
    	delegate.cancelClicked();
    }
    
    @UiHandler("searchButton")
    public void searchButtonClicked(ClickEvent event) {
    	delegate.searchClicked();
    }
    
	@UiHandler("searchTerm")
	void defaultAction(KeyDownEvent event) {
		if (event.getNativeKeyCode() == KeyCodes.KEY_ENTER) {
			delegate.searchClicked();
		}
	}
    
    public void createTableColumns() {
    	FieldUpdater<PersonProxy, String> showDetails = new FieldUpdater<PersonProxy, String>() {
			@Override
			public void update(int index, PersonProxy object, String value) {
				delegate.showDetails(PersonProxy.class, object.getId().toString());
			}
		};
    	
        paths.add("firstName");
        table.addColumn(new CustomColumn<PersonProxy, String>("firstName", true, showDetails) {

			@Override
			public String getValue(PersonProxy person) {
				return person.getFirstName();
			}
			
			@Override
			public void render(Context context, PersonProxy object, SafeHtmlBuilder sb) {
				Anchor anchor = new Anchor(SafeHtmlUtils.htmlEscape(getValue(object)));
				sb.append(SafeHtmlUtils.fromTrustedString(anchor.toString()));
			};			
		}, "First Name");

        paths.add("firstName");
        table.addColumn(new CustomColumn<PersonProxy, String>("firstName", true) {

			@Override
			public String getValue(PersonProxy person) {
				return person.getFirstName();
			}
		}, "Last Name");
       
    }

}
