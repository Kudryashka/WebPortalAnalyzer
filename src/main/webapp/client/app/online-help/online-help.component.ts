import {Component} from '@angular/core';

import {OnlineHelpReport} from './online-help-report';

@Component({
	selector: 'online-help',
	templateUrl: 'app/online-help/online-help.component.html'
})
export class OnlineHelpComponent {

	report: OnlineHelpReport = {
		summary: {
			checkCount: 2,
			availableOnline: 0,
			availableOffline: 0,
			unavailable: 2
		},
		details: [
			{
				date: "19:21 28/08/2016",
				isOnlineAvailable: false,
				isOfflineAvailable: false,
			},
			{
				date: "19:22 28/08/2016",
				isOnlineAvailable: false,
				isOfflineAvailable: false,
			}
		]
	}
}