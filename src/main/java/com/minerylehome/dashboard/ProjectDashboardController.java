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
}
