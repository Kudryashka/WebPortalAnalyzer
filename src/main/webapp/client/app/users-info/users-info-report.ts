export class UsersInfoReport {
	summary: GeneralSummary;
	mobilePlatforms: DevicePlatformsSummary[];
	geolocations: GeolocationSummary[];
}

export class GeneralSummary {
	requestsCount: number;
	mobileCount: number;
	tabletCount: number;
}

export class DevicePlatformsSummary {
	type: string;
	requestsCount: number;
}

export class GeolocationSummary {
	requestsCount: number;
	latitude: number;
	longitude: number;
	city: string;
	region: string;
	country: string;
}