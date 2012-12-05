package contact.admin;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.inject.Inject;

import contact.AbstractBean;
import contact.Contact;
import contact.ContactEJBRemote;
import contact.User;
import contact.UserEJBRemote;

@ManagedBean
public class AdminBean extends AbstractBean{
	
	@Inject private UserEJBRemote userManager;
	@Inject private ContactEJBRemote contactManager;
	
	public List<User> getAllUsers() {
		return userManager.getAllUsers();
	}

	public List<Contact> getAllContacts() {
		return contactManager.getAllContacts();
	}

}
