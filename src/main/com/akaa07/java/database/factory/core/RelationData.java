package com.akaa07.java.database.factory.core;

import java.util.ArrayList;
import java.util.HashMap;

public class RelationData
{
	/** テーブル定義リスト */
	private final HashMap<String, ArrayList<TableData>> tables;

	/**
	 * コンストラクタ
	 *
	 * @param tables
	 */
	protected RelationData(HashMap<String, ArrayList<TableDefine>> tables)
	{
		this.tables = new HashMap<String, ArrayList<TableData>>();

		for (String tableName : tables.keySet())
		{
			ArrayList<TableData> tableDataList = new ArrayList<TableData>();

			for (TableDefine tableDefine : tables.get(tableName))
			{
				tableDataList.add(tableDefine.getValues());
			}

			this.tables.put(tableName, tableDataList);
		}
	}

	/**
	 * レコードを返却します。
	 *
	 * @param tableName
	 * @return 構築したデータセットのレコード
	 */
	public ArrayList<TableData> getRecords(String tableName)
	{
		return tables.get(tableName);
	}

	/**
	 * そのまま返却
	 *
	 * @return テーブル定義リスト
	 */
	protected HashMap<String, ArrayList<TableData>> getRawData()
	{
		return tables;
	}

}
