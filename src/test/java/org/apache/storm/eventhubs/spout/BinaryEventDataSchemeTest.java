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

import org.apache.qpid.amqp_1_0.client.Message;
import org.apache.qpid.amqp_1_0.type.Binary;
import org.apache.qpid.amqp_1_0.type.Section;
import org.apache.qpid.amqp_1_0.type.messaging.ApplicationProperties;
import org.apache.qpid.amqp_1_0.type.messaging.Data;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class BinaryEventDataSchemeTest {

    private static final String TEST_KEY = "TEST_KEY";
    private static final String TEST_VALUE = "TEST_VALUE";
    private static final String TEST_MESSAGE_DATA = "Hello world";
    private BinaryEventDataScheme scheme;

    @Before
    public void setUp() throws Exception {
        scheme = new BinaryEventDataScheme();
    }

    @Test
    public void testDeserialize() {

        final Message message = buildMessage();

        final List<Object> tuple = scheme.deserialize(message);

        assertEquals("Tuple field count does not match declared field count", scheme.getOutputFields().size(), tuple.size());

        final byte[] dataResult = (byte[]) tuple.get(0);
        assertEquals(TEST_MESSAGE_DATA, new String(dataResult));

        final Map metaDataResult = (Map) tuple.get(1);
        assertTrue(metaDataResult.containsKey(TEST_KEY));
        assertEquals(TEST_VALUE, metaDataResult.get(TEST_KEY));
    }

    @Test
    public void testDeserializeDefaults() {
        final List<Object> tuple = scheme.deserialize(new Message());

        assertEquals("Tuple field count does not match declared field count", scheme.getOutputFields().size(), tuple.size());

        final byte[] dataResult = (byte[]) tuple.get(0);
        assertEquals("", new String(dataResult));

        final Map metaDataResult = (Map) tuple.get(1);
        assertTrue(metaDataResult.isEmpty());
    }

    private Message buildMessage() {
        final Data data = new Data(new Binary(TEST_MESSAGE_DATA.getBytes()));
        final Map<String, String> metaDataMap = new HashMap<String, String>();
        metaDataMap.put(TEST_KEY, TEST_VALUE);
        final ApplicationProperties metaData = new ApplicationProperties(metaDataMap);

        List<Section> sections = new ArrayList<Section>();
        sections.add(data);
        sections.add(metaData);

        return new Message(sections);
    }
}
