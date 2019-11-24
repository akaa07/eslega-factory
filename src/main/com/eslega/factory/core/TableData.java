package com.eslega.factory.core;

import java.util.LinkedHashMap;

public class TableData
{
	/** テーブル名 */
	private final String tableName;

	/** 属性値マップ */
	private final LinkedHashMap<String, Object> values;

	/**
	 * コンストラクタ
	 *
	 * @param tableName
	 */
	protected TableData(String tableName)
	{
		this.tableName = tableName;
		this.values = new LinkedHashMap<String, Object>();
	}

	/**
	 * 属性値を保持します。
	 *
	 * @param columnName
	 * @param value
	 */
	protected void put(String columnName, Object value)
	{
		values.put(columnName, value);
	}

	/**
	 * テーブル名を返却します。
	 *
	 * @return テーブル名
	 */
	public String getTableName()
	{
		return tableName;
	}

	/**
	 * 属性値をオブジェクトとして取得します。
	 *
	 * @param columnName
	 * @return
	 */
	public Object getObject(String columnName)
	{
		return values.get(columnName);
	}

	/**
	 * 属性値を文字列として取得します。
	 *
	 * @param columnName
	 * @return
	 */
	public String getString(String columnName)
	{
		return (String) values.get(columnName);
	}

	/**
	 * 属性値を整数として取得します。
	 *
	 * @param columnName
	 * @return
	 */
	public int getInt(String columnName)
	{
		return (Integer) values.get(columnName);
	}
}
