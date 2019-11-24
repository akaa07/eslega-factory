package com.eslega.factory.tables;

import com.eslega.factory.core.TableDefine;

public class Department extends TableDefine
{
	/** テーブル名 */
	public final static String TABLE_NAME = "department";

	public static String ID = "id";
	public static String NAME = "name";

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
		this.column(Department.ID, this.fakerRegexify("[a-z0-9]{5}"))
			.column(Department.NAME, faker.job().field());
	}
}
