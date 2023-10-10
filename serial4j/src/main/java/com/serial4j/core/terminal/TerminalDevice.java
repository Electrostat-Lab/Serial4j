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
package com.serial4j.core.terminal;

import com.serial4j.core.errno.Errno;
import com.serial4j.core.errno.ErrnoToException;
import com.serial4j.core.serial.SerialPort;
import com.serial4j.core.serial.throwable.*;
import com.serial4j.core.terminal.control.*;
import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * The main entry class for the native terminal device for Serial4j api.
 *
 * @author pavl_g.
 */
public final class TerminalDevice {

    private static final Logger LOGGER = Logger.getLogger(TerminalDevice.class.getName());
    final NativeTerminalDevice nativeTerminalDevice = new NativeTerminalDevice();

    private Permissions permissions = new Permissions(0, "").append(Permissions.Const.O_RDWR)
            .append(Permissions.Const.O_NOCTTY);
    private boolean loggingEnabled;

    /**
     * Instantiates a Unix terminal device object.
     */
    public TerminalDevice() {
    }

    /**
     * Sets up the jvm pointer from jni environment pointer on the heap for the global
     * multithreaded reference.
     *
     * <p>
     * Note: Native and jvm threads can access the jni pointer from the global jvm pointer without
     * referencing one of the local jni env pointers stored on another thread stack (as this leads to a thread-transition error).
     * </p>
     */
    @Deprecated
    private static void setupJniEnvironment() {
        final int errno = NativeTerminalDevice.setupJniEnvironment();
        ErrnoToException.throwFromErrno(errno);
    }

    /**
     * Opens this device's serial port using the path from [serialPort] instance.
     *
     * @param serialPort the serial port instance to open.
     * @throws NoSuchDeviceException          if an attempt is made to open a non-device file.
     * @throws NoSuchFileException            if an attempt is made to access a file that doesn't exist.
     * @throws FileAlreadyOpenedException     if an attempt is made to open an already opened terminal device.
     * @throws InterruptedSystemCallException if there is a process interruption while opening the port.
     * @throws FileIsDirectoryException       if an attempt is made to open a directory instead of a device.
     * @throws TooManyOpenedFilesException    if an attempt is made to open too many devices exceeding the system limit.
     * @throws FileTableOverflowException     if an attempt is made to open a device while system io is halt.
     * @throws NoSpaceLeftException           if an attempt is made to open a device while there is no space left.
     * @throws ReadOnlyFileSystemException    if an attempt is made to open a read-only device.
     * @throws PermissionDeniedException      if an unauthorized user attempts to open this device.
     */
    public void openPort(final SerialPort serialPort) throws NoSuchDeviceException,
            NoSuchFileException,
            FileAlreadyOpenedException,
            InterruptedSystemCallException,
            FileIsDirectoryException,
            TooManyOpenedFilesException,
            FileTableOverflowException,
            NoSpaceLeftException,
            ReadOnlyFileSystemException,
            PermissionDeniedException {
        if (isSerial4jLoggingEnabled()) {
            LOGGER.log(Level.INFO, "Opening serial device " + serialPort.getPath());
        }
        this.nativeTerminalDevice.setSerialPort(serialPort);
        final int returnValue = nativeTerminalDevice.openPort(serialPort.getPath(), permissions.getValue());
        if (isOperationFailed(returnValue)) {
            ErrnoToException.throwFromErrno(nativeTerminalDevice.getErrno());
        }
        /* update port data natively */
        /* ... */
    }

    /**
     * Initializes this terminal device with the default flags and the default
     * read timeout configuration.
     *
     * @throws InvalidPortException           if an attempt is made to initialize an invalid port (non-opened/still-closed device).
     * @throws FileNotFoundException          if the device opened before has been ejected.
     * @throws NoAvailableTtyDevicesException if the port is closed while performing the initialization operation.
     */
    public void initTerminal() throws InvalidPortException,
            FileNotFoundException,
            NoAvailableTtyDevicesException {
        if (nativeTerminalDevice.getSerialPort() == null) {
            throw new InvalidPortException("Bad serial port!");
        }
        if (isSerial4jLoggingEnabled()) {
            LOGGER.log(Level.INFO, "Initializing serial device " + getSerialPort().getPath());
        }
        int returnValue = nativeTerminalDevice.initTerminal();
        if (isOperationFailed(returnValue)) {
            returnValue = nativeTerminalDevice.getErrno();
        }
        ErrnoToException.throwFromErrno(returnValue);
    }

