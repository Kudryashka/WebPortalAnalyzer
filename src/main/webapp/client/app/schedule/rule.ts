import {RuleType} from './rule-type';
import {DayOfWeek} from './day-of-week';

export class Rule {
	type: RuleType;
	dayOfWeek: DayOfWeek;
	hours:string;
	minutes:string;
	active:boolean;
}