/* 
 * Copyright 2002-2008 Andy Clark
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.cyberneko.html.filters;

import org.apache.xerces.xni.Augmentations;
import org.apache.xerces.xni.QName;
import org.apache.xerces.xni.XMLAttributes;
import org.apache.xerces.xni.XNIException;
import org.cyberneko.html.HTMLEventInfo;

/**
 * This filter performs the identity operation of the original 
 * document event stream generated by the HTML scanner by removing 
 * events that are synthesized by the tag balancer. This operation
 * is essentially the same as turning off tag-balancing in the
 * parser. However, this filter is useful when you want the tag
 * balancer to report "errors" but do not want the synthesized
 * events in the output.
 * <p>
 * <strong>Note:</strong>
 * This filter requires the augmentations feature to be turned on.
 * For example:
 * <pre>
 *  XMLParserConfiguration parser = new HTMLConfiguration();
 *  parser.setFeature("http://cyberneko.org/html/features/augmentations", true);
 * </pre>
 * <p>
 * <strong>Note:</strong>
 * This isn't <em>exactly</em> the identify transform because the
 * element and attributes names may have been modified from the
 * original document. For example, by default, NekoHTML converts
 * element names to upper-case and attribute names to lower-case.
 *
 * @author Andy Clark
 *
 * @version $Id: Identity.java,v 1.4 2005/02/14 03:56:54 andyc Exp $
 */
public class Identity
    extends DefaultFilter {

    //
    // Constants
    //

    /** Augmentations feature identifier. */
    protected static final String AUGMENTATIONS = "http://cyberneko.org/html/features/augmentations";

    /** Filters property identifier. */
    protected static final String FILTERS = "http://cyberneko.org/html/properties/filters";

    //
    // XMLDocumentHandler methods
    //

    /** Start element. */
    public void startElement(QName element, XMLAttributes attributes,
                             Augmentations augs) throws XNIException {
        if (augs == null || !synthesized(augs)) {
            super.startElement(element, attributes, augs);
        }
    } // startElement(QName,XMLAttributes,Augmentations)

    /** Empty element. */
    public void emptyElement(QName element, XMLAttributes attributes,
                             Augmentations augs) throws XNIException {
        if (augs == null || !synthesized(augs)) {
            super.emptyElement(element, attributes, augs);
        }
    } // emptyElement(QName,XMLAttributes,Augmentations)

    /** End element. */
    public void endElement(QName element, Augmentations augs) 
        throws XNIException {
        if (augs == null || !synthesized(augs)) {
            super.endElement(element, augs);
        }
    } // endElement(QName,XMLAttributes,Augmentations)

    //
    // Protected static methods
    //

    /** Returns true if the information provided is synthesized. */
    protected static boolean synthesized(Augmentations augs) {
        HTMLEventInfo info = (HTMLEventInfo)augs.getItem(AUGMENTATIONS);
        return info != null ? info.isSynthesized() : false;
    } // synthesized(Augmentations):boolean

} // class Identity
