package com.letsstartcoding.springbootrestapiexample.utility;

import org.hibernate.dialect.MySQLDialect;
import org.hibernate.dialect.function.SQLFunctionTemplate;
import org.hibernate.type.StandardBasicTypes;

public class MySQLDialectCustom extends MySQLDialect {

	
	public MySQLDialectCustom() 
	{
	    super();
	    registerFunction("match", new SQLFunctionTemplate(StandardBasicTypes.DOUBLE, " MATCH(?1) AGAINST(?2 IN BOOLEAN MODE) "));
	}
	
	@Override
    public String getTableTypeString() {
        return " ENGINE=InnoDB DEFAULT CHARSET=utf8";
    }
}