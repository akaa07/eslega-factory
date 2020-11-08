package com.akaa07.java.database.factory.core;

import static com.ninja_squad.dbsetup.Operations.*;

import java.util.HashMap;

import com.ninja_squad.dbsetup.operation.Insert.RowBuilder;
import com.ninja_squad.dbsetup.operation.Operation;

public abstract class TableDefine extends AbstractDefine<TableData>
{
	/** 属性値マップ DbSetupでは属性値を取り出すことができないため、ラップして属性値を保持する。 */
	private TableData tableData = new TableData(getTableName());

	/** レコード行を格納するDbSetupのオブジェクト */
	private RowBuilder row = null;

	/**
	 * コンストラクタ
	 */
	protected TableDefine()
	{
		// 1インスタンスにつき1レコードのみ許容する
		this.row();
	}

	/**
	 * テーブル名を返却します。
	 *
	 */
	abstract public String getTableName();

	/**
	 * 新しいレコードの構築を開始します。
	 *
	 * @return
	 */
	protected TableDefine row()
	{
		this.row = insertInto(getTableName()).row();

		return this;
	}

	/**
	 * レコードの属性値を設定します。
	 *
	 * @param String name 列名
	 * @param Object value 値
	 * @return テーブル定義
	 */
	public TableDefine column(String name, Object value)
	{
		row = row.column(name, value);

		// 値を退避する。
		tableData.put(name, value);

		return this;
	}

	/**
	 * レコードの属性値を設定します。
	 *
	 * @param HashMap<String, Object> データセット
	 * @return テーブル定義
	 */
	public TableDefine column(HashMap<String, ?> values)
	{
		for (String columnName : values.keySet())
		{
			column(columnName, values.get(columnName));
		}

		return this;
	}

	/**
	 * レコード登録を行うオペレーションオブジェクトを生成します。
	 *
	 */
	public Operation build()
	{
		return row.end().build();
	}

	/**
	 * 現在設定されている属性値を取得します。
	 *
	 * @return 構築したデータセット
	 */
	public Object getValue(String name)
	{
		return tableData.getObject(name);
	}

	/**
	 * 現在設定されている属性値を取得します。
	 *
	 * @return 構築したデータセット
	 */
	public TableData getValues()
	{
		return tableData;
	}
}
