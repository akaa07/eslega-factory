package com.akaa07.java.database.factory;

import javax.sql.DataSource;

import com.akaa07.java.database.factory.core.RelationBuilder;
import com.akaa07.java.database.factory.core.RelationData;
import com.akaa07.java.database.factory.core.RelationDefine;
import com.akaa07.java.database.factory.core.StackBox;
import com.akaa07.java.database.factory.core.TableBuilder;
import com.akaa07.java.database.factory.core.TableDefine;
import com.ninja_squad.dbsetup.destination.DataSourceDestination;

public class Factory
{
	/** データソース */
	private DataSourceDestination destination = null;

	/** テーブル定義を持ちまわる */
	private StackBox stackbox = null;

	/**
	 * コンストラクタ
	 *
	 * @param dataSource
	 */
	public Factory(DataSource dataSource)
	{
		destination = new DataSourceDestination(dataSource);
		stackbox = new StackBox(destination);
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
		return new TableBuilder(destination, defineClass, stackbox);
	}

	/**
	 * リレーションのデータセット構築を開始します。
	 *
	 * @param defineClass リレーション定義のクラスオブジェクト
	 * @return RelationBuilder
	 * @throws Exception
	 */
	public RelationBuilder relation(Class<? extends RelationDefine> defineClass) throws Exception
	{
		return new RelationBuilder(destination, defineClass, stackbox);
	}

	/**
	 * 保持しているデータセットを破棄します。
	 *
	 * @return void
	 */
	public void flush()
	{
		stackbox.flush();
	}

	/**
	 * データを登録します。
	 *
	 * @return 構築したデータセット
	 */
	public RelationData save()
	{
		return stackbox.save();
	}

	/**
	 * データを作成します。
	 *
	 * @return 構築したデータセット
	 */
	public RelationData make()
	{
		return stackbox.make();
	}
}
