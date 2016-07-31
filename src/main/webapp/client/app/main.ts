import {bootstrap} from '@angular/platform-browser-dynamic';

import {AppComponent} from './app.component';
import {appRouterProviders} from './nav/app.routes';

bootstrap(AppComponent, [
		appRouterProviders
]);
