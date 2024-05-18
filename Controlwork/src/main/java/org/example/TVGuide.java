package org.example;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalTime;
import java.util.*;

public class TVGuide {
    private Map<BroadcastsTime, List<Program>> timeProgramMap = new TreeMap<>();
    private List<Program> allPrograms = new ArrayList<>();

    public TVGuide(String filePath) throws IOException {
        List<String> lines = Files.readAllLines(Paths.get(filePath));
        String currentChannel = "";

        for (String line : lines) {
            if (line.startsWith("#")) {
                currentChannel = line.substring(1).trim();
            } else if (line.matches("\\d{2}:\\d{2}")) {
                BroadcastsTime time = new BroadcastsTime(line);
                String title = lines.get(lines.indexOf(line) + 1);
                Program program = new Program(currentChannel, time, title);

                timeProgramMap.putIfAbsent(time, new ArrayList<>());
                timeProgramMap.get(time).add(program);
            }
        }

        for (List<Program> programs : timeProgramMap.values()) {
            allPrograms.addAll(programs);
        }
    }

    public List<Program> getAllPrograms() {
        allPrograms.sort(Comparator.comparing(Program::getChannel).thenComparing(Program::getTime));
        return allPrograms;
    }

    public List<Program> getCurrentPrograms() {
        LocalTime now = LocalTime.now();
        BroadcastsTime currentTime = new BroadcastsTime(now.getHour() + ":" + now.getMinute());

        List<Program> currentPrograms = new ArrayList<>();
        for (BroadcastsTime time : timeProgramMap.keySet()) {
            if (time.equals(currentTime)) {
                currentPrograms.addAll(timeProgramMap.get(time));
            }
        }
        return currentPrograms;
    }

    public List<Program> searchProgramsByTitle(String title) {
        List<Program> result = new ArrayList<>();
        for (Program program : allPrograms) {
            if (program.getTitle().equalsIgnoreCase(title)) {
                result.add(program);
            }
        }
        return result;
    }

    public List<Program> getCurrentProgramsByChannel(String channel) {
        List<Program> currentPrograms = getCurrentPrograms();
        List<Program> channelPrograms = new ArrayList<>();
        for (Program program : currentPrograms) {
            if (program.getChannel().equalsIgnoreCase(channel)) {
                channelPrograms.add(program);
            }
        }
        return channelPrograms;
    }

    public List<Program> getProgramsByChannelAndTimeRange(String channel, BroadcastsTime start, BroadcastsTime end) {
        List<Program> result = new ArrayList<>();
        for (Map.Entry<BroadcastsTime, List<Program>> entry : timeProgramMap.entrySet()) {
            BroadcastsTime time = entry.getKey();
            if (time.between(start, end)) {
                for (Program program : entry.getValue()) {
                    if (program.getChannel().equalsIgnoreCase(channel)) {
                        result.add(program);
                    }
                }
            }
        }
        return result;
    }
}

