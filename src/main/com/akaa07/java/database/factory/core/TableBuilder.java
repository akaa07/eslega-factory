package com.akaa07.java.database.factory.core;

import java.util.HashMap;

import com.ninja_squad.dbsetup.destination.DataSourceDestination;

public class TableBuilder extends AbstractBuilder<TableData>
{
	/** テーブル定義 */
	private TableDefine def = null;

	/**
	 * コンストラクタ
	 *
	 * @param destination	データソース
	 * @param defineClass	テーブル定義クラス
	 * @param stackbox		データセットのスタック
	 * @return void
	 */
	public TableBuilder(DataSourceDestination destination, Class<? extends TableDefine> defineClass, StackBox stackbox) throws Exception
	{
		this.dest = destination;
		this.def = (TableDefine) TableDefine.forClass(defineClass, stackbox);
	}

	/**
	 * テーブル定義を返却します。
	 *
	 * @return TableDefine
	 */
	protected AbstractDefine<TableData> getDefine()
	{
		return def;
	}

	/**
	 * テーブル定義に登録されたデータセットの状態にします。 属性値が渡された場合、その属性値が優先されます。
	 *
	 * @param name データセット名
	 * @param values 設定する属性値のマップ
	 * @return ビルダ
	 * @throws Exception
	 */
	public TableBuilder state(String name, HashMap<String, ?> values) throws Exception
	{
		this.state(name);

		def.column(values);

		return this;
	}

	/**
	 * データセットを蓄積し、データビルドを終了します。
	 *
	 * @return void
	 */
	public void stack()
	{
		def.stackbox.stack(this);
	}

	/**
	 * レコードの属性値を設定します。
	 *
	 * @param name
	 * @param value
	 * @return
	 */
	public TableBuilder column(String name, Object value)
	{
		def.column(name, value);

		return this;
	}

	/**
	 * レコードの属性値を設定します。
	 *
	 * @param values
	 * @return
	 */
	public TableBuilder column(HashMap<String, ?> values)
	{
		def.column(values);

		return this;
	}
}
