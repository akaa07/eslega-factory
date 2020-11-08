package com.akaa07.java.database.factory.core;

import java.lang.reflect.Method;

import com.ninja_squad.dbsetup.DbSetup;
import com.ninja_squad.dbsetup.destination.DataSourceDestination;
import com.ninja_squad.dbsetup.operation.Operation;

public abstract class AbstractBuilder<T>
{
	/** DbSetupの接続先 */
	protected DataSourceDestination dest = null;

	/**
	 * データセット定義を返却します。
	 *
	 * @return
	 */
	abstract protected AbstractDefine<T> getDefine();

	/**
	 * データセット定義に登録されたデータセットの状態にします。
	 *
	 * @param String className
	 * @return this
	 */
	public AbstractBuilder<?> state(String name) throws Exception
	{
		Method method = getDefine().getClass().getMethod("status_" + name);
		method.invoke(getDefine());

		return this;
	}

	/**
	 * データセットをDBに登録します。
	 *
	 * @return HashMap 登録したデータセット
	 */
	protected void launch(Operation op)
	{
		DbSetup dbSetup = new DbSetup(dest, op);
		dbSetup.launch();
	}

	/**
	 * 構築されたデータセットをDBに登録します。
	 *
	 * @return 登録したデータセット
	 */
	public T save()
	{
		Operation op = getDefine().build();

		launch(op);

		return getDefine().getValues();
	}

	/**
	 * 構築されたデータセットを返却します。 DB登録は行いません。
	 *
	 * @return 登録したデータセット
	 */
	public T make()
	{
		return getDefine().getValues();
	}
}
