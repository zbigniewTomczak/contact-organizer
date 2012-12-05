package contact;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OrderBy;

/**
 * Entity implementation class for Entity: Contact
 *
 */
@Entity

public class Contact implements Serializable {

	   
	@Id
	@GeneratedValue
	private Long id;
	private String name;
	private String surname;
	private String phone;
	private String email;
	private String notes;
	@ManyToMany
	@OrderBy("name")
	private List<Category> belongs;
	
	private static final long serialVersionUID = 1L;

	public void addCategory(Category category) {
		if (belongs == null) {
			belongs = new ArrayList<Category>();
		}
		
		if (category.getId() == null) {
			return;
		}
		
		boolean contains = false;
		for (Category cat : belongs) {
			if (cat.getId().equals(category.getId())) {
				contains = true;
			}
		}
		
		if (! contains) {
			belongs.add(category);
		}
	}
	public Contact() {
		super();
	}   
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}   
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}   
	public String getSurname() {
		return this.surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}   
	public String getPhone() {
		return this.phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}   
	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}   
	public String getNotes() {
		return this.notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}
	public List<Category> getBelongs() {
		return belongs;
	}
	public void setBelongs(List<Category> belongs) {
		this.belongs = belongs;
	}
   
	public void trimAll() {
		name = name != null ? name.trim() : null;
		surname = surname != null ? surname.trim() : null;
		email = email != null ? email.trim() : null;
		phone = phone != null ? phone.trim() : null;
		notes = notes != null ? notes.trim() : null;
	}

	public String getKey() { 
		return name+surname;
	}
}
