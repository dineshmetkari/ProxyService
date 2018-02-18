package com.bay.webservicex.utility.parser.helpers;

import java.util.Locale;

/**
 * @Class Const
 * 
 * @author Dinesh Metkari
 * @version v0.1
 * @since 2018-02-18
 * 
 */
public class Const {

    public static final String[] nodeTypes = new String[13];

    static {
        nodeTypes[1] = "ELEMENT".toLowerCase(Locale.US);
        nodeTypes[2] = "ATTRIBUTE".toLowerCase(Locale.US);
        nodeTypes[3] = "TEXT".toLowerCase(Locale.US);
        nodeTypes[4] = "CDATA_SECTION".toLowerCase(Locale.US);
        nodeTypes[5] = "ENTITY_REFERENCE".toLowerCase(Locale.US);
        nodeTypes[6] = "ENTITY".toLowerCase(Locale.US);
        nodeTypes[7] = "PROCESSING_INSTRUCTION".toLowerCase(Locale.US);
        nodeTypes[8] = "COMMENT".toLowerCase(Locale.US);
        nodeTypes[9] = "DOCUMENT".toLowerCase(Locale.US);
        nodeTypes[10] = "DOCUMENT_TYPE".toLowerCase(Locale.US);
        nodeTypes[11] = "DOCUMENT_FRAGMENT".toLowerCase(Locale.US);
        nodeTypes[12] = "NOTATION".toLowerCase(Locale.US);
    }

}
