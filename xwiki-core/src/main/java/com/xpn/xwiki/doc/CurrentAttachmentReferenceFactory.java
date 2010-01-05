/*
 * See the NOTICE file distributed with this work for additional
 * information regarding copyright ownership.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */
package com.xpn.xwiki.doc;

import org.xwiki.component.annotation.Component;
import org.xwiki.component.annotation.Requirement;
import org.xwiki.model.EntityType;
import org.xwiki.model.reference.AttachmentReference;
import org.xwiki.model.reference.AttachmentReferenceFactory;
import org.xwiki.model.reference.EntityReferenceFactory;

/**
 * Specialized version of {@link org.xwiki.model.reference.EntityReferenceFactory} which can be considered a helper
 * component to create {@link AttachmentReference} objects from their string representation. This implementation
 * uses values from the current document reference in the context when parts of the Reference are missing in the string
 * representation. 
 *
 * @version $Id$
 * @since 2.2M1
 */
@Component("current")
public class CurrentAttachmentReferenceFactory implements AttachmentReferenceFactory<String>
{
    @Requirement("current")
    private EntityReferenceFactory entityReferenceFactory;

    public AttachmentReference createAttachmentReference(String attachmentReferenceRepresentation)
    {
        return new AttachmentReference(this.entityReferenceFactory.createEntityReference(
            attachmentReferenceRepresentation, EntityType.ATTACHMENT));
    }
}