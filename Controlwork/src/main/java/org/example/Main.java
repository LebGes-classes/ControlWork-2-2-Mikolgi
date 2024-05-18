package org.example;

import java.io.IOException;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException {
        TVGuide tvGuide = new TVGuide("data.txt");
        System.out.println("Все отсортировано");
        List<Program> allPrograms = tvGuide.getAllPrograms();
        for (Program program : allPrograms) {
            System.out.println(program);
        }

        System.out.println("Текущие программы:");
        List<Program> currentPrograms = tvGuide.getCurrentPrograms();
        for (Program program : currentPrograms) {
            System.out.println(program);
        }

        String searchTitle = "Новости";
        List<Program> searchResults = tvGuide.searchProgramsByTitle(searchTitle);
        System.out.println("Programs with title '" + searchTitle + "':");
        for (Program program : searchResults) {
            System.out.println(program);
        }

        String searchChannel = "НТВ";
        List<Program> currentChannelPrograms = tvGuide.getCurrentProgramsByChannel(searchChannel);
        System.out.println("Текущие программы на канале '" + searchChannel + "':");
        for (Program program : currentChannelPrograms) {
            System.out.println(program);
        }

        BroadcastsTime startTime = new BroadcastsTime("9:00");
        BroadcastsTime endTime = new BroadcastsTime("12:00");
        List<Program> channelProgramsInTimeRange = tvGuide.getProgramsByChannelAndTimeRange(searchChannel, startTime, endTime);
        System.out.println("Программы на канале '" + searchChannel + "' между " + startTime + " и " + endTime + ":");
        for (Program program : channelProgramsInTimeRange) {
            System.out.println(program);
        }
        ExcelWriter.saveToExcel(allPrograms, "programs.xlsx");
    }
}


