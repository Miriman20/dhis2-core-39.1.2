/*
 * Copyright (c) 2004-2022, University of Oslo
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 * Redistributions of source code must retain the above copyright notice, this
 * list of conditions and the following disclaimer.
 *
 * Redistributions in binary form must reproduce the above copyright notice,
 * this list of conditions and the following disclaimer in the documentation
 * and/or other materials provided with the distribution.
 * Neither the name of the HISP project nor the names of its contributors may
 * be used to endorse or promote products derived from this software without
 * specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR
 * ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
 * ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package org.hisp.dhis.webapi.controller.deprecated.tracker;

import static org.hisp.dhis.web.WebClient.ContentType;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.hisp.dhis.organisationunit.OrganisationUnit;
import org.hisp.dhis.program.Program;
import org.hisp.dhis.program.ProgramStage;
import org.hisp.dhis.web.HttpStatus;
import org.hisp.dhis.webapi.DhisControllerIntegrationTest;
import org.hisp.dhis.webapi.utils.ContextUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * @author Viet Nguyen
 */
class TEIControllerIntegrationTest extends DhisControllerIntegrationTest
{
    @BeforeEach
    public void setUp()
    {
        OrganisationUnit organisationUnit = createOrganisationUnit( "a" );
        organisationUnit.setUid( "ZiMBqH865GV" );
        manager.save( organisationUnit );
        Program program = createProgram( 'A' );
        program.getOrganisationUnits().add( organisationUnit );
        program.setUid( "q04UBOqq3rp" );
        manager.save( program );
        ProgramStage programStage = createProgramStage( 'A', program );
        programStage.setUid( "pSllsjpfLH2" );
        program.getProgramStages().add( programStage );
        manager.save( programStage );
    }

    @Test
    void testGetCsv()
    {
        HttpResponse res = GET(
            "/trackedEntityInstances.csv?format=csv&ou=ZiMBqH865GV&program=q04UBOqq3rp&programStage=pSllsjpfLH2&attachment=trackedEntityInstances.csv",
            ContentType( "text/csv" ) );
        assertEquals( HttpStatus.OK, res.status() );
        assertEquals( ContextUtils.CONTENT_TYPE_TEXT_CSV, res.header( "Content-Type" ) );
    }

    @Test
    void testGetXml()
    {
        HttpResponse res = GET(
            "/trackedEntityInstances.xml?format=xml&ou=ZiMBqH865GV&program=q04UBOqq3rp&programStage=pSllsjpfLH2&attachment=trackedEntityInstances.xml",
            ContentType( "application/xml" ) );
        assertEquals( HttpStatus.OK, res.status() );
        assertEquals( "application/xml;charset=UTF-8", res.header( "Content-Type" ) );
    }
}
