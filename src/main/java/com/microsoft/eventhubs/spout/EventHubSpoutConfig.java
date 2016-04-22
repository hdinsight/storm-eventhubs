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
package com.microsoft.eventhubs.spout;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import com.microsoft.eventhubs.client.ConnectionStringBuilder;

public class EventHubSpoutConfig implements Serializable {
  private static final long serialVersionUID = 1L; 

  public static final String EH_SERVICE_FQDN_SUFFIX = "servicebus.windows.net";
  private final String userName;
  private final String password;
  private final String namespace;
  private final String entityPath;
  private final int partitionCount;

  private String zkConnectionString = null; //if null then use zookeeper used by Storm
  private int checkpointIntervalInSeconds = 10;
  private int receiverCredits = 1024;
  private long enqueueTimeFilter = 0; //timestamp in millisecond, 0 means disabling filter
  private String connectionString;
  private String topologyName;
  private IEventDataScheme scheme = new StringEventDataScheme(); //Use StringEventDataScheme for backwards compatibility
  private String consumerGroupName = null; //if null then use default consumer group

  //These are mandatory parameters
  public EventHubSpoutConfig(String username, String password, String namespace,
      String entityPath, int partitionCount) {
    this.userName = username;
    this.password = password;
    this.connectionString = new ConnectionStringBuilder(username, password,
        namespace).getConnectionString();
    this.namespace = namespace;
    this.entityPath = entityPath;
    this.partitionCount = partitionCount;
  }

  //Keep this constructor for backward compatibility
  public EventHubSpoutConfig(String username, String password, String namespace,
      String entityPath, int partitionCount, String zkConnectionString) {
    this(username, password, namespace, entityPath, partitionCount);
    setZkConnectionString(zkConnectionString);
  }

  //Keep this constructor for backward compatibility
  public EventHubSpoutConfig(String username, String password, String namespace,
      String entityPath, int partitionCount, String zkConnectionString,
      int checkpointIntervalInSeconds, int receiverCredits) {
    this(username, password, namespace, entityPath, partitionCount,
        zkConnectionString);
    setCheckpointIntervalInSeconds(checkpointIntervalInSeconds);
    setReceiverCredits(receiverCredits);
  }

  //Keep this constructor for backward compatibility
  public EventHubSpoutConfig(String username, String password, String namespace,
    String entityPath, int partitionCount, String zkConnectionString,
    int checkpointIntervalInSeconds, int receiverCredits, long enqueueTimeFilter) {
    this(username, password, namespace, entityPath, partitionCount,
        zkConnectionString, checkpointIntervalInSeconds, receiverCredits);
    setEnqueueTimeFilter(enqueueTimeFilter);
  }

  public String getNamespace() {
    return this.namespace;
  }

  public String getEntityPath() {
    return entityPath;
  }

  public int getPartitionCount() {
    return this.partitionCount;
  }

  public String getZkConnectionString() {
    return this.zkConnectionString;
  }

  public void setZkConnectionString(String value) {
    this.zkConnectionString = value;
  }

  public EventHubSpoutConfig withZkConnectionString(String value) {
    this.setZkConnectionString(value);
    return this;
  }

  public int getCheckpointIntervalInSeconds() {
    return this.checkpointIntervalInSeconds;
  }

  public void setCheckpointIntervalInSeconds(int value) {
    this.checkpointIntervalInSeconds = value;
  }

  public EventHubSpoutConfig withCheckpointIntervalInSeconds(int value) {
    this.setCheckpointIntervalInSeconds(value);
    return this;
  }

  public int getReceiverCredits() {
    return this.receiverCredits;
  }

  public void setReceiverCredits(int value) {
    this.receiverCredits = value;
  }

  public EventHubSpoutConfig withReceiverCredits(int value) {
    this.setReceiverCredits(value);
    return this;
  }

  public long getEnqueueTimeFilter() {
    return this.enqueueTimeFilter;
  }

  public void setEnqueueTimeFilter(long value) {
    this.enqueueTimeFilter = value;
  }

  public EventHubSpoutConfig withEnqueueTimeFilter(long value) {
    this.setEnqueueTimeFilter(value);
    return this;
  }

  public String getTopologyName() {
    return this.topologyName;
  }

  public EventHubSpoutConfig setTopologyName(String value) {
    this.topologyName = value;
    return this;
  }

  public EventHubSpoutConfig withTopologyName(String value) {
    this.setTopologyName(value);
    return this;
  }

  public IEventDataScheme getEventDataScheme() {
    return this.scheme;
  }

  public void setEventDataScheme(IEventDataScheme value) {
    this.scheme = value;
  }

  public EventHubSpoutConfig withEventDataScheme(IEventDataScheme value) {
    this.setEventDataScheme(value);
    return this;
  }
  
  public String getConsumerGroupName() {
    return consumerGroupName;
  }

  public void setConsumerGroupName(String value) {
    this.consumerGroupName = value;
  }

  public EventHubSpoutConfig withConsumerGroupName(String value) {
    this.setConsumerGroupName(value);
    return this;
  }

  public List<String> getPartitionList() {
    List<String> partitionList = new ArrayList<String>();

    for (int i = 0; i < this.partitionCount; i++) {
      partitionList.add(Integer.toString(i));
    }

    return partitionList;
  }

  public String getConnectionString() {
    return this.connectionString;
  }

  public void setTargetAddress(String value) {
    this.connectionString = new ConnectionStringBuilder(userName, password,
      namespace, value).getConnectionString();
  }

  public EventHubSpoutConfig withTargetAddress(String value) {
    this.setTargetAddress(value);
    return this;
  }
}
