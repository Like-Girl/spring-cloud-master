package cn.likegirl.rt.config.database;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

class ReadWriteSplitRoutingDataSource extends AbstractRoutingDataSource {

	@Override
	protected Object determineCurrentLookupKey() {
		return DataBaseContextHolder.getDataBaseType();
	}

}
