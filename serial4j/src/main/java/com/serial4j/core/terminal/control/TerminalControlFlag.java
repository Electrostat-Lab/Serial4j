/*
 * BSD 3-Clause License
 *
 * Copyright (c) 2022, Scrappers Team, The AVR-Sandbox Project, Serial4j API.
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
package com.serial4j.core.terminal.control;

public final class TerminalControlFlag extends TerminalFlag {

    public static final TerminalControlFlag EMPTY_INSTANCE = new TerminalControlFlag(0);
    public static final TerminalControlFlag CIBAUD =
            new TerminalControlFlag(NativeTerminalFlags.ControlFlags.getInputBaudRate());
    public static final TerminalControlFlag CLOCAL =
            new TerminalControlFlag(NativeTerminalFlags.ControlFlags.getIgnoreModemStatusLines());
    public static final TerminalControlFlag CREAD =
            new TerminalControlFlag(NativeTerminalFlags.ControlFlags.getAllowInput());
    public static final TerminalControlFlag CMSPAR =
            new TerminalControlFlag(NativeTerminalFlags.ControlFlags.getUseStickParity());
    public static final TerminalControlFlag CRTSCTS =
            new TerminalControlFlag(NativeTerminalFlags.ControlFlags.getEnableHardwareFlowControl());
    public static final TerminalControlFlag CSIZE =
            new TerminalControlFlag(NativeTerminalFlags.ControlFlags.getCharacterSizeMask());
    public static final TerminalControlFlag CSTOPB =
            new TerminalControlFlag(NativeTerminalFlags.ControlFlags.getUse2StopBitsPerCharacter());
    public static final TerminalControlFlag HUPCL =
            new TerminalControlFlag(NativeTerminalFlags.ControlFlags.getHangUpOnLastClose());
    public static final TerminalControlFlag PARENB =
            new TerminalControlFlag(NativeTerminalFlags.ControlFlags.getParityEnable());
    public static final TerminalControlFlag PARODD =
            new TerminalControlFlag(NativeTerminalFlags.ControlFlags.getUseOddParity());

    private TerminalControlFlag(final int value) {
        super(value);
    }

    public static final class MaskBits {
        public static final TerminalControlFlag B0 = new TerminalControlFlag(NativeTerminalFlags.ControlFlags.MaskBits.getBaud0());
        public static final TerminalControlFlag B2400 = new TerminalControlFlag(NativeTerminalFlags.ControlFlags.MaskBits.getBaud2400());
        public static final TerminalControlFlag B9600 = new TerminalControlFlag(NativeTerminalFlags.ControlFlags.MaskBits.getBaud9600());
        public static final TerminalControlFlag B38400 = new TerminalControlFlag(NativeTerminalFlags.ControlFlags.MaskBits.getBaud38400());
        public static final TerminalControlFlag CS5 = new TerminalControlFlag(NativeTerminalFlags.ControlFlags
                .MaskBits
                .getCharacterSize5bits());
        public static final TerminalControlFlag CS6 = new TerminalControlFlag(NativeTerminalFlags.ControlFlags
                .MaskBits
                .getCharacterSize6bits());
        public static final TerminalControlFlag CS7 = new TerminalControlFlag(NativeTerminalFlags.ControlFlags
                .MaskBits
                .getCharacterSize7bits());
        public static final TerminalControlFlag CS8 = new TerminalControlFlag(NativeTerminalFlags.ControlFlags
                .MaskBits
                .getCharacterSize8bits());

        private MaskBits() {
        }
    }
}
