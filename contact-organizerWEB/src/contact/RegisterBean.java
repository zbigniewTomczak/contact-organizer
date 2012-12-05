package contact;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.inject.Inject;


@ManagedBean
@ViewScoped
public class RegisterBean extends AbstractBean{
	
	@Inject private UserEJBRemote userManager;

	@ManagedProperty(value="#{loginBean}")
	private LoginBean loginBean;
	
	private User user;
	private String password;
	private String passwordRepeat;
	
	private UIComponent emailInput;
	private UIComponent passwordInput;
	private UIComponent passwordRepeatInput;
	
	
	@PostConstruct
	public void init() {
		user = new User();
	}
	
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	public String register() {
		System.out.println(user.getName());
		user.trimAll();
		password = password.trim();
		passwordRepeat = passwordRepeat.trim();
		if (userManager.exists(user)) {
			FacesContext.getCurrentInstance().addMessage(emailInput.getClientId(), getMessage("emailExists"));
			return null;			
		}
		if (password.isEmpty()) {
			FacesContext.getCurrentInstance().addMessage(passwordInput.getClientId(), getMessage("emptyPassword"));
			return null;
		}
		if (passwordRepeat.isEmpty()) {
			FacesContext.getCurrentInstance().addMessage(passwordRepeatInput.getClientId(), getMessage("emptyPassword"));
			return null;
		}		
		if (!password.equals(passwordRepeat)) {
			FacesContext.getCurrentInstance().addMessage(passwordInput.getClientId(), getMessage("samePassword"));
			FacesContext.getCurrentInstance().addMessage(passwordRepeatInput.getClientId(), getMessage("samePassword"));
			return null;
		}		
		
		user.setPassword(password);
		userManager.persist(user);
		
		//categoryManager.generateDefaultCategories(user);

		loginBean.setEmail(user.getEmail());

		preserveMessages();
		addInfoMessage("registrationSuccess");
		
		return "login?faces-redirect=true";
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPasswordRepeat() {
		return passwordRepeat;
	}

	public void setPasswordRepeat(String passwordRepeat) {
		this.passwordRepeat = passwordRepeat;
	}

	public UIComponent getPasswordInput() {
		return passwordInput;
	}

	public void setPasswordInput(UIComponent passwordInput) {
		this.passwordInput = passwordInput;
	}

	public UIComponent getPasswordRepeatInput() {
		return passwordRepeatInput;
	}

	public void setPasswordRepeatInput(UIComponent passwordRepeatInput) {
		this.passwordRepeatInput = passwordRepeatInput;
	}

	public UIComponent getEmailInput() {
		return emailInput;
	}

	public void setEmailInput(UIComponent emailInput) {
		this.emailInput = emailInput;
	}

	public LoginBean getLoginBean() {
		return loginBean;
	}

	public void setLoginBean(LoginBean loginBean) {
		this.loginBean = loginBean;
	}
	
}
