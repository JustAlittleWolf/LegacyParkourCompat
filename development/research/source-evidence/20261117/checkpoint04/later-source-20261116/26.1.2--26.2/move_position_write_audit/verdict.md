# Independent source audit verdict: S-MOVE-POS

Audited report SHA-256: `D463BA6A337952B4F2CB142A39B1BE27A398A665F46155946AFFB09A8E224780` (`build/later-source-20261116/26.1.2--26.2/move_position_write/report.md`). This verdict applies only to that immutable report revision and the exact evidence identity below.

## Verdict

**Accepted for the bounded source slice.** I independently confirmed the report's no-difference claim for the post-collision threshold and admitted position-write procedure, the `setPos` → `setPosRaw` setter chain, the invoked `movementThisTick` queue procedure, and reachability from `LocalPlayer` through inherited player movement. This is not a full-pair coverage freeze, dependency closure, implementation acceptance, or runtime parity verdict.

## Evidence identity and integrity

Both root sidecars identify the exact release with `unobfuscated` mapping, `Vineflower`, Java 25, Gradle 9.7.1, and Vineflower 1.12.0 provenance. Recomputed identities match the report:

| Release | Ready SHA-256 | Provenance SHA-256 | Source manifest SHA-256 | Artifact manifest SHA-256 | Manifest entries |
| --- | --- | --- | --- | --- | --- |
| 26.1.2 | `f24af0f4d38543c2bd9c19b50f347d4e89d6027050e4b21ae070734843946bb7` | `8d727f880cc91f4a297e148c5b75976303d051457334bc689268fbdf631a012a` | `54ae320660ca911a6d20514c965c33eb414fbedd1ad6727bbaa95fe906aa23c0` | `89f648229de83b0109460695c9f1f3bed7ef36efb84ede9224e80cce87118f92` | 6,882 sources / 78 artifacts; ready declares both counts |
| 26.2 | `f9406adb6bf7cb4c1ab0792a798ab2cb90e082ad4c2eee032c9f674d0ea8070f` | `a328f5333a1b27aadde4422b23159a323ae219ccd26f11207c1a5461366f27d1` | `a7ad74fc712567eb87136afc1eafcfd602b4088e9869f3596331fd34faefe894` | `ba9dc53a41bca9744ec1c8ab2ce9363764a22333c39ef64d5105f4e3bf5c98ba` | 7,055 sources / 91 artifacts; ready has no artifact-count field |

I rehashed all six listed relevant source files on both sides and each matched its entry in the root source manifest: `Entity.java`, `Player.java`, `Avatar.java`, `LivingEntity.java`, `AbstractClientPlayer.java`, and `LocalPlayer.java`. The source manifest counts and hashes, artifact manifest hashes, and declared 26.1.2 artifact count also match the ready markers. Provenance records 26.1.2's full Gradle log and 26.2's summary-excerpt-only log.

## Independent comparison

- `Entity.move`: 26.1.2 `Entity.java:731-748`; 26.2 `Entity.java:738-755`. In each, after `movement = this.collide(delta)`, the admission condition is exactly `movement.lengthSqr() > 1.0E-7 || delta.lengthSqr() - movement.lengthSqr() < 1.0E-7` (using the local `movementLength` for the first term). The same fall-distance guard is inside it. For an admitted write, both compute `pos`, then `newPosition = pos.add(movement)`, append `new Entity.Movement(pos, newPosition, delta)`, then call `setPos(newPosition)`, in that order.
- `setPos`: 26.1.2 `Entity.java:460-467`; 26.2 `Entity.java:467-474`. Both overloads forward `Vec3` components, then the scalar overload calls `setPosRaw` before rebuilding the bounding box.
- `setPosRaw`: 26.1.2 `Entity.java:3740-3765`; 26.2 `Entity.java:3813-3838`. The component inequality guard, `Vec3` assignment, `Mth.floor` block-position calculation and updates, cache clear, chunk-position update, and `levelCallback.onMove()` occur in the same order; following guarded server waypoint updates are also the same.
- Movement queue: 26.1.2 `Entity.java:856-865`; 26.2 `Entity.java:920-929`. At size `>= 100`, both remove the first two records, prepend a combined `(first.from(), second.to())` record, then append the incoming record.
- Player path: on both endpoints `LocalPlayer extends AbstractClientPlayer`, `AbstractClientPlayer extends Player`, `Player extends Avatar`, and `Avatar extends LivingEntity`. `Avatar` declares no `travel` method. Each `Player.travel` forwards to `super.travel(input)` for passenger, flying and ordinary branches (26.1.2 `Player.java:1381-1402`; 26.2 `Player.java:1402-1423`). `LivingEntity`'s travel branches call `this.move(MoverType.SELF, this.getDeltaMovement())` (26.1.2 `LivingEntity.java:2417-2626`; 26.2 `LivingEntity.java:2455-2679`). Thus the inherited `Entity.move` path is reachable from the local player; this does not review the surrounding travel behavior.

The strict comparison and squared-length arithmetic are preserved. The unchanged gate admits zero clipped displacement when the requested-minus-clipped squared-length difference is below epsilon, and admits nontrivial clipped displacement only when its squared length is above epsilon. This is a source-level condition, not a trajectory measurement.

## Limits

`collide`, edge backoff, collision shapes, world-border behavior, the remainder of `Entity.move`, and the full movement call graph remain open dependencies or outside this bounded assignment. This verdict does not establish complete pair coverage or runtime parity.
