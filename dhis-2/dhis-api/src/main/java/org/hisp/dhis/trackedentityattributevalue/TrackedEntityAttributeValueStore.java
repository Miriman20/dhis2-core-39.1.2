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
package org.hisp.dhis.trackedentityattributevalue;

import java.util.Collection;
import java.util.List;

import org.hisp.dhis.common.GenericStore;
import org.hisp.dhis.program.Program;
import org.hisp.dhis.trackedentity.TrackedEntity;
import org.hisp.dhis.trackedentity.TrackedEntityAttribute;

/**
 * @author Abyot Asalefew
 */
public interface TrackedEntityAttributeValueStore
    extends GenericStore<TrackedEntityAttributeValue>
{
    String ID = TrackedEntityAttributeValueStore.class.getName();

    /**
     * Adds a {@link TrackedEntityAttribute}
     *
     * @param attributeValue The to TrackedEntityAttribute add.
     */
    void saveVoid( TrackedEntityAttributeValue attributeValue );

    /**
     * Deletes all {@link TrackedEntityAttributeValue} of a tracked entity.
     *
     * @param instance {@link TrackedEntity}
     * @return The error code. If the code is 0, deleting success
     */
    int deleteByTrackedEntity( TrackedEntity instance );

    /**
     * Retrieve a {@link TrackedEntityAttributeValue} on a {@link TrackedEntity}
     * and {@link TrackedEntityAttribute}
     *
     * @param instance the {@link TrackedEntity}
     * @param attribute the {@link TrackedEntityAttribute}
     * @return TrackedEntityAttributeValue
     */
    TrackedEntityAttributeValue get( TrackedEntity instance, TrackedEntityAttribute attribute );

    /**
     * Retrieve {@link TrackedEntityAttributeValue} of a {@link TrackedEntity}
     *
     * @param instance TrackedEntity
     * @return TrackedEntityAttributeValue list
     */
    List<TrackedEntityAttributeValue> get( TrackedEntity instance );

    /**
     * Retrieve {@link TrackedEntityAttributeValue} of a {@link TrackedEntity}
     *
     * @param attribute the {@link TrackedEntityAttribute}
     * @return TrackedEntityAttributeValue list
     */
    List<TrackedEntityAttributeValue> get( TrackedEntityAttribute attribute );

    /**
     * Gets a list of {@link TrackedEntityAttributeValue} that matches the
     * parameters
     *
     * @param attribute {@link TrackedEntityAttribute} to get value for
     * @param values List of literal values
     * @return list of {@link TrackedEntityAttributeValue}
     */
    List<TrackedEntityAttributeValue> get( TrackedEntityAttribute attribute, Collection<String> values );

    /**
     * Gets a list of {@link TrackedEntityAttributeValue} that matches the
     * parameters
     *
     * @param attribute {@link TrackedEntityAttribute} to get value for
     * @param value literal value to find within the specified
     *        {@link TrackedEntityAttribute}
     * @return list of {@link TrackedEntityAttributeValue}
     */
    List<TrackedEntityAttributeValue> get( TrackedEntityAttribute attribute, String value );

    /**
     * Retrieve attribute values of an instance by a program.
     *
     * @param instance the TrackedEntity
     * @param program the Program.
     * @return TrackedEntityAttributeValue list
     */
    List<TrackedEntityAttributeValue> get( TrackedEntity instance, Program program );

    /**
     * Return the number of assigned {@link TrackedEntityAttributeValue}s to the
     * {@link TrackedEntityAttribute}
     *
     * @param attribute {@link TrackedEntityAttribute}
     * @return Number of assigned TrackedEntityAttributeValues
     */
    int getCountOfAssignedTEAValues( TrackedEntityAttribute attribute );
}
