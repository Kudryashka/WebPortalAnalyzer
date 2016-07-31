import {Component, OnInit} from '@angular/core';
import {ROUTER_DIRECTIVES} from '@angular/router'

import {NavigatorService} from './nav/navigator.service';
import {NavComponent} from './nav/nav-component';


import {DashboardComponent} from './dashboard/dashboard.component';
import {LinksCheckComponent} from './links-check/links-check.component';
import {UsersInfoComponent} from './users-info/users-info.component';



@Component({
	selector: 'client-app',
	template: `
		<h1>{{title}}</h1>
		<h2>TODO: Current component</h2>
		<nav>
			<a *ngFor="let navComp of navComponents" [routerLink]="navComp.uri">{{navComp.name}}</a>
		</nav>
		<router-outlet></router-outlet>
	`,
	directives: [ROUTER_DIRECTIVES],
	providers: [NavigatorService]
})
export class AppComponent implements OnInit {
	title = 'WebPortalAnalyzer-Client';
	navComponents: NavComponent[];

	constructor(private navService: NavigatorService) {}

	ngOnInit() {
		this.navComponents = this.navService.getComponents();
	}
}