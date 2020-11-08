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

import com.akaa07.java.database.factory.core.RelationData;
import com.akaa07.java.database.factory.core.TableData;
import com.akaa07.java.database.factory.relations.CompanyRelation;
import com.akaa07.java.database.factory.relations.ProjectRelation;
import com.akaa07.java.database.factory.tables.Department;
import com.akaa07.java.database.factory.tables.Employee;
import com.akaa07.java.database.factory.tables.Project;
import com.akaa07.java.database.factory.tables.ProjectMember;

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
	 * リレーション定義からデータを登録できること。
	 *
	 * @throws Exception
	 */
	@Test
	public void relationTest() throws Exception
	{
		Factory factory = new Factory(datasource);
		RelationData result = factory.relation(CompanyRelation.class).save();

		Assert.assertEquals(2, result.getRecords(Employee.TABLE_NAME).size());
		Assert.assertEquals(1, result.getRecords(Department.TABLE_NAME).size());
		Assert.assertEquals(Employee.TABLE_NAME, result.getRecords(Employee.TABLE_NAME).get(0).getTableName());
	}

	/**
	 * リレーション定義からデータを生成できること。
	 *
	 * @throws Exception
	 */
	@Test
	public void relationMakeTest() throws Exception
	{
		Factory factory = new Factory(datasource);
		RelationData result = factory.relation(CompanyRelation.class).make();

		Assert.assertEquals(2, result.getRecords(Employee.TABLE_NAME).size());
		Assert.assertEquals(1, result.getRecords(Department.TABLE_NAME).size());
		Assert.assertEquals(Employee.TABLE_NAME, result.getRecords(Employee.TABLE_NAME).get(0).getTableName());
	}

	/**
	 * リレーション定義から特定の状態のデータを生成できること。
	 *
	 * @throws Exception
	 */
	@Test
	public void relationSetStateTest() throws Exception
	{
		Factory factory = new Factory(datasource);
		RelationData result = factory.relation(CompanyRelation.class).state("single").save();

		Assert.assertEquals(1, result.getRecords(Employee.TABLE_NAME).size());
		Assert.assertEquals(1, result.getRecords(Department.TABLE_NAME).size());
	}

	/**
	 * リレーション定義からデータを生成し、
	 * 1レコード目の情報を変更できること。
	 *
	 * @throws Exception
	 */
	@Test
	public void relationChangeTableSingleRowTest() throws Exception
	{
		Factory factory = new Factory(datasource);
		RelationData result = factory.relation(CompanyRelation.class)
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
	 * リレーション定義からデータを生成し、
	 * 1レコード目の情報をマップを用いて変更できること。
	 *
	 * @throws Exception
	 */
	@Test
	public void relationChangeTableSingleRowByMapTest() throws Exception
	{
		Factory factory = new Factory(datasource);
		HashMap<String, String> changedValues = new HashMap<String, String>();
		String changedName = "Changed Name";
		changedValues.put(Employee.NAME, changedName);

		RelationData result = factory.relation(CompanyRelation.class)
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
	 * リレーション定義からデータを生成し、
	 * 複数行の情報を変更できること。
	 *
	 * @throws Exception
	 */
	@Test
	public void relationChangeTableMuiltiRowTest() throws Exception
	{
		Factory factory = new Factory(datasource);
		HashMap<String, String> changedValues = new HashMap<String, String>();
		String changedName = "Changed Name";
		changedValues.put(Employee.NAME, changedName);

		RelationData result = factory.relation(CompanyRelation.class)
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

	/**
	 * 複数のリレーション定義からリレーションのあるデータを登録できること。
	 *
	 * @throws Exception
	 */
	@Test
	public void multipleRelationTest() throws Exception
	{
		Factory factory = new Factory(datasource);
		factory.relation(CompanyRelation.class).stack();
		factory.relation(ProjectRelation.class).stack();
		RelationData testdata = factory.save();

		Assert.assertNotNull(testdata.getRecords(Employee.TABLE_NAME));
		Assert.assertNotNull(testdata.getRecords(Department.TABLE_NAME));
		Assert.assertNotNull(testdata.getRecords(Project.TABLE_NAME));
		Assert.assertNotNull(testdata.getRecords(ProjectMember.TABLE_NAME));

		Assert.assertEquals(testdata.getRecords(Department.TABLE_NAME).get(0).getString(Department.ID), testdata.getRecords(Project.TABLE_NAME).get(0).getString(Project.DEPARTMENT_ID));
		Assert.assertEquals(testdata.getRecords(Employee.TABLE_NAME).get(0).getString(Employee.ID), testdata.getRecords(ProjectMember.TABLE_NAME).get(0).getString(ProjectMember.EMPLOYEE_ID));
	}

	/**
	 * スタックしたデータをクリアできること。
	 *
	 * @throws Exception
	 */
	@Test
	public void stackFlushTest() throws Exception
	{
		Factory factory = new Factory(datasource);
		factory.relation(CompanyRelation.class).stack();
		factory.flush();
		factory.relation(ProjectRelation.class).stack();
		RelationData testdata = factory.save();

		Assert.assertNull(testdata.getRecords(Employee.TABLE_NAME));
		Assert.assertNull(testdata.getRecords(Department.TABLE_NAME));
		Assert.assertNotNull(testdata.getRecords(Project.TABLE_NAME));
		Assert.assertNotNull(testdata.getRecords(ProjectMember.TABLE_NAME));
	}

	/**
	 * テーブルをスタックできること。
	 *
	 * @throws Exception
	 */
	@Test
	public void stackTableTest() throws Exception
	{
		Factory factory = new Factory(datasource);
		factory.table(Department.class).stack();
		factory.table(Employee.class).stack();
		factory.table(Employee.class).stack();
		factory.table(Employee.class).stack();
		RelationData testdata = factory.save();

		Assert.assertNotNull(testdata.getRecords(Employee.TABLE_NAME));
		Assert.assertEquals(3, testdata.getRecords(Employee.TABLE_NAME).size());
	}

	/**
	 * テーブルをスタックできること。
	 *
	 * @throws Exception
	 */
	@Test
	public void stackGetKeysTest() throws Exception
	{
		Factory factory = new Factory(datasource);
		factory.table(Project.class).column(Project.ID, "").stack();
		factory.table(Project.class).column(Project.ID, null).stack();
		factory.table(Project.class).column(Project.ID, "aaa").stack();
		factory.table(ProjectMember.class).stack();
		RelationData testdata = factory.save();

		Assert.assertNotNull(testdata.getRecords(Project.TABLE_NAME));
		Assert.assertNotNull(testdata.getRecords(ProjectMember.TABLE_NAME));
		Assert.assertEquals(3, testdata.getRecords(Project.TABLE_NAME).size());
		Assert.assertEquals(testdata.getRecords(Project.TABLE_NAME).get(2).getString(Project.ID), testdata.getRecords(ProjectMember.TABLE_NAME).get(0).getString(ProjectMember.PROJECT_ID));
	}

	/**
	 * リレーション定義からデータを生成できること。
	 *
	 * @throws Exception
	 */
	@Test
	public void relationMakeWithStackTest() throws Exception
	{
		Factory factory = new Factory(datasource);
		factory.relation(CompanyRelation.class).stack();
		RelationData result = factory.make();

		Assert.assertEquals(2, result.getRecords(Employee.TABLE_NAME).size());
		Assert.assertEquals(1, result.getRecords(Department.TABLE_NAME).size());
		Assert.assertEquals(Employee.TABLE_NAME, result.getRecords(Employee.TABLE_NAME).get(0).getTableName());
	}
}
