# 1.21.1–1.21.2 active Elytra glide at a dry climbable block

## Bounded verdict

For an ordinary on-foot local client player whose shared fall-flying flag is already true, with an enabled/default chest Elytra, no fluid, and feet in a vanilla climbable block such as a ladder or vine, neither version checks climbability and exits before the first glide move. Both route to fall-flying travel before the air/climbable movement path. The movement cap `checkSlowFallDistance()` still occurs before glide velocity calculation and the first glide move in this dry case. No separate player-movement delta is established for this bounded path.

The source structure does change: 1.21.1 calls `updateFallFlying()` unconditionally in the pre-travel tick section and caps distance inside the selected fall-flying travel branch. 1.21.2 calls `updateFallFlying()` only when the fall-flying flag is already true, before `travel`; the method calls the same cap first. This means 1.21.2 also caps before fluid-vs-glide routing when the flag is active. That wet/environmental case is outside this assignment. With the stated dry precondition, both cap before the first glide move.

## Exact-source evidence

**1.21.1, Mojmap:** `decompiled_minecraft/1.21.1/mojmap/net/minecraft/world/entity/LivingEntity.java`.
- `onClimbable()` 1517–1533 reads the current block state and recognizes `BlockTags.CLIMBABLE` or an eligible trapdoor.
- `travel(Vec3)` 2091–2216 gates on `isControlledByLocalInstance()`. The fluid branches precede `else if (this.isFallFlying())` (2151); that branch calls `checkSlowFallDistance()` (2152), computes the glide velocity and first calls `move(MoverType.SELF, ...)` at 2177. The ordinary-air branch follows at 2193 and uses `handleRelativeFrictionAndCalculateMovement`, whose climbable handling is not reached for an active glide branch.
- Tick travel setup calls `updateFallFlying()` at 2668 before `travelRidden` / `travel`.
- `updateFallFlying()` 2716–2740 computes its local candidate from existing flag, `!onGround`, `!isPassenger`, `!LEVITATION`, and chest-slot Elytra with `ElytraItem.isFlyEnabled`; it writes the shared flag only off-client. Thus these predicates do not clear the local client's already-set flag.
- `Entity.checkSlowFallDistance()` at 2386–2390 caps `fallDistance` to `1.0F` iff current vertical delta is greater than `-0.5` and distance exceeds `1.0F`.

**1.21.2, Mojmap:** `decompiled_minecraft/1.21.2/mojmap/net/minecraft/world/entity/LivingEntity.java`.
- `onClimbable()` 1561–1577 has the same climbable tag/trapdoor predicate.
- `travel(Vec3)` 2178–2187 gates on `isControlledByLocalInstance()`, routes non-standable water/lava to `travelInFluid`, then routes `isFallFlying()` to `travelFallFlying()`, then ordinary movement to `travelInAir()`.
- `travelFallFlying()` at 2268–2276 applies glide movement; its first `move(MoverType.SELF, ...)` is line 2272. No `onClimbable()` check precedes it. The on-climbable path is in ordinary air handling, which the active glide branch bypasses.
- Tick setup calls `updateFallFlying()` only under `if (this.isFallFlying())` at 2758–2759, before `travelRidden` / `travel`.
- `updateFallFlying()` 2814–2834 calls `checkSlowFallDistance()` first. It evaluates new `canGlide()` eligibility and may clear the shared flag only inside `if (!this.level().isClientSide)`. `canGlide()` 2834–2845 requires not grounded, not passenger and no levitation, plus some equipment slot satisfying `canGlideUsing`; that predicate at 3615–3621 requires the GLIDER component, matching equippable slot, and that the next damage will not break it. Therefore the local client does not run the clear predicate's effect while retaining an already-set flag.
- `Entity.checkSlowFallDistance()` at 2484–2488 has the same `y > -0.5`, `fallDistance > 1.0F`, set-to-`1.0F` condition.

