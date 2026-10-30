package me.wolfii.legacyparkourcompat.mechanic;

import java.lang.ref.WeakReference;
import java.util.Objects;
import java.util.function.Supplier;

/**
 * Holds state for one object without retaining it or relying on its {@code equals} implementation.
 * Intended for runtime paths that are scoped to one current owner at a time.
 */
public final class WeakIdentityState<O, S> {
    private WeakReference<O> owner = new WeakReference<>(null);
    private S state;

    public S get(O candidate) {
        Objects.requireNonNull(candidate, "candidate");
        return this.owner.get() == candidate ? this.state : null;
    }

    public S getOrCreate(O candidate, Supplier<? extends S> factory) {
        Objects.requireNonNull(candidate, "candidate");
        Objects.requireNonNull(factory, "factory");
        if (this.owner.get() != candidate) {
            S created = Objects.requireNonNull(factory.get(), "factory result");
            this.owner = new WeakReference<>(candidate);
            this.state = created;
        } else if (this.state == null) {
            this.state = Objects.requireNonNull(factory.get(), "factory result");
        }
        return this.state;
    }

    public void clear(O candidate) {
        Objects.requireNonNull(candidate, "candidate");
        if (this.owner.get() == candidate) {
            this.owner.clear();
            this.state = null;
        }
    }
}
