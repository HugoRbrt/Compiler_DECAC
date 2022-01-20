package fr.ensimag.deca.context;

import fr.ensimag.deca.tree.Location;

public class Warning {
    String message;
    Location location;

    public Warning(String message, Location location) {
        this.message = message;
        this.location = location;
    }

    public void emit() {
        StringBuilder sb = new StringBuilder(location.toString());
        sb.deleteCharAt(0); sb.deleteCharAt(sb.length()-1);
        int i = sb.indexOf((", "));
        sb.setCharAt(i, ':'); sb.deleteCharAt(i+1);
        System.err.println(
                "[\u001B[31mWARNING\u001B[0m]" + location.getFilename() + " " +
                sb + ":" + message);
    }
}
