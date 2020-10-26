/**
 *
 */
package com.akaa07.java.database.factory;

import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.HashMap;

import javax.sql.DataSource;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.akaa07.java.database.factory.core.PatternData;
import com.akaa07.java.database.factory.core.TableData;
import com.akaa07.java.database.factory.patterns.CompanyPattern;
import com.akaa07.java.database.factory.tables.Department;
import com.akaa07.java.database.factory.tables.Employee;

/**
 *
 *
 */
public class FacoryTest
{

	private DataSource datasource;

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception
	{
	}

	/**
	 * @throws java.lang.Exception
	 */
	@AfterClass
	public static void tearDownAfterClass() throws Exception
	{
	}

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception
	{
		datasource = mock(DataSource.class);
		Connection connection = mock(Connection.class);
		PreparedStatement preparedStatement = mock(PreparedStatement.class);
		when(datasource.getConnection()).thenReturn(connection);
		when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception
	{
	}

//	@Test(expected = IllegalStateException.class)
//	public void shouldThrowExceptionWhenDatasourceNotInitialize() throws Exception
//	{
//		// Factory factory = new Factory(datasource);
//		factory.table(Employee.class).save();
//	}

	/**
	 * テーブル定義からデータを登録できること。
	 *
	 * @throws Exception
	 */
	@Test
	public void tableTest() throws Exception
	{
		Factory factory = new Factory(datasource);

		TableData result = factory.table(Employee.class).save();

		Assert.assertNotNull(result.getString(Employee.ID));
		Assert.assertNotNull(result.getString(Employee.DEPARTMENT_ID));
		Assert.assertNotNull(result.getString(Employee.NAME));
	}

	/**
	 * テーブル定義からデータを生成できること。
	 *
	 * @throws Exception
	 */
	@Test
	public void tableMakeTest() throws Exception
	{
		Factory factory = new Factory(datasource);
		TableData result = factory.table(Employee.class).make();

		Assert.assertNotNull(result.getString(Employee.ID));
		Assert.assertNotNull(result.getString(Employee.DEPARTMENT_ID));
		Assert.assertNotNull(result.getString(Employee.NAME));
	}

	/**
	 * テーブル定義からデータを生成し、
	 * 属性値を変更できること。
	 *
	 * @throws Exception
	 */
	@Test
	public void tableChangeColumnValueTest() throws Exception
	{
		Factory factory = new Factory(datasource);
		String changedName = "Changed Name";
		TableData result = factory.table(Employee.class)
				.column(Employee.DEPARTMENT_ID, null)
				.column(Employee.NAME, changedName)
				.column(Employee.GENDER, 1)
				.save();

		Assert.assertNotNull(result.getString(Employee.ID));
		Assert.assertNull(result.getString(Employee.DEPARTMENT_ID));
		Assert.assertEquals(changedName, result.getString(Employee.NAME));
		Assert.assertEquals(1, result.getInt(Employee.GENDER));
	}

	/**
	 * テーブル定義からデータを生成し、
	 * 属性値をマップを使用して変更できること。
	 *
	 * @throws Exception
	 */
	@Test
	public void tableChangeColumnValuesTest() throws Exception
	{
		Factory factory = new Factory(datasource);
		HashMap<String, String> changedValues = new HashMap<String, String>();
		String changedName = "Changed Name";
		String changedDepartmentId = "123";
		changedValues.put(Employee.DEPARTMENT_ID, changedDepartmentId);
		changedValues.put(Employee.NAME, changedName);

		TableData result = factory.table(Employee.class)
				.column(changedValues)
				.save();

		Assert.assertNotNull(result.getString(Employee.ID));
		Assert.assertEquals(changedDepartmentId, result.getString(Employee.DEPARTMENT_ID));
		Assert.assertEquals(changedName, result.getString(Employee.NAME));
	}

	/**
	 * テーブル定義から特定の状態のデータを生成し、
	 * 属性値をマップを使用して変更できること。
	 *
	 * @throws Exception
	 */
	@Test
	public void tableSetStateWithChangedValuesTest() throws Exception
	{
		Factory factory = new Factory(datasource);
		HashMap<String, String> changedValues = new HashMap<String, String>();
		String changedName = "Changed Name";
		String changedDepartmentId = "123";
		changedValues.put(Employee.DEPARTMENT_ID, changedDepartmentId);
		changedValues.put(Employee.NAME, changedName);

		TableData result = factory.table(Employee.class)
				.state("TemporaryRegistration", changedValues)
				.save();

		Assert.assertNotNull(result.getString(Employee.ID));
		Assert.assertEquals(changedDepartmentId, result.getString(Employee.DEPARTMENT_ID));
		Assert.assertEquals(changedName, result.getString(Employee.NAME));
	}

	/**
	 * パターン定義からデータを登録できること。
	 *
	 * @throws Exception
	 */
	@Test
	public void patternTest() throws Exception
	{
		Factory factory = new Factory(datasource);
		PatternData result = factory.pattern(CompanyPattern.class).save();

		Assert.assertEquals(2, result.getRecords(Employee.TABLE_NAME).size());
		Assert.assertEquals(1, result.getRecords(Department.TABLE_NAME).size());
		Assert.assertEquals(Employee.TABLE_NAME, result.getRecords(Employee.TABLE_NAME).get(0).getTableName());
	}

	/**
	 * パターン定義からデータを生成できること。
	 *
	 * @throws Exception
	 */
	@Test
	public void patternMakeTest() throws Exception
	{
		Factory factory = new Factory(datasource);
		PatternData result = factory.pattern(CompanyPattern.class).make();

		Assert.assertEquals(2, result.getRecords(Employee.TABLE_NAME).size());
		Assert.assertEquals(1, result.getRecords(Department.TABLE_NAME).size());
		Assert.assertEquals(Employee.TABLE_NAME, result.getRecords(Employee.TABLE_NAME).get(0).getTableName());
	}

	/**
	 * パターン定義から特定の状態のデータを生成できること。
	 *
	 * @throws Exception
	 */
	@Test
	public void patternSetStateTest() throws Exception
	{
		Factory factory = new Factory(datasource);
		PatternData result = factory.pattern(CompanyPattern.class).state("single").save();

		Assert.assertEquals(1, result.getRecords(Employee.TABLE_NAME).size());
		Assert.assertEquals(1, result.getRecords(Department.TABLE_NAME).size());
	}

	/**
	 * パターン定義からデータを生成し、
	 * 1レコード目の情報を変更できること。
	 *
	 * @throws Exception
	 */
	@Test
	public void patternChangeTableSingleRowTest() throws Exception
	{
		Factory factory = new Factory(datasource);
		PatternData result = factory.pattern(CompanyPattern.class)
				.state("single")
				.table(Employee.class)
					.column(Employee.NAME, null)
				.end()
				.save();

		Assert.assertEquals(1, result.getRecords(Employee.TABLE_NAME).size());
		Assert.assertEquals(1, result.getRecords(Department.TABLE_NAME).size());
		Assert.assertNull(result.getRecords(Employee.TABLE_NAME).get(0).getString(Employee.NAME));
	}

	/**
	 * パターン定義からデータを生成し、
	 * 1レコード目の情報をマップを用いて変更できること。
	 *
	 * @throws Exception
	 */
	@Test
	public void patternChangeTableSingleRowByMapTest() throws Exception
	{
		Factory factory = new Factory(datasource);
		HashMap<String, String> changedValues = new HashMap<String, String>();
		String changedName = "Changed Name";
		changedValues.put(Employee.NAME, changedName);

		PatternData result = factory.pattern(CompanyPattern.class)
				.state("single")
				.table(Employee.class)
					.column(changedValues)
				.end()
				.save();

		Assert.assertEquals(1, result.getRecords(Employee.TABLE_NAME).size());
		Assert.assertEquals(1, result.getRecords(Department.TABLE_NAME).size());
		Assert.assertEquals(changedName, result.getRecords(Employee.TABLE_NAME).get(0).getString(Employee.NAME));
	}

	/**
	 * パターン定義からデータを生成し、
	 * 複数行の情報を変更できること。
	 *
	 * @throws Exception
	 */
	@Test
	public void patternChangeTableMuiltiRowTest() throws Exception
	{
		Factory factory = new Factory(datasource);
		HashMap<String, String> changedValues = new HashMap<String, String>();
		String changedName = "Changed Name";
		changedValues.put(Employee.NAME, changedName);

		PatternData result = factory.pattern(CompanyPattern.class)
				.table(Employee.class)
					.row(0)
						.column(Employee.NAME, null)
					.end()
					.row(1)
						.column(changedValues)
					.end()
				.end()
				.save();

		Assert.assertEquals(2, result.getRecords(Employee.TABLE_NAME).size());
		Assert.assertEquals(1, result.getRecords(Department.TABLE_NAME).size());
		Assert.assertNull(result.getRecords(Employee.TABLE_NAME).get(0).getString(Employee.NAME));
		Assert.assertEquals(changedName, result.getRecords(Employee.TABLE_NAME).get(1).getString(Employee.NAME));
	}
}
