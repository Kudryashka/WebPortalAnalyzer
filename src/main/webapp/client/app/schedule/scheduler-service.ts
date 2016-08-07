import {Injectable} from '@angular/core';
import {Http, Headers} from '@angular/http';
import 'rxjs/add/operator/toPromise';

import {Rule} from './rule';
import {RULE_TYPES} from './rule-types';
import {DAYS_OF_WEEK} from './days-of-week';

const REST_URL = '';

const PORTAL_CHECK_RULES_STUB: Rule[] = [
	{
		type: RULE_TYPES[0],
		dayOfWeek: DAYS_OF_WEEK[0],
		hours: '0',
		minutes: '0',
		active: true
	},
	{
		type: RULE_TYPES[1],
		dayOfWeek: DAYS_OF_WEEK[1],
		hours: '10',
		minutes: '10',
		active: true
	},
	{
		type: RULE_TYPES[1],
		dayOfWeek: DAYS_OF_WEEK[3],
		hours: '0',
		minutes: '0',
		active: false
	}
]

@Injectable()
export class SchedulerService {

	getScheduledPortalCheckRules() {
		return Promise.resolve(PORTAL_CHECK_RULES_STUB);
	}
}