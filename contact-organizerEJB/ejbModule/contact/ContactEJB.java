package contact;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

//@TransactionAttribute(TransactionAttributeType.NEVER)
@Stateless
@LocalBean
public class ContactEJB extends AbstractEJB implements ContactEJBRemote {

	//@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	@Override
	public void persist(Contact contact) {
		em.persist(contact);
		em.flush();
		em.refresh(contact);
	}

	@Override
	public List<Contact> getAllContacts() {
		return em.createQuery("SELECT c FROM Contact c", Contact.class).getResultList();
	}

	@Override
	public boolean contactExists(Contact contact) {
		String match = contact.getKey();
		for (Contact c : getAllContacts()) {
			String m = c.getKey();
			if (m.equals(match)) {
				if (contact.getId() != null) {
					if(!contact.getId().equals(c.getId())) {
						return true;
					}
				} else {
					return true;
				}
			}
		}
		
		return false;
	}

	//@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	@Override
	public void merge(Contact contact) {
		em.merge(contact);
		em.flush();
//		em.refresh(contact);
	}

}
