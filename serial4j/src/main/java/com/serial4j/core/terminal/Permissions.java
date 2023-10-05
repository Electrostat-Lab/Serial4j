/*
 * From POSIX Standard: 6.5 File Control Operations.
 * Copyright (C) 1993-2019 Free Software Foundation, Inc.
 * This file is part of the GNU C Library.
 *
 * The GNU C Library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * The GNU C Library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with the GNU C Library; if not, see
 * <http://www.gnu.org/licenses/>.
 *
 *  -----------------------------------------------------------------------
 *
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
package com.serial4j.core.serial;

/**
 * Provides Unix file system permissions for the specified serial port
 * of the terminal device.
 *
 * @author pavl_g.
 */
public enum Permissions {

    O_RDONLY(00, "Read Only"),
    O_WRONLY(01, "Write Only"),
    O_RDWR(02, "Read/Write"),
    O_NOCTTY(0400, "No Control terminal device"),
    O_NONBLOCK(04000, "Terminal non block");

    private int value;
    private String description;

    /**
     * Wraps a POSIX IO flag using an integer value.
     *
     * @param value       the value of the permission flag.
     * @param description the description of the flag.
     */
    Permissions(final int value, final String description) {
        this.value = value;
        this.description = description;
    }

    /**
     * Appends new permissions to this permissions object.
     *
     * @param permissions the new permissions to append.
     * @return this permissions object with the new appended value.
     */
    public Permissions append(final Permissions permissions) {
        this.value |= permissions.getValue();
        this.description += "-" + permissions.getDescription();
        return this;
    }

    /**
     * Appends some new permissions to this permissions object.
     *
     * @param permissions an array args of the new permissions to append.
     * @return this permissions object with the new appended value.
     */
    public Permissions append(final Permissions... permissions) {
        for (Permissions permission : permissions) {
            append(permission);
        }
        return this;
    }

    /**
     * Retrieves the value of this permissions object.
     *
     * @return the value of this permissions object.
     */
    public int getValue() {
        return value;
    }

    /**
     * Retrieves the description of this permissions object.
     *
     * @return the description of this permissions object.
     */
    public String getDescription() {
        return description;
    }
}
