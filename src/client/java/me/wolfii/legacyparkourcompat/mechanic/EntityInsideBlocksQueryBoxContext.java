package me.wolfii.legacyparkourcompat.mechanic;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.AABB;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.IdentityHashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

/** Reentrant per-entity scope for the query box captured by a movement-list callback scan. */
public final class EntityInsideBlocksQueryBoxContext {
    private static final ThreadLocal<Map<Entity, Deque<Scope>>> QUERY_BOXES = new ThreadLocal<>();

    private EntityInsideBlocksQueryBoxContext() {}

    public static Scope enter(Entity entity, AABB queryBox) {
        Objects.requireNonNull(entity, "entity");
        Objects.requireNonNull(queryBox, "queryBox");

        Map<Entity, Deque<Scope>> scopes = QUERY_BOXES.get();
        if (scopes == null) {
            scopes = new IdentityHashMap<>();
            QUERY_BOXES.set(scopes);
        }
        Scope scope = new Scope(entity, queryBox);
        scopes.computeIfAbsent(entity, ignored -> new ArrayDeque<>()).push(scope);
        return scope;
    }

    public static Optional<AABB> current(Entity entity) {
        Map<Entity, Deque<Scope>> scopes = QUERY_BOXES.get();
        if (scopes == null) {
            return Optional.empty();
        }
        Deque<Scope> stack = scopes.get(entity);
        if (stack == null) {
            return Optional.empty();
        }
        Scope scope = stack.peek();
        if (scope == null) {
            throw new IllegalStateException("Entity inside-block query-box scope has an empty stack");
        }
        return Optional.of(scope.queryBox);
    }

    public static final class Scope implements AutoCloseable {
        private final Entity entity;
        private final AABB queryBox;
        private boolean closed;

        private Scope(Entity entity, AABB queryBox) {
            this.entity = entity;
            this.queryBox = queryBox;
        }

        @Override
        public void close() {
            if (this.closed) {
                throw new IllegalStateException("Entity inside-block query-box scope closed twice");
            }

            Map<Entity, Deque<Scope>> scopes = QUERY_BOXES.get();
            Deque<Scope> stack = scopes == null ? null : scopes.get(this.entity);
            if (stack == null || stack.peek() != this) {
                throw new IllegalStateException("Entity inside-block query-box scope was not closed in stack order");
            }

            stack.pop();
            if (stack.isEmpty()) {
                scopes.remove(this.entity);
            }
            if (scopes.isEmpty()) {
                QUERY_BOXES.remove();
            }
            this.closed = true;
        }
    }
}
