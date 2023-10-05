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

import com.serial4j.core.serial.control.NativeTerminalFlags.InputFlags;

/**
 * Provides Unix [c_iflag] termios bits.
 *
 * @author pavl_g.
 */
public final class TerminalInputFlag extends TerminalFlag {

    public static final TerminalInputFlag EMPTY_INSTANCE = new TerminalInputFlag(0);
    public static final TerminalInputFlag BRKINT =
            new TerminalInputFlag(InputFlags.getSignalInterrupt());
    public static final TerminalInputFlag ICRNL =
            new TerminalInputFlag(InputFlags.getMapCarriageReturnToNewLine());
    public static final TerminalInputFlag IGNBRK =
            new TerminalInputFlag(InputFlags.getIgnoreBreakCondition());
    public static final TerminalInputFlag IGNCR =
            new TerminalInputFlag(InputFlags.getIgnoreCarriageReturn());
    public static final TerminalInputFlag IMAXBEL =
            new TerminalInputFlag(InputFlags.getRingBell());
    public static final TerminalInputFlag INLCR =
            new TerminalInputFlag(InputFlags.getMapNewLineToCarriageReturn());
    public static final TerminalInputFlag INPCK =
            new TerminalInputFlag(InputFlags.getEnableParityChecking());
    public static final TerminalInputFlag ISTRIP =
            new TerminalInputFlag(InputFlags.getStripHighBit());
    public static final TerminalInputFlag IUTF8 =
            new TerminalInputFlag(InputFlags.getInputIsUnicode8());
    public static final TerminalInputFlag IUCLC =
            new TerminalInputFlag(InputFlags.getMapUppercaseToLowercase());
    public static final TerminalInputFlag IXANY =
            new TerminalInputFlag(InputFlags.getAllowToRestartStoppedOutput());
    public static final TerminalInputFlag IXOFF =
            new TerminalInputFlag(InputFlags.getEnableInputFlowControl());
    public static final TerminalInputFlag IXON =
            new TerminalInputFlag(InputFlags.getEnableOutputFlowControl());
    public static final TerminalInputFlag PARMRK =
            new TerminalInputFlag(InputFlags.getMarkParityErrors());

    private TerminalInputFlag(final int value) {
        super(value);
    }
}
