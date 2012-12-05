package contact;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ComponentSystemEvent;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@ManagedBean
@SessionScoped
public class LoginBean extends AbstractBean{
	
	@Inject UserEJBRemote userManager;
	
	private User currentUser;

	private String password;
	private String email;

	public boolean isLoggedIn() {
		if (currentUser != null) {
			return true;
		}

		return false; 
	}
	
	public String logout() {
		currentUser = null;
		preserveMessages();
		addInfoMessage("logoutSuccesful");
		return "login";
	}
	
	public String login() {
		HttpServletRequest req = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
		HttpServletResponse res = (HttpServletResponse)FacesContext.getCurrentInstance().getExternalContext().getResponse();
		System.out.println(req.getCharacterEncoding());
		System.out.println(req.getContentType());
		System.out.println(res.getCharacterEncoding());
		System.out.println(res.getContentType());
		if (password != null && email != null) {
			password = password.trim();
			email = email.trim();
			
			User u = userManager.getByEmail(email);
			if (u != null) {
				if (u.passwordMatch(password)) {
					currentUser = u;
					password = null;
					return "index";
				} else {
					addErrorMessage("wrongPassword");
				}
			} else {
				addErrorMessage("userNotExists");
			}
		}
		password = null;
		currentUser = null;
		preserveMessages();
		return "login";
	}

	public void checkIfLogIn(ComponentSystemEvent evt) {
		if (!isLoggedIn()) {
			getFacesContext().getApplication().getNavigationHandler().handleNavigation(getFacesContext(), null, "login");
		}
	}
	
	public void checkIfNotLogIn(ComponentSystemEvent evt) {
		if (isLoggedIn()) {
			getFacesContext().getApplication().getNavigationHandler().handleNavigation(getFacesContext(), null, "index");
		}
	}
	
	public User getCurrentUser() {
		return currentUser;
	}

	
	public String getPassword() {
		return null;
	}

	public void setCurrentUser(User currentUser) {
		//this.currentUser = currentUser;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	
}
