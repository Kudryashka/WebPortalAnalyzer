import {Injectable} from '@angular/core';
import {Http, Headers} from '@angular/http';
import 'rxjs/add/operator/toPromise';

import {LOGIN_URL} from '../config/config';

@Injectable()
export class AuthenticationService {

	// private headers: Headers;
	private cookies: string;

	constructor(private http: Http) {
		// this.headers = new Headers();
		// this.headers.append('Content-Type', 'application/x-www-form-urlencoded');
	}

	login(username: string, password: string) {
		let body = `username=${username}&password=${password}`;

		let headers: Headers = new Headers();
		headers.append('Content-Type', 'application/x-www-form-urlencoded');

		this.http.post("http://localhost:8080/WebPortalAnalyzer/api/login", body, {headers: headers, withCredentials:true})
			.subscribe(
					data => console.log(data.headers.keys()),
					error => console.log(error)
				);
	}

	saveAuthCookies(cookies: string) {
		console.log("Cookies: " + cookies);
		this.cookies = cookies;
	}

	getCookies() {
		return this.cookies;
	}
}