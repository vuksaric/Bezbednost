package com.example.KT1.config;

public class DataSource {

    public static javax.sql.DataSource createDataSource()
    {
        /* use a data source with connection pooling */
        org.postgresql.ds.PGSimpleDataSource ds = new org.postgresql.ds.PGSimpleDataSource();
        ds.setUrl("localhost://5432");
        ds.setUser("postgres");
        ds.setPassword("buba");
        /* use SSL connections without checking server certificate */
        ds.setSslMode("require");
        ds.setSslfactory("org.postgresql.ssl.NonValidatingFactory");

        return ds;
    }
}
