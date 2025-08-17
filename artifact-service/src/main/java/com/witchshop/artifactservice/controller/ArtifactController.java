package com.witchshop.artifactservice.controller;

import com.witchshop.artifactservice.service.DBService;
import com.witchshop.sharedlib.dao.PipelineStepDefinition;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/artifact")
@RequiredArgsConstructor
public class ArtifactController {
    private final DBService dbService;

    @GetMapping("/blueprints")
    public List<PipelineStepDefinition> getArtifactBlueprints() {
        return dbService.selectBlueprints();
    }
}