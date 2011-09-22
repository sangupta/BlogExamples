package com.sangupta.gaeperf;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.jsr107cache.Cache;
import net.sf.jsr107cache.CacheException;
import net.sf.jsr107cache.CacheManager;

@SuppressWarnings("serial")
public class GaeperfServlet extends HttpServlet {
	
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        String line = null;
		long startTime = 0, endTime = 0, time = 0;
		String action = req.getParameter("action");
		
		if("add".equals(action)) {
			startTime = System.currentTimeMillis();
			
			addTest();
			
			endTime = System.currentTimeMillis();
		} else if("fetch".equals(action)) {
			startTime = System.currentTimeMillis();
			
			for(int i = 0; i < 10; i++) {
				action += " " + fetchTest(i);
			}
			
			endTime = System.currentTimeMillis();
		} else if("cacheput".equals(action)) {
			startTime = System.currentTimeMillis();
			
			for(int i = 0; i < 10; i++) {
				cachePutTest();
			}
	        
	        endTime = System.currentTimeMillis();
		} else if("cacheget".equals(action)) {
			startTime = System.currentTimeMillis();
			
			for(int i = 0; i < 10; i++) {
				cacheGetTest();
			}
	        
	        endTime = System.currentTimeMillis();
		} else if("delete".equals(action)) {
			PersistenceManager manager = PersistenceManagerFactoryImpl.getPersistenceManager();
			try {
				Query query = manager.newQuery(UserFeedInfo.class);
				long items = query.deletePersistentAll();
				action += " " + items;
				query.closeAll();
			} catch(Throwable t) {
				t.printStackTrace();
			} finally {
				manager.close();
			}
		} else if("url".equals(action)) {
			startTime = System.currentTimeMillis();
			
			try {
	            URL url = new URL("http://googleappengine.blogspot.com/atom.xml");
	            BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));

	            StringBuilder builder = new StringBuilder();
	            while ((line = reader.readLine()) != null) {
	                // do nothing
	            	builder.append(line);
	            }
	            
	            line = builder.toString();
	            
	            reader.close();
	        } catch (MalformedURLException e) {
	            e.printStackTrace();
	        } catch (IOException e) {
	        	e.printStackTrace();
	        }
	        
	        endTime = System.currentTimeMillis();
		}
		
		time = endTime - startTime;
		
		resp.setContentType("text/plain");
		resp.getWriter().println(action + ": " + time + " ms.");
		
		if(line != null) {
			resp.getWriter().println(line);
		}
	}

	private void cacheGetTest() {
		Cache cache;

		try {
		    cache = CacheManager.getInstance().getCacheFactory().createCache(Collections.emptyMap());

		    String test = (String) cache.get("sangupta");
		} catch (CacheException e) {
		    e.printStackTrace();
		}
	}

	private void cachePutTest() {
		Cache cache;

		try {
		    cache = CacheManager.getInstance().getCacheFactory().createCache(Collections.emptyMap());

		    StringBuilder builder = new StringBuilder();
		    for(int i = 0; i < 5000; i++) {
		    	builder.append("0123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789");
		    }
		    cache.put("sangupta", builder.toString());
		} catch (CacheException e) {
		    e.printStackTrace();
		}
	}

	private int fetchTest(int i) {
		PersistenceManager manager = PersistenceManagerFactoryImpl.getPersistenceManager();
		try {
			Query query = manager.newQuery(UserFeedInfo.class);
			query.setFilter("userID == userIDParam");
			query.declareParameters("String lastNameParam");
			Object o = query.execute("1");
			query.closeAll();
			if(o instanceof List) {
				return ((List) o).size();
			}
			
			return 0;
		} catch(Throwable t) {
			t.printStackTrace();
		} finally {
			manager.close();
		}
		
		return -1;
	}

	private void addTest() {
		PersistenceManager manager = PersistenceManagerFactoryImpl.getPersistenceManager();
		try {
			List<UserFeedInfo> list = new ArrayList<UserFeedInfo>();
			
			for(int i = 0; i < 50; i++) {
				UserFeedInfo ui = new UserFeedInfo();
				int userID = 1;
				
				ui.setUserID(String.valueOf(userID));
				ui.setFeedID("feed" + i);
				ui.setTotalPosts(100l);
				ui.setUnreadCount(50l);
				
				list.add(ui);
			}
			
			manager.makePersistentAll(list);
		} catch(Throwable t) {
			t.printStackTrace();
		} finally {
			manager.close();
		}
	}
}
