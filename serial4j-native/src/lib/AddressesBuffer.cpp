#include<DynamicBuffer.h>

void DynamicBuffer::add(void* address) {

    /* move the pointer to point to the last item */
    /* then, obtain a superficial copy */
    void** copy = (startAddress + count);
    /* dereference and evalute using the superficial copy */
    *copy = (void*) calloc(1, sizeof(void*));
    *copy = address;
    count++;
} 

void DynamicBuffer::add(int index, void* address) {
    /* adds on the count if the location was empty previously */
    if (startAddress[index] == NULL) {
        ++count;
    }
    startAddress[index] = address;
}

void DynamicBuffer::removeAt(int index) {
    startAddress[index] = NULL;
    BufferUtils::reValidateBuffer(startAddress, *getAddressesCount());
    
    count--;
}

void DynamicBuffer::removeAll() {
    for (int i = 0; i < count; i++) {
        startAddress[i] = NULL;
    }

    BufferUtils::reValidateBuffer(startAddress, *getAddressesCount());

    this->resetToStartAddress();
}

void* DynamicBuffer::getAddress(int index) {
    return startAddress[index];
}

int DynamicBuffer::indexOf(void* address) {
    for (int i = 0; i < *getAddressesCount(); i++) {
        if (startAddress[i] == address) {
            return i;
        }
    }
    return ERR_OPERATION_FAILED;
}

int* DynamicBuffer::getAddressesCount() {
    return &count;
}
