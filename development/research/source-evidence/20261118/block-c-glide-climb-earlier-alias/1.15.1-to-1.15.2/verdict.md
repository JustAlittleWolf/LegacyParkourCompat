# Independent source audit: 1.15.1 → 1.15.2 Elytra/climbable gate

## Bound input

Audited `../c-glide-climb-1151-1152-alias-gate/report.md`, SHA-256 `c5c6ac94828bf3a6da124832ef617fe187cf9a57fe3fa3bd24ba1c36830a9ef1` (exact bytes). This verdict applies only to that report revision and the two canonical Mojmap source trees identified below.

## Verdict

**Reject the report's no-difference verdict as written.** The already-active Elytra travel subcase is unchanged, but the report also asserts that the local start gate is unchanged and that it does not reject climbable state. Both assertions are false: in 1.15.2, `LocalPlayer.tick` adds `!this.onLadder()` to the Elytra start condition. A jumping local player on a ladder, vine, or other state for which `onLadder()` is true can start fall-flying through this input path in 1.15.1, but cannot start it through this path in 1.15.2. The separate `Player.tryToStartFallFlying()` predicate remains unchanged and has no climbable test; the new rejection occurs before it is called.

For the narrower precondition “already fall-flying at travel entry,” the report's central travel observation is supported: both releases use the Elytra branch, skip `handleOnClimbable`, test the incoming `Vec3.y` as a `double` with strict `> -0.5`, write exactly `1.0F` before look-angle math and `move`, and only clear the shared flag after movement when `onGround && !level.isClientSide`. Thus the active-flight path has no ladder/vine-specific cleanup. This does not rescue the report's broader no-difference claim because its own call-chain assertions include the differing start gate.

## Source trace

- `net/minecraft/client/player/LocalPlayer.java`: 1.15.1 lines 715–720 checks jump, abilities, passenger state, chest Elytra and `ElytraItem.isFlyEnabled`, then calls `tryToStartFallFlying`. At the corresponding 1.15.2 lines 715–720, `&& !this.onLadder()` is added before chest-slot lookup. This is the confirmed adjacent-release difference.
- `net/minecraft/world/entity/LivingEntity.java`: both releases' `onLadder()` at lines 1226–1238 recognizes ladder/vine (and the listed other climbables); the method is unchanged. In `travel` lines 1831–1846, the local-controlled path is eligible and the active Elytra branch reads current delta movement and performs the strict threshold / `1.0F` write. In the following lines 1848–1871 it computes Elytra motion and moves; the later on-ground clear is at 1882–1884. `handleOnClimbable` is called only in the `else` ordinary movement branch at 1890, after the active-flight branch has been excluded.
- Both releases call `updateFallFlying()` immediately before `travel()` in `aiStep` (lines 2282–2287). Its lines 2299–2317 validate the shared flag, ground/passenger state and chest Elytra; client-side execution does not write the validated flag back because `setSharedFlag` is guarded by `!level.isClientSide`. Thus it does not undo the local start-gate change or add client climbable rejection for an already-active flag. The `onGround && !level.isClientSide` clear after Elytra movement is likewise server-only. `LocalPlayer` snapshots `wasFallFlying` after the input block at line 722 in both releases.
- For the bounded external-motion check, both releases have the same `LivingEntity.knockback` body (lines 1195–1201), `LivingEntity.push(Entity)` body (1691–1695), and `Entity.push(double,double,double)` / `lerpMotion` bodies (1189–1192 and 1733–1735). Those paths supply/change delta movement; active Elytra travel consumes the current delta movement in the same order in both source trees. No adjacent difference was found in these bounded external-motion paths.

The claim that a chest Elytra can start from climbable feet applies only when the other gates hold (including jumping and the on-foot/airborne/water/durability checks). This is a source-level reachability conclusion; no trajectory beyond the described writes and calls is claimed.

## Canonical evidence identity and integrity

The canonical paths are `decompiled_minecraft/<version>/mojmap/` in the primary checkout. Both ready markers say `status: ready`, exact `versionId` and `versionMetadataId`, mapping `mojmap`, decompiler Vineflower, Java runtime 25. The per-tree `mojmap.sources.sha256` hashes match both ready-marker declarations and the actual manifest bytes. The artifact-manifest hashes likewise match the ready markers and actual bytes.

