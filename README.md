**:heavy_exclamation_mark:```IMPORTANT NOTE```:heavy_exclamation_mark:: This repository is a clone of [Apache Storm External Eventhubs](https://github.com/apache/storm/tree/master/external/storm-eventhubs) to make the latest version of the EventHubs spout compatible with Apache Storm 0.9.3 on Microsoft HDInsight Clusters with version 3.2.**

Users of Apache Storm 0.9.3 on Microsoft HDInsight Clusters with version 3.2 can take the benefits of any bug fixes landing in newer versions of storm-eventhubs spout through this project.

**If you are using Storm 0.10.x or later, please use the storm-eventhubs from the [Apache Storm GitHub Project](https://github.com/apache/storm).**

=====================
storm-eventhubs
=====================

Storm spout and bolt implementation for Microsoft Azure Eventhubs

### build ###
	mvn clean package

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

	eventhubspout.checkpoint.interval = 10
	eventhub.receiver.credits = 1024

Then you can use storm.cmd to submit the sample topology:
	storm jar {jarfile} com.microsoft.eventhubs.samples.EventCount {topologyname} {spoutconffile}
	where the {jarfile} should be: eventhubs-storm-spout-{version}-jar-with-dependencies.jar

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

## Developers
* Shanyu Zhao ([shzhao@microsoft.com](mailto: shzhao@microsoft.com))
* SeongJoon Kwak ([sjkwak@microsoft.com](mailto: sjkwak@microsoft.com))
* Ravi Tandon ([rtandon@microsoft.com](mailto: rtandon@microsoft.com))

## Committer Sponsors

 * P. Taylor Goetz ([ptgoetz@apache.org](mailto:ptgoetz@apache.org))

