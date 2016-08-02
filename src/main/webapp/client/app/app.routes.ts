import {provideRouter, RouterConfig} from '@angular/router';

import {DashboardComponent} from './dashboard/dashboard.component';
import {LinksCheckComponent} from './links-check/links-check.component';
import {UsersInfoComponent} from './users-info/users-info.component';
import {MobileAppsComponent} from './mobile-apps/mobile-apps.component';
import {MonitoringComponent} from './monitoring/monitoring.component';
import {OnlineHelpComponent} from './online-help/online-help.component';
import {SettingsComponent} from './settings/settings.component';


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
		path: 'settings',
		component: SettingsComponent
	}
];

export const appRouterProviders = [
	provideRouter(routes)
];