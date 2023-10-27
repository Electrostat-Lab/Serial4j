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

package com.serial4j.core.hid.shiftavr;

import com.serial4j.core.hid.SerialInterfaceDevice;
import com.serial4j.core.serial.SerialPort;
import com.serial4j.core.terminal.TerminalDevice;

/**
 * A shift-avr joystick serial interface device providing a data
 * descriptor decoder that decodes raw reports into {X} and {Y} values
 * based on the {@link JoystickDescriptor}.
 *
 * @author pavl_g
 */
public class JoystickDevice extends SerialInterfaceDevice<JoystickDescriptor> {

    /**
     * The input buffer for the reading stream.
     */
    protected final StringBuffer buffer = new StringBuffer();

    /**
     * Instantiates a serial shift-avr device that is capable of
     * reading descriptor reports composed of max-line "[x = 1023, y = 1023]\n\r".
     *
     * @param terminalDevice the associated terminal device
     * @param serialPort     the serial port of the connected device
     */
    public JoystickDevice(TerminalDevice terminalDevice, SerialPort serialPort) {
        super(terminalDevice, serialPort);
    }

    @Override
    public void init() {
        super.init();
        decoder = new JoystickDescriptor.Decoder();
        setDecoder(decoder);
    }

    @Override
    public void decode() {
        // reads a frame terminated by LF-CR, Line feed - Carriage return ("\n\r")
        // then finishes by dispatching the decoder implementation
        // after which the decoding listeners come into play
        // terminating the loop thereafter.
        // Note: as a part of loop control:
        // the loop self-terminates with an exception if the
        // reading exceeds 1024 (10 << 2 or 2^10) bytes without dispatching the decoder
        for (int chars = 0; getTerminalDevice().iread(1) > 0; chars++) {
            buffer.append(getTerminalDevice().getBuffer());
            if (buffer.toString().endsWith("\n\r")) {
                final JoystickDescriptor descriptor =
                        decoder.decode(buffer.toString());
                buffer.delete(0, buffer.length());  // flush input buffer
                if (decoderListener != null) {
                    decoderListener.onDecodingCompleted(descriptor);
                }
                return;
            } else if (chars > (0x10 << 0x02)) {
                throw new NotJoystickDeviceException();
            }
        }
    }
}