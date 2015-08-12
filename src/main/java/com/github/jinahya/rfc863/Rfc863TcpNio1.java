/*
 * Copyright 2015 Jin Kwon &lt;jinahya_at_gmail.com&gt;.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */


package com.github.jinahya.rfc863;


import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.CancelledKeyException;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Set;


/**
 *
 * @author Jin Kwon &lt;jinahya_at_gmail.com&gt;
 */
public class Rfc863TcpNio1 {


    public static void main(final String[] args) throws IOException {

        final Selector s = Selector.open();

        try (ServerSocketChannel ssc = ServerSocketChannel.open()) {
            ssc.bind(new InetSocketAddress(Rfc863Constants.PORT));
            ssc.configureBlocking(false);
            final SelectionKey ssk = ssc.register(s, SelectionKey.OP_ACCEPT);

            final ByteBuffer bb = ByteBuffer.allocateDirect(1);

            while (ssk.isValid()) {
                s.select();
                final Set<SelectionKey> sks = s.selectedKeys();
                for (final SelectionKey sk : sks) {
                    try {
                        if (sk.isAcceptable()) {
                            final SocketChannel sc
                                = ((ServerSocketChannel) sk.channel()).accept();
                            sc.configureBlocking(false);
                            sc.register(s, SelectionKey.OP_READ);
                        }
                    } catch (final CancelledKeyException cke) {
                    }
                    try {
                        if (sk.isReadable()) {
                            ((ReadableByteChannel) sk.channel()).read(bb);
                            bb.clear();
                        }
                    } catch (final CancelledKeyException cke) {
                    }
                }
                sks.clear();
            }
        }
    }


}

