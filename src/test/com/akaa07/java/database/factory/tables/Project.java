package com.akaa07.java.database.factory.tables;

import com.akaa07.java.database.factory.core.TableDefine;

public class Project extends TableDefine
{
	/** テーブル名 */
	public final static String TABLE_NAME = "project";

	public final static String ID = "id";
	public final static String DEPARTMENT_ID = "department_id";
	public final static String NAME = "name";

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

		this.column(Project.ID, this.fakerRegexify("[a-z0-9]{10}"))
			.column(Project.DEPARTMENT_ID, departmentId)
			.column(Project.NAME, faker.name().fullName());
	}
}
