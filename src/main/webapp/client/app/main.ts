import {bootstrap} from '@angular/platform-browser-dynamic';
import {disableDeprecatedForms, provideForms} from '@angular/forms';
import {HTTP_PROVIDERS} from '@angular/http';
import {provide} from '@angular/core';

import {AppComponent} from './app.component';
import {appRouterProviders} from './app.routes';

import {GOOGLE_MAPS_PROVIDERS, LazyMapsAPILoaderConfig} from 'angular2-google-maps/core';

bootstrap(AppComponent, [
		appRouterProviders, 
		disableDeprecatedForms(),
		provideForms(),
		HTTP_PROVIDERS,
		GOOGLE_MAPS_PROVIDERS,
		provide(LazyMapsAPILoaderConfig, {useFactory: () => {
			let config = new LazyMapsAPILoaderConfig();
			config.apiKey = 'AIzaSyBo_WDq_6trNTX73M0hiUOnOvaVdbvXr5c';
			config.region = 'UA';
			return config;
		}})
]);
