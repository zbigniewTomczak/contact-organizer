package contact.model;

import java.util.ArrayList;
import java.util.Map;

import javax.faces.model.ListDataModel;

import org.primefaces.model.SelectableDataModel;

import contact.Contact;

public class ContactsDataModel extends ListDataModel<Contact> implements
		SelectableDataModel<Contact> {
	
	private Map<String, Contact> data;
	public ContactsDataModel() {
		
	}
	
	public ContactsDataModel(Map<String, Contact> data) {
		super(new ArrayList<Contact>(data.values()));
		this.data = data;
	}

	@Override
	public Contact getRowData(String key) {
		return data != null ? data.get(key) : null;
	}

	@Override
	public Object getRowKey(Contact contact) {
		return contact.getKey();
	}

	
}
