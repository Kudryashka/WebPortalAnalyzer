export class OnlineHelpReport {
	summary: OnlineHelpReportSummary;
	details: OnlineHelpReportDetail[];
}

export class OnlineHelpReportSummary {
	checkCount: number;
	availableOnline: number;
	availableOffline: number;
	unavailable: number;
}

export class OnlineHelpReportDetail {
	date: string;
	isOnlineAvailable: boolean;
	isOfflineAvailable: boolean;
}