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
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;


/**
 *
 * @author Jin Kwon &lt;jinahya_at_gmail.com&gt;
 */
public class Rfc863TcpOio2 {


    public static void main(final String[] args) throws IOException {

        try (ServerSocketChannel ssc = ServerSocketChannel.open()) {
            ssc.bind(new InetSocketAddress(Rfc863Constants.PORT));
            while (ssc.isOpen()) {
                final SocketChannel sc = ssc.accept();
                new Thread(() -> {
                    try {
                        try {
                            final ByteBuffer bb = ByteBuffer.allocate(1);
                            while (sc.isOpen()) {
                                sc.read(bb);
                                bb.clear();
                            }
                        } finally {
                            sc.close();
                        }
                    } catch (final IOException ioe) {
                    }
                }).start();
            }
        }
    }


}

