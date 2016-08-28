import {bootstrap} from '@angular/platform-browser-dynamic';
import {disableDeprecatedForms, provideForms} from '@angular/forms';
import {HTTP_PROVIDERS} from '@angular/http';

import {AppComponent} from './app.component';
import {appRouterProviders} from './app.routes';

import {GOOGLE_MAPS_PROVIDERS} from 'angular2-google-maps/core';

bootstrap(AppComponent, [
		appRouterProviders, 
		disableDeprecatedForms(),
		provideForms(),
		HTTP_PROVIDERS,
		GOOGLE_MAPS_PROVIDERS
]);
