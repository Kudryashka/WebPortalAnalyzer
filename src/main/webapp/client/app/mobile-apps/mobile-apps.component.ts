import {Component} from '@angular/core';

import {MobileAppsReport} from './mobile-apps-report';

@Component({
	selector: 'mobile-apps',
	templateUrl: 'app/mobile-apps/mobile-apps.component.html'
})
export class MobileAppsComponent {

	report: MobileAppsReport = {
		summary: {
			checkCount: 0,
			failedCheckCount: 0
		},
		details: []
	}
}