package com.minerylehome.dashboard;

import com.google.gson.Gson;

import jakarta.servlet.http.HttpSession;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.minerylehome.auth.AuthSessionAttributes;

@RestController
@RequestMapping("/api/dashboards/project")
public class ProjectDashboardController {

    private final ProjectDashboardRepository dashboardRepository;
    private final Gson gson = new Gson();

    public ProjectDashboardController(ProjectDashboardRepository dashboardRepository) {
        this.dashboardRepository = dashboardRepository;
    }

    @GetMapping(value = "/summary", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getSummaryCard(HttpSession session) {
        Long userId = getUserId(session);
        if (userId == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        return jsonResponse(dashboardRepository.findMetric(userId, "summary")
                .orElseGet(() -> new ProjectCardData(0, "Due Tasks", "Completed:", 0)));
    }

    @GetMapping(value = "/overdue", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getOverdueCard(HttpSession session) {
        Long userId = getUserId(session);
        if (userId == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        return jsonResponse(dashboardRepository.findMetric(userId, "overdue")
                .orElseGet(() -> new ProjectCardData(0, "Tasks", "From yesterday:", 0)));
    }

    @GetMapping(value = "/issues", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getIssuesCard(HttpSession session) {
        Long userId = getUserId(session);
        if (userId == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        return jsonResponse(dashboardRepository.findMetric(userId, "issues")
                .orElseGet(() -> new ProjectCardData(0, "Open", "Closed today:", 0)));
    }

    @GetMapping(value = "/features", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getFeaturesCard(HttpSession session) {
        Long userId = getUserId(session);
        if (userId == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        return jsonResponse(dashboardRepository.findMetric(userId, "features")
                .orElseGet(() -> new ProjectCardData(0, "Proposals", "Implemented:", 0)));
    }

    @GetMapping(value = "/projects/selected", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getSelectedProject() {
        ProjectSelection payload = new ProjectSelection("ACME Corp. Backend App");
        return jsonResponse(payload);
    }

    @GetMapping(value = "/projects", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getProjectOptions() {
        ProjectOptions payload = new ProjectOptions(new String[] {
                "ACME Corp. Backend App",
                "ACME Corp. Frontend App",
                "Creapond",
                "Withinpixels"
        });
        return jsonResponse(payload);
    }

    @GetMapping(value = "/github-issues", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getGithubIssues(HttpSession session) {
        Long userId = getUserId(session);
        if (userId == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        return jsonResponse(dashboardRepository.findGithubIssues(userId)
                .orElseGet(this::emptyGithubIssues));
    }

    @GetMapping(value = "/task-distribution", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getTaskDistribution(HttpSession session) {
        Long userId = getUserId(session);
        if (userId == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        return jsonResponse(dashboardRepository.findTaskDistribution(userId)
                .orElseGet(this::emptyTaskDistribution));
    }

    @GetMapping(value = "/schedule", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getSchedule(HttpSession session) {
        Long userId = getUserId(session);
        if (userId == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        return jsonResponse(dashboardRepository.findSchedule(userId)
                .orElseGet(this::emptySchedule));
    }

    private ResponseEntity<String> jsonResponse(Object payload) {
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(gson.toJson(payload));
    }

    private Long getUserId(HttpSession session) {
        Object id = session.getAttribute(AuthSessionAttributes.USER_ID);
        if (id instanceof Long userId) {
            return userId;
        }
        return null;
    }

    private GithubIssuesData emptyGithubIssues() {
        IssueBreakdown emptyBreakdown = new IssueBreakdown(0, 0, 0, 0, 0, 0);
        IssueSeries[] emptySeries = new IssueSeries[] {
                new IssueSeries("No data available", "line", new int[0]),
                new IssueSeries("No data available", "column", new int[0])
        };
        return new GithubIssuesData(
                new GithubIssuesOverview(emptyBreakdown, emptyBreakdown),
                new String[0],
                new GithubIssuesSeries(emptySeries, emptySeries));
    }

    private TaskDistributionData emptyTaskDistribution() {
        TaskDistributionTotals emptyTotals = new TaskDistributionTotals(0, 0);
        return new TaskDistributionData(
                new TaskDistributionOverview(emptyTotals, emptyTotals),
                new String[0],
                new TaskDistributionSeries(new int[0], new int[0]));
    }

    private ScheduleData emptySchedule() {
        ScheduleItem emptyItem = new ScheduleItem("No data available", "", null);
        return new ScheduleData(new ScheduleItem[] { emptyItem }, new ScheduleItem[] { emptyItem });
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