    public TerminalFlag getTerminalControlFlag() throws BadFileDescriptorException,
            InvalidPortException,
            NotTtyDeviceException {
        final TerminalFlag TCF = TerminalFlag.createEmptyFlag();
        final int returnValue = nativeTerminalDevice.getTerminalControlFlag();
        if (isOperationFailed(returnValue)) {
            /* Warning: Force cast the errno to (int) */
            ErrnoToException.throwFromErrno(nativeTerminalDevice.getErrno());
        }
        TCF.setValue(returnValue);
        return TCF;
    }

    /**
     * Adjusts the terminal control flag of the termios, the terminal control flag controls the
     * characters behavior in the local terminal.
     *
     * @param flag the terminal control flag to adjust.
     * @throws BadFileDescriptorException if the filedes is not a valid file descriptor.
     * @throws InvalidPortException       if the port is null or has not been initialized yet.
     * @throws NotTtyDeviceException      if the filedes is not associated with a terminal device.
     * @throws InvalidArgumentException   if the value of the when argument is not valid,
     *                                    or there is something wrong with the data in the termios-p argument.
     */
    public void setTerminalControlFlag(final TerminalFlag flag) throws BadFileDescriptorException,
            InvalidPortException,
            NotTtyDeviceException,
            InvalidArgumentException {

        int returnValue = nativeTerminalDevice.setTerminalControlFlag(flag.getValue());
        if (isOperationFailed(returnValue)) {
            returnValue = nativeTerminalDevice.getErrno();
        }
        ErrnoToException.throwFromErrno(returnValue);
    }

    public TerminalFlag getTerminalLocalFlag() throws BadFileDescriptorException,
            InvalidPortException,
            NotTtyDeviceException {
        final TerminalFlag TLF = TerminalFlag.createEmptyFlag();
        final int returnValue = nativeTerminalDevice.getTerminalLocalFlag();
        /* Warning: Force cast the errno to (int) */
        if (isOperationFailed(returnValue)) {
            /* Warning: Force cast the errno to (int) */
            ErrnoToException.throwFromErrno(nativeTerminalDevice.getErrno());
        }
        TLF.setValue(returnValue);
        return TLF;
    }

    /**
     * Adjusts the terminal local flag of the termios, the terminal local flag controls how the
     * terminal interprets the characters on the local console.
     *
     * @param flag the local flag to adjust.
     * @throws BadFileDescriptorException if the filedes is not a valid file descriptor.
     * @throws InvalidPortException       if the port is null or has not been initialized yet.
     * @throws NotTtyDeviceException      if the filedes is not associated with a terminal device.
     * @throws InvalidArgumentException   if the value of the when argument is not valid,
     *                                    or there is something wrong with the data in the termios-p argument.
     */
    public void setTerminalLocalFlag(final TerminalFlag flag) throws BadFileDescriptorException,
            InvalidPortException,
            NotTtyDeviceException,
            InvalidArgumentException {
        int returnValue = nativeTerminalDevice.setTerminalLocalFlag(flag.getValue());
        if (isOperationFailed(returnValue)) {
            returnValue = nativeTerminalDevice.getErrno();
        }
        ErrnoToException.throwFromErrno(returnValue);
    }

    public TerminalFlag getTerminalInputFlag() throws BadFileDescriptorException,
            InvalidPortException,
            NotTtyDeviceException {
        final TerminalFlag TIF = TerminalFlag.createEmptyFlag();
        final int returnValue = nativeTerminalDevice.getTerminalInputFlag();
        /* Warning: Force cast the errno to (int) */
        if (isOperationFailed(returnValue)) {
            /* Warning: Force cast the errno to (int) */
            ErrnoToException.throwFromErrno(nativeTerminalDevice.getErrno());
        }
        TIF.setValue(returnValue);
        return TIF;
    }

