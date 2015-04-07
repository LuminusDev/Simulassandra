/*
* Licensed to the Apache Software Foundation (ASF) under one
* or more contributor license agreements.  See the NOTICE file
* distributed with this work for additional information
* regarding copyright ownership.  The ASF licenses this file
* to you under the Apache License, Version 2.0 (the
* "License"); you may not use this file except in compliance
* with the License.  You may obtain a copy of the License at
*
*    http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing,
* software distributed under the License is distributed on an
* "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
* KIND, either express or implied.  See the License for the
* specific language governing permissions and limitations
* under the License.
*/

package org.apache.cassandra.locator;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.Assert;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.apache.cassandra.exceptions.ConfigurationException;
import org.apache.cassandra.config.DatabaseDescriptor;
import org.apache.cassandra.dht.StringToken;
import org.apache.cassandra.dht.LongToken;
import org.apache.cassandra.dht.Token;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;

public class MultiHashStrategyTest
{
    private String keyspaceName = "Keyspace1";

    @Test
    public void testProperties() throws IOException, ConfigurationException
    {
        IEndpointSnitch snitch = new SimpleSnitch();
        DatabaseDescriptor.setEndpointSnitch(snitch);
        TokenMetadata metadata = new TokenMetadata();
        createDummyTokens(metadata);

        Map<String, String> configOptions = new HashMap<String, String>();
        configOptions.put("replication_factor", "2");

        MultiHashStrategy strategy = new MultiHashStrategy(keyspaceName, metadata, snitch, configOptions);
        Assert.assertEquals(strategy.getReplicationFactor(), 2);

        // Query for the natural hosts
        List<InetAddress> endpoints = strategy.calculateNaturalEndpoints(new LongToken(4), metadata);
        Assert.assertEquals("Wrong endpoints size", 1, endpoints.size());
        Assert.assertTrue(endpoints.contains(InetAddress.getByAddress(new byte[]{ 10, 0, 0, 12 })));

        endpoints = strategy.calculateNaturalEndpoints(new LongToken(-5074457345618258603L), metadata);
        Assert.assertEquals("Wrong endpoints size", 2, endpoints.size());
        Assert.assertTrue(endpoints.contains(InetAddress.getByAddress(new byte[]{ 10, 0, 0, 11 })));
        Assert.assertTrue(endpoints.contains(InetAddress.getByAddress(new byte[]{ 10, 0, 0, 10 })));
    }

    public void createDummyTokens(TokenMetadata metadata) throws UnknownHostException
    {
        tokenFactory(metadata, -9223372036854775808L, new byte[]{ 10, 0, 0, 10 });
        tokenFactory(metadata, -3074457345618258603L, new byte[]{ 10, 0, 0, 11 });
        tokenFactory(metadata, 3074457345618258602L, new byte[]{ 10, 0, 0, 12 });
    }

    public void tokenFactory(TokenMetadata metadata, long token, byte[] bytes) throws UnknownHostException
    {
        Token token1 = new LongToken(token);
        InetAddress add1 = InetAddress.getByAddress(bytes);
        metadata.updateNormalToken(token1, add1);
    }
}
