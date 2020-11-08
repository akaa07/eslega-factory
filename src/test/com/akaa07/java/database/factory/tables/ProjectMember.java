package com.akaa07.java.database.factory.tables;

import com.akaa07.java.database.factory.core.TableDefine;

public class ProjectMember extends TableDefine
{
	/** テーブル名 */
	public final static String TABLE_NAME = "project_member";

	public final static String ID = "id";
	public final static String PROJECT_ID = "project_id";
	public final static String EMPLOYEE_ID = "employee_id";

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
		String projectId = this.fakerRegexify("[a-z0-9]{16}");

		if (this.stackbox.getKeys(ProjectMember.PROJECT_ID).length > 0)
		{
			projectId = this.stackbox.getKeys(ProjectMember.PROJECT_ID)[0];
		}

		String employeeId = this.fakerRegexify("[a-z0-9]{10}");

		if (this.stackbox.contains(Employee.TABLE_NAME))
		{
			employeeId = this.stackbox.getTable(Employee.TABLE_NAME).get(0).getString(Employee.ID);
		}

		this.column(ProjectMember.ID, this.fakerRegexify("[a-z0-9]{10}"))
			.column(ProjectMember.PROJECT_ID, projectId)
			.column(ProjectMember.EMPLOYEE_ID, employeeId);
	}
}
