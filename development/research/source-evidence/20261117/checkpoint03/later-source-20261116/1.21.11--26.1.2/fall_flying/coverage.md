# 1.21.11 → 26.1.2: fall-flying source slice

Status: `NO_DIFF` for the bounded fall-flying start/stop and ordinary local-player travel slice examined below. This is an endpoint comparison, not a release-pair coverage freeze or cutoff statement. No intermediate releases were examined.

## Exact source identities

| Endpoint | Canonical root | Ready marker SHA-256 | Provenance SHA-256 | Source manifest SHA-256 | Client artifact SHA-256 |
|---|---|---|---|---|---|
| 1.21.11 Mojmap | `decompiled_minecraft/1.21.11/mojmap` | `0AD98D0EBD654650492C97EB58BC324D33864787F0CE10D99FAEC3AE2F5B804B` | `99C9FB741EB5E4AC8FB5780E2158DA324D7C8B6AFC179D494E5986F78FE29F72` | `0c4d83fef84c101d9db88f331acec7960f3c8346228c28995e6cf57bbfcae555` | `1473c9489ac50fda3c435049a76a70d61a10b8610db27f5ba9d8756b686cd3bd` |
| 26.1.2 unobfuscated | `decompiled_minecraft/26.1.2/unobfuscated` | `F24AF0F4D38543C2BD9C19B50F347D4E89D6027050E4B21AE070734843946BB7` | `8D727F880CC91F4A297E148C5B75976303D051457334BC689268FBDF631A012A` | `54ae320660ca911a6d20514c965c33eb414fbedd1ad6727bbaa95fe906aa23c0` | `b1b3158572666445eff01e82fad8c7de2e4953db6d354f311730d77a8359d0b0` |

Ready markers identify `versionId` and namespace and bind the listed source and artifact manifest digests. Corresponding provenance records identify exact decompile runs and endpoint versions. Both roots contain the relevant Java files; identity was not inferred from marker absence.

## Compared source and evidence

All paths below are canonical relative to the repository root. Line numbers are one-based decompiler source anchors.

| Source area | 1.21.11 anchor | 26.1.2 anchor | 1.21.11 file SHA-256 | 26.1.2 file SHA-256 |
|---|---:|---:|---|---|
| `net/minecraft/client/player/LocalPlayer.java` input gate and post-check flying-state sample | 815–819 | 854–858 | `948E94F8E874B2F72E9A689E6E3BA1933F5728EC381D64F3BB5E2388718BD477` | `433FD995AD317AF0F6EF0E50C1E8E3483CB8F00E0E327D4EDF27A4DD99666EBE` |
| `net/minecraft/world/entity/player/Player.java` flight override and start eligibility/state write | 1386–1387, 1441–1451 | 1407–1408, 1442–1452 | `8E97167350A91741D0AA10D3B0D92A33150ED6DCCDC94CD5B37D9C7CA22BCC81` | `44CF28E0C64E78D39FD13368E9991381DBEBAB67029070CB9DDC43F09D45D14D` |
| `net/minecraft/world/entity/LivingEntity.java` travel dispatch and fall-flying response | 2309–2316, 2441–2495 | 2392–2399, 2528–2584 | `19ED2D565858C401C69A06750B054A633A20EA864CAB3A753B5C767F0BDD60E8` | `C3B64DE8DBABA8AD8A7CCD4F33255346D8206E66F12CA91260971BF5E5AC93BD` |
| `net/minecraft/world/entity/LivingEntity.java` equipment eligibility and shared-flag query | 3049–3060, 3486–3487, 3835–3842 | 3148–3159, 3597–3598, 3946–3953 | same `LivingEntity.java` hashes above | same `LivingEntity.java` hashes above |

## Comparison result

* LocalPlayer checks a held jump input together with the creative-flight toggle edge, prior jump edge, climbable state, and `tryToStartFallFlying()`. On success it sends the start command; it then samples `isFallFlying()`. Both endpoints use the same gate, order, call, state sample, and subsequent ordinary water-input branch. Variable names and formatting differ.
* Player eligibility at both endpoints is `!isFallFlying() && canGlide() && !isInWater()`. Success sets shared entity flag 7; failure returns false. The Player override blocks gliding while abilities have creative flight enabled.
* LivingEntity `canGlide()` at both endpoints requires off-ground, not passenger, no levitation, and a qualifying equipped item in some `EquipmentSlot.VALUES` slot. `canGlideUsing` checks `GLIDER`, a non-null `EQUIPPABLE`, matching item/slot, and `!nextDamageWillBreak()`. These method predicates and ordering are unchanged. This records the item-component dependency; it does not establish which concrete items or datapack equipment values exist in every runtime/resource set.
* `travel` checks fluid travel before the fall-flying flag and falls back to air travel otherwise. Fluid travel itself is outside this slice. `travelFallFlying` uses ordinary air travel then clears shared flag 7 when climbable; otherwise it snapshots current movement/horizontal speed, computes the fall-flying vector, writes it as delta movement, moves SELF, and only on the server evaluates the collision response.
* The fall-flying vector math matches operation-for-operation: look-angle horizontal magnitude; pitch to radians using the same float cast; horizontal movement length and effective gravity; squared cosine lift; gravity update; downward-look lift correction; negative-pitch dive correction; 0.1 horizontal alignment; final component multipliers `.99F, .98F, .99F`. Variable names/layout changed. Collision speed-loss float cast and threshold are likewise the same; damage application is excluded from movement acceptance.
* `stopFallFlying()` at both endpoints toggles shared flag 7 true then false. `isFallFlying()` reads shared flag 7. Start/stop state calls and query are equivalent.

## Boundaries and exclusions

This result does not claim complete 1.21.11→26.1.2 pair coverage or an intervening change cutoff. It does not inspect resource payloads or establish concrete glider equipment contents; item/component/equipment data is an explicit external dependency. Packet serialization is not compared (only the local input condition and local state mutation are in this slice). Server-only Elytra durability/event work, collision damage effects, fluid movement, ordinary ground/air input implementation, the previously assigned flying-climbable predicate, vehicles, and any visual/sound callback behavior are not accepted as differences here. No implementation or runtime claim follows from this source comparison.
