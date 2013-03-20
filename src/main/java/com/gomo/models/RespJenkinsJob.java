package com.gomo.models;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class RespJenkinsJob {

    private String displayName;

    private LastBuild lastBuild;

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public void setLastBuild(LastBuild lastBuild) {
        this.lastBuild = lastBuild;
    }

    public LastBuild getLastBuild() {
        return lastBuild;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class LastBuild {

        private String url;

        public void setUrl(String url) {
            this.url = url;
        }

        public String getUrl() {
            return url;
        }
    }
}
