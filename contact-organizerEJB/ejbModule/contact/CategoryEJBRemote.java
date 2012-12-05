package contact;

import javax.ejb.Remote;

@Remote
public interface CategoryEJBRemote {
	Category update(Category category);

	void persist(Category category);
}
