/*******************************************************************************
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *******************************************************************************/
package org.apache.storm.eventhubs.spout;

import backtype.storm.tuple.Fields;
import org.apache.qpid.amqp_1_0.client.Message;
import org.apache.qpid.amqp_1_0.type.Section;
import org.apache.qpid.amqp_1_0.type.messaging.ApplicationProperties;
import org.apache.qpid.amqp_1_0.type.messaging.Data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * An Event Data Scheme which deserializes the message payloads into the raw bytes.
 *
 * Contrast this to the default EventDataScheme which deserializes into String.  The assumption that these payloads
 * are Strings isn't always true and so this implementation allows for a more robust use case.
 */
public class BinaryEventDataScheme implements IEventDataScheme {

    @Override
    public List<Object> deserialize(Message message) {
        final List<Object> fieldContents = new ArrayList<Object>();

        /**
         * Default values.
         */
        Map metaDataMap = new HashMap();
        byte[] messageData = new byte[0];

        for (Section section : message.getPayload()) {
            if (section instanceof Data) {
                Data data = (Data) section;
                messageData = data.getValue().getArray();
            } else if(section instanceof ApplicationProperties) {
                final ApplicationProperties applicationProperties = (ApplicationProperties) section;
                metaDataMap = applicationProperties.getValue();
            }
        }

        fieldContents.add(messageData);
        fieldContents.add(metaDataMap);
        return fieldContents;
    }

    @Override
    public Fields getOutputFields() {
        return new Fields(FieldConstants.Message, FieldConstants.META_DATA);
    }
}