    /**
     * Adjusts the terminal input flag for the termios, the terminal input flag controls how
     * the terminal interpret the characters at the input from the terminal device.
     *
     * @param flag the terminal input flag to adjust.
     * @throws BadFileDescriptorException if the filedes is not a valid file descriptor.
     * @throws InvalidPortException       if the port is null or has not been initialized yet.
     * @throws NotTtyDeviceException      if the filedes is not associated with a terminal device.
     * @throws InvalidArgumentException   if the value of the when argument is not valid,
     *                                    or there is something wrong with the data in the termios-p argument.
     */
    public void setTerminalInputFlag(final TerminalFlag flag) throws BadFileDescriptorException,
            InvalidPortException,
            NotTtyDeviceException,
            InvalidArgumentException {
        int returnValue = nativeTerminalDevice.setTerminalInputFlag(flag.getValue());
        if (isOperationFailed(returnValue)) {
            returnValue = nativeTerminalDevice.getErrno();
        }
        ErrnoToException.throwFromErrno(returnValue);
    }

    public TerminalFlag getTerminalOutputFlag() throws BadFileDescriptorException,
            InvalidPortException,
            NotTtyDeviceException {
        final TerminalFlag TOF = TerminalFlag.createEmptyFlag();
        final int returnValue = nativeTerminalDevice.getTerminalOutputFlag();
        /* Warning: Force cast the errno to (int) */
        if (isOperationFailed(returnValue)) {
            /* Warning: Force cast the errno to (int) */
            ErrnoToException.throwFromErrno(nativeTerminalDevice.getErrno());
        }
        TOF.setValue(returnValue);
        return TOF;
    }

    /**
     * Adjusts the terminal output flag for this terminal device, the terminal output flag controls
     * how the terminal interpret the charachters at the output to the terminal device.
     *
     * @throws BadFileDescriptorException if the filedes is not a valid file descriptor.
     * @throws InvalidPortException       if the port is null or has not been initialized yet.
     * @throws NotTtyDeviceException      if the filedes is not associated with a terminal device.
     * @throws InvalidArgumentException   if the value of the when argument is not valid,
     *                                    or there is something wrong with the data in the termios-p argument.
     */
    public void setTerminalOutputFlag(final TerminalFlag flag) throws BadFileDescriptorException,
            InvalidPortException,
            NotTtyDeviceException,
            InvalidArgumentException {
        int returnValue = nativeTerminalDevice.setTerminalOutputFlag(flag.getValue());
        if (isOperationFailed(returnValue)) {
            returnValue = nativeTerminalDevice.getErrno();
        }
        ErrnoToException.throwFromErrno(returnValue);
    }

    public Permissions getPermissions() {
        return permissions;
    }

    public void setPermissions(final Permissions permissions) {
        this.permissions = permissions;
    }

    public void setReadConfigurationMode(final ReadConfiguration readConfiguration, final int timeoutValue, final int minimumBytes) throws NoSuchDeviceException,
            PermissionDeniedException,
            BrokenPipeException,
            InvalidPortException,
            NoAvailableTtyDevicesException {

        if (isSerial4jLoggingEnabled()) {
            LOGGER.log(Level.INFO, "Setting reading config to " + readConfiguration.getDescription());
        }
        final short timeoutByteValue = (short) (readConfiguration.getMode()[0] * timeoutValue);
        final short minimumBytesValue = (short) (readConfiguration.getMode()[1] * minimumBytes);
        final int errno = nativeTerminalDevice.setReadConfigurationMode((short) Math.min(255, timeoutByteValue),
                (short) Math.min(255, minimumBytesValue));
        ErrnoToException.throwFromErrno(errno);
    }

