package org.example;


public class BroadcastsTime implements Comparable<BroadcastsTime> {
    private byte hour;
    private byte minute;

    public BroadcastsTime(String time) {
        String[] parts = time.split(":");
        this.hour = Byte.parseByte(parts[0]);
        this.minute = Byte.parseByte(parts[1]);
    }

    public byte hour() {
        return hour;
    }

    public byte minutes() {
        return minute;
    }

    public boolean after(BroadcastsTime t) {
        return this.compareTo(t) > 0;
    }

    public boolean before(BroadcastsTime t) {
        return this.compareTo(t) < 0;
    }

    public boolean between(BroadcastsTime t1, BroadcastsTime t2) {
        return this.after(t1) && this.before(t2);
    }

    @Override
    public int compareTo(BroadcastsTime t) {
        if (this.hour == t.hour) {
            return this.minute - t.minute;
        }
        return this.hour - t.hour;
    }

    @Override
    public String toString() {
        return String.format("%02d:%02d", hour, minute);
    }
}


