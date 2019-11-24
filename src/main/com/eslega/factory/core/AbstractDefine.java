package com.eslega.factory.core;

import java.util.Locale;

import com.github.javafaker.Faker;
import com.github.javafaker.service.FakeValuesService;
import com.github.javafaker.service.RandomService;
import com.ninja_squad.dbsetup.operation.Operation;

public abstract class AbstractDefine<T>
{
	/** Facker */
	protected static Faker faker = new Faker(new Locale("ja_JP"));
	protected static FakeValuesService fakeValuesService = new FakeValuesService(new Locale("ja-JP"),
			new RandomService());

	/**
	 * データセット定義のインスタンスを生成します。
	 *
	 * @return
	 */
	public static AbstractDefine<?> forClass(Class<? extends AbstractDefine<?>> defineClass) throws Exception
	{
		// データセット定義をインスタンス化する
		// 初期値で登録する際にわざわざstateメソッドを呼び出さなくて良いようにするため、
		// status_defaultメソッドを固定で呼び出す。
		AbstractDefine<?> def = (AbstractDefine<?>) defineClass.newInstance();
		def.status_default();

		return def;
	}

	/**
	 * 正規表現を元にランダムに値を生成します。
	 *
	 * @param String regex
	 * @return String
	 */
	protected String fakerRegexify(String regex)
	{
		return fakeValuesService.regexify(regex);
	}

	/**
	 * レコード登録を行うオペレーションオブジェクトを生成します。
	 *
	 * @return
	 */
	abstract Operation build();

	/**
	 * 現在設定されている属性値を取得します。
	 *
	 * @return
	 */
	abstract T getValues();

	/**
	 * デフォルトの属性値を設定します。
	 *
	 */
	abstract public void status_default() throws Exception;
}