    public ReadConfiguration getReadConfigurationMode() {
        return ReadConfiguration.getFromNativeReadConfig(nativeTerminalDevice.getReadConfigurationMode());
    }

    public long write(final String buffer) throws NoSuchDeviceException,
            PermissionDeniedException,
            BrokenPipeException,
            InvalidPortException,
            NoAvailableTtyDevicesException {
        if (nativeTerminalDevice.getSerialPort() == null) {
            throw new InvalidPortException("Bad serial port!");
        }
        final long numberOfWrittenBytes = nativeTerminalDevice.write(buffer, buffer.length());
        if (numberOfWrittenBytes == Errno.ERR_INVALID_PORT.getValue()) {
            ErrnoToException.throwFromErrno(Errno.ERR_INVALID_PORT.getValue());
        } else if (numberOfWrittenBytes <= 0) {
            ErrnoToException.throwFromErrno(nativeTerminalDevice.getErrno());
        }
        return numberOfWrittenBytes;
    }

    public long write(final int data) throws NoSuchDeviceException,
            PermissionDeniedException,
            BrokenPipeException,
            InvalidPortException,
            NoAvailableTtyDevicesException {
        if (nativeTerminalDevice.getSerialPort() == null) {
            throw new InvalidPortException("Bad serial port!");
        }

        final long numberOfWrittenBytes = nativeTerminalDevice.write(data);
        if (numberOfWrittenBytes == Errno.ERR_INVALID_PORT.getValue()) {
            ErrnoToException.throwFromErrno(Errno.ERR_INVALID_PORT.getValue());
        } else if (numberOfWrittenBytes <= 0) {
            ErrnoToException.throwFromErrno(nativeTerminalDevice.getErrno());
        }
        return numberOfWrittenBytes;
    }

    public long write(final int[] data) throws NoSuchDeviceException,
            PermissionDeniedException,
            BrokenPipeException,
            InvalidPortException,
            NoAvailableTtyDevicesException {
        long numberOfWrittenBytes = 0;
        for (int datum : data) {
            numberOfWrittenBytes += this.write(datum);
            if (numberOfWrittenBytes <= 0) {
                break;
            }
        }
        return numberOfWrittenBytes;
    }

    public long sread() {
        if (nativeTerminalDevice.getSerialPort() == null) {
            throw new InvalidPortException("Bad serial port!");
        }
        long bytes = nativeTerminalDevice.sread();
        if (bytes == Errno.ERR_OPERATION_FAILED.getValue()) {
            ErrnoToException.throwFromErrno(nativeTerminalDevice.getErrno());
        }

        return bytes;
    }

    public long sread(final int length) {
        if (nativeTerminalDevice.getSerialPort() == null) {
            throw new InvalidPortException("Bad serial port!");
        }
        long bytes = nativeTerminalDevice.sread(length);
        if (bytes == Errno.ERR_OPERATION_FAILED.getValue()) {
            ErrnoToException.throwFromErrno(nativeTerminalDevice.getErrno());
        }

        return bytes;
    }

    public long iread(final int length) {
        if (nativeTerminalDevice.getSerialPort() == null) {
            throw new InvalidPortException("Bad serial port!");
        }
        long bytes = nativeTerminalDevice.iread(length);
        if (bytes == Errno.ERR_OPERATION_FAILED.getValue()) {
            ErrnoToException.throwFromErrno(nativeTerminalDevice.getErrno());
        }

        return bytes;
    }

    /**
     * Moves the current file-system position by a 64-bit offset value
     * forwardly or backwardly according to the file-seek criterion (the "whence" parameter).
     *
     * @param offset    the number of bytes to seek with; based on the criterion applied
     * @param criterion the file seeking criterion
     * @return the number of sought bytes in a 64-bit integer format
     */
    public long seek(long offset, NativeTerminalDevice.FileSeekCriterion criterion) {
        if (nativeTerminalDevice.getSerialPort() == null) {
            throw new InvalidPortException("Bad or not initialized serial port!");
        }
        long bytes = nativeTerminalDevice.seek(offset, criterion.getWhence());
        if (bytes == Errno.ERR_OPERATION_FAILED.getValue()) {
            ErrnoToException.throwFromErrno(nativeTerminalDevice.getErrno());
        }
        return bytes;
    }

