/*
 * MIT License
 *
 * Copyright (c) 2018 KubeMQ
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package io.kubemq.sdk.queuePolicy;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import java.io.IOException;

import org.junit.Test;

import io.kubemq.sdk.queue.Message;
import io.kubemq.sdk.basic.ServerAddressNotSuppliedException;
import io.kubemq.sdk.tools.Converter;

public class queueSetPolicyTest {

    @Test
    public void TestQueuePolicyExpiration5Sec() throws ServerAddressNotSuppliedException, IOException {
        // Queue queue = createQueue("rec");
        Message message = new Message(Converter.ToByteArray("hi"), "", "1", null).setExpiration(5);
        assertEquals(5, message.getMessagePolicy().getExpirationSeconds());
    }
    @Test
    public void TestQueuePolicyMaxReceiveCount3() throws ServerAddressNotSuppliedException, IOException {
        // Queue queue = createQueue("rec");
        Message message = new Message(Converter.ToByteArray("hi"), "", "1", null).setMaxReciveCount(3);
        assertEquals(3, message.getMessagePolicy().getMaxReceiveCount());
    }
    @Test
    public void TestQueuePolicyMaxReceiveQueueNoName() throws ServerAddressNotSuppliedException, IOException {
        // Queue queue = createQueue("rec");
        Message message = new Message(Converter.ToByteArray("hi"), "", "1", null).setMaxReciveQueue("NoName");
        assertEquals("NoName", message.getMessagePolicy().getMaxReceiveQueue());
    }
    @Test
    public void TestQueuePolicyDelay5() throws ServerAddressNotSuppliedException, IOException {
        // Queue queue = createQueue("rec");
        Message message = new Message(Converter.ToByteArray("hi"), "", "1", null).setDelay(5);
        assertEquals(5, message.getMessagePolicy().getDelaySeconds());
    }
    @Test
    public void TestQueuePolicyAll() throws ServerAddressNotSuppliedException, IOException {
        // Queue queue = createQueue("rec");
        Message message = new Message(Converter.ToByteArray("hi"), "", "1", null).setDelay(5);
        int DelaySeconds= message.getMessagePolicy().getDelaySeconds();
        assertEquals(5, DelaySeconds);
        message.setExpiration(3);
        int ExpirationSeconds  =message.getMessagePolicy().getExpirationSeconds();
        assertEquals(3, ExpirationSeconds);
        message.setMaxReciveCount(2);
        int MaxReceiveCount  =message.getMessagePolicy().getMaxReceiveCount();
        assertEquals(2, MaxReceiveCount);
        message.setMaxReciveQueue("NoName");
        String MaxReceiveQueue =  message.getMessagePolicy().getMaxReceiveQueue();
        assertEquals("NoName", MaxReceiveQueue);
    
        assertArrayEquals(new int[]{5,3,2},new int[]{DelaySeconds,ExpirationSeconds,MaxReceiveCount});
        
    }
}