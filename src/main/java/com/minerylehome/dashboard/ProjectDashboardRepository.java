package com.minerylehome.dashboard;

import com.google.gson.Gson;

import java.util.Optional;

import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.impl.DSL;
import org.springframework.stereotype.Repository;

@Repository
public class ProjectDashboardRepository {

    private final DSLContext dsl;
    private final Gson gson = new Gson();

    public ProjectDashboardRepository(DSLContext dsl) {
        this.dsl = dsl;
    }

    public Optional<ProjectDashboardController.ProjectCardData> findMetric(long accountSid, String metricKey) {
        Record record = dsl.select(
                        DSL.field("value", Integer.class),
                        DSL.field("label", String.class),
                        DSL.field("secondary_label", String.class),
                        DSL.field("secondary_value", Integer.class))
                .from(DSL.table(DSL.name("metrics")))
                .where(DSL.field("account_sid").eq(accountSid))
                .and(DSL.field("metric_key").eq(metricKey))
                .fetchOne();
        if (record == null) {
            return Optional.empty();
        }
        return Optional.of(new ProjectDashboardController.ProjectCardData(
                record.get(DSL.field("value", Integer.class)),
                record.get(DSL.field("label", String.class)),
                record.get(DSL.field("secondary_label", String.class)),
                record.get(DSL.field("secondary_value", Integer.class))));
    }

    public Optional<ProjectDashboardController.GithubIssuesData> findGithubIssues(long accountSid) {
        Record record = dsl.select(
                        DSL.field("overview_this_week_new_issues", Integer.class),
                        DSL.field("overview_this_week_closed_issues", Integer.class),
                        DSL.field("overview_this_week_fixed", Integer.class),
                        DSL.field("overview_this_week_wont_fix", Integer.class),
                        DSL.field("overview_this_week_re_opened", Integer.class),
                        DSL.field("overview_this_week_needs_triage", Integer.class),
                        DSL.field("overview_last_week_new_issues", Integer.class),
                        DSL.field("overview_last_week_closed_issues", Integer.class),
                        DSL.field("overview_last_week_fixed", Integer.class),
                        DSL.field("overview_last_week_wont_fix", Integer.class),
                        DSL.field("overview_last_week_re_opened", Integer.class),
                        DSL.field("overview_last_week_needs_triage", Integer.class),
                        DSL.field("labels", String[].class),
                        DSL.field("this_week_new_issues_name", String.class),
                        DSL.field("this_week_new_issues_type", String.class),
                        DSL.field("this_week_new_issues_data", Integer[].class),
                        DSL.field("this_week_closed_issues_name", String.class),
                        DSL.field("this_week_closed_issues_type", String.class),
                        DSL.field("this_week_closed_issues_data", Integer[].class),
                        DSL.field("last_week_new_issues_name", String.class),
                        DSL.field("last_week_new_issues_type", String.class),
                        DSL.field("last_week_new_issues_data", Integer[].class),
                        DSL.field("last_week_closed_issues_name", String.class),
                        DSL.field("last_week_closed_issues_type", String.class),
                        DSL.field("last_week_closed_issues_data", Integer[].class))
                .from(DSL.table(DSL.name("github_metrics_component")))
                .where(DSL.field("account_sid").eq(accountSid))
                .fetchOne();
        if (record == null) {
            return Optional.empty();
        }

        ProjectDashboardController.IssueBreakdown thisWeek = new ProjectDashboardController.IssueBreakdown(
                record.get(DSL.field("overview_this_week_new_issues", Integer.class)),
                record.get(DSL.field("overview_this_week_closed_issues", Integer.class)),
                record.get(DSL.field("overview_this_week_fixed", Integer.class)),
                record.get(DSL.field("overview_this_week_wont_fix", Integer.class)),
                record.get(DSL.field("overview_this_week_re_opened", Integer.class)),
                record.get(DSL.field("overview_this_week_needs_triage", Integer.class)));

        ProjectDashboardController.IssueBreakdown lastWeek = new ProjectDashboardController.IssueBreakdown(
                record.get(DSL.field("overview_last_week_new_issues", Integer.class)),
                record.get(DSL.field("overview_last_week_closed_issues", Integer.class)),
                record.get(DSL.field("overview_last_week_fixed", Integer.class)),
                record.get(DSL.field("overview_last_week_wont_fix", Integer.class)),
                record.get(DSL.field("overview_last_week_re_opened", Integer.class)),
                record.get(DSL.field("overview_last_week_needs_triage", Integer.class)));

        ProjectDashboardController.IssueSeries[] thisWeekSeries = new ProjectDashboardController.IssueSeries[] {
                new ProjectDashboardController.IssueSeries(
                        record.get(DSL.field("this_week_new_issues_name", String.class)),
                        record.get(DSL.field("this_week_new_issues_type", String.class)),
                        toIntArray(record.get(DSL.field("this_week_new_issues_data", Integer[].class)))),
                new ProjectDashboardController.IssueSeries(
                        record.get(DSL.field("this_week_closed_issues_name", String.class)),
                        record.get(DSL.field("this_week_closed_issues_type", String.class)),
                        toIntArray(record.get(DSL.field("this_week_closed_issues_data", Integer[].class))))
        };

        ProjectDashboardController.IssueSeries[] lastWeekSeries = new ProjectDashboardController.IssueSeries[] {
                new ProjectDashboardController.IssueSeries(
                        record.get(DSL.field("last_week_new_issues_name", String.class)),
                        record.get(DSL.field("last_week_new_issues_type", String.class)),
                        toIntArray(record.get(DSL.field("last_week_new_issues_data", Integer[].class)))),
                new ProjectDashboardController.IssueSeries(
                        record.get(DSL.field("last_week_closed_issues_name", String.class)),
                        record.get(DSL.field("last_week_closed_issues_type", String.class)),
                        toIntArray(record.get(DSL.field("last_week_closed_issues_data", Integer[].class))))
        };

        ProjectDashboardController.GithubIssuesOverview overview =
                new ProjectDashboardController.GithubIssuesOverview(thisWeek, lastWeek);

        ProjectDashboardController.GithubIssuesSeries series =
                new ProjectDashboardController.GithubIssuesSeries(thisWeekSeries, lastWeekSeries);

        return Optional.of(new ProjectDashboardController.GithubIssuesData(
                overview,
                record.get(DSL.field("labels", String[].class)),
                series));
    }

