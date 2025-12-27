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
}
