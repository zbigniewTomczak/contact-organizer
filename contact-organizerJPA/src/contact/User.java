package contact;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;

@Entity
@NamedQueries(
		@NamedQuery(name=User.GetByEmail, query="SELECT u FROM User u WHERE u.email=:email")
		)
public class User implements Serializable {

	public static final String GetByEmail = "GetByEmail";
	   
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	@Column(nullable=false)
	private String name;
	@Column(nullable=false)
	private String surname;
	@Column(unique=true, nullable=false)
	private String email;
	@Column(nullable=false)
	private Byte[] password;
	
	@OneToMany(mappedBy="owner", fetch=FetchType.EAGER)
	@OrderBy("name")
	private List<Category> categories;
	
	private static final long serialVersionUID = 1L;

	public List<Category> getRootCategories() {
		List<Category> list = new ArrayList<Category>();
		if (categories != null) {
			for (Category c : this.getCategories()) {
				if (!c.hasParent()) {
					list.add(c);
				}
			}
		}
		
		return list;
	}
	
	public User() {
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
	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}   
	public Byte[] getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = convertToMd5(password);
	}
	
	public List<Category> getCategories() {
		return categories;
	}
	public void setCategories(List<Category> categories) {
		this.categories = categories;
	}
	
	private Byte[] convertToMd5(String password) {
		MessageDigest md = null;
		byte[] bytes = null;
		try {
			md = MessageDigest.getInstance("MD5");
			bytes = password.getBytes("UTF-8");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		if (md != null && bytes != null) {
			bytes = md.digest(bytes);
			Byte[] md5Bytes = new Byte[bytes.length];
			for(int i = 0; i < bytes.length; i++) {
				md5Bytes[i] = bytes[i];
			}
			return md5Bytes;
		}
		
		return null;
		
	}
   
	public boolean passwordMatch(String password) {
		return Arrays.equals(this.password, convertToMd5(password));
	}
	public void trimAll() {
		name = name != null ? name.trim() : null;
		surname = surname != null ? surname.trim() : null;
		email = email != null ? email.trim() : null;
	}
}
