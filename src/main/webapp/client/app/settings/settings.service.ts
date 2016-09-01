import {Injectable} from '@angular/core';
import {Http} from '@angular/http';
import 'rxjs/add/operator/toPromise';

import {Setting} from './setting';
import {SETTINGS_URL} from '../config/config';

@Injectable()
export class SettingsService {

	constructor(private http: Http) {}

	getAllSettings() {
		let url = `${SETTINGS_URL}`;
		return this.http.get(url, {withCredentials: true}).toPromise()
			.then(settings => settings.json() as Setting[])
			.catch(this.handleError)
	}

	private handleError(error: any) {
    	console.error('An error occurred', error);
    	return Promise.reject(error.message || error);
  	}
}