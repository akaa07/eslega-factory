package com.akaa07.java.database.factory.core;

import static com.ninja_squad.dbsetup.Operations.*;

import java.util.ArrayList;
import java.util.HashMap;

import com.ninja_squad.dbsetup.operation.Operation;

public abstract class PatternDefine extends AbstractDefine<PatternData>
{
	/** テーブル定義リスト */
	private final HashMap<String, ArrayList<TableDefine>> tables;

	/**
	 * コンストラクタ
	 */
	public PatternDefine()
	{
		tables = new HashMap<String, ArrayList<TableDefine>>();
	}

	/**
	 * セットアップ後のテーブルを保持します。
	 *
	 * @param tableDefine
	 * @return
	 */
	public PatternDefine add(TableDefine... tableDefine)
	{
		for (TableDefine def : tableDefine)
		{
			if (tables.containsKey(def.getTableName()))
			{
				// テーブルが既に存在する場合は、リストに追加する
				tables.get(def.getTableName()).add(def);
			}
			else
			{
				// テーブルがまだ存在しない場合は、新しくマップにキーを追加する
				ArrayList<TableDefine> rows = new ArrayList<TableDefine>();
				rows.add(def);

				tables.put(def.getTableName(), rows);
			}
		}

		return this;
	}

	/**
	 * 保持しているテーブル情報をクリアします。
	 *
	 */
	public void clear()
	{
		tables.clear();
	}

	/**
	 * テーブル定義を返却します。
	 *
	 * @param defineClass
	 * @return
	 * @throws Exception
	 */
	public TableDefine table(Class<? extends TableDefine> defineClass) throws Exception
	{
		return (TableDefine) TableDefine.forClass(defineClass);
	}

	/**
	 * テーブル定義のリストを返却します。
	 *
	 * @see com.akaa07.java.database.factory.core.PatternBuilder#table
	 * @param tableName
	 * @return
	 */
	protected ArrayList<TableDefine> getTableDefineList(String tableName)
	{
		return tables.get(tableName);
	}

	/**
	 * レコード登録を行うオペレーションオブジェクトを生成します。
	 *
	 * @see com.akaa07.java.database.factory.core.AbstractBuilder#save
	 * @return
	 */
	public Operation build()
	{
		Operation ops = null;

		for (String tableName : tables.keySet())
		{
			for (TableDefine def : tables.get(tableName))
			{
				if (ops == null)
				{
					ops = def.build();
				}
				else
				{
					ops = sequenceOf(ops, def.build());
				}
			}
		}

		return ops;
	}

	/**
	 * 現在設定されている属性値を取得します。
	 *
	 * @see com.akaa07.java.database.factory.core.AbstractBuilder#save
	 * @see com.akaa07.java.database.factory.core.AbstractBuilder#make
	 * @return
	 */
	public PatternData getValues()
	{
		return new PatternData(tables);
	}

}
