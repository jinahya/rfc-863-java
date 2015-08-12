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
import java.io.InputStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;


/**
 *
 * @author Jin Kwon &lt;jinahya_at_gmail.com&gt;
 */
public class Rfc863TcpOio1 {


    public static void main(final String[] args) throws IOException {

        try (ServerSocket ss = new ServerSocket()) {
            ss.bind(new InetSocketAddress(Rfc863Constants.PORT));
            while (!ss.isClosed()) {
                final Socket s = ss.accept();
                new Thread(() -> {
                    try {
                        try {
                            final InputStream is = s.getInputStream();
                            while (!s.isClosed()) {
                                final int r = is.read();
                            }
                        } finally {
                            s.close();
                        }
                    } catch (final IOException ioe) {
                    }
                }).start();
            }
        }
    }


}

