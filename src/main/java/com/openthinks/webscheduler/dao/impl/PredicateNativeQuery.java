package com.openthinks.webscheduler.dao.impl;

import java.util.function.Predicate;

import org.neodatis.odb.core.query.nq.SimpleNativeQuery;

class PredicateNativeQuery<T> extends SimpleNativeQuery {
	private static final long serialVersionUID = 4093182978782308969L;
	private Predicate<T> predicate;

	public PredicateNativeQuery(Predicate<T> predicate) {
		super();
		this.predicate = predicate;
	}

	public boolean match(T runTimeData) {
		if (this.predicate != null) {
			return this.predicate.test(runTimeData);
		}
		return false;
	}
}