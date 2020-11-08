package com.akaa07.java.database.factory.relations;

import com.akaa07.java.database.factory.core.RelationDefine;
import com.akaa07.java.database.factory.tables.Project;
import com.akaa07.java.database.factory.tables.ProjectMember;

public class ProjectRelation extends RelationDefine
{
	public void status_default() throws Exception
	{
		Project project = (Project) table(Project.class);

		String projectId = (String) project.getValue(Project.ID);
		ProjectMember e1 = (ProjectMember) table(ProjectMember.class).column(ProjectMember.ID, projectId);

		this.add(project, e1);
	}
}
