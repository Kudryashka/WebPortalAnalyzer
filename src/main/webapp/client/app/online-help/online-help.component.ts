import {Component, OnInit} from '@angular/core';

import {OnlineHelpReport} from './online-help-report';
import {AuthenticationService} from '../authentication/authentication.service';

@Component({
	selector: 'online-help',
	templateUrl: 'app/online-help/online-help.component.html'
})
export class OnlineHelpComponent implements OnInit {

	report: OnlineHelpReport = {
		summary: {
			checkCount: 0,
			availableOnline: 0,
			availableOffline: 0,
			unavailable: 0
		},
		details: [
			// {
			// 	date: "19:21 28/08/2016",
			// 	isOnlineAvailable: false,
			// 	isOfflineAvailable: false,
			// },
			// {
			// 	date: "19:22 28/08/2016",
			// 	isOnlineAvailable: false,
			// 	isOfflineAvailable: false,
			// }
		]
	}

	constructor(private authenticationService: AuthenticationService) {}

	ngOnInit() {
		this.authenticationService.checkAuthorization();
	}
}