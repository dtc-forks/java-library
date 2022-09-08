package com.urbanairship.api.push.model.notification.ios;

import com.google.common.base.Preconditions;
import com.urbanairship.api.push.model.PushModelObject;

import java.util.Objects;
import java.util.Optional;

public final class IOSSoundData extends PushModelObject {
    private final Optional<Boolean> critical;
    private final Optional<Double> volume;
    private final Optional<String> name;

    public IOSSoundData(Optional<Boolean> critical, Optional<Double> volume, Optional<String> name) {
        this.critical = critical;
        this.volume = volume;
        this.name = name;
    }

    public static Builder newBuilder() { return new Builder(); }

    public Optional<Boolean> getCritical() {
        return critical;
    }

    public Optional<Double> getVolume() {
        return volume;
    }

    public Optional<String> getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        IOSSoundData that = (IOSSoundData) o;
        return Objects.equals(critical, that.critical) &&
                Objects.equals(volume, that.volume) &&
                Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(critical, volume, name);
    }

    @Override
    public String toString() {
        return "IOSSoundData{" +
                "critical=" + critical +
                ", volume=" + volume +
                ", name='" + name + '\'' +
                '}';
    }

    /**
     * Determines whether the 'sound' key should be mapped to just a string or the entire IOSDataSound object.
     *
     * @return boolean True if the entire IOSSoundData object needs to be read or false if just the file name needs to be read.
     */
    public boolean shouldBeDict() {
        return critical.isPresent() && critical.get();
    }

    public static class Builder {
       private Boolean critical = null;
       private Double volume = null;
       private String name = null;

       public Builder() {}

        public Builder setCritical(Boolean critical) {
            this.critical = critical;
            return this;
        }

        public Builder setVolume(Double volume) {
            this.volume = volume;
            return this;
        }

        public Builder setName(String name) {
            this.name = name;
            return this;
        }

        public IOSSoundData build() {
            Preconditions.checkNotNull(name, "The sound file name cannot be null");
            return new IOSSoundData(Optional.ofNullable(critical), Optional.ofNullable(volume), Optional.ofNullable(name));
        }
    }
}
