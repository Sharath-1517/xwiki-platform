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
package org.xwiki.rendering.macro.script;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.xwiki.bridge.DocumentAccessBridge;
import org.xwiki.component.descriptor.DefaultComponentDescriptor;
import org.xwiki.component.manager.ComponentManager;
import org.xwiki.model.reference.AttachmentReferenceFactory;
import org.xwiki.model.reference.DocumentReferenceFactory;

/**
 * Dynamic mock setup for script macros.
 * 
 * @version $Id$
 * @since 2.0RC1
 */
public class ScriptMockSetup
{
    public Mockery mockery;
    
    public DocumentAccessBridge bridge;
    
    public AttachmentReferenceFactory attachmentReferenceFactory;
    
    public DocumentReferenceFactory documentReferenceFactory;

    public ScriptMockSetup(ComponentManager componentManager) throws Exception
    {
        mockery = new Mockery();

        // Document Access Bridge Mock setup
        bridge = mockery.mock(DocumentAccessBridge.class);
        mockery.checking(new Expectations() {{
            allowing(bridge).hasProgrammingRights(); will(returnValue(true));
        }});

        DefaultComponentDescriptor<DocumentAccessBridge> descriptorDAB =
            new DefaultComponentDescriptor<DocumentAccessBridge>();
        descriptorDAB.setRole(DocumentAccessBridge.class);
        componentManager.registerComponent(descriptorDAB, bridge);

        // Use a mock for the AttachmentReference Factory
        attachmentReferenceFactory = mockery.mock(AttachmentReferenceFactory.class);
        DefaultComponentDescriptor<AttachmentReferenceFactory> descriptorARF =
            new DefaultComponentDescriptor<AttachmentReferenceFactory>();
        descriptorARF.setRole(AttachmentReferenceFactory.class);
        descriptorARF.setRoleHint("current");
        componentManager.registerComponent(descriptorARF, attachmentReferenceFactory);

        // Use a mock for the DocumentReference Factory
        documentReferenceFactory = mockery.mock(DocumentReferenceFactory.class);
        DefaultComponentDescriptor<DocumentReferenceFactory> descriptorDRF =
            new DefaultComponentDescriptor<DocumentReferenceFactory>();
        descriptorDRF.setRole(DocumentReferenceFactory.class);
        descriptorDRF.setRoleHint("current");
        componentManager.registerComponent(descriptorDRF, documentReferenceFactory);        
    }
}
