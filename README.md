|javadrivertest
==============

Datastax Cassandra Java driver test

This simple console app writes 1000 rows with CL.QUORUM and then tries to read same data back with CL.QUORUM in an infinite loop.

Howtorun:
  
$ccm create test -v 2.0.1  
$ccm populate -n 3  
$ccm start  
$ccm cqlsh  
cqlsh> CREATE KEYSPACE testks WITH replication = {'class': 'SimpleStrategy', 'replication_factor': 3};  
cqlsh> CREATE TABLE testks.table1 ( epoch bigint,sequence int, bogus text, PRIMARY KEY ((epoch, sequence), bogus));  

$mvn compile exec:java  
This will write 1000 rows to Cassandra and then try to read them back until you quit.

$sudo iptables -I INPUT -d 127.0.0.2 -j DROP; sudo iptables -I OUTPUT -s 127.0.0.2 -j DROP  
This simulates network cable disconnect. Here I'm getting couple of errors

$sudo iptables -D INPUT 1; sudo iptables -D OUTPUT 1  
Here we reconnect network cable, and should expect burst of exceptions until node is UP again.
