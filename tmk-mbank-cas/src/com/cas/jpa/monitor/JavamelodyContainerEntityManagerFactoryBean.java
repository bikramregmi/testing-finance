package com.cas.jpa.monitor;

import javax.sql.DataSource;

import net.bull.javamelody.JdbcWrapper;

import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;

public class JavamelodyContainerEntityManagerFactoryBean extends LocalContainerEntityManagerFactoryBean {
	@Override
	public void setDataSource(DataSource dataSource) {
		super.setDataSource(JdbcWrapper.SINGLETON.createDataSourceProxy(dataSource));
	}
}
