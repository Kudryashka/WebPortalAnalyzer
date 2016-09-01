import {Injectable} from '@angular/core';
import {Http, Headers} from '@angular/http';
import {Router} from '@angular/router';
import 'rxjs/add/operator/toPromise';

import {LOGIN_URL} from '../config/config';

@Injectable()
export class AuthenticationService {

	authorized: boolean = false;

	constructor(private http: Http, private router: Router) {}

	login(username: string, password: string) {
		let body = `username=${username}&password=${password}`;

		let headers: Headers = new Headers();
		headers.append('Content-Type', 'application/x-www-form-urlencoded');

		return this.http.post("http://localhost:8080/WebPortalAnalyzer/api/login", body, {headers: headers, withCredentials:true});
	}

	isAuthorized() {
		return this.authorized;
	}

	setIsAuthorized(authorized: boolean) {
		this.authorized = authorized;
	}

	checkAuthorization() {
		if (!this.authorized) this.router.navigateByUrl('/login');
	}
}