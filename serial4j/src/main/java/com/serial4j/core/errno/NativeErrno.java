package com.serial4j.core.errno;

public final class NativeErrno {
    
    private NativeErrno() {
    }

    static native int getBadFileDescriptorErrno();

    static native int getBrokenPipeErrno();

    static native int getFileAlreadyOpenedErrno();

    static native int getFileIsDirectoryErrno();

    static native int getFileTableOverflowErrno();

    static native int getFileTooLargeErrno();

    static native int getInputOutputErrno();

    static native int getInterruptedSystemCallErrno();

    static native int getInvalidArgumentErrno();

    static native int getInvalidPortErrno();

    static native int getNoAvailableTtyDevicesErrno();

    static native int getNoSpaceLeftErrno();

    static native int getNoSuchDeviceErrno();

    static native int getNoSuchFileErrno();

    static native int getNotTtyDeviceErrno();

    static native int getOperationFailedErrno();

    static native int getOperationSucceededCode();

    static native int getPermissionDeniedErrno();

    static native int getReadOnlyFileSystemErrno();

    static native int getTooManyOpenedFilesErrno();

    static native int getTryAgainErrno();
}