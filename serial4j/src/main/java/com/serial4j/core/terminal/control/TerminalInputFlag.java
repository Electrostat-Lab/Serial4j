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

/**
 * Provides Unix [c_iflag] termios bits.
 *
 * @author pavl_g.
 */
public enum TerminalInputFlag implements Const {
    BRKINT(NativeTerminalFlags.InputFlags.getSignalInterrupt()),
    ICRNL(NativeTerminalFlags.InputFlags.getMapCarriageReturnToNewLine()),
    IGNBRK(NativeTerminalFlags.InputFlags.getIgnoreBreakCondition()),
    IGNCR(NativeTerminalFlags.InputFlags.getIgnoreCarriageReturn()),
    IMAXBEL(NativeTerminalFlags.InputFlags.getRingBell()),
    INLCR(NativeTerminalFlags.InputFlags.getMapNewLineToCarriageReturn()),
    INPCK(NativeTerminalFlags.InputFlags.getEnableParityChecking()),
    ISTRIP(NativeTerminalFlags.InputFlags.getStripHighBit()),
    IUTF8(NativeTerminalFlags.InputFlags.getInputIsUnicode8()),
    IUCLC(NativeTerminalFlags.InputFlags.getMapUppercaseToLowercase()),
    IXANY(NativeTerminalFlags.InputFlags.getAllowToRestartStoppedOutput()),
    IXOFF(NativeTerminalFlags.InputFlags.getEnableInputFlowControl()),
    IXON(NativeTerminalFlags.InputFlags.getEnableOutputFlowControl()),
    PARMRK(NativeTerminalFlags.InputFlags.getMarkParityErrors());

    private final int value;

    TerminalInputFlag(final int value) {
        this.value = value;
    }

    @Override
    public int getValue() {
        return value;
    }
}
