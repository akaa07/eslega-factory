package com.akaa07.java.database.factory.relations;

import com.akaa07.java.database.factory.core.RelationDefine;
import com.akaa07.java.database.factory.tables.Department;
import com.akaa07.java.database.factory.tables.Employee;

public class CompanyRelation extends RelationDefine
{
	public void status_default() throws Exception
	{
		Department dep = (Department) table(Department.class);

		String depId = (String) dep.getValue(Department.ID);
		Employee e1 = (Employee) table(Employee.class).column(Employee.ID, depId);
		Employee e2 = (Employee) table(Employee.class).column(Employee.ID, depId);

		this.add(dep, e1, e2);
	}

	public void status_single() throws Exception
	{
		Department dep = (Department) table(Department.class);

		String depId = (String) dep.getValue(Department.ID);
		Employee e1 = (Employee) table(Employee.class).column(Employee.ID, depId);

		this.add(dep, e1);
	}
}
