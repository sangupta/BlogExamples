package com.sangupta.gaeperf;

import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;

public class PersistenceManagerFactoryImpl {

	private static final PersistenceManagerFactory instance = JDOHelper.getPersistenceManagerFactory("transactions-optional");
	
	private PersistenceManagerFactoryImpl() {
		// make it a singleton
	}
	
	public static PersistenceManagerFactory getFactory() {
		return instance;
	}
	
	public static PersistenceManager getPersistenceManager() {
		return instance.getPersistenceManager();
	}

}
