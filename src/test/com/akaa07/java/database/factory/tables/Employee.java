package com.akaa07.java.database.factory.tables;

import com.akaa07.java.database.factory.core.TableDefine;

public class Employee extends TableDefine
{
	/** テーブル名 */
	public final static String TABLE_NAME = "employee";

	public final static String ID = "id";
	public final static String DEPARTMENT_ID = "department_id";
	public final static String NAME = "name";
	public final static String GENDER = "gender";

	/**
	 * テーブル名を返却します。
	 *
	 * @return String テーブル名
	 */
	public String getTableName()
	{
		return TABLE_NAME;
	}

	public void status_default()
	{
		String departmentId = this.fakerRegexify("[a-z0-9]{5}");

		if (this.stackbox.contains(Department.TABLE_NAME))
		{
			departmentId = this.stackbox.getTable(Department.TABLE_NAME).get(0).getString(Department.ID);
		}

		this.column(Employee.ID, this.fakerRegexify("[a-z0-9]{10}"))
			.column(Employee.DEPARTMENT_ID, departmentId)
			.column(Employee.NAME, faker.name().fullName())
			.column(Employee.GENDER, 0);
	}

	public void status_TemporaryRegistration()
	{
		this.column(Employee.ID, this.fakerRegexify("[a-z0-9]{10}"))
			.column(Employee.DEPARTMENT_ID, null)
			.column(Employee.NAME, null)
			.column(Employee.GENDER, 0);
	}
}
