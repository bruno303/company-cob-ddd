package com.bso.companycob.registration.controller;

import com.bso.companycob.registration.config.CompanyCobController;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.info.BuildProperties;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

@CompanyCobController
@RequiredArgsConstructor
public class ProjectInfoController {

    private final BuildProperties buildProperties;

    @GetMapping("v1/info")
    public ResponseEntity<BuildInfo> getInfo() {
        BuildInfo buildInfo = new BuildInfo(buildProperties.getVersion());
        return ResponseEntity.ok(buildInfo);
    }

    @Data
    public static class BuildInfo {
        private final String version;
    }
}
