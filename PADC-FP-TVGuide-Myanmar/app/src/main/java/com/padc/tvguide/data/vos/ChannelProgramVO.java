package com.padc.tvguide.data.vos;

import java.util.ArrayList;

/**
 * Created by user on 9/17/2016.
 */
public class ChannelProgramVO {

    private int channel_program_id;
    private String air_date;
    private String start_time;
    private int duration;
    private int channel_id;
    private int program_id;
    private long row_timestamp;
    private int record_status;
    private ProgramVO program;
/*    private int parent_id;
    private String program_title;
    private String parent_title;
    private String program_desc;
    private String program_image;
    private ArrayList<TagVO> tags;*/

    public int getChannel_program_id() {
        return channel_program_id;
    }

    public void setChannel_program_id(int channel_program_id) {
        this.channel_program_id = channel_program_id;
    }

    public String getAir_date() {
        return air_date;
    }

    public void setAir_date(String air_date) {
        this.air_date = air_date;
    }

    public String getStart_time() {
        return start_time;
    }

    public void setStart_time(String start_time) {
        this.start_time = start_time;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public int getChannel_id() {
        return channel_id;
    }

    public void setChannel_id(int channel_id) {
        this.channel_id = channel_id;
    }

    public int getProgram_id() {
        return program_id;
    }

    public void setProgram_id(int program_id) {
        this.program_id = program_id;
    }

    public long getRow_timestamp() {
        return row_timestamp;
    }

    public void setRow_timestamp(long row_timestamp) {
        this.row_timestamp = row_timestamp;
    }

    public int getRecord_status() {
        return record_status;
    }

    public void setRecord_status(int record_status) {
        this.record_status = record_status;
    }

    public ProgramVO getProgram() {
        return program;
    }

    public void setProgram(ProgramVO program) {
        this.program = program;
    }
}
