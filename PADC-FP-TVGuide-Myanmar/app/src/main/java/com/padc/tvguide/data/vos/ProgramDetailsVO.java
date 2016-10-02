package com.padc.tvguide.data.vos;

import java.util.ArrayList;

/**
 * Created by user on 10/1/2016.
 */

public class ProgramDetailsVO {

    private ProgramVO program_parent;
    private ProgramVO program_root;
    private ArrayList<ProgramVO> program_segments;

    public ProgramVO getProgram_parent() {
        return program_parent;
    }

    public void setProgram_parent(ProgramVO program_parent) {
        this.program_parent = program_parent;
    }

    public ProgramVO getProgram_root() {
        return program_root;
    }

    public void setProgram_root(ProgramVO program_root) {
        this.program_root = program_root;
    }

    public ArrayList<ProgramVO> getProgram_segments() {
        return program_segments;
    }

    public void setProgram_segments(ArrayList<ProgramVO> program_segments) {
        this.program_segments = program_segments;
    }

    public ChannelProgramVO getChannelProgramVOByID(int program_id, int channel_program_id){
        ChannelProgramVO channelProgramVO = null;
        for (int index = 0; index < program_segments.size(); index++) {
            if(program_segments.get(index).getProgram_id() == program_id) {
                for (ChannelProgramVO channelProgram : program_segments.get(index).getAir_repeats()){
                    if(channelProgram.getChannel_program_id() == channel_program_id){
                        channelProgramVO = channelProgram;
                        channelProgramVO.setProgram(program_segments.get(index));
                        return channelProgramVO;
                    }
                }
                break;
            }
        }
        return channelProgramVO;
    }

    public ProgramVO getProgramVOByID(int program_id){
        ProgramVO programVO = new ProgramVO();
        for (ProgramVO program : program_segments){
            if(program.getProgram_id() == program_id){
                return program;
            }
        }
        return programVO;
    }

    public static void saveProgramDetails(ProgramDetailsVO programDetailsVO) {

        ProgramVO.saveProgram(programDetailsVO.getProgram_parent());
        ProgramVO.saveProgram(programDetailsVO.getProgram_root());
        //Bulk insert into programs.
        ProgramVO.savePrograms(programDetailsVO.getProgram_segments());
    }
}
