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
		PatternDefine tmp = new TmpPatternDefine();

		tmp.add(p_def);

		this.stack(tmp);
	}

	/**
	 * パターン定義を保持する。
	 *
	 * @param p_def パターン定義
	 */
	public void stack(PatternDefine p_def)
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
	 * ビルダーからテーブル定義を保持する。
	 *
	 * @param builder テーブル定義ビルダー
	 */
	public void stack(TableBuilder builder)
	{
		this.stack((TableDefine) builder.getDefine());
	}

	/**
	 * ビルダーからパターン定義を保持する。
	 *
	 * @param builder パターン定義ビルダー
	 */
	public void stack(PatternBuilder builder)
	{
		this.stack((PatternDefine) builder.getDefine());
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
	public PatternData save()
	{
		TmpBuilder tmpBuilder = new TmpBuilder(new TmpPatternDefine(tables));
		return (PatternData) tmpBuilder.save();
	}

	/**
	 * 保持している定義からデータを作成する。
	 *
	 * @return 構築したデータセット
	 */
	public PatternData make()
	{
		TmpBuilder tmpBuilder = new TmpBuilder(new TmpPatternDefine(tables));
		return (PatternData) tmpBuilder.make();
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
	 * return キー情報
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

		return (String[]) keys.toArray();
	}

	/**
	 * PatternDefineを一時的に使う用
	 * TableDefineからの変換や、PatternBuilderの生成に利用するなど。
	 *
	 */
	private class TmpPatternDefine extends PatternDefine
	{
		public TmpPatternDefine()
		{
		}

		public TmpPatternDefine(HashMap<String, ArrayList<TableDefine>> p_tables)
		{
			// PatternDefine.tables
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
	private class TmpBuilder extends AbstractBuilder<PatternData>
	{
		private PatternDefine def;

		public TmpBuilder(PatternDefine p_def)
		{
			def = p_def;
			dest =destination;
		}

		/**
		 * パターン定義を返却します。
		 *
		 * @return PatternDefine
		 */
		protected AbstractDefine<PatternData> getDefine()
		{
			return def;
		}
	}
}
