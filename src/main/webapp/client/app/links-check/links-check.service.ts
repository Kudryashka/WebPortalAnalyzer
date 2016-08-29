import {Injectable} from '@angular/core';
import {Http, Headers} from '@angular/http';
import 'rxjs/add/operator/toPromise';

import {
	REST_LINKS_CHECK_RUN_URL, 
	REST_LINKS_CHECK_STOP_URL, 
	REST_LINKS_CHECK_STATUS_URL
} from '../config/config';

import {LinksCheckReport} from './links-check-report';

@Injectable()
export class LinksCheckService {

	private headers: Headers;

	private fakeReport: LinksCheckReport = {
		summary: {
			checkCount: 1,
			okLinksCount: 3,
			errorLinksCount: 0,
			unreachableLinksCount: 0,
			redirectLinksCount: 0
		},
		details: [
			{
				date: "17:13 28/08/2016",
				summary: {
					okLinksCount: 3,
                	errorLinksCount: 0,
                	unreachableLinksCount: 0,
                	redirectLinksCount: 0
				},
				okLinks: [
					{
						type: "ANCHOR",
						location: "",
						target: "http://dimasik.name",
						responseCode: 200
					},
					{
						type: "ANCHOR",
						location: "http://dimasik.name",
						target: "http://dimasik.name/mail",
						responseCode: 200
					},
					{
						type: "ANCHOR",
						location: "http://dimasik.name",
						target: "http://dimasik.name/job",
						responseCode: 200
					}
				],
				errorLinks: [],
				unreachableLinks: [],
				redirectLinks: []
			}
		]
	} 

	constructor(private http: Http){
		this.headers = new Headers();
		this.headers.append('Content-Type', 'application/json');
	}

	public executeRun() {
		this.http.put(REST_LINKS_CHECK_RUN_URL, JSON.stringify(""), {headers: this.headers}).subscribe();
	}

	public executeStop() {
		this.http.put(REST_LINKS_CHECK_STOP_URL, JSON.stringify(""), {headers: this.headers}).subscribe();
	}

	public getThirtyDaysReport() {
	}

	private handleError(error: any) {
    	console.error('An error occurred', error);
    	return Promise.reject(error.message || error);
  	}
}