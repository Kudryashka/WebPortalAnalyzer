import {Component} from '@angular/core';
import {Router} from '@angular/router';

import {AuthenticationService} from './authentication.service';

@Component({
	selector: "login",
	templateUrl: 'app/authentication/authentication.component.html',
	styles: [
	`
	.wrapper {	
		margin-top: 80px;
  		margin-bottom: 80px;
	}

	.form-signin {
  		max-width: 380px;
  		padding: 15px 35px 45px;
  		margin: 0 auto;
  		background-color: #fff;
  		border: 1px solid rgba(0,0,0,0.1);  

  		.form-signin-heading, .form-control {
	  		position: relative;
	  		font-size: 16px;
	  		height: auto;
	  		padding: 10px;
			@include box-sizing(border-box);

			&:focus {
				z-index: 2;
			}
		}

		input[type="text"] {
	  		margin-bottom: 10px;
	  		border-bottom-left-radius: 0;
	  		border-bottom-right-radius: 0;
		}

		input[type="password"] {
	  		margin-bottom: 20px;
	  		border-top-left-radius: 0;
	  		border-top-right-radius: 0;
		}
	}

	`
	]
})
export class AuthenticationComponent {

	username: string;
	password: string;

	constructor(private authenticationService: AuthenticationService,
		private router: Router) {}

	doLogin() {
		console.log("Name: " + this.username + " pass: " + this.password);
		this.authenticationService.login(this.username, this.password);
		// this.router.navigateByUrl("/links");
	}
}