import {Component, OnInit} from '@angular/core';

import {SearchService} from './search.service';
import {QueryCountPair} from './query-count-pair';


@Component({
	selector: 'search',
	templateUrl: 'app/search/search.component.html'
})
export class SearchComponent implements OnInit {

	tenDaysQueries: QueryCountPair[];
	thirtyDaysQueries: QueryCountPair[];

	constructor(private searchService: SearchService) {}

	ngOnInit() {
		this.updateQueriesLists();
	}

	updateQueriesLists() {
		this.searchService.requestTenDaysQueries()
			.then(queries => this.tenDaysQueries = queries)
			.catch(this.handleError);
		this.searchService.requestThirtyDaysQueries()
			.then(queries => this.thirtyDaysQueries = queries)
			.catch(this.handleError);
	}	

	private handleError() {
		console.log("Error");
	}
}