package io.kubemq.sdk.tools;

import com.google.protobuf.ByteString;

import java.io.*;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.TimeZone;

/**
 * A class that is responsible for Converting Byte[] to object and vice versa.
 */
public class Converter {

    /**
     * Byte Array to ByteString
     *
     * @param byteArray byteArray to convert into ByteString
     * @return com.google.protobuf.ByteString
     */
    static ByteString ToByteString(byte[] byteArray) {
        return ByteString.copyFrom(byteArray);
    }

    /**
     * Convert from byte array to object
     *
     * @param data byteArray to convert into Object
     * @return Object
     * @throws IOException            Signals that an I/O exception of some sort has occurred.
     * @throws ClassNotFoundException Thrown when an application tries to load in a class through its string name
     */
    public static Object FromByteArray(byte[] data) throws IOException, ClassNotFoundException {
        ByteArrayInputStream bis = new ByteArrayInputStream(data);
        try (ObjectInput in = new ObjectInputStream(bis)) {
            return in.readObject();
        }
    }

    /**
     * Convert from object to byte array
     *
     * @param obj Object to convert into byte array
     * @return byte[]
     * @throws IOException Signals that an I/O exception of some sort has occurred.
     */
    public static byte[] ToByteArray(Object obj) throws IOException {
        try (ByteArrayOutputStream bos = new ByteArrayOutputStream()) {
            ObjectOutput out = null;
            out = new ObjectOutputStream(bos);
            out.writeObject(obj);
            out.flush();
            return bos.toByteArray();
        }
    }

    public static LocalDateTime FromUnixTime(long unitTime) {
        return LocalDateTime.ofInstant(Instant.ofEpochMilli(unitTime),
                TimeZone.getDefault().toZoneId());
    }

    public static long ToUnixTime(LocalDateTime timestamp) {
        return timestamp.atZone(TimeZone.getDefault().toZoneId()).toInstant().toEpochMilli();
    }
}
