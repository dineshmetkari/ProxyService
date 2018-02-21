package com.bay.webservicex.utility.parser.soaptojson.helpers;

import org.w3c.dom.DocumentType;
import org.w3c.dom.Element;

/**
 * @Class XmlNodeToJsonElement 
 * 
 * @author Dinesh Metkari
 * @version v0.1
 * @since 2018-02-18
 * 
 */
public class XmlNodeToJsonElement {
    private XmlNodeToJsonElement() {
        throw new IllegalStateException();
    }

    public static Object[] entity(org.w3c.dom.Entity e) {
        return new String[]{e.getNodeName(), e.getNodeValue()};
    }

    public static Object element(Element n, Object[] attrs, Object[] children) {
        if (children.length <= 0 && attrs.length <= 0) {
            return new Object[]{n.getNodeType(), n.getNodeName()};
        } else if (attrs.length <= 0) {
            return new Object[]{n.getNodeType(), n.getNodeName(), children};
        } else {
            return new Object[]{n.getNodeType(), n.getNodeName(), children, attrs};
        }
    }

    /**
     * Each Document has a doctype attribute whose value is either null or a DocumentType object.
     * The DocumentType interface in the DOM Core provides an interface to the list of entities
     * that are defined for the document, and little else because the effect of namespaces and
     * the various XML schema efforts on DTD representation are not clearly understood as of
     * this writing.
     * <p>
     * DOM Level 3 doesn't support editing DocumentType nodes. DocumentType nodes are read-only.
     */
    public static Object documentType(DocumentType dtd, String[][] entities, String[][] notations) {
        return new Object[]{
                dtd.getNodeType(),// short
                dtd.getName(),// String
                entities,// NamedNodeMap
                notations,// NamedNodeMap
                dtd.getPublicId(),// String
                dtd.getSystemId(),// String
                dtd.getInternalSubset() //String
        };
    }
}
