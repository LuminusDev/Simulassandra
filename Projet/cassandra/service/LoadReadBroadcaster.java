 /*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.cassandra.service;

import java.net.InetAddress;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.apache.cassandra.gms.*;
import org.apache.cassandra.concurrent.*;

public class LoadReadBroadcaster implements IEndpointStateChangeSubscriber
{
    public static final LoadReadBroadcaster instance = new LoadReadBroadcaster();

    private static final Logger logger = LoggerFactory.getLogger(LoadReadBroadcaster.class);

    private ConcurrentMap<InetAddress, Long> loadReadInfo = new ConcurrentHashMap<InetAddress, java.lang.Long>();

    private LoadReadBroadcaster()
    {
        Gossiper.instance.register(this);
    }

    public void onChange(InetAddress endpoint, ApplicationState state, VersionedValue value)
    {
        if (state != ApplicationState.READ_LOAD)
            return;
        loadReadInfo.put(endpoint, Long.valueOf(value.value));
    }

    public void onJoin(InetAddress endpoint, EndpointState epState)
    {
        VersionedValue localValue = epState.getApplicationState(ApplicationState.READ_LOAD);
        if (localValue != null)
        {
            onChange(endpoint, ApplicationState.READ_LOAD, localValue);
        }
    }
    
    public void beforeChange(InetAddress endpoint, EndpointState currentState, ApplicationState newStateKey, VersionedValue newValue) {}

    public void onAlive(InetAddress endpoint, EndpointState state) {}

    public void onDead(InetAddress endpoint, EndpointState state) {}

    public void onRestart(InetAddress endpoint, EndpointState state) {}

    public void onRemove(InetAddress endpoint)
    {
        loadReadInfo.remove(endpoint);
    }

    public Map<InetAddress, Long> getLoadInfo()
    {
        return Collections.unmodifiableMap(loadReadInfo);
    }

    public void broadcast()
    {
        VersionedValue readLoad = StorageService.instance.valueFactory.loadRead(((SEPExecutor)StageManager.getStage(Stage.READ)).getNonAffectedTasks());
        Gossiper.instance.addLocalApplicationState(ApplicationState.READ_LOAD, readLoad);
    }
}

