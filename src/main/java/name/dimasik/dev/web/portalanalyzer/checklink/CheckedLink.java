package name.dimasik.dev.web.portalanalyzer.checklink;

/**
 * Represents checked link as DAO object.
 *
 * @author Dmytro Kudria
 * @author <a href="http://dimasik.name">http://dimasik.name</a>
 */
public interface CheckedLink {

	/**
	 * Get links check relative to this checked link object.
	 * @return The links check.
	 */
	LinksCheck getLinksCheck();
	
	/**
	 * Get status of the link.
	 * @return The link status.
	 */
	LinkStatus getLinkStatus();
	
	/**
	 * Get type of the link.
	 * @return The link type.
	 */
	LinkType getLinkType();
	
	/**
	 * Get link target address.
	 * @return The target address.
	 */
	String getTarget();
	
	/**
	 * Get location address of the link.
	 * @return The location address.
	 */
	String getLocation();
	
	/**
	 * Get response code from target address.
	 * @return The response code.
	 */
	Integer getResponseCode();
	
	/**
	 * Get redirect URL received from target address.
	 * @return The redirect URL.
	 */
	String getRedirectUrl();
	
	/**
	 * Set check relative to this checked link.
	 * @param The links check object.
	 */
	void setLinksCheck(LinksCheck linksCheck);
	
	/**
	 * Set status of the link.
	 * @param status The status of the link.
	 */
	void setLinkStatus(LinkStatus status);
	
	/**
	 * Set type of the link.
	 * @param type The type of the link.
	 */
	void setLinkType(LinkType type);
	
	/**
	 * Set target address of the link.
	 * @param target The target address.
	 */
	void setTarget(String target);
	
	/**
	 * Set location address of the link.
	 * @param location The location address.
	 */
	void setLocation(String location);
	
	/**
	 * Set response code received from target address.
	 * @param responseCode The response code.
	 */
	void setResponseCode(Integer responseCode);
	
	/**
	 * Set redirect URL received from target address.
	 * @param redirectUrl The redirect URL.
	 */
	void setRedirectUrl(String redirectUrl);
}
