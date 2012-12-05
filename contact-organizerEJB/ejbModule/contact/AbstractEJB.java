package contact;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;

public abstract class AbstractEJB {
	@PersistenceContext(name="KontaktJPA", type=PersistenceContextType.EXTENDED)
    protected EntityManager em;
    
    
}
