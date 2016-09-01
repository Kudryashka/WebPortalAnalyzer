import {Component, OnInit} from '@angular/core';

import {MobileAppsReport} from './mobile-apps-report';
import {AuthenticationService} from '../authentication/authentication.service';

@Component({
	selector: 'mobile-apps',
	templateUrl: 'app/mobile-apps/mobile-apps.component.html'
})
export class MobileAppsComponent implements OnInit {

	report: MobileAppsReport = {
		summary: {
			checkCount: 0,
			failedCheckCount: 0
		},
		details: []
	}

	constructor(private authorizationService: AuthenticationService) {}

	ngOnInit() {
		this.authorizationService.checkAuthorization();
	}
}