| Identity | 1.15.1 | 1.15.2 |
|---|---|---|
| Provenance file SHA-256 | `6a7af1f5d65d7af165c2ab9a90c1fae20169b38e70c8e92dbc5f59d94efe9461` | `d4802d35ee2927a44d753871f184c3255c060eb94457a5c65a8bc121a087954e` |
| Provenance batch / exact metadata identity | `1.15,1.15.1`; requested/version metadata ID `1.15.1`; metadata SHA-256 `dda0859bca7447ff5f071643006b35a51f54e2fbef851a6665b424cf5743c598` | `1.15.2,1.16.5,1.17.1,1.18.2`; provenance names exact `versionId: 1.15.2` and `namespace: mojmap`; ready marker confirms metadata ID `1.15.2` |
| Raw client SHA-256 | `6e28ecd008d0ca5e1dcd346dbc2882430f02a77a78864d57acc49fc8bc1b5358` | `4a73008a73f3824b7c711750a5a37556df8614f193c0a531e292dad159a73a7c` |
| Source-manifest SHA-256 | `c8fdce2f9432b72fc13c19a48f9ab3be203cac41a617139e81bac3552245912d` | `cb7fd93f8730d8b2813857744f8572060535c7de44fcee86222acd1ceb6f43e7` |
| Artifact-manifest SHA-256 | `d2c40f0770820752fe879670b35bd718b87e4f14491b3ec782e2e74df6b38bbb` | `208ab867640097a0c188e452de4876934deb217d75ac726d358cfa6730260406` |

The 1.15.2 provenance file is the older compact schema and does not contain per-version raw-input hashes or manifest hashes; its ready marker supplies exact metadata/client identity and both manifest hashes, which were independently matched to the present files. This limitation does not affect the compared source bytes. For each release, the source-manifest rows match the actual SHA-256 of `LocalPlayer.java`, `Entity.java`, `LivingEntity.java`, `Player.java`, and `ElytraItem.java`. The exact file hashes are:

| Relative source file | 1.15.1 SHA-256 | 1.15.2 SHA-256 |
|---|---|---|
| `net/minecraft/client/player/LocalPlayer.java` | `7227da5233e3c72ced008187b92a6a86842f84778eaa9f40fa1eb117286af823` | `3a9019bd7b860e251c23fd8d0cd70b7f5b38566d34470c4e29b1014ef689ccbd` |
| `net/minecraft/world/entity/Entity.java` | `191b3ad3e7348c9bac1e703fff896706d23a751bf15162aacf676f5f97c0a10e` | `191b3ad3e7348c9bac1e703fff896706d23a751bf15162aacf676f5f97c0a10e` |
| `net/minecraft/world/entity/LivingEntity.java` | `139c3866ad2c6a1ad73496f22ff1a4b5259998442092ffbafb8664428e28bf79` | `46d243bb7e51f7b54404aa1d7d6e6b827682d0f5925d02847c4193306c4d5e54` |
| `net/minecraft/world/entity/player/Player.java` | `6985e11ad9be553e9a2f25e5decc20a9da5c3b59b68f4e83a5b9f448cd2e48a0` | `1ba2724c22163862b8f7fdfdea5a04a66e4db26a119d7e5360ba024724a34793` |
| `net/minecraft/world/item/ElytraItem.java` | `701e25f13e9ba8db74aca774f404a5281b2a58f4f7dc72e3aeabac76bbf28b43` | `701e25f13e9ba8db74aca774f404a5281b2a58f4f7dc72e3aeabac76bbf28b43` |

## Audit boundary

This is a bounded audit of the Elytra start/active-flight/climbable interaction, shared-flag validation and cleanup, and the named external delta-motion paths. It does not close full-pair tick coverage, establish other Elytra or climbable behavior, or prove runtime parity. The report must be revised to separate the unchanged already-active travel case from the changed 1.15.2 local start gate before its verdict can be accepted.
