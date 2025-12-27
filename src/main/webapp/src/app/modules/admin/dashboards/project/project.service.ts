import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable, tap } from 'rxjs';

export interface ProjectCardData {
    value: number;
    label: string;
    secondaryLabel: string;
    secondaryValue: number;
}

export interface ProjectSelection {
    name: string;
}

export interface ProjectOptions {
    options: string[];
}

export interface GithubIssuesData {
    overview: GithubIssuesOverview;
    labels: string[];
    series: GithubIssuesSeries;
}

export interface GithubIssuesOverview {
    'this-week': GithubIssuesBreakdown;
    'last-week': GithubIssuesBreakdown;
}

export interface GithubIssuesBreakdown {
    'new-issues': number;
    'closed-issues': number;
    fixed: number;
    'wont-fix': number;
    're-opened': number;
    'needs-triage': number;
}

export interface GithubIssuesSeries {
    'this-week': GithubIssuesSeriesEntry[];
    'last-week': GithubIssuesSeriesEntry[];
}

export interface GithubIssuesSeriesEntry {
    name: string;
    type: string;
    data: number[];
}

export interface TaskDistributionData {
    overview: TaskDistributionOverview;
    labels: string[];
    series: TaskDistributionSeries;
}

export interface TaskDistributionOverview {
    'this-week': TaskDistributionTotals;
    'last-week': TaskDistributionTotals;
}

export interface TaskDistributionTotals {
    new: number;
    completed: number;
}

export interface TaskDistributionSeries {
    'this-week': number[];
    'last-week': number[];
}

export interface ScheduleData {
    today: ScheduleItem[];
    tomorrow: ScheduleItem[];
}

export interface ScheduleItem {
    title: string;
    time?: string;
    location?: string;
}

@Injectable({ providedIn: 'root' })
export class ProjectService {
    private _data: BehaviorSubject<any> = new BehaviorSubject(null);

    /**
     * Constructor
     */
    constructor(private _httpClient: HttpClient) {}

    // -----------------------------------------------------------------------------------------------------
    // @ Accessors
    // -----------------------------------------------------------------------------------------------------

    /**
     * Getter for data
     */
    get data$(): Observable<any> {
        return this._data.asObservable();
    }

    // -----------------------------------------------------------------------------------------------------
    // @ Public methods
    // -----------------------------------------------------------------------------------------------------

    /**
     * Get data
     */
    getData(): Observable<any> {
        return this._httpClient.get('api/dashboards/project').pipe(
            tap((response: any) => {
                this._data.next(response);
            })
        );
    }

    getSummaryCard(): Observable<ProjectCardData> {
        return this._httpClient.get<ProjectCardData>(
            'api/dashboards/project/summary'
        );
    }

    getOverdueCard(): Observable<ProjectCardData> {
        return this._httpClient.get<ProjectCardData>(
            'api/dashboards/project/overdue'
        );
    }

    getIssuesCard(): Observable<ProjectCardData> {
        return this._httpClient.get<ProjectCardData>(
            'api/dashboards/project/issues'
        );
    }

    getFeaturesCard(): Observable<ProjectCardData> {
        return this._httpClient.get<ProjectCardData>(
            'api/dashboards/project/features'
        );
    }

    getSelectedProject(): Observable<ProjectSelection> {
        return this._httpClient.get<ProjectSelection>(
            'api/dashboards/project/projects/selected'
        );
    }

    getProjectOptions(): Observable<ProjectOptions> {
        return this._httpClient.get<ProjectOptions>(
            'api/dashboards/project/projects'
        );
    }

    getGithubIssues(): Observable<GithubIssuesData> {
        return this._httpClient.get<GithubIssuesData>(
            'api/dashboards/project/github-issues'
        );
    }

    getTaskDistribution(): Observable<TaskDistributionData> {
        return this._httpClient.get<TaskDistributionData>(
            'api/dashboards/project/task-distribution'
        );
    }

    getSchedule(): Observable<ScheduleData> {
        return this._httpClient.get<ScheduleData>(
            'api/dashboards/project/schedule'
        );
    }
}
