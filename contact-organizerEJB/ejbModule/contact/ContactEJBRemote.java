package contact;

import java.util.List;

import javax.ejb.Remote;


@Remote
public interface ContactEJBRemote {
	void persist(Contact contact);

	List<Contact> getAllContacts();

	boolean contactExists(Contact contact);

	void merge(Contact contact);
	
}
