import {Injectable} from '@angular/core';

import {NavComponent} from './nav-component';

const COMPONENTS: NavComponent[] = [
	{uri: '/dashboard', name: 'Dashboard'},
	{uri: '/linkscheck', name: 'Links check'},
	{uri: '/usersinfo', name: 'Users info'}
];

@Injectable()
export class NavigatorService {

	getComponents() {
		return COMPONENTS;
	}
}
