package com.padc.tvguide.data.vos;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import com.padc.tvguide.TVGuideApp;
import com.padc.tvguide.data.persistence.TVGuideContract;
import com.padc.tvguide.data.persistence.TVGuideContract.ProgramEntry;

import java.util.ArrayList;
import java.util.List;

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
    private ArrayList<ChannelProgramVO> air_repeats;

    public ProgramVO(){}

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

    public ArrayList<ChannelProgramVO> getAir_repeats() {
        return air_repeats;
    }

    public void setAir_repeats(ArrayList<ChannelProgramVO> air_repeats) {
        this.air_repeats = air_repeats;
    }

    public static void saveProgram(ProgramVO program) {
        Context context = TVGuideApp.getContext();
        ContentValues channelCV = program.parseToContentValues();
        //insert into channels.
        context.getContentResolver().insert(ProgramEntry.CONTENT_URI, channelCV);

        Log.d(TVGuideApp.TAG, "Channel inserted into channel table");
    }

    public static void savePrograms(List<ProgramVO> programList) {
        Context context = TVGuideApp.getContext();
        ContentValues[] programCVs = new ContentValues[programList.size()];
        for (int index = 0; index < programList.size(); index++) {
            ProgramVO program = programList.get(index);
            programCVs[index] = program.parseToContentValues();
        }

        //Bulk insert into channels.
        int insertedCount = context.getContentResolver().bulkInsert(TVGuideContract.ProgramEntry.CONTENT_URI, programCVs);

        Log.d(TVGuideApp.TAG, "Bulk inserted into program table : " + insertedCount);
    }

    public static ProgramVO loadProgramByID(int program_id) {
        Context context = TVGuideApp.getContext();

        String selection = ProgramEntry.COLUMN_PROGRAM_ID+"=?";
        String[] selectionArgs = {String.valueOf(program_id)};

        Cursor cursor = context.getContentResolver().query(TVGuideContract.ProgramEntry.buildProgramUriWithID(program_id),
                null, selection, selectionArgs, null);

        ProgramVO program = new ProgramVO();
        if(cursor != null && cursor.moveToFirst()) {
            program = parseFromCursor(cursor);
//            do {
//                programVOList.add(parseFromCursor(cursor));
//            } while (cursor.moveToNext());
        }

        return program;
    }

    private ContentValues parseToContentValues() {
        ContentValues cv = new ContentValues();
        cv.put(ProgramEntry.COLUMN_PROGRAM_ID, program_id);
        cv.put(ProgramEntry.COLUMN_PARENT_ID, parent_id);
        cv.put(ProgramEntry.COLUMN_PROGRAM_TITLE, program_title);
        cv.put(ProgramEntry.COLUMN_PROGRAM_DESC, program_desc);
        cv.put(ProgramEntry.COLUMN_PROGRAM_IMAGE, program_image);
        cv.put(ProgramEntry.COLUMN_LANGUAGE, language);
        cv.put(ProgramEntry.COLUMN_PROGRAM_TYPE, program_type);
        cv.put(ProgramEntry.COLUMN_ROW_TIMESTAMP, row_timestamp);
        cv.put(ProgramEntry.COLUMN_RECORD_STATUS, record_status);
        return cv;
    }

    public static ProgramVO parseFromCursor(Cursor data) {
        ProgramVO program = new ProgramVO();
        program.program_id = data.getInt(data.getColumnIndex(ProgramEntry.COLUMN_PROGRAM_ID));
        program.parent_id = data.getInt(data.getColumnIndex(ProgramEntry.COLUMN_PARENT_ID));
        program.program_title = data.getString(data.getColumnIndex(ProgramEntry.COLUMN_PROGRAM_TITLE));
        program.program_desc = data.getString(data.getColumnIndex(ProgramEntry.COLUMN_PROGRAM_DESC));
        program.program_image = data.getString(data.getColumnIndex(ProgramEntry.COLUMN_PROGRAM_IMAGE));
        program.language = data.getString(data.getColumnIndex(ProgramEntry.COLUMN_LANGUAGE));
        program.program_type = data.getInt(data.getColumnIndex(ProgramEntry.COLUMN_PROGRAM_TYPE));
        program.row_timestamp = data.getInt(data.getColumnIndex(ProgramEntry.COLUMN_ROW_TIMESTAMP));
        program.record_status = data.getInt(data.getColumnIndex(ProgramEntry.COLUMN_RECORD_STATUS));
        return program;
    }
}
