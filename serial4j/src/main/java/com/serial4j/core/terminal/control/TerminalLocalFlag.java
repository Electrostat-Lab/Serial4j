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

public final class TerminalLocalFlag extends TerminalFlag {

    public static final TerminalLocalFlag EMPTY_INSTANCE = new TerminalLocalFlag(0);
    public static final TerminalLocalFlag ECHO =
            new TerminalLocalFlag(NativeTerminalFlags.LocalFlags.getEchoInputCharacters());
    public static final TerminalLocalFlag ECHOCTL =
            new TerminalLocalFlag(NativeTerminalFlags.LocalFlags.getEchoControlCharacters());
    public static final TerminalLocalFlag ECHOE =
            new TerminalLocalFlag(NativeTerminalFlags.LocalFlags.getEchoErase());
    public static final TerminalLocalFlag ECHOK =
            new TerminalLocalFlag(NativeTerminalFlags.LocalFlags.getEchoKill());
    public static final TerminalLocalFlag ECHOKE =
            new TerminalLocalFlag(NativeTerminalFlags.LocalFlags.getDisableNewLineAfterEchoKill());
    public static final TerminalLocalFlag ECHONL =
            new TerminalLocalFlag(NativeTerminalFlags.LocalFlags.getEchoNewLine());
    public static final TerminalLocalFlag ECHOPRT =
            new TerminalLocalFlag(NativeTerminalFlags.LocalFlags.getEchoDeletedCharactersBackward());
    public static final TerminalLocalFlag FLUSHO =
            new TerminalLocalFlag(NativeTerminalFlags.LocalFlags.getOutputBeingFlushed());
    public static final TerminalLocalFlag ICANON =
            new TerminalLocalFlag(NativeTerminalFlags.LocalFlags.getCanonicalModeInput());
    public static final TerminalLocalFlag IEXTEN =
            new TerminalLocalFlag(NativeTerminalFlags.LocalFlags.getEnableExtendedProcessingOfInput());
    public static final TerminalLocalFlag ISIG =
            new TerminalLocalFlag(NativeTerminalFlags.LocalFlags.getEnableSignalGeneratingCharacters());
    public static final TerminalLocalFlag NOFLSH =
            new TerminalLocalFlag(NativeTerminalFlags.LocalFlags.getDisableFlushing());
    public static final TerminalLocalFlag PENDIN =
            new TerminalLocalFlag(NativeTerminalFlags.LocalFlags.getRedisplayPendingInput());
    public static final TerminalLocalFlag TOSTOP =
            new TerminalLocalFlag(NativeTerminalFlags.LocalFlags.getGenerateTerminateSignalForBackgroundProcess());
    public static final TerminalLocalFlag XCASE =
            new TerminalLocalFlag(NativeTerminalFlags.LocalFlags.getCanonicalPresentation());

    private TerminalLocalFlag(final int value) {
        super(value);
    }
}
