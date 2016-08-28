import {Component, OnInit} from '@angular/core';

import {UsersInfoReport} from './users-info-report';
import {UsersInfoService} from './users-info.service';


@Component({
	selector: "users-info",
	templateUrl: "app/users-info/users-info.component.html"
})
export class UsersInfoComponent implements OnInit {

	report: UsersInfoReport;

	constructor(private usersInfoService: UsersInfoService) {}

	ngOnInit() {
		this.updateUsersInfoReport();
	}

	updateUsersInfoReport() {
		this.usersInfoService.getReport()
			.then(rep => this.report = rep)
			.catch(this.handleError);
	}

	private handleError() {
		console.log("UsersInfoReportReceive Error");
	}
}
