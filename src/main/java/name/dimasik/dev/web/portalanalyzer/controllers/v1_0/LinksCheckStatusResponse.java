package name.dimasik.dev.web.portalanalyzer.controllers.v1_0;

import java.util.Objects;

import name.dimasik.dev.web.portalanalyzer.checklink.CheckLinkServiceStatus;

/**
 * TODO add description
 *
 * @author Dmytro Kudria
 * @author <a href="http://dimasik.name">http://dimasik.name</a>
 *
 */
public class LinksCheckStatusResponse {

	private final CheckLinkServiceStatus status;
	
	public LinksCheckStatusResponse(CheckLinkServiceStatus status) {
		if (Objects.isNull(status)) {
			throw new IllegalArgumentException("Status can not be null!");
		}
		this.status = status;
	}
	
	public CheckLinkServiceStatus getStatus() {
		return status;
	}
}
