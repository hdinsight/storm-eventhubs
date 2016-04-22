**:heavy_exclamation_mark:```IMPORTANT NOTE```:heavy_exclamation_mark:: This repository is a clone of [Apache Storm External Eventhubs](https://github.com/apache/storm/tree/master/external/storm-eventhubs) to make the latest version of the EventHubs spout compatible with Apache Storm 0.10.x on Microsoft HDInsight Clusters with version 3.3 or above.**

This code will be finally merged into [Apache Storm External Eventhubs](https://github.com/apache/storm/tree/master/external/storm-eventhubs).

Refer to the (CHANGELOG)[CHANGELOG.md] for the list of changes.

**If you are using Storm 0.9.x, please use the 0.9.x branch in this same repo.**

=====================
storm-eventhubs
=====================

Storm spout and bolt implementation for Microsoft Azure Eventhubs

### build ###
    mvn clean package

### build status ###

[![Build status](https://ci.appveyor.com/api/projects/status/742hmpexjqg537dp?svg=true)](https://ci.appveyor.com/project/rtandonmsft/storm-eventhubs)

### run sample topology ###
To run the sample topology, you need to modify the config.properties file with
the eventhubs configurations. Here is an example:

    eventhubspout.username = [username: policy name in EventHubs Portal]
    eventhubspout.password = [password: shared access key in EventHubs Portal]
    eventhubspout.namespace = [namespace]
    eventhubspout.entitypath = [entitypath]
    eventhubspout.partitions.count = [partitioncount]

    # if not provided, will use storm's zookeeper settings
    # zookeeper.connectionstring=zookeeper0:2181,zookeeper1:2181,zookeeper2:2181

    # optional - defaults to 10
    eventhubspout.checkpoint.interval = 10
    # optional - defaults to 1024
    eventhub.receiver.credits = 1024

Then you can use storm.cmd to submit the sample topology:

    storm jar {jarfile} org.apache.storm.eventhubs.samples.EventCount {topologyname} {spoutconffile}
    where the {jarfile} should be: storm-eventhubs-{version}-jar-with-dependencies.jar

### Run EventHubSendClient ###
We have included a simple EventHubs send client for testing purpose. You can run the client like this:

    java -cp .\target\eventhubs-storm-spout-{version}-jar-with-dependencies.jar com.microsoft.eventhubs.client.EventHubSendClient
    [username] [password] [entityPath] [partitionId] [messageSize] [messageCount]

If you want to send messages to all partitions, use "-1" as partitionId.

### Windows Azure Eventhubs ###
* http://azure.microsoft.com/en-us/services/event-hubs/
    
### Suggested reads
* http://blogs.msdn.com/b/shanyu/archive/2015/05/14/performance-tuning-for-hdinsight-storm-and-microsoft-azure-eventhubs.aspx

### Related projects
* https://github.com/hdinsight/eventhubs-client
* https://github.com/hdinsight/hdinsight-storm-examples
