=====================
CHANGELOG
=====================

##0.10.0##
1. Updated the namespaces to be compatible with Apache Storm External EventHubs. The spout now uses "org.apache.storm.eventhubs" instead of "com.microsoft.eventhubs"

##0.9.5##
1. Changed EventHubSpoutConfig to use fluent "with" methods in addition to "set"
2. Removed max pending messages per partition configuration from EventHubSpout to avoid confusion with topology.max.spout.pending. Users should use Storm config: TOPOLOGY_MAX_SPOUT_PENDING instead to control number of messages pending for each spout task.

##0.9.4##
1. Fixed an issue with EventHubBolt that did not allow it to recover from a disconnected state.
2. Integrated following pull requests:
  1. Binary data support - https://github.com/hdinsight/storm-eventhubs/pull/5
  2. Application Metadata Added To Tuples - https://github.com/hdinsight/storm-eventhubs/pull/4
