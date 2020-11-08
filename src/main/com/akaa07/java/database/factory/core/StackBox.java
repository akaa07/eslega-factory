/**
 *
 */
package com.akaa07.java.database.factory.core;

import java.util.ArrayList;
import java.util.HashMap;

import com.ninja_squad.dbsetup.destination.DataSourceDestination;

/**
 *
 */
public class StackBox
{
	/** テーブル定義リスト */
	private final HashMap<String, ArrayList<TableDefine>> tables;

	/** データソース */
	private DataSourceDestination destination = null;

	/**
	 * コンストラクタ
	 * @param destination
	 */
	public StackBox(DataSourceDestination destination)
	{
		tables = new HashMap<String, ArrayList<TableDefine>>();

		// builderで使用するため持ちたくないけど持っておく
		this.destination = destination;
	}

	/**
	 * テーブル定義を保持する。
	 *
	 * @param p_def テーブル定義
	 */
	public void stack(TableDefine p_def)
	{
		RelationDefine tmp = new TmpRelationDefine();

		tmp.add(p_def);

		this.stack(tmp);
	}

	/**
	 * リレーション定義を保持する。
	 *
	 * @param p_def リレーション定義
	 */
	public void stack(RelationDefine p_def)
	{
		for (String tableName : p_def.getTableDefineList().keySet())
		{

			if (tables.containsKey(tableName))
			{
				tables.get(tableName)
						.addAll(p_def.getTableDefineList(tableName));
			}
			else
			{
				tables.put(tableName, p_def.getTableDefineList(tableName));
			}
		}
	}

	/**
	 * ビルダーからリレーション定義を保持する。
	 *
	 * @param builder リレーション定義ビルダー
	 */
	public void stack(RelationBuilder builder)
	{
		this.stack((RelationDefine) builder.getDefine());
	}

	/**
	 * 保持している定義を破棄する。
	 *
	 */
	public void flush()
	{
		tables.clear();
	}

	/**
	 * 保持している定義からテーブルに登録する。
	 *
	 * @return 構築したデータセット
	 */
	public RelationData save()
	{
		TmpBuilder tmpBuilder = new TmpBuilder(new TmpRelationDefine(tables));
		return (RelationData) tmpBuilder.save();
	}

	/**
	 * 保持している定義からデータを作成する。
	 *
	 * @return 構築したデータセット
	 */
	public RelationData make()
	{
		TmpBuilder tmpBuilder = new TmpBuilder(new TmpRelationDefine(tables));
		return (RelationData) tmpBuilder.make();
	}

	/**
	 * 指定されたテーブルが保持されているか判定する。
	 *
	 * @param tableName
	 * @return 指定されたテーブルが保持されている場合true
	 */
	public boolean contains(String tableName)
	{
		return tables.containsKey(tableName);
	}

	/**
	 * 指定されたテーブルのレコードを返却する。
	 *
	 * @param tableName
	 * @return 指定されたテーブルのレコード
	 */
	public ArrayList<TableData> getTable(String tableName)
	{
		return this.make().getRecords(tableName);
	}

	/**
	 * 指定されたカラム名に一致するデータを返却する。
	 *
	 * @return キー情報
	 */
	public String[] getKeys(String columnName)
	{
		ArrayList<String> keys = new ArrayList<String>();

		HashMap<String, ArrayList<TableData>> data = this.make().getRawData();

		for (String tableName : data.keySet())
		{
			for (TableData table : data.get(tableName))
			{
				if (table.contains(columnName) == false)
				{
					continue;
				}

				if (table.getString(columnName) == null)
				{
					continue;
				}

				if (table.getString(columnName).isEmpty())
				{
					continue;
				}

				keys.add(table.getString(columnName));
			}
		}

		return keys.toArray(new String[keys.size()]);
	}

	/**
	 * RelationDefineを一時的に使う用
	 * TableDefineからの変換や、RelationBuilderの生成に利用するなど。
	 *
	 */
	private class TmpRelationDefine extends RelationDefine
	{
		public TmpRelationDefine()
		{
		}

		public TmpRelationDefine(HashMap<String, ArrayList<TableDefine>> p_tables)
		{
			// RelationDefine.tables
			this.tables.putAll(p_tables);
		}

		public void status_default()
		{
		}
	};

	/**
	 * AbstractBuilderの#makeと#saveが使いたかっただけ
	 *
	 *
	 */
	private class TmpBuilder extends AbstractBuilder<RelationData>
	{
		private RelationDefine def;

		public TmpBuilder(RelationDefine p_def)
		{
			def = p_def;
			dest =destination;
		}

		/**
		 * リレーション定義を返却します。
		 *
		 * @return RelationDefine
		 */
		protected AbstractDefine<RelationData> getDefine()
		{
			return def;
		}
	}
}
