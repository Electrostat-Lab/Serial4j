/*
 * BSD 3-Clause License
 *
 * Copyright (c) 2022, Scrappers Team, The AVR-Sandbox Project
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * 1. Redistributions of source code must retain the above copyright notice, this
 * list of conditions and the following disclaimer.
 *
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 * this list of conditions and the following disclaimer in the documentation
 * and/or other materials provided with the distribution.
 *
 * 3. Neither the name of the copyright holder nor the names of its
 * contributors may be used to endorse or promote products derived from
 * this software without specific prior written permission.

 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package com.serial4j.core.hid;

/**
 * Defines a device standard data report for the device driver.
 *
 * <p>
 * A device report descriptor defines the order of the send/receive
 * packets, the decoder defines a standard to decode/encode data packets.
 * </p>
 *
 * @param <T> the type of the decoded data packets
 * @author pavl_g
 */
public class ReportDescriptor<T> {

    /**
     * The encoded data packet.
     */
    protected String encodedData;

    /**
     * The decoded data packet.
     */
    protected T decodedData;

    /**
     * Instantiates a simple report descriptor composed of 1 byte
     * of data.
     * <p>
     * This method could be used in aggregating data frames and delimiting
     * them using the line feed and carriage return control bytes.
     * </p>
     */
    public ReportDescriptor() {
        this(1);
    }

    /**
     * Instantiates a new report descriptor with a length.
     *
     * @param length the length of the report descriptor data packet
     */
    public ReportDescriptor(final int length) {
        encodedData = new String(new char[length]);
    }

    /**
     * Retrieves the reference to the encoded data packet.
     *
     * @return the encoded data packet in character literals
     */
    public String getEncodedData() {
        return encodedData;
    }

    /**
     * Sets a reference to the encoded data packet for future-use.
     *
     * @param data the encoded data packet in character literals
     */
    public void setEncodedData(String data) {
        this.encodedData = data;
    }

    /**
     * Retrieves the reference to the decoded version of the data packet.
     *
     * @return a reference to the decoded version of the data packet
     */
    public T getDecodedData() {
        return decodedData;
    }

    /**
     * Sets a reference to decoded version of the data packet.
     *
     * @param decodedData the decoded version of the data packet
     */
    public void setDecodedData(T decodedData) {
        this.decodedData = decodedData;
    }

    /**
     * Defines a standard to decode/encode data
     * packets for this report descriptor.
     *
     * @param <T> the type of the decoded data packets
     */
    public interface Decoder<T> {

        /**
         * Encodes concrete data packets before sending
         * to the device.
         *
         * @param data the data data packet to encode.
         * @return an encoded version of the data packet ready to send
         * to the I/O pipeline
         */
        String encode(T data);

        /**
         * Decodes received raw data packets into a concrete version.
         *
         * @param raw the raw received data packets
         * @return a decoded version of the received data packets
         */
        T decode(String raw);
    }

    /**
     * Defines a dispatch listener that is dispatched
     * when decoding/encoding completes, usually this
     * is a user-side API.
     *
     * @param <T> the type of the decoded data packets
     */
    public interface DecoderListener<T> {

        /**
         * Dispatched when encoding a data packet completes
         * before sending it to the device.
         *
         * @param raw the raw data packet
         */
        void onEncodingCompleted(String raw);

        /**
         * Dispatched when decoding a received data packet completes.
         *
         * @param data the decoded data data packet
         */
        void onDecodingCompleted(T data);
    }
}