package com.thirty.insitereadservice.flow.dto;

import com.thirty.insitereadservice.flow.dto.response.ExitFlowResDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExitFlowDto implements Comparable<ExitFlowDto>{
    private int id;
    private int exitCount;
    private String currentUrl;
    private double ratio;


    public static ExitFlowDto create(String currentUrl,int exitCount){
        return ExitFlowDto.builder().currentUrl(currentUrl).exitCount(exitCount).build();
    }
    public void addExitCount(){
        ++this.exitCount;
    }
    public ExitFlowDto addSize(int size){
        ratio= (double) exitCount/(double) size;
        return this;
    }
    public ExitFlowDto addId(int id){
        this.id=id;
        return this;
    }
    @Override
    public int compareTo(@NotNull ExitFlowDto o) {
        return o.exitCount-this.exitCount;
    }
}
