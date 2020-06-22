package com.ebicep.undeadstorm;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class User implements Serializable {
    private String name;
    private int kills;
    private DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
    private String date;

    public User(String name, int kills) {
        if (name == null)
            this.name = "Unknown";
        else
            this.name = name;
        this.kills = kills;
        date = dateFormat.format(new Date());
    }

    public String toString() {
        return name + "---" + kills + "---" + date;
    }

    public String getName() {
        return name;
    }

    public int getKills() {
        return kills;
    }

    public String getDate() {
        return date;
    }
}
