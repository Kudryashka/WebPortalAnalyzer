import {provideRouter, RouterConfig} from '@angular/router';

import {AuthenticationComponent} from './authentication/authentication.component';
import {DashboardComponent} from './dashboard/dashboard.component';
import {LinksCheckComponent} from './links-check/links-check.component';
import {UsersInfoComponent} from './users-info/users-info.component';
import {MobileAppsComponent} from './mobile-apps/mobile-apps.component';
import {MonitoringComponent} from './monitoring/monitoring.component';
import {OnlineHelpComponent} from './online-help/online-help.component';
import {SettingsComponent} from './settings/settings.component';
import {SearchComponent} from './search/search.component';


const routes: RouterConfig = [
	{
		path: '',
		redirectTo: '/login',
		pathMatch: 'full'
	},
	{
		path: 'login',
		component: AuthenticationComponent
	},
	{
		path: 'dashboard',
		component: DashboardComponent
	},
	{
		path: 'links',
		component: LinksCheckComponent
	},
	{
		path: 'users',
		component: UsersInfoComponent
	},
	{
		path: 'mobile',
		component: MobileAppsComponent
	},
	{
		path: 'monitoring',
		component: MonitoringComponent
	},
	{
		path: 'helps',
		component: OnlineHelpComponent
	},
	{
		path: 'search',
		component: SearchComponent
	},
	{
		path: 'settings',
		component: SettingsComponent
	}
];

export const appRouterProviders = [
	provideRouter(routes)
];