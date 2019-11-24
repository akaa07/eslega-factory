package com.eslega.factory.patterns;

import com.eslega.factory.core.PatternDefine;
import com.eslega.factory.tables.Department;
import com.eslega.factory.tables.Employee;

public class CompanyPattern extends PatternDefine
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
