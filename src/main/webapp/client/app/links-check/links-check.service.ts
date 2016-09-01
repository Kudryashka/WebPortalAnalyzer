import {Injectable} from '@angular/core';
import {Http, Headers, HTTP_PROVIDERS} from '@angular/http';
import 'rxjs/add/operator/toPromise';

import {
	REST_LINKS_CHECK_RUN_URL, 
	REST_LINKS_CHECK_STOP_URL, 
	REST_LINKS_CHECK_STATUS_URL,
	REST_LINKS_CHECK_GET_REPORT_URL
} from '../config/config';

import {LinksCheckReport} from './links-check-report';
import {AuthenticationService} from '../authentication/authentication.service';

@Injectable()
export class LinksCheckService {

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

	constructor(private http: Http, private authenticationService: AuthenticationService){}

	public executeRun() {
		let headers: Headers = new Headers();
		headers.append('Content-Type', 'application/json');
		this.http.put(REST_LINKS_CHECK_RUN_URL, '', {headers: headers}).subscribe();
	}

	public executeStop() {
		let headers: Headers = new Headers();
		headers.append('Content-Type', 'application/json');
		this.http.put(REST_LINKS_CHECK_STOP_URL, '', {headers: headers}).subscribe();
	}

	public getThirtyDaysReport() {
		let url = `${REST_LINKS_CHECK_GET_REPORT_URL}30`;
		return this.http.get(url).toPromise()
			.then(report => report.json() as LinksCheckReport)
			.catch(this.handleError);
	}

	private handleError(error: any) {
    	console.error('An error occurred', error);
    	return Promise.reject(error.message || error);
  	}
}