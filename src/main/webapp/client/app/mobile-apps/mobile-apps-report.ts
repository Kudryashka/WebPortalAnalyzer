export class MobileAppsReport {
	summary: MobileAppsReportSummary;
	details: MobileAppsReportDetail[];
}

export class MobileAppsReportSummary {
	checkCount: number;
	failedCheckCount: number;
}

export class MobileAppsReportDetail {
	date: string;
	checkType: string;
	apps: MobileAppsReportApp[];
}

export class MobileAppsReportApp {
	appName: string;
	appUrl: string;
	isAvailable: boolean;
}