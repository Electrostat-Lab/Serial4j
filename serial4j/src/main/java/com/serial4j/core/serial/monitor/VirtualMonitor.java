package com.serial4j.core.serial.monitor;

import com.serial4j.core.serial.SerialPort;
import com.serial4j.core.serial.entity.impl.SerialReadEntity;
import com.serial4j.core.serial.entity.impl.SerialWriteEntity;
import com.serial4j.core.serial.throwable.*;
import com.serial4j.core.terminal.NativeBufferInputStream;
import com.serial4j.core.terminal.NativeBufferOutputStream;
import com.serial4j.core.terminal.Permissions;
import com.serial4j.core.terminal.control.BaudRate;
import java.io.FileNotFoundException;
import java.io.IOException;

public class VirtualMonitor extends SerialMonitor {

    /**
     * Instantiates a new virtual monitor with a name (acting on filesystem only).
     * <p>
     * Use {@link SerialMonitor#startDataMonitoring(String, BaudRate, Permissions)} to initialize and start
     * data monitoring.
     * </p>
     *
     * @param monitorName the name for this monitor.
     */
    public VirtualMonitor(String monitorName) {
        super(monitorName);
    }

    @Override
    public void startDataMonitoring(String port, BaudRate baudRate, Permissions permissions) throws NoSuchDeviceException, PermissionDeniedException, BrokenPipeException, InvalidPortException, NoAvailableTtyDevicesException, FileNotFoundException {
        terminalDevice.setSerial4jLoggingEnabled(true);
        if (permissions != null) {
            terminalDevice.setPermissions(permissions);
        }
        terminalDevice.setSerial4jLoggingEnabled(false);
        readEntityStream = new NativeBufferInputStream(terminalDevice);
        writeEntityStream = new NativeBufferOutputStream(terminalDevice);

        serialWriteEntity = new SerialWriteEntity(this);
        serialReadEntity = new SerialReadEntity(this);

        terminalDevice.openPort(new SerialPort(port));

        chmod("+rw", port, () -> monitorThread = new Thread(() -> {
            while (!isTerminate()) {
                /* change mode access and start data monitoring */
                serialWriteEntity.run();
                serialReadEntity.run();
            }
        }, monitorName));

        monitorThread.start();
    }

    protected void chmod(String flags, String script, Runnable afterChangeModeAccess) {
        try {
            // change mode access to read/write/execute
            ProcessBuilder builder = new ProcessBuilder().command("chmod", flags, script);
            builder.start().onExit().thenApply(process -> {
                afterChangeModeAccess.run();
                process.destroy();
                return null;
            });
        } catch (IOException e) {
            if (serialReadEntityEntityStatus != null) {
                serialReadEntityEntityStatus.onExceptionThrown(e);
            }
            if (serialWriteEntityEntityStatus != null) {
                serialWriteEntityEntityStatus.onExceptionThrown(e);
            }
        }
    }
}
