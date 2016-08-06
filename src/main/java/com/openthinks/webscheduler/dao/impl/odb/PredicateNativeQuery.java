package com.openthinks.webscheduler.dao.impl.odb;

import java.util.function.Predicate;

import org.neodatis.odb.core.query.nq.NativeQuery;

class PredicateNativeQuery<T> extends NativeQuery {
	private static final long serialVersionUID = 4093182978782308969L;
	private Predicate<T> predicate;
	private Class<T> classType;

	public PredicateNativeQuery(Predicate<T> predicate, Class<T> classType) {
		super();
		this.predicate = predicate;
		this.classType = classType;
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean match(Object runTimeData) {
		if (this.predicate != null) {
			return this.predicate.test((T) runTimeData);
		}
		return false;
	}

	@Override
	public Class<T> getObjectType() {
		return this.classType;
	}
}