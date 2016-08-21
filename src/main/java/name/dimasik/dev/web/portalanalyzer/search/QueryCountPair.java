package name.dimasik.dev.web.portalanalyzer.search;

import com.fasterxml.jackson.annotation.JsonView;

import name.dimasik.dev.web.portalanalyzer.controllers.v1_0.EmptySearchReportView;

/**
 * TODO add description
 *
 * @author Dmytro Kudria
 * @author <a href="http://dimasik.name">http://dimasik.name</a>
 *
 */
public class QueryCountPair {

	@JsonView(EmptySearchReportView.class)
	private final String query;
	@JsonView(EmptySearchReportView.class)
	private int count;
	
	public QueryCountPair(String query) {
		this(query, 1);
	}
	
	public QueryCountPair(String query, int count) {
		this.query = query;
		this.count = count;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public String getQuery() {
		return query;
	}
}
