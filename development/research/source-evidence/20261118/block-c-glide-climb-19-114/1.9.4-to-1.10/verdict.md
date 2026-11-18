# Independent source audit — C glide/climb gate

## Verdict

**PASS: the supplied bounded finding is independently corroborated as “no difference” for the stated state and operation.** The reported gate, ordering, literal, strict comparison, and skipped climb cleanup match in 1.9.4 and 1.10. This is not a full-pair coverage verdict.

Frozen input report: `c-glide-climb-194-110-alias-gate/report.md`  
Full SHA-256: `4D28790A0B2907BC9C6F58F61CDBC0648FB0844739E36DFEC03EF48491A66E47`

## Exact source identity

Host and shell checked: Windows 11, PowerShell. The release pair is **1.9.4 → 1.10** (not 1.19.4). Both trees use the `ornithe-feather` namespace and the package paths below.

- **1.9.4:** `decompiled_minecraft/1.9.4/ornithe-feather/`; ready marker `decompiled_minecraft/1.9.4/ornithe-feather.ready.json` names `versionId=1.9.4`, `namespace=ornithe-feather`, Feather Gen2 build.2 merged JAR and Tiny artifact, and client SHA-256 `23e90103a1ca2ac71100004c6d5846de09f85695f579843ef8da41571e60c908`. The artifact manifest records version metadata `4b21379203f0d87df8cc2ab4dd3c3689f139772f1cdb37b40688252547e2d5be`, Feather JAR `49a38d0adfbda1749e519c29844116e9f22e895cb505633261b7f587268f4125`, and Tiny `9e21708d4bc32a43ac404735ea3238465889797110204a0375bcb069a3798027`. Source-manifest SHA-256 is `c7b508fe01634887b65919dcd3a900c311a21d9510a1f1ab248d5c17c528ab19`.
- **1.10:** `decompiled_minecraft/1.10/ornithe-feather/`; `.decompile-provenance.json` pins the exact 1.10 client (`c00f8170ebf4fd834f552246743a64885a6dffb3dd1e3a3f3dba9676b7a0af35`), Feather metadata (`a1eb1ccff25e2b76fa1d08e9b515508e97ac4837fbcacbd7f77b3a01026d3950`), Feather Gen2 build.2 merged JAR (`ad7cf6744d81d0260c9d97ddfe8250b2fdbd54fe332d4d00266b3545d8ddf8dd`), and release metadata (`462441ee38c35c6ad52ffe2ab668b217eb2b29b51de25f1774a454560bc3a261`).

Current source-file SHA-256 values match the respective provenance/source-manifest entries:

| Source file | 1.9.4 SHA-256 | 1.10 SHA-256 |
| --- | --- | --- |
| `net/minecraft/entity/living/LivingEntity.java` | `bbb7703f18fd5da05c4e4a43a77ea644b388e63c01d34166d308ea52054be4e5` | `d40dd476b6b68c6ce45b4202823475deb546ecda2284da330ff6724b33815e82` |
| `net/minecraft/entity/living/player/PlayerEntity.java` | `d658a0d95452d12bb7e347bfd802240eeecaf7f938e10dcd43e2640434387f85` | `db7bbbc15e2bcb0de18476fec1b88e73eccf8ae63b170276b1d8327dd0be796b` |
| `net/minecraft/client/entity/living/player/LocalClientPlayerEntity.java` | `8aaf711948b7602c2e6c015a37e36ed06073d39727d999d80480b4910b704f5d` | `a9637065f21ad67464eb5c204c74ebf228c3bb0da8a96ddf4ae73c0490fed443` |

## Independent trace

For the supplied dry local player with flag 7 already active and located at ladder/vine feet:

- Travel reaches `LivingEntity.moveRelative` after its speed multipliers and `flyingTick`: 1.9.4 `LivingEntity.java:1706–1711`; 1.10 `:1742–1747`. The local-player override returns `true` for `isLocallyControlled`: 1.9.4 `LocalClientPlayerEntity.java:446–449`; 1.10 `:457–460`. `PlayerEntity.moveRelative` takes its special branch only for `abilities.flying && !isRiding()`, otherwise delegates to `super`: 1.9.4 `PlayerEntity.java:1372–1387`; 1.10 `:1391–1406`. Thus the supplied ordinary state reaches the `LivingEntity` body.
- The outer local-control/logical-update gate is present in both: 1.9.4 `LivingEntity.java:1302–1305`; 1.10 `:1332–1335`. Dry water/lava guards pass. `isFallFlying()` returns tracked flag 7: 1.9.4 `LivingEntity.java:2052–2054`; 1.10 `:2088–2090`. Chest Elytra is not read by this movement branch. The local input path merely requests activation when its input/falling/usable-Elytra preconditions pass: 1.9.4 `LocalClientPlayerEntity.java:707–711`; 1.10 `:730–734`.
- With flag 7 true, `moveRelative` enters the glide `if` before the `else` containing climb cleanup. Both releases perform `if (velocityY > -0.5) fallDistance = 1.0F;` before look-vector work and the `move(...)` call: 1.9.4 `LivingEntity.java:1306–1341`; 1.10 `:1336–1371`. The comparison is strict and the assigned value is float `1.0F`; exactly `-0.5` does not take that write.
- The ladder/vine predicate is the same vanilla-state check at `floor(x), floor(getShape().minY), floor(z)`, returning false for spectators and true for ladder/vine: 1.9.4 `LivingEntity.java:905–917`; 1.10 `:928–940`. It is not reached by this call's climb branch while flag 7 selects glide. Ordinary non-gliding climb cleanup (horizontal clamp, fall-distance zero, downward cap/sneak stop, and post-move horizontal-collision boost) is in 1.9.4 `LivingEntity.java:1376–1394` and 1.10 `:1406–1424`.

## State caveat and boundary

The `1.0F` write is pre-move, not a guaranteed end-of-tick value. The immediately called inherited `Entity.move` can call `checkFallDamage`: a landing resets `fallDistance` to zero, while downward displacement adds to it. Relevant code is 1.9.4 `Entity.java:441`, `:645`, and `:795–804`; 1.10 `Entity.java:446`, `:650`, and `:808–817`. The glide branch itself has no subsequent ladder/vine cleanup. Its post-move fall-flying flag clear is guarded by `!world.isClient` (1.9.4 `LivingEntity.java:1352–1354`; 1.10 `:1382–1384`), so it does not clear the flag on this client path.

No source discrepancy was found for this bounded comparison. No claim is made about other movement operations, startup/final state, A/B outcomes, the complete pair inventory, or an intermediate patch boundary.

