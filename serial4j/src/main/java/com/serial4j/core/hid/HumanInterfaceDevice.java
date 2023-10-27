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

import java.util.function.Function;

/**
 * A Human Interface Device is an abstract interactive interface that
 * defines standards of sending and receiving data packets between
 * devices wherein a report descriptor {@link ReportDescriptor}
 * represents the raw data packets in which data is organized generally
 * as [address = data] or [address, data], a {@link ReportDescriptor.Decoder}
 * is capable of decoding/encoding data packets after which attached listeners could
 * be dispatched to receive those post-processed packets.
 *
 * <p>
 * This simple API is based on the linux hid, for more use:
 * <a href="https://docs.kernel.org/hid">Linux-HID</a>
 * </p>
 *
 * @param <T> a type-arg for the decoded (concrete) post-processed data
 * @author pavl_g
 * @see com.serial4j.core.hid.shiftavr.JoystickDevice Demo Joystick Serial-HID
 */
public abstract class HumanInterfaceDevice<T> {

    /**
     * An associated report descriptor that provides descriptive
     * data for IO packets.
     */
    protected ReportDescriptor<T> reportDescriptor;

    /**
     * An associated listener to be dispatched as a part of the
     * post-processing encoding/decoding operations performed by
     * the associated decoder {@link HumanInterfaceDevice#decoder},
     * typically utilized as a user-code.
     */
    protected ReportDescriptor.DecoderListener<T> decoderListener;

    /**
     * An associated decoder that provides the interface for the
     * liberty of assigning decoding/encoding algorithms that would
     * run as a part of post raw IO operations, and before the dispatch
     * of the {@link HumanInterfaceDevice#decoderListener}, typically
     * utilized as a part of the defined HID standards (API code).
     */
    protected ReportDescriptor.Decoder<T> decoder;

    /**
     * Not directly instantiable class, inheritance
     * is required to provide full-functionality.
     */
    protected HumanInterfaceDevice() {
    }

    /**
     * Retrieves the reference to the report descriptor.
     *
     * @return the reference to the device-associated report descriptor
     */
    public ReportDescriptor<T> getReportDescriptor() {
        return reportDescriptor;
    }

    /**
     * Sets the report descriptor object, the report descriptor is the data packet
     * for the I/O operations.
     *
     * @param reportDescriptor a report descriptor object
     */
    public void setReportDescriptor(ReportDescriptor<T> reportDescriptor) {
        this.reportDescriptor = reportDescriptor;
    }

    /**
     * Retrieves the decoder listeners, listeners are getting dispatched
     * as a part of the decoder post-processing operations, typically
     * utilized in user-code.
     *
     * @return a reference to the decoder listener component
     */
    public ReportDescriptor.DecoderListener<T> getDecoderListener() {
        return decoderListener;
    }

    /**
     * Sets the decoder listeners, listeners are getting dispatched
     * as a part of the decoder post-processing operations, typically
     * utilized in user-code.
     *
     * @param decoderListener a reference to the decoder listener
     */
    public void setDecoderListener(ReportDescriptor.DecoderListener<T> decoderListener) {
        this.decoderListener = decoderListener;
    }

    /**
     * Retrieves the report decoder object associated with
     * this device, the decoder job is decode/encode data
     * packets (reports) after/before being received/sent,
     * respectively.
     *
     * @return a reference to the report descriptor decoder instance
     */
    public ReportDescriptor.Decoder<T> getDecoder() {
        return decoder;
    }

    /**
     * Sets the report descriptor decoder instance.
     *
     * @param decoder the new decoder instance used for encoding/decoding data
     */
    protected void setDecoder(ReportDescriptor.Decoder<T> decoder) {
        this.decoder = decoder;
    }

    /**
     * Override to provide initialization code for this device,
     *
     * <p>
     * Initialization code could include: opening the device, sending
     * feature reports, initializing line-speeds, and more.
     * </p>
     */
    protected abstract void init();

    /**
     * Nullifies references for GC operations.
     * <p>
     * Override to provide stream/IO/memory release operations.
     * </p>
     */
    protected void terminate() {
        reportDescriptor = null;
        decoderListener = null;
    }

    /**
     * Override to provide a decoding algorithm,
     * usually a call to {@link HumanInterfaceDevice#decode(Function, String)} is made
     * with different parameter sets according to the device standards.
     */
    protected abstract void decode();

    /**
     * Override to provide an encoding algorithm,
     * usually a call to {@link HumanInterfaceDevice#encode(Function, Object)} is made
     * with different parameter sets according to the device standards.
     */
    protected abstract void encode();

    /**
     * Defines a default algorithm for executing decoding post-read operations.
     *
     * <p>
     * A call to this function dispatches the function
     * {@link com.serial4j.core.hid.ReportDescriptor.DecoderListener#onDecodingCompleted(Object)}
     * as a part of post-processing user operations.
     * </p>
     *
     * @param read a reference to the device read() function to dispatch
     * @param raw  the raw data as an output of the reading operation to
     *             be processed by the defined decoder
     */
    protected final void decode(final Function<Integer, Long> read, final String raw) {
        reportDescriptor.setEncodedData(raw);
        read.apply(reportDescriptor.getEncodedData().length());
        final T concreteData = decoder.decode(raw);
        if (decoderListener != null) {
            decoderListener.onDecodingCompleted(concreteData);
        }
    }

    /**
     * Defines a default algorithm for encoding data packets before
     * being sent.
     *
     * <p>
     * A call to this function dispatches the function
     * {@link com.serial4j.core.hid.ReportDescriptor.DecoderListener#onEncodingCompleted(String)}
     * as a part of post-processing user operations.
     * </p>
     *
     * @param write    a reference to the device write() function to dispatch
     * @param concrete a decoded (concrete) data packet to be encoded before
     *                 being sent to the device
     */
    protected final void encode(final Function<String, Long> write, final T concrete) {
        reportDescriptor.setDecodedData(concrete);
        final String raw = decoder.encode(concrete);
        write.apply(raw);
        if (decoderListener != null) {
            decoderListener.onEncodingCompleted(raw);
        }
    }
}