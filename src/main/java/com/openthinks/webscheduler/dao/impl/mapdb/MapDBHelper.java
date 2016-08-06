package com.openthinks.webscheduler.dao.impl.mapdb;

import java.io.File;
import java.util.NavigableSet;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.mapdb.DB;
import org.mapdb.DBMaker;
import org.mapdb.Serializer;
import org.mapdb.serializer.GroupSerializer;
import org.mapdb.serializer.SerializerUtils;

import com.openthinks.easyweb.WebUtils;
import com.openthinks.webscheduler.help.StaticDict;
import com.openthinks.webscheduler.model.task.def.TaskDefRuntimeData;

public final class MapDBHelper {

	private static String storeDBPath;
	private static DB dbInstance;
	private static Lock lock = new ReentrantLock();
	static {
		try {
			setUp(new File(WebUtils.getWebClassDir()), StaticDict.STORE_DB);
		} catch (Exception e) {
		}
	}

	/**
	 * set location of DB
	 * 
	 * @param dir
	 *            File director of DB
	 * @param name
	 *            String DB name
	 */
	public static final void setUp(File dir, String name) {
		// File dbFile = new File(WebUtils.getWebClassDir(),
		// StaticDict.STORE_DB);
		File dbFile = new File(dir, name);
		storeDBPath = dbFile.getAbsolutePath();
	}

	public static void initialize() {
		lock.lock();
		try {
			if (dbInstance == null || dbInstance.isClosed()) {
				dbInstance = DBMaker.fileDB(storeDBPath)
						.transactionEnable()
						.make();
			}
		} finally {
			lock.unlock();
		}
		
	}

	public static DB getDB() {
		lock.lock();
		try {
			if (dbInstance == null || dbInstance.isClosed()) {
				dbInstance = DBMaker.fileDB(storeDBPath).make();
			}
		} finally {
			lock.unlock();
		}
		return dbInstance;
	}

}
