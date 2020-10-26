package com.akaa07.java.database.factory;

import javax.sql.DataSource;

import com.akaa07.java.database.factory.core.PatternBuilder;
import com.akaa07.java.database.factory.core.PatternDefine;
import com.akaa07.java.database.factory.core.TableBuilder;
import com.akaa07.java.database.factory.core.TableDefine;
import com.ninja_squad.dbsetup.destination.DataSourceDestination;

public class Factory
{
	/** データソース */
	private static DataSourceDestination destination = null;

	/**
	 * コンストラクタ インスタンス生成を制限する。
	 *
	 * @return void
	 */
	private Factory()
	{
	}

	/**
	 * データソースを設定します。
	 * 使用する前に呼び出してください。
	 *
	 * @param dataSource
	 */
	public static void setDataSource(DataSource dataSource)
	{
		destination = new DataSourceDestination(dataSource);
	}

	/**
	 * テーブルのデータセット構築を開始します。
	 *
	 * @param defineClass テーブル定義のクラスオブジェクト
	 * @return TableBuilder
	 * @throws Exception
	 */
	public static TableBuilder table(Class<? extends TableDefine> defineClass) throws Exception
	{
		checkState();
		return new TableBuilder(destination, defineClass);
	}

	/**
	 * パターンのデータセット構築を開始します。
	 *
	 * @param defineClass パターン定義のクラスオブジェクト
	 * @return PatternBuilder
	 * @throws Exception
	 */
	public static PatternBuilder pattern(Class<? extends PatternDefine> defineClass) throws Exception
	{
		checkState();
		return new PatternBuilder(destination, defineClass);
	}

	/**
	 * データセット構築可能な状態かチェックします。
	 *
	 * @throws IllegalStateException
	 */
	private static void checkState() throws IllegalStateException
	{
		if (destination == null)
		{
			throw new IllegalStateException("Should be called after setting the setDataSource method.");
		}
	}
}
