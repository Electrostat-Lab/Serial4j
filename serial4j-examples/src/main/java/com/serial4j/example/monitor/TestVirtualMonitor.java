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

package com.serial4j.example.monitor;

import com.serial4j.core.serial.entity.EntityStatus;
import com.serial4j.core.serial.entity.impl.SerialReadEntity;
import com.serial4j.core.serial.entity.impl.SerialWriteEntity;
import com.serial4j.core.serial.entity.impl.WritableCapsule;
import com.serial4j.core.serial.monitor.SerialDataListener;
import com.serial4j.core.serial.monitor.VirtualMonitor;
import com.serial4j.core.terminal.NativeTerminalDevice;
import com.serial4j.core.terminal.Permissions;
import com.serial4j.core.terminal.control.BaudRate;
import java.io.IOException;

/**
 * Tests the virtual monitor on the Java Streams {@link VirtualMonitor}.
 *
 * @author pavl_g
 */
public final class TestVirtualMonitor {
    public static void main(String[] args) throws IOException {
        final byte[] x = new byte[]{0};
        final byte[] y = new byte[]{0};
        final String[] data = new String[] {""};

        final VirtualMonitor virtualMonitor = new VirtualMonitor("ttyVirtual-monitor");
        virtualMonitor.setUseReturnCarriage(true);
        virtualMonitor.setReadEntityListener(new EntityStatus<>() {
            @Override
            public void onSerialEntityInitialized(SerialReadEntity serialMonitorEntity) {

            }

            @Override
            public void onSerialEntityTerminated(SerialReadEntity serialMonitorEntity) {

            }

            @Override
            public void onUpdate(SerialReadEntity serialMonitorEntity) {
                /* seeks the Java Streams back to zero before attempting further reading operations */
                virtualMonitor.getTerminalDevice().seek(0, NativeTerminalDevice.FileSeekCriterion.SEEK_SET);
            }

            @Override
            public void onExceptionThrown(Exception e) {

            }
        });
        virtualMonitor.addSerialDataListener(new SerialDataListener() {
            @Override
            public void onDataReceived(int data) {
            }

            @Override
            public void onDataTransmitted(int data) {
            }

            @Override
            public void onDataReceived(String data) {
                System.out.print("Data frame received = " + data.replace("\r", ""));
            }
        });

        virtualMonitor.setWriteEntityStatus(new EntityStatus<>() {
            @Override
            public void onSerialEntityInitialized(SerialWriteEntity serialMonitorEntity) {

            }

            @Override
            public void onSerialEntityTerminated(SerialWriteEntity serialMonitorEntity) {

            }

            @Override
            public void onUpdate(SerialWriteEntity serialMonitorEntity) {

                final WritableCapsule writableCapsule = new WritableCapsule();
                writableCapsule.write(data[0]);
                serialMonitorEntity.addWritableCapsule(writableCapsule);
                /* seeks the Java Streams back to zero */
                serialMonitorEntity.getSerialMonitor()
                        .getTerminalDevice().seek(0, NativeTerminalDevice.FileSeekCriterion.SEEK_SET);

                x[0] += 10;
                y[0] += 20;
                data[0] = String.format("[x = %d, y = %d]\n\r", x[0], y[0]);

                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }

            @Override
            public void onExceptionThrown(Exception e) {
                throw new RuntimeException(e);
            }
        });

        final Permissions permissions = Permissions.createEmptyPermissions()
                .append(Permissions.Const.O_RDWR)
                .append(Permissions.Const.O_CREATE);
        virtualMonitor.startDataMonitoring(args[0], BaudRate.B0, permissions);
    }
}