    public Optional<ProjectDashboardController.TaskDistributionData> findTaskDistribution(long accountSid) {
        Record record = dsl.select(
                        DSL.field("overview_this_week_new", Integer.class),
                        DSL.field("overview_this_week_completed", Integer.class),
                        DSL.field("overview_last_week_new", Integer.class),
                        DSL.field("overview_last_week_completed", Integer.class),
                        DSL.field("labels", String[].class),
                        DSL.field("this_week_series", Integer[].class),
                        DSL.field("last_week_series", Integer[].class))
                .from(DSL.table(DSL.name("task_distribution_component")))
                .where(DSL.field("account_sid").eq(accountSid))
                .fetchOne();
        if (record == null) {
            return Optional.empty();
        }

        ProjectDashboardController.TaskDistributionTotals thisWeek =
                new ProjectDashboardController.TaskDistributionTotals(
                        record.get(DSL.field("overview_this_week_new", Integer.class)),
                        record.get(DSL.field("overview_this_week_completed", Integer.class)));

        ProjectDashboardController.TaskDistributionTotals lastWeek =
                new ProjectDashboardController.TaskDistributionTotals(
                        record.get(DSL.field("overview_last_week_new", Integer.class)),
                        record.get(DSL.field("overview_last_week_completed", Integer.class)));

        ProjectDashboardController.TaskDistributionOverview overview =
                new ProjectDashboardController.TaskDistributionOverview(thisWeek, lastWeek);

        ProjectDashboardController.TaskDistributionSeries series =
                new ProjectDashboardController.TaskDistributionSeries(
                        toIntArray(record.get(DSL.field("this_week_series", Integer[].class))),
                        toIntArray(record.get(DSL.field("last_week_series", Integer[].class))));

        return Optional.of(new ProjectDashboardController.TaskDistributionData(
                overview,
                record.get(DSL.field("labels", String[].class)),
                series));
    }

    public Optional<ProjectDashboardController.ScheduleData> findSchedule(long accountSid) {
        Record record = dsl.select(
                        DSL.field("today", String.class),
                        DSL.field("tomorrow", String.class))
                .from(DSL.table(DSL.name("schedule_component")))
                .where(DSL.field("account_sid").eq(accountSid))
                .fetchOne();
        if (record == null) {
            return Optional.empty();
        }

        ProjectDashboardController.ScheduleItem[] today = gson.fromJson(
                record.get(DSL.field("today", String.class)),
                ProjectDashboardController.ScheduleItem[].class);
        ProjectDashboardController.ScheduleItem[] tomorrow = gson.fromJson(
                record.get(DSL.field("tomorrow", String.class)),
                ProjectDashboardController.ScheduleItem[].class);

        return Optional.of(new ProjectDashboardController.ScheduleData(today, tomorrow));
    }

    private int[] toIntArray(Integer[] values) {
        if (values == null) {
            return new int[0];
        }
        int[] result = new int[values.length];
        for (int i = 0; i < values.length; i++) {
            result[i] = values[i] == null ? 0 : values[i];
        }
        return result;
    }
}
