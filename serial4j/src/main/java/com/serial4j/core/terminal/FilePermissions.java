/*
 * BSD 3-Clause License for Serial4j from the AVR-Sandbox Project.
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

package com.serial4j.core.terminal;

import com.serial4j.core.flag.AppendableFlag;
import com.serial4j.core.flag.FlagConst;

/**
 * Provides Unix file system permissions for the specified serial port
 * of the terminal device.
 *
 * @author pavl_g.
 */
public final class FilePermissions extends AppendableFlag {

    /**
     * Wraps a POSIX IO flag using an integer value.
     *
     * @param value       the value of the permission flag.
     * @param description the description of the flag.
     */
    public FilePermissions(int value, String description) {
        super(value, description);
    }

    public static FilePermissions build() {
        return (FilePermissions) AppendableFlag.build(FilePermissions.class);
    }

    public enum AccessModeConst implements FlagConst {
        S_IRUSR(NativeFileAccessPermissions.getGrantReadByOwner(), "Read by owner"),
        S_IWUSR(NativeFileAccessPermissions.getGrantWriteByOwner(), "Write by owner"),
        S_IXUSR(NativeFileAccessPermissions.getGrantExecuteByOwner(), "Execute by owner"),
        S_IRWXU(NativeFileAccessPermissions.getGrantFullPermissions(), "Grant full permissions (+rwx)");

        private final int value;
        private final String description;


        AccessModeConst(int value, String description) {
            this.value = value;
            this.description = description;
        }

        @Override
        public int getValue() {
            return value;
        }

        @Override
        public String getDescription() {
            return description;
        }
    }

    public enum OperativeConst implements FlagConst {
        /**
         * Aliasing Read-only file permission.
         */
        O_RDONLY(NativeFilePermissions.getReadOnly(), "READ_ONLY"),

        /**
         * Aliasing Write-only file permission.
         */
        O_WRONLY(NativeFilePermissions.getWriteOnly(), "WRITE_ONLY"),

        /**
         * Aliasing R/W file permission.
         */
        O_RDWR(NativeFilePermissions.getReadWrite(), "READ_WRITE"),

        /**
         * Specifies that the terminal is not the controlling device.
         */
        O_NOCTTY(NativeFilePermissions.getNoControlTerminalDevice(), "NO_CONTROL_TTY_DEVICE"),

        /**
         * Applies non-blocking operations for the target terminal device.
         */
        O_NONBLOCK(NativeFilePermissions.getTerminalNonBlock(), "NON_BLOCK_READ"),

        /**
         * Creates the file if it doesn't exist.
         */
        O_CREATE(NativeFilePermissions.getCreateFile(), "CREATE_NEW_FILE");

        private final int value;
        private final String description;

        OperativeConst(final int value, final String description) {
            this.value = value;
            this.description = description;
        }

        @Override
        public int getValue() {
            return value;
        }

        @Override
        public String getDescription() {
            return description;
        }
    }
}
