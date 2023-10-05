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
package com.serial4j.core.serial.control;

import com.serial4j.core.serial.control.NativeTerminalFlags.OutputFlags;

public final class TerminalOutputFlag extends TerminalFlag {
    public static final TerminalOutputFlag EMPTY_INSTANCE = new TerminalOutputFlag(0);
    public static final TerminalOutputFlag BSDLY =
            new TerminalOutputFlag(OutputFlags.getBackspaceDelayMask());
    public static final TerminalOutputFlag CRDLY =
            new TerminalOutputFlag(OutputFlags.getCarriageReturnDelayMask());
    public static final TerminalOutputFlag FFDLY =
            new TerminalOutputFlag(OutputFlags.getFormFeedDelayMask());
    public static final TerminalOutputFlag NLDLY =
            new TerminalOutputFlag(OutputFlags.getNewLineDelayMask());
    public static final TerminalOutputFlag OCRNL =
            new TerminalOutputFlag(OutputFlags.getMapCarriageReturnToNewLine());
    public static final TerminalOutputFlag OFDEL =
            new TerminalOutputFlag(OutputFlags.getUsePredefinedFillCharacters());
    public static final TerminalOutputFlag OFILL =
            new TerminalOutputFlag(OutputFlags.getUseFillCharacters());
    public static final TerminalOutputFlag OLCUC =
            new TerminalOutputFlag(OutputFlags.getMapLowercaseToUppercase());
    public static final TerminalOutputFlag ONLCR =
            new TerminalOutputFlag(OutputFlags.getMapNewLineToCarriageReturn());
    public static final TerminalOutputFlag ONLRET =
            new TerminalOutputFlag(OutputFlags.getNewLineAsCarriageReturn());
    public static final TerminalOutputFlag ONOCR =
            new TerminalOutputFlag(OutputFlags.getNoCarriageReturnDuplicateOutput());
    public static final TerminalOutputFlag OPOST =
            new TerminalOutputFlag(OutputFlags.getPerformOutputPostProcessing());
    public static final TerminalOutputFlag TABDLY =
            new TerminalOutputFlag(OutputFlags.getHorizontalTabDelayMask());
    public static final TerminalOutputFlag VTDLY =
            new TerminalOutputFlag(OutputFlags.getVerticalTabDelayMask());

    private TerminalOutputFlag(final int value) {
        super(value);
    }

    public static final class MaskBits {
        public static final TerminalOutputFlag BS0 =
                new TerminalOutputFlag(OutputFlags.MaskBits.getBackspace0());
        public static final TerminalOutputFlag BS1 =
                new TerminalOutputFlag(OutputFlags.MaskBits.getBackspace1());
        public static final TerminalOutputFlag CR0 =
                new TerminalOutputFlag(OutputFlags.MaskBits.getCarriageReturn0());
        public static final TerminalOutputFlag CR1 =
                new TerminalOutputFlag(OutputFlags.MaskBits.getCarriageReturn1());
        public static final TerminalOutputFlag CR2 =
                new TerminalOutputFlag(OutputFlags.MaskBits.getCarriageReturn2());
        public static final TerminalOutputFlag CR3 =
                new TerminalOutputFlag(OutputFlags.MaskBits.getCarriageReturn3());
        public static final TerminalOutputFlag FF0 =
                new TerminalOutputFlag(OutputFlags.MaskBits.getFormFeed0());
        public static final TerminalOutputFlag FF1 =
                new TerminalOutputFlag(OutputFlags.MaskBits.getFormFeed1());
        public static final TerminalOutputFlag NL0 =
                new TerminalOutputFlag(OutputFlags.MaskBits.getNewLineDelay0());
        public static final TerminalOutputFlag NL1 =
                new TerminalOutputFlag(OutputFlags.MaskBits.getNewLineDelay1());
        public static final TerminalOutputFlag TAB0 =
                new TerminalOutputFlag(OutputFlags.MaskBits.getHorizontalTabDelay0());
        public static final TerminalOutputFlag TAB1 =
                new TerminalOutputFlag(OutputFlags.MaskBits.getHorizontalTabDelay1());
        public static final TerminalOutputFlag TAB2 =
                new TerminalOutputFlag(OutputFlags.MaskBits.getHorizontalTabDelay2());
        public static final TerminalOutputFlag TAB3 =
                new TerminalOutputFlag(OutputFlags.MaskBits.getHorizontalTabDelay3());
        public static final TerminalOutputFlag VT0 =
                new TerminalOutputFlag(OutputFlags.MaskBits.getVerticalTabDelay0());
        public static final TerminalOutputFlag VT1 =
                new TerminalOutputFlag(OutputFlags.MaskBits.getVerticalTabDelay1());

        private MaskBits() {
        }
    }
}
