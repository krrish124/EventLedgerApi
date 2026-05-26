package com.mphasis.events.config;

import org.hibernate.boot.MappingException;
import org.hibernate.dialect.Dialect;
import org.hibernate.dialect.function.SQLFunctionTemplate;
import org.hibernate.dialect.function.StandardSQLFunction;
import org.hibernate.dialect.identity.IdentityColumnSupport;
import org.hibernate.dialect.identity.IdentityColumnSupportImpl;
import org.hibernate.type.StandardBasicTypes;
import org.hibernate.type.StringType;

public class SQLiteDialect extends Dialect{
	public SQLiteDialect() {
		registerFunction("concat", new StandardSQLFunction("concat", StringType.INSTANCE));
        registerFunction("mod", new StandardSQLFunction("mod", StandardBasicTypes.INTEGER));
        registerFunction("substr", new StandardSQLFunction("substr", StringType.INSTANCE));
        registerFunction("substring", new SQLFunctionTemplate(StandardBasicTypes.STRING, "substr(?1, ?2, ?3)"));

    }

    @Override
    public IdentityColumnSupport getIdentityColumnSupport() {
        return new SQLiteIdentityColumnSupport();
    }

    @Override
    public boolean dropConstraints() {
        return false;
    }

    @Override
    public boolean hasAlterTable() {
        return false;
    }

    @Override
    public boolean supportsIfExistsBeforeTableName() {
        return true;
    }

    @Override
    public boolean supportsCascadeDelete() {
        return false;
    }

    private static class SQLiteIdentityColumnSupport extends IdentityColumnSupportImpl {
        @Override
        public boolean supportsIdentityColumns() {
            return true;
        }

        @Override
        public String getIdentityColumnString(int type) throws MappingException {
            // SQLite uses "integer primary key autoincrement" for identity columns
            return "integer";
        }

        @Override
        public String getIdentitySelectString(String table, String column, int type) throws MappingException {
            return "select last_insert_rowid()";
        }
    }
}
