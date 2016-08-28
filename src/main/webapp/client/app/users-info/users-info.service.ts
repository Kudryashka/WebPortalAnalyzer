import {Injectable} from '@angular/core';
import {Http} from '@angular/http';
import 'rxjs/add/operator/toPromise';

import {REST_USER_INFO_URL} from '../config/config';
import {UsersInfoReport} from './users-info-report';


@Injectable()
export class UsersInfoService {

	constructor(private http: Http) {}

	public getReport() {
		return this.getUsersInfo(30);
	}

	private getUsersInfo(days: number) {
		let url = `${REST_USER_INFO_URL}${days}`;
		return this.http.get(url).toPromise()
			.then(infos => infos.json() as UsersInfoReport)
			.catch(this.handleError);

	}

	private handleError(error: any) {
    	console.error('An error occurred', error);
    	return Promise.reject(error.message || error);
  	}
}