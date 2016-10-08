package com.padc.mmchannels.data.vos;

/**
 * Created by user on 9/25/2016.
 */
public class UrlVO {
    private long url_id;
    private int type;
    private String label;
    private String url;
    private long row_timestamp;
    private int record_status;

    public long getUrl_id() {
        return url_id;
    }

    public void setUrl_id(long url_id) {
        this.url_id = url_id;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
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
}
