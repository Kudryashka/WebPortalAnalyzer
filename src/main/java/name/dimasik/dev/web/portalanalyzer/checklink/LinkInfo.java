package name.dimasik.dev.web.portalanalyzer.checklink;

import java.util.LinkedList;
import java.util.List;

/**
 * Holds informations about a link.
 * @author Dmytro Kudria
 * @author <a href="http://dimasik.name">http://dimasik.name</a>
 */
public class LinkInfo {

	//TODO
	public final LinkType type;
	//TODO
	public final String targetUrl;
	//TODO
	public final String pageUrl;
	//TODO
	public final LinkStatus linkStatus;
	//TODO
	public final int responseCode;
	//TODO
	public final String redirectTarget;

	/**
	 * Construct new {@link LinkInfo} with {@link LinkStatus#OK}, response code 200 and without redirect target.
	 * @param linkType The {@link LinkType} of the link.
	 * @param targetUrl URL the link targeted to.
	 * @param pageUrl URL of a page where the link founded.
	 */
	public LinkInfo(LinkType linkType, String targetUrl, String pageUrl) {
		this(linkType, targetUrl, pageUrl, LinkStatus.OK, 200);
	}
	
	/**
	 * Construct new {@link LinkInfo} without redirect target.
	 * @param linkType The {@link LinkType} of the link.
	 * @param targetUrl URL the link targeted to.
	 * @param pageUrl URL of a page where the link founded.
	 */
	public LinkInfo(LinkType linkType, String targetUrl, String pageUrl, LinkStatus status, int responseCode) {
		this(linkType, targetUrl, pageUrl, status, responseCode, null);
	}
	
	/**
	 * Construct new {@link LinkInfo}.
	 * @param linkType The {@link LinkType} of the link.
	 * @param targetUrl URL the link targeted to.
	 * @param pageUrl URL of a page where the link founded.
	 */
	public LinkInfo(LinkType linkType, String targetUrl, String pageUrl, LinkStatus status, int responseCode, String redirectTarget) {
		this.type = linkType;
		this.targetUrl = targetUrl;
		this.pageUrl = pageUrl;
		this.linkStatus = status;
		this.responseCode = responseCode;
		this.redirectTarget = redirectTarget;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder()
				.append(super.toString()).append(" ")
				.append("<").append(type).append("> ")
				.append("target url: '").append(targetUrl).append("', ")
				.append("page url: '").append(pageUrl).append("', ")
				.append("status: ").append(linkStatus).append(", ")
				.append("response code: ").append(responseCode).append(";");
		return builder.toString();
	}
}
