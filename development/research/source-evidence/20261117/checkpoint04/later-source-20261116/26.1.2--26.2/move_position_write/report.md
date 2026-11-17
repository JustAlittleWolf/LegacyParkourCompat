# S-MOVE-POS: post-collision position write and integration

Bounded endpoint review: Minecraft 26.1.2 unobfuscated → 26.2 unobfuscated. Pair coverage remains partial; this is a source-only slice verdict, not a pair freeze or runtime claim.

## Source identity and integrity

Both ready markers identify the requested exact release and `unobfuscated` mapping with Vineflower, Java 25, and Gradle 9.7.1 / Vineflower 1.12.0 in provenance.

| Side | Ready / provenance SHA-256 | Source manifest SHA-256 (ready and actual) | Artifact manifest SHA-256 (ready and actual) | Source files checked |
|---|---|---|---|---:|
| 26.1.2 | `f24af0f4d38543c2bd9c19b50f347d4e89d6027050e4b21ae070734843946bb7` / `8d727f880cc91f4a297e148c5b75976303d051457334bc689268fbdf631a012a` | `54ae320660ca911a6d20514c965c33eb414fbedd1ad6727bbaa95fe906aa23c0` | `89f648229de83b0109460695c9f1f3bed7ef36efb84ede9224e80cce87118f92` | 6,882 / 6,882 |
| 26.2 | `f9406adb6bf7cb4c1ab0792a798ab2cb90e082ad4c2eee032c9f674d0ea8070f` / `a328f5333a1b27aadde4422b23159a323ae219ccd26f11207c1a5461366f27d1` | `a7ad74fc712567eb87136afc1eafcfd602b4088e9869f3596331fd34faefe894` | `ba9dc53a41bca9744ec1c8ab2ce9363764a22333c39ef64d5105f4e3bf5c98ba` | 7,055 / 7,055 |

The artifact manifest line counts match 26.1.2's ready-declared count (78); 26.2's ready marker does not declare an artifact count. Every source file listed in each source manifest was present and matched its declared SHA-256. `Entity.java` specifically hashes to `8b83b1f036aabbd13d990897c540c993f7120f02955486cfcf229517d4097ccf` (26.1.2) and `7afb9c1294893ffe73e3b1acffcad41c648f15de8378bff3dffaff869bb811d5` (26.2), matching each source manifest. Provenance records 26.1.2's full Gradle log and 26.2's summary-excerpt-only log; this difference does not affect the ready/source manifest checks above.

## Ordered bounded coverage

1. **Player reachability.** `Player` extends `Avatar`, which extends `LivingEntity`; `AbstractClientPlayer` extends `Player`, and `LocalPlayer` extends `AbstractClientPlayer` on both endpoints. `Player.travel` delegates to `super.travel` on the movement branches, while `LivingEntity` calls `this.move(MoverType.SELF, this.getDeltaMovement())` (26.1.2 `Player.java:1381-1402`, `LivingEntity.java:2417-2626`; 26.2 `Player.java:1402-1423`, `LivingEntity.java:2455-2679`). Thus the inherited `Entity.move` path is reachable by the local player. This establishes reachability only; travel behavior is not reviewed here.
2. **Clipped movement threshold and position integration.** 26.1.2 `Entity.move(MoverType, Vec3)`, `Entity.java:731-748`; 26.2, `Entity.java:738-755`. In both, after `movement = collide(delta)`, the same `double movementLength = movement.lengthSqr()` is tested by `movementLength > 1.0E-7 || delta.lengthSqr() - movementLength < 1.0E-7`. If admitted, the same nested fall-distance check runs, then the source position is captured, `newPosition = pos.add(movement)` is calculated, a movement record is appended with `(pos, newPosition, delta)`, and `setPos(newPosition)` is called, in that order.
3. **Position setter chain.** `setPos(Vec3)` delegates to `setPos(x,y,z)` and that calls `setPosRaw` then rebuilds the bounding box in both: 26.1.2 `Entity.java:460-467`; 26.2 `Entity.java:467-474`. `setPosRaw` has the same component inequality guard, `Vec3` write, floor-to-block/chunk bookkeeping, cache clearing, and `levelCallback.onMove()` order: 26.1.2 `Entity.java:3740-3765`; 26.2 `Entity.java:3813-3838`.
4. **Movement recording invoked by the admitted write.** The call target `addMovementThisTick` has identical queue-cap behavior (combine first two entries when size is at least 100, prepend the combined entry, then append the new record): 26.1.2 `Entity.java:856-865`; 26.2 `Entity.java:920-929`.

## Bounded verdict

No source difference found in the post-collision position-write/integration slice, its `1.0E-7` admission threshold, the `setPos`/`setPosRaw` chain, or the invoked movement-record queue procedure. The zero/small-motion gate is textually and operationally identical: a clipped displacement with squared length above the epsilon admits the write; otherwise the write is admitted when the difference between requested and clipped squared lengths is below the same epsilon. Consequently, zero clipped displacement is still admitted when that squared-length difference is under the threshold; an appreciably clipped nontrivial request can skip the write. This describes source conditions, not a measured trajectory.

## Open dependencies and next gate

`collide`, the upstream edge-backoff/input delta, collision shapes and world-border behavior remain open dependencies; they are not used to close this slice's reachability or pair coverage. The surrounding `Entity.move` remainder and full movement call graph are outside this assignment. Next gate for S-MOVE-POS is another bounded source review of its assigned neighboring slices and dependency closure, followed by the independent complete-pair coverage audit required for freeze. No implementation, tests, builds, gameplay, or runtime parity was assessed here.
