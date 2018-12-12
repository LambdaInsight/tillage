#!/usr/bin/env bash
java \
-Xmx512m \
-server \
-XX:+UseG1GC \
-XX:+UseNUMA \
-XX:MaxGCPauseMillis=10 \
-XX:+UseTLAB \
-XX:+UseStringDeduplication \
-XX:+HeapDumpOnOutOfMemoryError \
-XX:HeapDumpPath=heapdump.hprof \
-XX:StartFlightRecording=disk=true,dumponexit=true,filename=jfr.jfr,\
maxsize=1024m,maxage=1d,settings=profile,path-to-gc-roots=true \
-jar target/harrow-0.1.0-standalone.jar
