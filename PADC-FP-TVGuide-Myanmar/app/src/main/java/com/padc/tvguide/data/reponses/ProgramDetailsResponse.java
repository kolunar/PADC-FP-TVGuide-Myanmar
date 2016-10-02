package com.padc.tvguide.data.reponses;

import com.google.gson.annotations.SerializedName;
import com.padc.tvguide.data.vos.ProgramDetailsVO;

/**
 * Created by user on 9/24/2016.
 */
public class ProgramDetailsResponse {
    @SerializedName("code")
    private int code;

    @SerializedName("message")
    private String message;

    @SerializedName("program_details")
    private ProgramDetailsVO programDetails;

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public ProgramDetailsVO getProgramDetails() {
        return programDetails;
    }
}
