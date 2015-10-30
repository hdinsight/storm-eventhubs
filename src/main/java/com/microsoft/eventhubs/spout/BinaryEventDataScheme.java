package com.microsoft.eventhubs.spout;

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
