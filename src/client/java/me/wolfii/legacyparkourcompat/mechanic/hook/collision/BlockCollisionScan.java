package me.wolfii.legacyparkourcompat.mechanic.hook.collision;

/** Cell bounds and edge rules used by one historical block-collision scan. */
public record BlockCollisionScan(
    int minX,
    int maxXExclusive,
    int minY,
    int maxYExclusive,
    int minZ,
    int maxZExclusive,
    boolean pruneHorizontalCorners,
    boolean pruneHorizontalVerticalEdges
) {
    public boolean includes(int x, int y, int z) {
        boolean xEdge = x == this.minX || x == this.maxXExclusive - 1;
        boolean zEdge = z == this.minZ || z == this.maxZExclusive - 1;
        int horizontalEdges = (xEdge ? 1 : 0) + (zEdge ? 1 : 0);
        if (this.pruneHorizontalCorners && horizontalEdges == 2) {
            return false;
        }
        boolean yEdge = y == this.minY || y == this.maxYExclusive - 1;
        return !this.pruneHorizontalVerticalEdges || horizontalEdges == 0 || !yEdge;
    }
}
