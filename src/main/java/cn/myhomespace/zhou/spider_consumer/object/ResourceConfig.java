package cn.myhomespace.zhou.spider_consumer.object;

import java.util.List;

/**
 * Created by zhouw on 2018/5/4.
 */
public class ResourceConfig {


    /**
     * {
     "type":"common",
     "analysis":[{
     "tag":"a","attr":"href","match":[]
     }],
     "opartation":"save"
     }

     {
     "type":"match",
     "analysis":[],
     "match":["[A-Za-z0-9]{40}","[A-Za-z0-9]{41}"],
     "opartation":"save"
     }

     {
     "type":"match",
     "analysis":[],
     "match":["[A-Za-z0-9]{40}","[A-Za-z0-9]{41}"],
     "origin":""
     "opartation":"append"
     }

     {
     "type":"match",
     "analysis":[],
     "match":["[A-Za-z0-9]{40}","[A-Za-z0-9]{41}"],
     "origin":""
     "opartation":"prepend"
     }
     */
    private String engineVersion;
    private String type;
    private List<Analysis> analysises;
    private List<String> matchs;
    private String origin;
    private String opertation;

    public String getEngineVersion() {
        return engineVersion;
    }

    public void setEngineVersion(String engineVersion) {
        this.engineVersion = engineVersion;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<Analysis> getAnalysises() {
        return analysises;
    }

    public void setAnalysises(List<Analysis> analysises) {
        this.analysises = analysises;
    }

    public List<String> getMatchs() {
        return matchs;
    }

    public void setMatchs(List<String> matchs) {
        this.matchs = matchs;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getOpertation() {
        return opertation;
    }

    public void setOpertation(String opertation) {
        this.opertation = opertation;
    }
}
