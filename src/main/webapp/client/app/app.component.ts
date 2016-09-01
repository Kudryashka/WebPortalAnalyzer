import {Component, OnInit, ViewContainerRef} from '@angular/core';
import {ROUTER_DIRECTIVES} from '@angular/router';

import {AuthenticationService} from './authentication/authentication.service';
import {SchedulerService} from './schedule/scheduler-service';
import {SearchService} from './search/search.service';
import {UsersInfoService} from './users-info/users-info.service';
import {LinksCheckService} from './links-check/links-check.service';
import {SettingsService} from './settings/settings.service';


@Component({
	selector: 'client-app',
	templateUrl: 'app/app.component.html',
	styleUrls: ['app/app.component.css'],
	directives: [ROUTER_DIRECTIVES],
	providers: [AuthenticationService, SchedulerService, SearchService, 
		UsersInfoService, LinksCheckService, SettingsService]})
export class AppComponent {
	title = 'WebPortalAnalyzer';
	description = 'Тонкий клієнт для аплікації';

	constructor(private viewContainerRef: ViewContainerRef) {}
}