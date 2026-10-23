package me.wolfii.legacyparkourcompat.mechanic.collision;

import it.unimi.dsi.fastutil.doubles.DoubleList;
import me.wolfii.legacyparkourcompat.mechanic.hook.collision.BlockComponentClipHook;
import me.wolfii.legacyparkourcompat.mixin.collision.VoxelShapeAccessor;
import net.minecraft.core.Direction;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.List;

/** Geometry plus explicitly supplied component provenance; never decomposes a voxel union. */
public final class ComponentCollisionShape extends VoxelShape {
    private final VoxelShape geometry;
    private final List<AABB> components;
    private final BlockComponentClipHook clip;

    public ComponentCollisionShape(List<AABB> components, BlockComponentClipHook clip) {
        this(components, clip, geometry(components));
    }

    private ComponentCollisionShape(List<AABB> components, BlockComponentClipHook clip, VoxelShape geometry) {
        super(((VoxelShapeAccessor) (Object) geometry).legacyparkourcompat$discreteShape());
        this.components = List.copyOf(components);
        this.clip = clip;
        this.geometry = geometry;
    }

    private static VoxelShape geometry(List<AABB> components) {
        VoxelShape geometry = Shapes.empty();
        for (AABB component : components) {
            geometry = Shapes.or(geometry, Shapes.create(component));
        }
        return geometry;
    }

    @Override
    public DoubleList getCoords(Direction.Axis axis) {
        return geometry.getCoords(axis);
    }

    @Override
    public VoxelShape move(double x, double y, double z) {
        return new ComponentCollisionShape(components.stream().map(box -> box.move(x, y, z)).toList(), clip, geometry.move(x, y, z));
    }

    @Override
    public double collide(Direction.Axis axis, AABB moving, double distance) {
        for (AABB component : components) {
            distance = clip.clip(component, axis, moving, distance);
        }
        return distance;
    }

    public boolean intersectsQuery(AABB query) {
        for (AABB component : components) {
            if (component.intersects(query)) {
                return true;
            }
        }
        return false;
    }
}
