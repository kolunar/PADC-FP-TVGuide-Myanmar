package com.padc.tvguide.data.vos;

import java.util.ArrayList;

/**
 * Created by user on 9/17/2016.
 */
public class ProgramVO {

    private int program_id;
    private int parent_id;
    private String program_title;
    private String parent_title;
    private String program_desc;
    private String program_image;
    private String language;
    private int program_type;
    private long row_timestamp;
    private int record_status;
    private ArrayList<TagVO> tags;
    private ArrayList<UrlVO> ref_urls;

    public ProgramVO(int id, String name, String icon) {
        this.program_id = id;
        this.program_title = name;
        this.program_image = icon;
    }

    public int getProgram_id() {
        return program_id;
    }

    public void setProgram_id(int program_id) {
        this.program_id = program_id;
    }

    public int getParent_id() {
        return parent_id;
    }

    public void setParent_id(int parent_id) {
        this.parent_id = parent_id;
    }

    public String getProgram_title() {
        return program_title;
    }

    public void setProgram_title(String program_title) {
        this.program_title = program_title;
    }

    public String getParent_title() {
        return parent_title;
    }

    public void setParent_title(String parent_title) {
        this.parent_title = parent_title;
    }

    public String getProgram_desc() {
        return program_desc;
    }

    public void setProgram_desc(String program_desc) {
        this.program_desc = program_desc;
    }

    public String getProgram_image() {
        return program_image;
    }

    public void setProgram_image(String program_image) {
        this.program_image = program_image;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public int getProgram_type() {
        return program_type;
    }

    public void setProgram_type(int program_type) {
        this.program_type = program_type;
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

    public ArrayList<TagVO> getTags() {
        return tags;
    }

    public void setTags(ArrayList<TagVO> tags) {
        this.tags = tags;
    }

    public ArrayList<UrlVO> getRef_urls() {
        return ref_urls;
    }

    public void setRef_urls(ArrayList<UrlVO> ref_urls) {
        this.ref_urls = ref_urls;
    }
}
