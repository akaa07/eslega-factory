# Overview

With Eslega Factory, test data creation can be patterned and generated dynamically.
You are freed from preparing data in advance, deleting data, and from extensive modification of test classes due to DB model changes.
Factory that wraps DBSetup makes it possible to create test classes for data setup that is smooth and highly cohesive.

Eslega Factoryを使用すると、テストデータの作成をパターン化して動的に生成することができます。
事前にXMLでデータを準備することも、データを削除することからも、DBモデルの変更によるテストクラスの大規模な修正もいりません。
DBSetupをラップしたFactoryにより、スムーズで凝集度の高いデータセットアップ用のテストクラスを作成することが可能になります。

古のプロジェクトで使用するために、JRE1.6+Antで作成していますが、今後様々なプロジェクトで使用できるようにしたいと考えています。

# How To Use

## 1. Create Table Define

Create a table definition that you want to use in the test.

```
package your.project.package.tables;

import com.eslega.factory.core.TableDefine;

public class Employee extends TableDefine
{
	/** Target table name */
	public final static String TABLE_NAME = "employee";

	public final static String ID = "id";
	public final static String DEPARTMENT_ID = "department_id";
	public final static String NAME = "name";
	public final static String GENDER = "gender";

	/**
	 * Get table name.
	 * This method is used in the library. 
	 *
	 * @return table name
	 */
	public String getTableName()
	{
		return TABLE_NAME;
	}

	/**
	 * Define the default state.
	 * Random data can be created by using Facker.
	 */
	public void status_default()
	{
		this.column(Employee.ID, this.fakerRegexify("[a-z0-9]{10}"))
			.column(Employee.DEPARTMENT_ID, this.fakerRegexify("[a-z0-9]{5}"))
			.column(Employee.NAME, faker.name().fullName())
			.column(Employee.GENDER, 0);
	}
}
```



## 2. Use Factory In TestCase

```
	@Test
	public void tableTest() throws Exception
	{
		// Set the data source in advance.
 		// It is convenient to set with common initial processing.
		Factory.setDataSource(datasource);
		
		// Data is generated with the contents set in "status_default".
		// You can customize the data with the "column" method.
		TableData result = Factory.table(Employee.class).save();

		// do testing
	}
```


# License

Eslega Factory is released under the [MIT License](http://en.wikipedia.org/wiki/MIT_License).

The MIT License

Copyright (c) 2019, nazuna

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in
all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
THE SOFTWARE.