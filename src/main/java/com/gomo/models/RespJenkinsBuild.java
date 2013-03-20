package com.gomo.models;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class RespJenkinsBuild {

    private String fullDisplayName;
    private String result;
    private int number;
    private ChangeSet changeSet;
    private boolean building;

    public void setFullDisplayName(String fullDisplayName) {
        this.fullDisplayName = fullDisplayName;
    }

    public String getFullDisplayName() {
        return fullDisplayName;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getResult() {
        return result;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public int getNumber() {
        return number;
    }

    public void setChangeSet(ChangeSet changeSet) {
        this.changeSet = changeSet;
    }

    public ChangeSet getChangeSet() {
        return changeSet;
    }

    public void setBuilding(boolean building) {
        this.building = building;
    }

    public boolean getBuilding() {
        return building;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class ChangeSet {

        private List<Items> items;

        public void setItems(List<Items> items) {
            this.items = items;
        }

        public List<Items> getItems() {
            return items;
        }

        @JsonIgnoreProperties(ignoreUnknown = true)
        public static class Items {

            private String msg;
            private String user;

            public void setMsg(String msg) {
                this.msg = msg;
            }

            public String getMsg() {
                return msg;
            }


            public void setUser(String user) {
                this.user = user;
            }

            public String getUser() {
                return user;
            }
        }
    }
}
