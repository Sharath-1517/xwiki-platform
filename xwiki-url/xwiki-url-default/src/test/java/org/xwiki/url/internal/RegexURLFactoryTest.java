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
 *
 */
package org.xwiki.url.internal;

import org.xwiki.test.AbstractXWikiComponentTestCase;
import org.xwiki.url.XWikiDocumentURL;
import org.xwiki.url.XWikiURLFactory;

/**
 * Unit tests for {@link RegexXWikiURLFactory}.
 * 
 * @version $Id$
 * @since 1.6M1
 */
public class RegexURLFactoryTest extends AbstractXWikiComponentTestCase
{
    private XWikiURLFactory<String> factory;

    @Override
    protected void setUp() throws Exception
    {
        this.factory = (XWikiURLFactory<String>) getComponentManager().lookup(XWikiURLFactory.class);
    }

    public void testCreateURL() throws Exception
    {
        XWikiDocumentURL url = (XWikiDocumentURL) this.factory.createURL(
            "http://wiki.domain.com:8080/xwiki/bin/view/Main/WebHome?language=fr");
        assertEquals("view", url.getAction());
        assertEquals("WebHome", url.getDocumentReference().getName());
        assertEquals("Main", url.getDocumentReference().getLastSpaceReference().getName());
        assertEquals("wiki", url.getDocumentReference().getWikiReference().getName());
        assertEquals("fr", url.getParameterValue("language"));
    }

    public void testCreateURLFromPath() throws Exception
    {
        XWikiDocumentURL url = (XWikiDocumentURL) this.factory.createURL("/xwiki/bin/view/Main/WebHome");
        assertEquals("view", url.getAction());
        assertEquals("WebHome", url.getDocumentReference().getName());
        assertEquals("Main", url.getDocumentReference().getLastSpaceReference().getName());
    }
}
