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
package org.xwiki.officeimporter.internal.builder;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import junit.framework.Assert;

import org.jmock.Expectations;
import org.junit.Before;
import org.xwiki.component.util.ReflectionUtils;
import org.xwiki.officeimporter.builder.PresentationBuilder;
import org.xwiki.officeimporter.document.XDOMOfficeDocument;
import org.xwiki.officeimporter.internal.AbstractOfficeImporterTest;
import org.xwiki.officeimporter.openoffice.OpenOfficeConverter;
import org.xwiki.officeimporter.openoffice.OpenOfficeManager;

/**
 * Test case for {@link DefaultPresentationBuilder}.
 * 
 * @version $Id$
 * @since 2.1M1
 */
public class DefaultPresentationBuilderTest extends AbstractOfficeImporterTest
{
    /**
     * The {@link PresentationBuilder} component.
     */
    private PresentationBuilder presentationBuilder;
    
    /**
     * Used to setup a mock document converter.
     */
    private OpenOfficeManager officeManager;

    /**
     * {@inheritDoc}
     */
    @Before
    public void setUp() throws Exception
    {
        super.setUp();
        this.presentationBuilder = getComponentManager().lookup(PresentationBuilder.class);
        this.officeManager = getComponentManager().lookup(OpenOfficeManager.class);
    }
    
    /**
     * Test presentation {@link XDOMOfficeDocument} building.
     * 
     * @throws Exception
     */
    @org.junit.Test
    public void testPresentationBuilding() throws  Exception
    {
        // Create & register a mock document converter to by-pass openoffice server.
        final InputStream mockOfficeFileStream = new ByteArrayInputStream(new byte[1024]);
        final Map<String, InputStream> mockInput = new HashMap<String, InputStream>();
        mockInput.put("input.ppt", mockOfficeFileStream);
        final Map<String, byte[]> mockOutput = new HashMap<String, byte[]>();
        mockOutput.put("output.html", "<html><head><title></tile></head><body><p>Slide1</p></body></html>".getBytes());

        final OpenOfficeConverter mockDocumentConverter = this.mockery.mock(OpenOfficeConverter.class);
        this.mockery.checking(new Expectations() {{
                allowing(mockDocumentConverter).convert(mockInput, "input.ppt", "output.html");
                will(returnValue(mockOutput));
        }});
        ReflectionUtils.setFieldValue(officeManager, "converter", mockDocumentConverter);
        
        XDOMOfficeDocument presentation = presentationBuilder.build(mockOfficeFileStream, "input.ppt");
        Assert.assertNotNull(presentation.getContentDocument());
        Assert.assertEquals(1, presentation.getArtifacts().size());
        Assert.assertTrue(presentation.getArtifacts().containsKey("presentation.zip"));
    }
}
