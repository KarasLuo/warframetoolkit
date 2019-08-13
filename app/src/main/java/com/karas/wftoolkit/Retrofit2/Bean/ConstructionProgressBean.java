package com.karas.wftoolkit.Retrofit2.Bean;

/**
 * Created by Hongliang Luo on 2019/5/31.
 **/
public class ConstructionProgressBean {

    /**
     * id : 155926659735036.247691667322
     * fomorianProgress : 36.25
     * razorbackProgress : 38.19
     * unknownProgress : 0.00
     */

    private String id;
    private String fomorianProgress;
    private String razorbackProgress;
    private String unknownProgress;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFomorianProgress() {
        return fomorianProgress;
    }

    public void setFomorianProgress(String fomorianProgress) {
        this.fomorianProgress = fomorianProgress;
    }

    public String getRazorbackProgress() {
        return razorbackProgress;
    }

    public void setRazorbackProgress(String razorbackProgress) {
        this.razorbackProgress = razorbackProgress;
    }

    public String getUnknownProgress() {
        return unknownProgress;
    }

    public void setUnknownProgress(String unknownProgress) {
        this.unknownProgress = unknownProgress;
    }
}
