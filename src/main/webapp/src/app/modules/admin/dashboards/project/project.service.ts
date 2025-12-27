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
}
