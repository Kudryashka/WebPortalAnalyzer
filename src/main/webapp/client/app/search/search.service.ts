import {Injectable} from '@angular/core';
import {Http, Headers} from '@angular/http';
import 'rxjs/add/operator/toPromise';

import {REST_SEARCH_URL} from '../config/config';
import {QueryCountPair} from './query-count-pair';

@Injectable()
export class SearchService {

	constructor(private http: Http) {}

	requestTenDaysQueries() {
		return this.getQueries(10);
	}

	requestThirtyDaysQueries() {
		return this.getQueries(30);
	}

	private getQueries(days: number) {
		let url = `${REST_SEARCH_URL}${days}`; 
		return this.http.get(url).toPromise()
			.then(queries => queries.json() as QueryCountPair[])
			.catch(this.handleError);
	}

	private handleError(error: any) {
    	console.error('An error occurred', error);
    	return Promise.reject(error.message || error);
  	}
}