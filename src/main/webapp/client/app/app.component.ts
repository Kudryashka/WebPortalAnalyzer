import {Component, OnInit} from '@angular/core';
import {ROUTER_DIRECTIVES} from '@angular/router'

@Component({
	selector: 'client-app',
	templateUrl: 'app/app.component.html',
	styleUrls: ['app/app.component.css'],
	directives: [ROUTER_DIRECTIVES]})
export class AppComponent {
	title = 'WebPortalAnalyzer';
	description = 'Thin client for application';
}