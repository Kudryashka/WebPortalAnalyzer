import {Component, OnInit, ViewContainerRef} from '@angular/core';
import {ROUTER_DIRECTIVES} from '@angular/router';

import {SchedulerService} from './schedule/scheduler-service';
import {SearchService} from './search/search.service';



@Component({
	selector: 'client-app',
	templateUrl: 'app/app.component.html',
	styleUrls: ['app/app.component.css'],
	directives: [ROUTER_DIRECTIVES],
	providers: [SchedulerService, SearchService]})
export class AppComponent {
	title = 'WebPortalAnalyzer';
	description = 'Тонкий клієнт для аплікації';

	constructor(private viewContainerRef: ViewContainerRef) {}
}