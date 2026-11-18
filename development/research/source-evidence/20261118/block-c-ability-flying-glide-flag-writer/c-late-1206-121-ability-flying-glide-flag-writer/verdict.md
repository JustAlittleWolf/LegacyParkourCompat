# Independent source audit verdict

## Verdict

**ACCEPT — bounded claim and overlap reachability verified.** The compared `Player.travel(Vec3)` creative-flight wrapper is unchanged from 1.20.6 to initial 1.21. For an ordinary local player with `mayfly`, an already-live valid Elytra flag, creative flight toggled on, and conditions that reach the Elytra travel branch, displacement occurs inside `super.travel(...)` before the wrapper directly clears shared flag 7. This verdict does not freeze the whole pair or establish runtime parity.

The report's reachability account is sound. To make its preconditions fully explicit, the enclosing `LivingEntity.travel` branch is under `isControlledByLocalInstance()` and gives applicable water and lava movement branches precedence over fall-flying. `LocalPlayer.isEffectiveAi()` returns true in both versions, satisfying the local-control gate; an ordinary dry-air glide reaches the Elytra branch. These are applicability conditions, not a source difference in the audited wrapper.

## Independent source check

In both versions, `LocalPlayer.aiStep` toggles `abilities.flying` under `mayfly` on the double-jump path without checking `isFallFlying()` (1.20.6 `LocalPlayer.java:720–749`; 1.21 `LocalPlayer.java:717–745`). The same method then calls `super.aiStep()` (1.20.6 line 807; 1.21 line 804). `Player.aiStep` calls `super.aiStep()` (1.20.6 `Player.java:515–533`; 1.21 `Player.java:513–535`), after which `LivingEntity.aiStep` calls `updateFallFlying()` and then virtual `this.travel(...)` (1.20.6 `LivingEntity.java:2673–2684`; 1.21 `LivingEntity.java:2668–2679`).

`updateFallFlying()` preserves an already-live flag only while airborne, not a passenger, without levitation, and wearing an enabled Elytra. It writes shared flag 7 only on `!level().isClientSide` (1.20.6 `LivingEntity.java:2721–2745`; 1.21 `LivingEntity.java:2716–2740`), so the valid local-client flag is not cleared by that updater before travel.

Within `Player.travel`, both versions gate the wrapper on `abilities.flying && !isPassenger()`, save the entry vertical velocity, invoke `super.travel`, then restore the resultant x/z with saved y multiplied by `0.6`, call `resetFallDistance()`, and directly call `setSharedFlag(7, false)` (1.20.6 `Player.java:1473–1495`; 1.21 `Player.java:1456–1478`). The eligible `LivingEntity.travel` Elytra branch sets the gliding motion and calls `move(MoverType.SELF, ...)` before it returns (1.20.6 `LivingEntity.java:2168–2207`; 1.21 `LivingEntity.java:2151–2190`). Its own on-ground flag clear is separately gated by `onGround() && !level().isClientSide`; on the client it does not supersede the later unconditional wrapper clear. Thus the displacement in this travel call precedes the wrapper's direct flag clear.

The vertical restoration and fall-distance reset are separate ordered writes; neither is being accepted as a movement difference or as a separate finding here. This audit did not inspect or rely on separate A/B reports.

## Evidence identity and limits

The audited report is frozen at SHA-256 `6d61b9784d85c0601cf7f5b32f8ea0df2969648be0177ecea79ecb67128832f0`.

- **1.20.6 Mojmap:** `mojmap.ready.json` SHA-256 `1ec1dc773ea2a8a3ef48a9fc868893bd2f83d6b13b057d75b151b711788c3d41`; it declares exact version ID `1.20.6`, mapping `mojmap`, and client SHA-256 `02dfd345ac1ad55692d5dbc8486ac7e4fea72cd54ac494a79cd48963048e56b2`. Source-manifest SHA-256 `56aae10684471d7abb1c366bd5dd431ab976112a87e6cc5c687a68f1eff06311`; artifact-manifest SHA-256 `e5882622bcf22b3e3c2c96c73843a1308e11e3096f4feefd945ea5808933ce31`. The actual `LocalPlayer.java`, `Player.java`, `LivingEntity.java`, and additional `Entity.java` hashes match their manifest entries. As the report discloses, the historical campaign cache path is absent here, so this audit did not independently rehash the old 1.20.6 raw input files.
- **Initial 1.21 Mojmap:** legacy `.decompile-provenance.json` SHA-256 `90246f277aefd2350e42e17245faa51928a949c8db4427f3be04a732527deb7c`; it identifies the 1.21 raw client, Mojmap mapping input, and version metadata. Their present cache SHA-256 values independently match the provenance: client `4b56bc9b44de7e88f9fbb52e716c18240da34469fcbafa5ed2e75b04d60617e8`, mapping `127485d79248d4ae2955a7d061c3626d38853ecb054c4d008707732264b0aac1`, metadata `cdd1a44db7b5e8787723c1f6c7c7dc3a4638d96a31ddc84aeb27d3f30efa802a`. The actual four cited Java source hashes match the legacy provenance. There is no ready marker or separate source/artifact manifest for this tree; the report identifies it accurately as legacy provenance.

Actual source SHA-256 values (all match their source manifest/provenance entries):

| Relative source | 1.20.6 | 1.21 |
| --- | --- | --- |
| `net/minecraft/client/player/LocalPlayer.java` | `6b429dfa6e0681251ec985dda1627f808652a7bbe5b70dc85c8fa0fe0ed46ffa` | `c555e68ac3c63ab9b4f9a9e31933e263b96350a2bc599d11a0de5928bc24b583` |
| `net/minecraft/world/entity/player/Player.java` | `785d93ccc94e1f912e545b2b0c355edeb352b44ee8e83a69364daec35266dbe2` | `ed32b88c3c7c8418b83db41823520f2b6b0b49a98610306ef26e9681dc925c71` |
| `net/minecraft/world/entity/LivingEntity.java` | `c66ec8dc3b1856e490e5834a46185589030d9fbc411e3e2ce64c73203cd753b2` | `324a3eee8496caab57cfaf5101ef576f1ae3c60c40e3857e96f35f3af9a3a0d8` |
| `net/minecraft/world/entity/Entity.java` (enclosing local-control gate) | `71cd6b9f6c002684154dce11d3745e8714d82f13c8e1b3aa56743930131c18f3` | `b81905c7879e2cc5c5063a41d865c4164ad919f156306705be791d1017b99850` |

The review was static and read-only. No wiki, product implementation, Git history, network, build, tests, runtime launch, or source regeneration was used. The verdict applies only to the report's bounded operation and the stated reachable local overlap.
