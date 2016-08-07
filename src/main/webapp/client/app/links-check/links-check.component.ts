import {Component, OnInit} from '@angular/core';
import {CORE_DIRECTIVES} from '@angular/common';
import {FORM_DIRECTIVES} from '@angular/forms';

import {MODAL_DIRECTIVES, BS_VIEW_PROVIDERS} from 'ng2-bootstrap/ng2-bootstrap';

import {RuleType} from '../schedule/rule-type';
import {RULE_TYPES} from '../schedule/rule-types';
import {DayOfWeek} from '../schedule/day-of-week';
import {DAYS_OF_WEEK} from '../schedule/days-of-week';
import {Rule} from '../schedule/rule';
import {SchedulerService} from '../schedule/scheduler-service';

@Component({
	selector: 'links-check',
	templateUrl: 'app/links-check/links-check.component.html',
	directives: [MODAL_DIRECTIVES, CORE_DIRECTIVES, FORM_DIRECTIVES],
	providers: [BS_VIEW_PROVIDERS]
})
export class LinksCheckComponent implements OnInit {

	ruleTypes: RuleType[];
	daysOfWeek: DayOfWeek[];

	newRule: Rule;

	registeredRules: Rule[];

	constructor(private schedulerService: SchedulerService) {}

	ngOnInit() {
		this.ruleTypes = RULE_TYPES;
		this.daysOfWeek = DAYS_OF_WEEK;

		this.newRule = {
			type: this.ruleTypes[0],
			dayOfWeek: this.daysOfWeek[0],
			hours: undefined,
			minutes: undefined,
			active: undefined
		}

		this.updateScheduledRules();
	}

	updateScheduledRules() {
		this.schedulerService.getScheduledPortalCheckRules().then(rules => this.registeredRules = rules);
	}

	callCheckManually() {
		console.log("call check manually");
	}

	disableCurrentActiveCheck() {
		console.log("disable current active check");
	}

	humanReadableRule(rule: Rule): string {
		if (rule.type.id === 0) {
			return rule.type.name + " (" + rule.hours + ":" + rule.minutes + ")";
		}
		if (rule.type.id === 1) {
			return rule.type.name + " (" + rule.dayOfWeek.name + " " +  rule.hours + ":" + rule.minutes + ")";
		}
		return String(rule);
	}

	removeRule(rule: Rule) {
		console.log(this.humanReadableRule(rule));
	}

	submitNewRule() {
		console.log("Type: " + this.newRule.type.id + ":" + this.newRule.type.name);
		console.log("Day of week: " + this.newRule.dayOfWeek.id + ":" + this.newRule.dayOfWeek.name);
		console.log("Time: " + this.newRule.hours + ":" + this.newRule.minutes);
	}
}
