import {provideRouter, RouterConfig} from '@angular/router';

import {DashboardComponent} from '../dashboard/dashboard.component';
import {LinksCheckComponent} from '../links-check/links-check.component';
import {UsersInfoComponent} from '../users-info/users-info.component';


const routes: RouterConfig = [
	{
		path: '',
		redirectTo: '/dashboard',
		pathMatch: 'full'
	},
	{
		path: 'dashboard',
		component: DashboardComponent
	},
	{
		path: 'linkscheck',
		component: LinksCheckComponent
	},
	{
		path: 'usersinfo',
		component: UsersInfoComponent
	}

];

export const appRouterProviders = [
	provideRouter(routes)
];