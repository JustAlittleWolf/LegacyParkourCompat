# Player edge-backoff skips positive-Y requests in 1.19

### 1.18.2

`net/minecraft/world/entity/player/Player.java`, `maybeBackOffFromEdge`, lines 1032–1080. The edge-backoff gate checks flying state, mover type (`SELF` or `PLAYER`), `isStayingOnGroundSurface()`, and `isAboveGround()`. Its horizontal trimming loops call `level.noCollision` and reduce X/Z in `0.05` steps. `net/minecraft/world/entity/Entity.java`, `move`, line 562, calls the hook before collision resolution.

### 1.19

`net/minecraft/world/entity/player/Player.java`, `maybeBackOffFromEdge`, lines 1060–1112. The same gate and trimming loops remain, with an added `$$0.y <= 0.0` condition. `net/minecraft/world/entity/Entity.java`, `move`, line 566, calls the hook before collision resolution.

### Difference

The `1.18.2→1.19` edge-backoff gate adds a nonpositive-Y requirement. The boundary of this change before 1.18.2 and after 1.19 is unknown.

### Movement meaning

For a positive-Y request with the other gates true, 1.18.2 can reduce the horizontal request through its collision checks, while 1.19 skips those loops. If a 1.18.2 loop iteration would trim the request, the horizontal vector passed to `Entity.collide` can differ. This is a conditional source-level movement consequence; no trajectory or runtime outcome is claimed. This is separate from the previously reviewed `fallDistance` writer feeding `isAboveGround()`.