    public String getReadBuffer() {
        return nativeTerminalDevice.getReadBuffer();
    }

    public char[] getBuffer() {
        return nativeTerminalDevice.getBuffer();
    }

    public int getBaudRate() throws NoSuchDeviceException,
            PermissionDeniedException,
            BrokenPipeException,
            InvalidPortException,
            NoAvailableTtyDevicesException {
        if (isSerial4jLoggingEnabled()) {
            LOGGER.log(Level.INFO, "Getting device baud");
        }
        final int errno = nativeTerminalDevice.getBaudRate();
        ErrnoToException.throwFromErrno(errno);
        return errno;
    }

    public void setBaudRate(final BaudRate baudRate) throws BadFileDescriptorException,
            InvalidPortException,
            NotTtyDeviceException {
        if (isSerial4jLoggingEnabled()) {
            LOGGER.log(Level.INFO, "Setting device baud rate to " + baudRate.getRealBaud());
        }
        int returnValue = nativeTerminalDevice.setBaudRate(baudRate.getBaudRate());
        if (isOperationFailed(returnValue)) {
            returnValue = nativeTerminalDevice.getErrno();
        }
        ErrnoToException.throwFromErrno(returnValue);
    }

    public String[] getSerialPorts() throws NoSuchDeviceException,
            PermissionDeniedException,
            BrokenPipeException,
            InvalidPortException,
            NoAvailableTtyDevicesException {
        fetchSerialPorts();
        return nativeTerminalDevice.getSerialPorts();
    }

    public void throwExceptionFromNativeErrno() throws NoSuchDeviceException,
            PermissionDeniedException,
            BrokenPipeException,
            InvalidPortException,
            NoAvailableTtyDevicesException {
        final int errno = nativeTerminalDevice.getErrno();
        ErrnoToException.throwFromErrno(errno);
    }

    public void closePort() throws NoSuchDeviceException,
            PermissionDeniedException,
            BrokenPipeException,
            InvalidPortException,
            NoAvailableTtyDevicesException {
        if (nativeTerminalDevice.getSerialPort() == null) {
            throw new InvalidPortException("Bad serial port!");
        }

        if (isSerial4jLoggingEnabled()) {
            LOGGER.log(Level.INFO, "Closing port: " + getSerialPort().getPath());
        }
        final int returnValue = nativeTerminalDevice.closePort();
        // sanity check for the business error code
        if (returnValue == Errno.ERR_INVALID_PORT.getValue()) {
            ErrnoToException.throwFromErrno(Errno.ERR_INVALID_PORT.getValue());
        } else if (returnValue < Errno.OPERATION_SUCCEEDED.getValue()) {
            ErrnoToException.throwFromErrno(nativeTerminalDevice.getErrno());
        }
    }

    public boolean isSerial4jLoggingEnabled() {
        return loggingEnabled;
    }

    public void setSerial4jLoggingEnabled(final boolean loggingEnabled) {
        this.loggingEnabled = loggingEnabled;
    }

    public SerialPort getSerialPort() {
        return nativeTerminalDevice.getSerialPort();
    }

    private void fetchSerialPorts() throws NoSuchDeviceException,
            PermissionDeniedException,
            BrokenPipeException,
            InvalidPortException,
            NoAvailableTtyDevicesException {
        if (isSerial4jLoggingEnabled()) {
            LOGGER.log(Level.INFO, "Fetching Serial ports.");
        }
        // throwing the return value here
        final int returnValue = nativeTerminalDevice.fetchSerialPorts();
        ErrnoToException.throwFromErrno(returnValue);
    }

    private boolean isOperationFailed(final int returnValue) {
        return returnValue == Errno.ERR_OPERATION_FAILED.getValue();
    }

    private boolean isErrnoAvailable(final int errno) {
        return errno > 0;
    }
}