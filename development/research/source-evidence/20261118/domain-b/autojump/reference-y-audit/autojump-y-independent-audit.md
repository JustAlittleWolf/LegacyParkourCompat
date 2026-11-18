# Independent audit: AutoJump Y operand closure

Verdict: **ACCEPT — equal in the reachable eligible grounded callback.** In ordinary vanilla local-player movement in both 1.14.4 and 1.15, `Entity.y` equals `getBoundingBox().minY` when `LocalPlayer.updateAutoJump` runs. The 1.14.4 box-minY operand and 1.15 position-Y operand therefore cannot differ at that callback. This verdict is limited to this operand invariant; it makes no finding about other AutoJump gates or geometry.

## Immutable input and source identity

- Audited dependency report: `domain-b-autojump/reference-y-source/autojump-y-operand-closure.md`, SHA-256 `B1D90A0E383FAF2EF938BD1D31AAC3444DA849A57396682E551D919DCCABD0BE`.
- Source root: `D:\Javastuff\LegacyParkourCompat\decompiled_minecraft`; mapping: Mojmap on both sides.
- 1.14.4 `mojmap.sources.sha256`: SHA-256 `af98406f3d4ed31494fbdce1078d1f3df3f712501878f9a0e9ad5879bdf23c3b`; ready marker reports exact version `1.14.4`, 3,250 source files; verification marker reports 3,250 files identical, zero missing, and matching published/regenerated source-manifest hashes.
- 1.15 `mojmap.sources.sha256`: SHA-256 `251a3019ccc3fd067f66365f3545b69a983383a28b19a1038debf1791be29340`; provenance reports exact version `1.15`, Mojmap/official client mappings, and 3,329 source files.
- Recomputed source-file hashes match both manifests:

| Release | File | SHA-256 |
|---|---|---|
| 1.14.4 | `net/minecraft/client/player/LocalPlayer.java` | `0795c1223198ce5acf5d2ed9e5db8435bbec4cd52b96f96b1ddf2865baaae85f` |
| 1.14.4 | `net/minecraft/world/entity/Entity.java` | `31c6be42d165102d3e6dc4295fdfef6e8971fc3aea13f938a5b3a33b91d524a7` |
| 1.14.4 | `net/minecraft/client/multiplayer/ClientPacketListener.java` | `6dddf37b79830d5413d91675e0bed8b4a3f0a179ac4535616dc57b510b778b45` |
| 1.15 | `net/minecraft/client/player/LocalPlayer.java` | `7227da5233e3c72ced008187b92a6a86842f84778eaa9f40fa1eb117286af823` |
| 1.15 | `net/minecraft/world/entity/Entity.java` | `191b3ad3e7348c9bac1e703fff896706d23a751bf15162aacf676f5f97c0a10e` |
| 1.15 | `net/minecraft/client/multiplayer/ClientPacketListener.java` | `d806d286af4b93d7c61c3c884f344a5870ce1871fb0a811506031adf78fafc30` |

## Independent dataflow trace

1. `LocalPlayer.move` calls `super.move(moverType, vec3)` and then calls `updateAutoJump` (1.14.4 `LocalPlayer.java:845-850`; 1.15 `LocalPlayer.java:851-856`). This ordering means the entity movement/box synchronization completes before AutoJump reads the operand. The old callback candidate checks grounded, not sneaking, and not passenger at 1.14.4 `856-862`; 1.15 calls `canAutoJump` at `862-863`, whose body checks `onGround`, the ground-surface condition, passenger state, movement and block jump factor (`962-970`). A passenger fails the applicable candidate gate.
2. The base position writer makes the lower box face equal to position Y. In 1.14.4 `Entity.setPos` stores `y=e` and constructs an `AABB` with `minY=e` (`Entity.java:310-316`). In 1.15 it stores the coordinates through `setPosRaw`, then constructs the same lower face (`Entity.java:315-320`). The 1.14.4 `absMoveTo` path calls `setPos` before returning (`Entity.java:1116-1138`); 1.15 `absMoveTo` does so (`Entity.java:1094-1105`).
3. Both `Entity.move` implementations preserve or re-establish the invariant. The `noPhysics` branch moves the existing box and immediately calls `setLocationFromBoundingbox` (1.14.4 `Entity.java:446-450`; 1.15 `:450-454`). In the normal branch, a resolved nonzero displacement moves the box and immediately synchronizes position (`1.14.4 :468-470`; `1.15 :472-474`). `setLocationFromBoundingbox` assigns `y = bb.minY` in 1.14.4 (`:791-796`) and uses `setPosRaw(..., bb.minY, ...)` in 1.15 (`:774-777`). If the displacement threshold skips the box write, neither value changes, so an established equality remains true.
4. The zero/piston path does not create an exception. For `MoverType.PISTON`, a limited `Vec3.ZERO` returns before changing either value (`1.14.4 Entity.java:451-455`; 1.15 `:455-459`). Returning from `super.move` still returns to the `LocalPlayer.move` override, which invokes AutoJump. If an earlier `onGround` value lets the callback gate pass, the position and box remain in their pre-call equal state. A regular zero-motion result similarly skips both writes. In non-early-return movement, `onGround` is assigned after synchronization (`1.14.4 Entity.java:474-477`; 1.15 `:478-481`).
5. Pose/dimension refresh also preserves the lower face. When width shrinks, each version builds the new box from `this.y`; otherwise it reuses `aABB.minY` (`1.14.4 Entity.java:2265-2285`; 1.15 `:2258-2280`). It does not independently alter Y. Starting from the invariant, either branch keeps `minY == y`.
6. I checked direct writes and transient raw-position paths. In canonical vanilla source, `Entity.setBoundingBox` only assigns `bb` (1.14.4 `:2333-2335`; 1.15 `:2328-2330`). The entity-class callers that can run on a player are `setPos`, movement, and dimension refresh described above. Other direct callers found in the full Mojmap source trees are in particle, hanging/decoration, and Shulker code, not LocalPlayer. Arbitrary external code calling the public setter is outside this vanilla source-path proof. In 1.15, the local-player correction handler temporarily calls `setPosRaw` at `ClientPacketListener.java:712`, then calls `absMoveTo` at `:727`; `absMoveTo` rebuilds the box with `setPos` before the packet handler returns. The 1.14.4 correction handler calls `absMoveTo` at `ClientPacketListener.java:704`, which likewise rebuilds the box before returning. The 1.15 `moveTo` raw path likewise calls `reapplyPosition`, which calls `setPos` (`Entity.java:322-324`, `1111-1124`). These are not callback windows with distinct operands.

Thus the callback operands are exactly equal, not merely close: `AABB.minY == Entity.y`. The old and new expressions in the dependency report subtracting those values from the same obstacle height yield identical operands and cannot diverge on a reachable eligible grounded vanilla callback.

## Bounds

This closes only the named AutoJump Y-operand dependency for 1.14.4 → 1.15. It does not audit or accept other gate, normalization, timer, query, or geometry differences. The invariant is established for vanilla LocalPlayer paths; arbitrary external mutation of the public bounding-box setter is not covered.

