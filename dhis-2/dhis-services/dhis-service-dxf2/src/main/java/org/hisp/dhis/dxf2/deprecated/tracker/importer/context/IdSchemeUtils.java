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
package org.hisp.dhis.dxf2.deprecated.tracker.importer.context;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.hisp.dhis.common.IdScheme;
import org.hisp.dhis.common.IdentifiableProperty;

/**
 * @author Luciano Fiandesio
 */
public class IdSchemeUtils
{
    public static String getKey( IdScheme scheme, ResultSet rs )
        throws SQLException
    {
        Object id = rs.getObject( getColumnNameByScheme( scheme ) );

        if ( id instanceof Long )
        {
            return Long.toString( (Long) id );
        }
        else if ( id instanceof Integer )
        {
            return Integer.toString( (Integer) id );
        }
        else
        {
            return (String) id;
        }
    }

    public static String getColumnNameByScheme( IdScheme idScheme )
    {
        return getColumnNameByScheme( idScheme, null );
    }

    public static String getColumnNameByScheme( IdScheme idScheme, String primaryKeyColumn )
    {
        String id;
        if ( idScheme.is( IdentifiableProperty.ID ) )
        {
            id = primaryKeyColumn != null ? primaryKeyColumn : "id";
        }
        else if ( idScheme.is( IdentifiableProperty.UID ) )
        {
            id = "uid";
        }
        else if ( idScheme.is( IdentifiableProperty.CODE ) )
        {
            id = "code";
        }
        else if ( idScheme.is( IdentifiableProperty.NAME ) )
        {
            id = "name";
        }
        else
        {
            id = null;
        }
        return id;
    }
}
