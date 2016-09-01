import {Component, OnInit} from '@angular/core';

import {Setting} from './setting';
import {SettingsService} from './settings.service';
import {AuthenticationService} from '../authentication/authentication.service';

@Component({
	selector: 'settings',
	templateUrl: 'app/settings/settings.component.html'
})
export class SettingsComponent implements OnInit {

	settings: Setting[] = [];

	constructor(private settingService: SettingsService,
		private authenticationService: AuthenticationService) {}

	ngOnInit() {
		this.authenticationService.checkAuthorization();
		this.updateSettings();
	}

	updateSettings() {
		this.settingService.getAllSettings()
			.then(settings => this.settings = settings)
			.catch(this.handleError);
	}

	private handleError() {
		console.log("Settings Error");
	}
}