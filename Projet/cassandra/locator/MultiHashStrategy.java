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
package org.apache.cassandra.locator;

import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.nio.ByteBuffer;

import org.apache.cassandra.exceptions.ConfigurationException;
import org.apache.cassandra.dht.Token;
import org.apache.cassandra.dht.LongToken;
import org.apache.cassandra.utils.MurmurHash;
import org.apache.cassandra.utils.ByteBufferUtil;


/**
 * This class returns the nodes responsible for a given
 * key but does not respect rack awareness. Each index
 * replica owns a hash function to retrieve the node responsible
 * for.
 */
public class MultiHashStrategy extends AbstractReplicationStrategy
{
    public MultiHashStrategy(String keyspaceName, TokenMetadata tokenMetadata, IEndpointSnitch snitch, Map<String, String> configOptions)
    {
        super(keyspaceName, tokenMetadata, snitch, configOptions);
    }

    public List<InetAddress> calculateNaturalEndpoints(Token token, TokenMetadata metadata)
    {
        int replicas = getReplicationFactor();
        ArrayList<Token> tokens = metadata.sortedTokens();
        List<Token> tokenReplicas = new ArrayList<Token>(replicas);
        List<InetAddress> endpoints = new ArrayList<InetAddress>(replicas);

        if (tokens.isEmpty())
            return endpoints;

        // Create tokens for replicas
        tokenReplicas.add(token);
        for (int i = 0; i < replicas-1; i++)
        {
            tokenReplicas.add(getToken((Long)token.getTokenValue(), (long)i));
        }

        // Retrieve endpoints for each tokens created.
        // If a node is responsible of many replica, we add
        // it only once.
        for (Token t : tokenReplicas)
        {
            InetAddress ep = metadata.getEndpoint(metadata.firstToken(tokens, t));
            if (!endpoints.contains(ep))
                endpoints.add(ep);
        }

        return endpoints;
    }

    public int getReplicationFactor()
    {
        return Integer.parseInt(this.configOptions.get("replication_factor"));
    }

    public void validateOptions() throws ConfigurationException
    {
        String rf = configOptions.get("replication_factor");
        if (rf == null)
            throw new ConfigurationException("MultiHashStrategy requires a replication_factor strategy option.");
        validateReplicationFactor(rf);
    }

    public Collection<String> recognizedOptions()
    {
        return Collections.<String>singleton("replication_factor");
    }

    /**
     * Inspired by Murmur3Partitioner to create 
     * multi hash functions.
     * @see org.apache.cassandra.dht.Murmur3Partitioner
     */
    private LongToken getToken(Long longKey, long seed)
    {
        ByteBuffer key = ByteBufferUtil.bytes(longKey);
        if (key.remaining() == 0)
            return new LongToken(Long.MIN_VALUE);

        long[] hash = new long[2];
        MurmurHash.hash3_x64_128(key, key.position(), key.remaining(), seed, hash);
        return new LongToken(normalize(hash[0]));
    }

    private long normalize(long v)
    {
        // We exclude the MINIMUM value
        return v == Long.MIN_VALUE ? Long.MAX_VALUE : v;
    }
}
