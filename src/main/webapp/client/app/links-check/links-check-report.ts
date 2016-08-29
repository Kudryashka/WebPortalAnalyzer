export class LinksCheckReport {
	summary: LinksCheckReportSummary;
	details: LinksCheckReportDetails[];
}

export class LinksCheckReportSummary {
	checkCount: number;
	okLinksCount: number;
	errorLinksCount: number;
	unreachableLinksCount: number;
	redirectLinksCount: number;
}

export class LinksCheckReportDetails {
	date: string;
	summary: LinksCheckReportDetailsSummary;
	okLinks: LinksCheckReportOkLink[];
	errorLinks: LinksCheckReportErrorLink[];
	unreachableLinks: LinksCheckReportUnreachableLink[];
	redirectLinks: LinksCheckReportRedirectLink[];
}

export class LinksCheckReportDetailsSummary {
	okLinksCount: number;
	errorLinksCount: number;
	unreachableLinksCount: number;
	redirectLinksCount: number;
}

export class LinksCheckReportOkLink {
	type: string;
	location: string;
	target: string;
	responseCode: number;
}

export class LinksCheckReportErrorLink {
	type: string;
	location: string;
	target: string;
	responseCode: number;	
}

export class LinksCheckReportUnreachableLink {
	type: string;
	location: string;
	target: string;
}

export class LinksCheckReportRedirectLink {
	type: string;
	location: string;
	target: string;
	redirectUrl: string;
	responseCode: number;
}