package com.bay.webservicex.utility.parser;

import org.w3c.dom.Node;

/**
 * @Class Parsefailure
 * 
 * @author Dinesh Metkari
 * @version v0.1
 * @since 2018-02-18
 * 
 */
public class Parsefailure extends Exception {
    public Parsefailure() {
        this("No implemented means to handle tag, \n" + "  Send a message to the developer.");
    }

    public Parsefailure(String message) {
        super(message);
    }

    public Parsefailure(String message, Throwable cause) {
        super(message, cause);
    }

    public Parsefailure(Throwable cause) {
        super(cause);
    }

    public Parsefailure(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public Parsefailure(Node n) {
        this("No implemented means to handle tag: \n"
                + n.toString() + " \n\n Send a message to the developer.");
    }
}
