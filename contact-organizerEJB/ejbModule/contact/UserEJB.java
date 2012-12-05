package contact;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

//@TransactionAttribute(TransactionAttributeType.NEVER)
@Stateless
@LocalBean
public class UserEJB extends AbstractEJB implements UserEJBRemote {

	@Override
	public List<User> getAllUsers() {
		return em.createQuery("select u from User u", User.class).getResultList();
	}
	
	@Override
	public User getByEmail(String email) {
		if (email != null) {
			List<User> list = em.createNamedQuery(User.GetByEmail, User.class).setParameter("email", email).getResultList();
			if (list.size() > 0) {
				return list.get(0);
			}
		}
		
		return null;
	}
	
	@Override
	public boolean exists(User user) {
		if (user.getEmail() == null) {
			return false;
		}
		if (em.createNamedQuery(User.GetByEmail, User.class).setParameter("email", user.getEmail()).getResultList().size() > 0) {
			return true;
		}
		
		return false;
	}

	//@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	@Override
	public void persist(User user) {
		em.persist(user);
		//generateDefaultCategories(user);
		
		Category family = new Category();
		family.setName("Rodzina");
		family.setOwner(user);
		em.persist(family);
		
		Category work = new Category();
		work.setName("Praca");
		work.setOwner(user);
		em.persist(work);
		
		Category others = new Category();
		others.setName("Inne");
		others.setOwner(user);
		em.persist(others);
		
		em.flush();
		em.refresh(user);
	}

	private void generateDefaultCategories(User user) {
		
		
	}


	@Override
	public User getById(Long id) {
		User u = em.find(User.class, id);
		return u;
	}
}
