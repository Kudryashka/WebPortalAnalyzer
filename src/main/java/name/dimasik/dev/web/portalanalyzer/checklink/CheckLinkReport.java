package name.dimasik.dev.web.portalanalyzer.checklink;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * TODO add description
 *
 * @author Dmytro Kudria
 * @author <a href="http://dimasik.name">http://dimasik.name</a>
 *
 */
@Deprecated
public class CheckLinkReport {

	private Date date;
	private List<String> okLinks;
	private List<String> errorLinks;
	
	public String getDate() {
		DateFormat df = new SimpleDateFormat("HH:mm dd/MM/yyyy");
		return df.format(date);
	}
	
	public void setDate(Date date) {
		this.date = date;
	}
	
	public List<String> getOkLinks() {
		return okLinks;
	}
	
	public void setOkLinks(List<String> okLinks) {
		this.okLinks = okLinks;
	}
	
	public List<String> getErrorLinks() {
		return errorLinks;
	}
	
	public void setErrorLinks(List<String> errorLinks) {
		this.errorLinks = errorLinks;
	}
}
