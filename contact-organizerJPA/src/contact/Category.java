package contact;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Transient;

@Entity
public class Category implements Serializable {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	@Column(nullable=false)
	private String name;
	@ManyToOne(optional = false)
	private User owner;
	@ManyToOne
	private Category parent;
	@OneToMany(mappedBy = "parent")
	@OrderBy("name")
	private Set<Category> children;
	@ManyToMany(mappedBy="belongs", fetch=FetchType.EAGER)
	private Set<Contact> holds;
	
	@Transient
	private Map<String, Contact> allContacts;
	
	
	private static final long serialVersionUID = 1L;

	public boolean hasParent() {
		return parent != null; 
	}
	
	public boolean hasChildren() {
		if (children != null) {
			return children.size() > 0;
		}
		
		return false;
	}

	public void setAllContacts(Map<String, Contact> allContacts) {
		this.allContacts = allContacts;
	}
	
	public Map<String, Contact> getAllContacts() {
		if (allContacts == null) {
			retrieveAllContacts();
		}
		
		return allContacts;
	}
	
	private void retrieveAllContacts() {
		TreeMap<String, Contact> map = new TreeMap<String, Contact>();
		
		if (holds != null) {
			for (Contact c: holds) {
				map.put(c.getKey(), c);
			}
		}
		
		if (children != null) {
			for (Category cat : children) {
				for (Contact c: cat.getAllContacts().values()) {
					map.put(c.getKey(), c);
				}
			}
		}
		
		allContacts = map;
	}

	public Category() {
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
	public User getOwner() {
		return this.owner;
	}

	public void setOwner(User owner) {
		this.owner = owner;
	}
	public Category getParent() {
		return parent;
	}
	public void setParent(Category parent) {
		this.parent = parent;
	}
	public Set<Category> getChildren() {
		return children;
	}
	public void setChildren(Set<Category> children) {
		this.children = children;
	}
	public Set<Contact> getHolds() {
		return holds;
	}
	public void setHolds(Set<Contact> holds) {
		this.holds = holds;
	}
   
	
}