## Boundaries

- **Start eligibility:** not compared as a new-start behavior. The assignment begins with the fall-flying flag already true and a working default chest Elytra. The server-side eligibility implementations differ (1.21.1 chest Elytra / `isFlyEnabled`; 1.21.2 component + matching equipment slot + durability predicate), but neither update method applies its flag-clear write on the local client.
- **Final tick:** the later ground/collision termination and server synchronization are not part of the pre-first-move verdict. In 1.21.1 the travel branch clears flag 7 on ground only server-side (2188–2189); 1.21.2's server-side `canGlide()` clear is also a separate termination gate.
- **A/B environmental/contact internals:** fluids, collisions, damage, block effects, and movement after the first glide move are excluded. Their branch placement may change which checks run in other states; no such behavior is inferred for the stated dry path.

## Provenance and integrity

Source roots are the local exact-version Mojmap decompilations under `decompiled_minecraft/`; no online mappings, product implementations, Git history, tests, builds, runtime launches or source generation were used.

- **1.21.1:** ready marker names exact version `1.21.1`, mapping `mojmap`, client SHA-256 `499f6897d1837516680f3114072d8106e11c9adcd933fe5cf051b551089b0c99`, and source-manifest SHA-256 `900f956e00f6fc1300bb3d689ea49df2b1a57bcaa54617344ef456d95d47cb48`. Provenance records the exact run's `--versions=1.21.1,1.21.3,1.21.4,1.21.5 --mappings=mojmap` batch. Target source hashes match `mojmap.sources.sha256`.
- **1.21.2:** source provenance pins the exact release metadata URL `https://piston-meta.mojang.com/v1/packages/c14db17c663edf3d7a698488263a21b1df6d02af/1.21.2.json` (SHA-256 `793e3fd0451354a26aaa00253dcb6600add11f9d2a90f7bdef75ddf5b38c2b45`) and client URL ending `c7ac2d0d86f4ca416cab9064ff8a281852ad0c7b/client.jar` (SHA-256 `613091da0f62426ae8dcf9afb30eb639485f493b81cda3a2b96b1fb8a6b9976c`). The local source tree is `decompiled_minecraft/1.21.2/mojmap`; source hashes below match its `.decompile-provenance.json` `sources` entries.

| Exact local file | SHA-256 |
| --- | --- |
| `1.21.1/mojmap/net/minecraft/world/entity/LivingEntity.java` | `324a3eee8496caab57cfaf5101ef576f1ae3c60c40e3857e96f35f3af9a3a0d8` |
| `1.21.1/mojmap/net/minecraft/world/entity/Entity.java` | `b81905c7879e2cc5c5063a41d865c4164ad919f156306705be791d1017b99850` |
| `1.21.1/mojmap.sources.sha256` | `900f956e00f6fc1300bb3d689ea49df2b1a57bcaa54617344ef456d95d47cb48` |
| `1.21.1/mojmap.provenance.json` | `6f84a596ea944a1d40c79b53ead9d2cdd2b3c726d710a977758d15b5578ce7a9` |
| `1.21.2/mojmap/net/minecraft/world/entity/LivingEntity.java` | `087390495f4fdfd14b9e12230e7aea4fdc50913bb4d1d91119e72892881cfc52` |
| `1.21.2/mojmap/net/minecraft/world/entity/Entity.java` | `a93719c302a0381a972af75ea360465e2e3551708dd07c34d4d40b7e5173c2b9` |
| `1.21.2/mojmap/.decompile-provenance.json` | `00f399407dc447737ea83e3e46562418f8c712f66e3f51cb85d46afa2ecac575` |

Integrity note: every listed source and provenance SHA-256 was verified against local bytes; the 1.21.1 LivingEntity and Entity entries also match `mojmap.sources.sha256`, and the 1.21.2 LivingEntity and Entity entries match `.decompile-provenance.json` source entries.