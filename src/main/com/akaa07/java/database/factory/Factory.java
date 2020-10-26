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
	private DataSourceDestination destination = null;

	/**
	 * コンストラクタ
	 *
	 * @param dataSource
	 */
	public Factory(DataSource dataSource)
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
	public TableBuilder table(Class<? extends TableDefine> defineClass) throws Exception
	{
		return new TableBuilder(destination, defineClass);
	}

	/**
	 * パターンのデータセット構築を開始します。
	 *
	 * @param defineClass パターン定義のクラスオブジェクト
	 * @return PatternBuilder
	 * @throws Exception
	 */
	public PatternBuilder pattern(Class<? extends PatternDefine> defineClass) throws Exception
	{
		return new PatternBuilder(destination, defineClass);
	}
}
