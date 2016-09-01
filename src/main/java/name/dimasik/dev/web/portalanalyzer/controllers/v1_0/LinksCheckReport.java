package name.dimasik.dev.web.portalanalyzer.controllers.v1_0;

import java.util.List;

/**
 * TODO add description
 *
 * @author Dmytro Kudria
 * @author <a href="http://dimasik.name">http://dimasik.name</a>
 *
 */
public class LinksCheckReport {

	private LinksCheckReportSummary summary;
	private List<LinksCheckReportDetail> details;
	
	public LinksCheckReportSummary getSummary() {
		return summary;
	}

	public void setSummary(LinksCheckReportSummary summary) {
		this.summary = summary;
	}

	public List<LinksCheckReportDetail> getDetails() {
		return details;
	}

	public void setDetails(List<LinksCheckReportDetail> details) {
		this.details = details;
	}

	/**
	 * TODO add description
	 *
	 * @author Dmytro Kudria
	 * @author <a href="http://dimasik.name">http://dimasik.name</a>
	 *
	 */
	public static class LinksCheckReportSummary {
		
		private int checkCount;
		private int okLinksCount;
		private int errorLinksCount;
		private int unreachableLinksCount;
		private int redirectLinksCount;
		
		public int getCheckCount() {
			return checkCount;
		}
		
		public void setCheckCount(int checkCount) {
			this.checkCount = checkCount;
		}
		
		public int getOkLinksCount() {
			return okLinksCount;
		}
		
		public void setOkLinksCount(int okLinksCount) {
			this.okLinksCount = okLinksCount;
		}
		
		public int getErrorLinksCount() {
			return errorLinksCount;
		}
		
		public void setErrorLinksCount(int errorLinksCount) {
			this.errorLinksCount = errorLinksCount;
		}
		
		public int getUnreachableLinksCount() {
			return unreachableLinksCount;
		}
		
		public void setUnreachableLinksCount(int unreachableLinksCount) {
			this.unreachableLinksCount = unreachableLinksCount;
		}
		
		public int getRedirectLinksCount() {
			return redirectLinksCount;
		}
		
		public void setRedirectLinksCount(int redirectLinksCount) {
			this.redirectLinksCount = redirectLinksCount;
		}
	}
	
	/**
	 * TODO add description
	 *
	 * @author Dmytro Kudria
	 * @author <a href="http://dimasik.name">http://dimasik.name</a>
	 *
	 */
	public static class LinksCheckReportDetail {
		private String date;
	 	private LinksCheckReportDetailSummary summary;
		private List<Link> okLinks;
		private List<Link> errorLinks;
		private List<UnreachableLink> unreachableLinks;
		private List<RedirectLink> redirectLinks;
		public String getDate() {
			return date;
		}
		public void setDate(String date) {
			this.date = date;
		}
		public LinksCheckReportDetailSummary getSummary() {
			return summary;
		}
		public void setSummary(LinksCheckReportDetailSummary summary) {
			this.summary = summary;
		}
		public List<Link> getOkLinks() {
			return okLinks;
		}
		public void setOkLinks(List<Link> okLinks) {
			this.okLinks = okLinks;
		}
		public List<Link> getErrorLinks() {
			return errorLinks;
		}
		public void setErrorLinks(List<Link> errorLinks) {
			this.errorLinks = errorLinks;
		}
		public List<UnreachableLink> getUnreachableLinks() {
			return unreachableLinks;
		}
		public void setUnreachableLinks(List<UnreachableLink> unreachableLinks) {
			this.unreachableLinks = unreachableLinks;
		}
		public List<RedirectLink> getRedirectLinks() {
			return redirectLinks;
		}
		public void setRedirectLinks(List<RedirectLink> redirectLinks) {
			this.redirectLinks = redirectLinks;
		}
	}
	
	/**
	 * TODO add description
	 *
	 * @author Dmytro Kudria
	 * @author <a href="http://dimasik.name">http://dimasik.name</a>
	 *
	 */
	public static class LinksCheckReportDetailSummary {
		private int okLinksCount;
		private int errorLinksCount;
		private int unreachableLinksCount;
		private int redirectLinksCount;
		public int getOkLinksCount() {
			return okLinksCount;
		}
		public void setOkLinksCount(int okLinksCount) {
			this.okLinksCount = okLinksCount;
		}
		public int getErrorLinksCount() {
			return errorLinksCount;
		}
		public void setErrorLinksCount(int errorLinksCount) {
			this.errorLinksCount = errorLinksCount;
		}
		public int getUnreachableLinksCount() {
			return unreachableLinksCount;
		}
		public void setUnreachableLinksCount(int unreachableLinksCount) {
			this.unreachableLinksCount = unreachableLinksCount;
		}
		public int getRedirectLinksCount() {
			return redirectLinksCount;
		}
		public void setRedirectLinksCount(int redirectLinksCount) {
			this.redirectLinksCount = redirectLinksCount;
		}
		
		
	}
	
	/**
	 * TODO add description
	 *
	 * @author Dmytro Kudria
	 * @author <a href="http://dimasik.name">http://dimasik.name</a>
	 *
	 */
	public static class UnreachableLink {
		private String type;
		private String location;
		private String target;
		public String getType() {
			return type;
		}
		public void setType(String type) {
			this.type = type;
		}
		public String getLocation() {
			return location;
		}
		public void setLocation(String location) {
			this.location = location;
		}
		public String getTarget() {
			return target;
		}
		public void setTarget(String target) {
			this.target = target;
		}
	}
	
	/**
	 * TODO add description
	 *
	 * @author Dmytro Kudria
	 * @author <a href="http://dimasik.name">http://dimasik.name</a>
	 *
	 */
	public static class Link extends UnreachableLink {
		private int responseCode;

		public int getResponseCode() {
			return responseCode;
		}

		public void setResponseCode(int responseCode) {
			this.responseCode = responseCode;
		}
	}
	
	/**
	 * TODO add description
	 *
	 * @author Dmytro Kudria
	 * @author <a href="http://dimasik.name">http://dimasik.name</a>
	 *
	 */
	public static class RedirectLink extends Link {
		private String redirectUrl;

		public String getRedirectUrl() {
			return redirectUrl;
		}

		public void setRedirectUrl(String redirectUrl) {
			this.redirectUrl = redirectUrl;
		}
		
	}
}
