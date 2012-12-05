package contact;

import java.util.List;

import javax.ejb.Remote;

@Remote
public interface UserEJBRemote {
	List<User> getAllUsers();
	boolean exists(User user);
	void persist(User user);
	User getByEmail(String email);
	User getById(Long id);

}
