package contact;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;

import org.primefaces.event.NodeSelectEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;

import contact.model.ContactsDataModel;

@ManagedBean
@ViewScoped
public class ContactBean extends AbstractBean {
	
	@ManagedProperty(value="#{loginBean}")
	private LoginBean loginBean;
	
	@Inject private ContactEJBRemote contactManager;
	@Inject private UserEJBRemote userManager;
	@Inject private CategoryEJBRemote categoryManager;
	
	private TreeNode categoryTreeRoot;
	 private TreeNode selectedNode;
	private Category selectedCategory;
	private boolean contactSelected;
	
	private Contact contact;
	private ContactsDataModel allContactsModel;

	private Category category;
	
	@PostConstruct
	private void constructCategoryTree() {
		User user = userManager.getById(loginBean.getCurrentUser().getId());
		categoryTreeRoot = new DefaultTreeNode("Root", null);
		for (Category c : user.getRootCategories()) {
			treeConstruct(c, categoryTreeRoot);
		}
	}
	
	private void treeConstruct(Category category, TreeNode parent) {
		TreeNode node = new DefaultTreeNode(category, parent);
		category = categoryManager.update(category);
		for (Category child : category.getChildren()) {
			treeConstruct(child, node);
		}
	}

	public ContactsDataModel getAllContactsModel() {
		if (allContactsModel == null) {
			//constructCategoryTree();
			selectedCategory = categoryManager.update(selectedCategory);
			allContactsModel = new ContactsDataModel(selectedCategory.getAllContacts());
		}
		
		return allContactsModel;		
	}

	public void newContact() {
		contact = new Contact();
		contactSelected = false;
	}
	
	public void edit() {
		contactSelected = false;
	}
	
	public void cancel() {
		if (isNewContact()) {
			contact = null;
		} else {
			contactSelected = true;
		}
	}
	
	public void save() {
		if (contact != null) {
			contact.trimAll();
			if (isNullOrEmpty(contact.getName()) && isNullOrEmpty(contact.getSurname())) {
				addErrorMessage("emptyNameAndSurname");
				return;				
			}
			if (contactManager.contactExists(contact)) {
				addErrorMessage("contactExists");
				return;
			}
			if (contact.getId() != null) {
				contactManager.merge(contact);
				addInfoMessage("saved");
				contactSelected = true;
			} else {
				contact.addCategory(selectedCategory); 
				contactManager.persist(contact);
				addInfoMessage("contactAdd");
				contact = null;
				contactSelected = false;
			}
			selectedCategory = categoryManager.update(selectedCategory);
			allContactsModel = null;
		}
	}
	
	public void onNodeSelect(NodeSelectEvent event) {
		TreeNode selectedNode = event.getTreeNode();
		if (selectedNode.getData() instanceof Category) {
			selectedCategory = (Category) selectedNode.getData();
			contact = null;
			allContactsModel = null;
			contactSelected = false;
			return;
		}		
    }

	public void onNodeDeselect(NodeSelectEvent event) {
		selectedCategory = null;
		allContactsModel = null;
		contact = null;
    }

	public void onRowSelect(SelectEvent event) {  
		if (event.getObject() instanceof Contact) {
			contact = (Contact) event.getObject();
			contactSelected = true;
			return;
		} 
    }
	
	public void onRowUnSelect(SelectEvent event) {  
		contact = null;
		contactSelected = false;
    }
	
	public void addCategoryPrepare() {
		category = new Category();
	}

	public void addCategory() {
		
		category.setName(category.getName().trim());
		if (isNullOrEmpty(category.getName())) {
			addErrorMessage("emptyName");
			return;
		}
		
		if (selectedCategory != null) {
			category.setOwner(selectedCategory.getOwner());
			category.setParent(selectedCategory);
			categoryManager.persist(category);
		}
		
		addInfoMessage("categoryAdded");
		constructCategoryTree();
		//selectCategory(category, categoryTreeRoot);
		category = null;
	}

//	private void selectCategory(Category category, TreeNode parent) {
//		for (TreeNode node : parent.getChildren()) {
//			if (node.getData() instanceof Category) {
//				Category c = (Category) node.getData();
//				if (c.getId().equals(category.getId())) {
//					selectedNode = node;
//					selectedCategory = (Category) selectedNode.getData();
//					contact = null;
//					allContactsModel = null;
//					contactSelected = false;
//					return;
//				}
//			}
//			
//			selectCategory(category, node);
//		}
//	}

	public TreeNode getCategoryTreeRoot() {
		return categoryTreeRoot;
	}

	public LoginBean getLoginBean() {
		return loginBean;
	}

	public void setLoginBean(LoginBean loginBean) {
		this.loginBean = loginBean;
	}

	public Category getSelectedCategory() {
		return selectedCategory;
	}

	public void setSelectedCategory(Category selectedCategory) {
		this.selectedCategory = selectedCategory;
	}

	public boolean isContactSelected() {
		return contactSelected;
	}
	
	public boolean isCategorySelected() {
		return selectedCategory != null;
	}

	public Contact getContact() {
		return contact;
	}

	public void setContact(Contact contact) {
		this.contact = contact;
	}

	public boolean isNewContact() {
		return contact != null && contact.getId() == null && !contactSelected;
	}

	public boolean isEditContact() {
		return contact != null && contact.getId() != null && !contactSelected;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public TreeNode getSelectedNode() {
		return selectedNode;
	}

	public void setSelectedNode(TreeNode selectedNode) {
		this.selectedNode = selectedNode;
	}

	
}
