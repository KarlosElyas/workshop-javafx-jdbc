package model.services;

import java.util.List;

import model.dao.DaoFactory;
import model.dao.DepartmentDao;
import model.entities.Department;

public class DepartmentService { // esta classe é a ponte entre javafx com o jdbc
	private DepartmentDao dao = DaoFactory.createDepartmentDao();
	
	public List<Department> findAll() {
		return dao.findAll(); // retorna o metodo do DepartmentDaoJDBC
	}
}
