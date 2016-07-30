package name.dimasik.dev.web.portalanalyzer.checklink;

public class LinkInfo {

	public final LinkType type;
	public final String targetUrl;
	public final String pageUrl;
	
	public LinkInfo(LinkType linkType, String targetUrl, String pageUrl) {
		this.type = linkType;
		this.targetUrl = targetUrl;
		this.pageUrl = pageUrl;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder()
				.append(super.toString()).append(" ")
				.append("<").append(type).append("> ")
				.append("target url: '").append(targetUrl).append("', ")
				.append("page url: '").append(pageUrl).append("';");
		return builder.toString();
	}
}
