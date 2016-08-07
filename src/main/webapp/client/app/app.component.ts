import {Component, OnInit, ViewContainerRef} from '@angular/core';
import {ROUTER_DIRECTIVES} from '@angular/router';

import {SchedulerService} from './schedule/scheduler-service';


@Component({
	selector: 'client-app',
	templateUrl: 'app/app.component.html',
	styleUrls: ['app/app.component.css'],
	directives: [ROUTER_DIRECTIVES],
	providers: [SchedulerService]})
export class AppComponent {
	title = 'WebPortalAnalyzer';
	description = 'Thin client for application';

	constructor(private viewContainerRef: ViewContainerRef) {}
}