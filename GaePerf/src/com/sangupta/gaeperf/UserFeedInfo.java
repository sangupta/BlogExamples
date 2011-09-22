package com.sangupta.gaeperf;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.google.appengine.api.datastore.Text;

@PersistenceCapable(identityType = IdentityType.APPLICATION, detachable = "true")
public class UserFeedInfo {

	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	private Long mappingID;

	@Persistent
	private String userID;
	
	@Persistent
	private String feedID;
	
	@Persistent
	private Long totalPosts;
	
	@Persistent
	private Long unreadCount;
	
	/**
	 * @return the mappingID
	 */
	public Long getMappingID() {
		return mappingID;
	}

	/**
	 * @param mappingID the mappingID to set
	 */
	public void setMappingID(Long mappingID) {
		this.mappingID = mappingID;
	}

	/**
	 * @return the userID
	 */
	public String getUserID() {
		return userID;
	}

	/**
	 * @param userID the userID to set
	 */
	public void setUserID(String userID) {
		this.userID = userID;
	}

	/**
	 * @return the feedID
	 */
	public String getFeedID() {
		return feedID;
	}

	/**
	 * @param feedID the feedID to set
	 */
	public void setFeedID(String feedID) {
		this.feedID = feedID;
	}

	/**
	 * @return the totalPosts
	 */
	public Long getTotalPosts() {
		return totalPosts;
	}

	/**
	 * @param totalPosts the totalPosts to set
	 */
	public void setTotalPosts(Long totalPosts) {
		this.totalPosts = totalPosts;
	}

	/**
	 * @return the unreadCount
	 */
	public Long getUnreadCount() {
		return unreadCount;
	}

	/**
	 * @param unreadCount the unreadCount to set
	 */
	public void setUnreadCount(Long unreadCount) {
		this.unreadCount = unreadCount;
	}

}
