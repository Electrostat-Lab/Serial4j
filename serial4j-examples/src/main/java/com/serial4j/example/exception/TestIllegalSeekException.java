/*
 * BSD 3-Clause License
 *
 * Copyright (c) 2022, Scrappers Team, The Arithmos Project.
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
 * OF THIS
 */

package com.serial4j.example.exception;

import com.serial4j.core.serial.SerialPort;
import com.serial4j.core.serial.throwable.IllegalSeekException;
import com.serial4j.core.terminal.NativeTerminalDevice;
import com.serial4j.core.terminal.Permissions;
import com.serial4j.core.terminal.TerminalDevice;
import java.io.FileNotFoundException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Isolates and examines the {@link IllegalSeekException} as
 * a result of applying {@link TerminalDevice#seek(long, NativeTerminalDevice.FileSeekCriterion)}
 * on a real terminal device.
 *
 * @author pavl_g
 */
public final class TestIllegalSeekException {
    public static void main(String[] args) throws FileNotFoundException {
        final TerminalDevice ttyDevice = new TerminalDevice();
        final Permissions permissions = Permissions.createEmptyPermissions().append(
                Permissions.Const.O_CREATE,
                Permissions.Const.O_RDWR,
                Permissions.Const.O_NOCTTY
        );
        ttyDevice.setPermissions(permissions);
        ttyDevice.openPort(new SerialPort(args[0]));
        ttyDevice.initTerminal();
        if (ttyDevice.sread(255) > 0) {
            System.out.println("Read Data = " + ttyDevice.getReadBuffer());
            try {
                /* try to seek back to start position on a terminal device */
                ttyDevice.seek(0, NativeTerminalDevice.FileSeekCriterion.SEEK_SET);
            } catch (IllegalSeekException e) {
                Logger.getLogger(TestIllegalSeekException.class.getName())
                        .log(Level.SEVERE, e.getMessage() + " " + ttyDevice.getSerialPort().getPath(), e);
                ttyDevice.closePort();
                System.exit(e.getCausingErrno().getValue());
            } finally {
                /* dispatched if the application didn't crash */
                ttyDevice.closePort();
            }
        }
    }
}
