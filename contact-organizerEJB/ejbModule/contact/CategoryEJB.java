package contact;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

//@TransactionAttribute(TransactionAttributeType.NEVER)
@Stateless
@LocalBean
public class CategoryEJB extends AbstractEJB implements CategoryEJBRemote {

	@Override
	public Category update(Category category) {
		if (category != null && category.getId() != null) {
			Category c = em.find(Category.class, category.getId());
			em.refresh(c);
			c.setAllContacts(null);
			return c;
		}
		
		return null;
	}

	@Override
	public void persist(Category category) {
		em.persist(category);
		em.flush();
	}
}
