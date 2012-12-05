package contact;

import java.util.Locale;
import java.util.ResourceBundle;

import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.context.FacesContext;

public abstract class AbstractBean {
	
	final static protected String BUNDLE = "resources";
	
	FacesMessage getMessage(String name) {
		ResourceBundle bundle = getBundle();

		return new FacesMessage(FacesMessage.SEVERITY_ERROR, bundle.getString(name), "");
	}

	FacesMessage getMessage(Severity severity, String name) {
		ResourceBundle bundle = getBundle();

		return new FacesMessage(severity, bundle.getString(name), "");
	}

	public ResourceBundle getBundle() {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		String messageBundleName = BUNDLE;
		//String messageBundleName = facesContext.getApplication().getMessageBundle();
		Locale locale = facesContext.getViewRoot().getLocale();
		ResourceBundle bundle = ResourceBundle.getBundle(messageBundleName, locale);
		return bundle;
	}

	public FacesContext getFacesContext() {
		return FacesContext.getCurrentInstance();
	}
	
	public void addErrorMessage(String name) {
		getFacesContext().addMessage(null, getMessage(name));
	}
	public void addInfoMessage(String name) {
		getFacesContext().addMessage(null, getMessage(FacesMessage.SEVERITY_INFO, name));
	}

	public void preserveMessages() {
		getFacesContext().getExternalContext().getFlash().setKeepMessages(true);
	}
	
	public boolean isNullOrEmpty(String string) {
		if (string != null) {
			if (!string.isEmpty()) {
				return false;
			}
		}
		
		return true;
	}
}
