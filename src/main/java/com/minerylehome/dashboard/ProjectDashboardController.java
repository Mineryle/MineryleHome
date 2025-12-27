package com.minerylehome.dashboard;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/dashboards/project")
public class ProjectDashboardController {

    @GetMapping(value = "/summary", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ProjectCardData> getSummaryCard() {
        return ResponseEntity.ok(new ProjectCardData(21, "Due Tasks", "Completed:", 13));
    }

    @GetMapping(value = "/overdue", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ProjectCardData> getOverdueCard() {
        return ResponseEntity.ok(new ProjectCardData(17, "Tasks", "From yesterday:", 9));
    }

    @GetMapping(value = "/issues", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ProjectCardData> getIssuesCard() {
        return ResponseEntity.ok(new ProjectCardData(24, "Open", "Closed today:", 19));
    }

    @GetMapping(value = "/features", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ProjectCardData> getFeaturesCard() {
        return ResponseEntity.ok(new ProjectCardData(38, "Proposals", "Implemented:", 16));
    }

    @GetMapping(value = "/projects/selected", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ProjectSelection> getSelectedProject() {
        return ResponseEntity.ok(new ProjectSelection("ACME Corp. Backend App"));
    }

    @GetMapping(value = "/projects", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ProjectOptions> getProjectOptions() {
        return ResponseEntity.ok(new ProjectOptions(new String[] {
                "ACME Corp. Backend App",
                "ACME Corp. Frontend App",
                "Creapond",
                "Withinpixels"
        }));
    }

    @GetMapping(value = "/github-issues", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<GithubIssuesData> getGithubIssues() {
        return ResponseEntity.ok(new GithubIssuesData(
                new GithubIssuesOverview(
                        new IssueBreakdown(214, 75, 3, 4, 8, 6),
                        new IssueBreakdown(197, 72, 6, 11, 6, 5)),
                new String[] { "Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun" },
                new GithubIssuesSeries(
                        new IssueSeries[] {
                                new IssueSeries("New issues", "line", new int[] { 42, 28, 43, 34, 20, 25, 22 }),
                                new IssueSeries("Closed issues", "column", new int[] { 11, 10, 8, 11, 8, 10, 17 })
                        },
                        new IssueSeries[] {
                                new IssueSeries("New issues", "line", new int[] { 37, 32, 39, 27, 18, 24, 20 }),
                                new IssueSeries("Closed issues", "column", new int[] { 9, 8, 10, 12, 7, 11, 15 })
                        })));
    }

    @GetMapping(value = "/task-distribution", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TaskDistributionData> getTaskDistribution() {
        return ResponseEntity.ok(new TaskDistributionData(
                new TaskDistributionOverview(
                        new TaskDistributionTotals(594, 287),
                        new TaskDistributionTotals(526, 260)),
                new String[] { "API", "Backend", "Frontend", "Issues" },
                new TaskDistributionSeries(
                        new int[] { 15, 20, 38, 27 },
                        new int[] { 19, 16, 42, 23 })));
    }

    @GetMapping(value = "/schedule", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ScheduleData> getSchedule() {
        return ResponseEntity.ok(new ScheduleData(
                new ScheduleItem[] {
                        new ScheduleItem("Group Meeting", "in 32 minutes", "Conference room 1B"),
                        new ScheduleItem("Coffee Break", "10:30 AM", null),
                        new ScheduleItem("Public Beta Release", "11:00 AM", null),
                        new ScheduleItem("Lunch", "12:10 PM", null),
                        new ScheduleItem("Dinner with David", "05:30 PM", "Magnolia"),
                        new ScheduleItem("Jane's Birthday Party", "07:30 PM", "Home"),
                        new ScheduleItem("Overseer's Retirement Party", "09:30 PM", "Overseer's room")
                },
                new ScheduleItem[] {
                        new ScheduleItem("Marketing Meeting", "09:00 AM", "Conference room 1A"),
                        new ScheduleItem("Public Announcement", "11:00 AM", null),
                        new ScheduleItem("Lunch", "12:10 PM", null),
                        new ScheduleItem("Meeting with Beta Testers", "03:00 PM", "Conference room 2C"),
                        new ScheduleItem("Live Stream", "05:30 PM", null),
                        new ScheduleItem("Release Party", "07:30 PM", "CEO's house"),
                        new ScheduleItem("CEO's Private Party", "09:30 PM", "CEO's Penthouse")
                }));
    }

    public record ProjectCardData(
            int value,
            String label,
            String secondaryLabel,
            int secondaryValue) {
    }

    public record ProjectSelection(String name) {
    }

    public record ProjectOptions(String[] options) {
    }

    public record GithubIssuesData(
            GithubIssuesOverview overview,
            String[] labels,
            GithubIssuesSeries series) {
    }

    public record GithubIssuesOverview(
            @com.fasterxml.jackson.annotation.JsonProperty("this-week") IssueBreakdown thisWeek,
            @com.fasterxml.jackson.annotation.JsonProperty("last-week") IssueBreakdown lastWeek) {
    }

    public record IssueBreakdown(
            @com.fasterxml.jackson.annotation.JsonProperty("new-issues") int newIssues,
            @com.fasterxml.jackson.annotation.JsonProperty("closed-issues") int closedIssues,
            int fixed,
            @com.fasterxml.jackson.annotation.JsonProperty("wont-fix") int wontFix,
            @com.fasterxml.jackson.annotation.JsonProperty("re-opened") int reOpened,
            @com.fasterxml.jackson.annotation.JsonProperty("needs-triage") int needsTriage) {
    }

    public record GithubIssuesSeries(
            @com.fasterxml.jackson.annotation.JsonProperty("this-week") IssueSeries[] thisWeek,
            @com.fasterxml.jackson.annotation.JsonProperty("last-week") IssueSeries[] lastWeek) {
    }

    public record IssueSeries(
            String name,
            String type,
            int[] data) {
    }

    public record TaskDistributionData(
            TaskDistributionOverview overview,
            String[] labels,
            TaskDistributionSeries series) {
    }

    public record TaskDistributionOverview(
            @com.fasterxml.jackson.annotation.JsonProperty("this-week") TaskDistributionTotals thisWeek,
            @com.fasterxml.jackson.annotation.JsonProperty("last-week") TaskDistributionTotals lastWeek) {
    }

    public record TaskDistributionTotals(
            @com.fasterxml.jackson.annotation.JsonProperty("new") int newCount,
            int completed) {
    }

    public record TaskDistributionSeries(
            @com.fasterxml.jackson.annotation.JsonProperty("this-week") int[] thisWeek,
            @com.fasterxml.jackson.annotation.JsonProperty("last-week") int[] lastWeek) {
    }

    public record ScheduleData(
            ScheduleItem[] today,
            ScheduleItem[] tomorrow) {
    }

    public record ScheduleItem(
            String title,
            String time,
            String location) {
    }
}
