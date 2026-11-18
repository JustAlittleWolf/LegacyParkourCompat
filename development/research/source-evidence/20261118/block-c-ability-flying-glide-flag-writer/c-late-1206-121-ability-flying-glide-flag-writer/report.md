# 1.20.6 → 1.21: creative-flight / Elytra flag overlap

## Bounded result

The compared `Player.travel(Vec3)` operation is unchanged between exact local Mojmap decompilations for 1.20.6 and initial 1.21. With `abilities.flying && !isPassenger()`, both versions save the entry vertical velocity, invoke `super.travel`, restore vertical velocity as the saved value times `0.6`, call `resetFallDistance()`, then directly clear shared flag 7. The direct flag clear is after the `super.travel` displacement. This is no newly introduced 1.21 source difference in this bounded operation.

An ordinary reachable client overlap exists: `LocalPlayer.aiStep` can toggle `Abilities.flying` for a player with `mayfly` on a double-jump input without checking `isFallFlying()`. If flag 7 was already live, the client-side `LivingEntity.updateFallFlying()` leaves that flag intact (the method only writes it under `!level().isClientSide`); with a valid Elytra and airborne/non-passenger state, its next normal `LivingEntity.aiStep` reaches virtual `this.travel(...)`. `Player.travel` then takes the creative-flight wrapper while `LivingEntity.travel` still dispatches through its already-live Elytra branch, computes that branch's motion and calls `move(...)`; only after the superclass travel returns does the Player wrapper's flag-7 clear run. This bounds the claim to order/reachability, not a predicted trajectory.

The `LocalPlayer` input/toggle path is client-owned. The compared `Player.travel` implementation is in shared player source and has no client-side predicate; the wrapper is not client-exclusive. The separate `LivingEntity.travel` on-ground clear is guarded by `onGround() && !level().isClientSide`, after its Elytra displacement, and can precede the wrapper clear on an eligible server-side call. No conclusion is made here about server policy, network timing, latency, or custom historical server movement.

`resetFallDistance()` is noted solely to preserve the observed order; it is not a finding in this report. This report does not evaluate Elytra flight math, flag introduction/start conditions, jump/key input changes beyond reachability, the broader tick inventory, or runtime parity.

## Exact source identities

Host checked: Windows 11 PowerShell. Source roots: local `decompiled_minecraft/<version>/mojmap/`; no decompilation or regeneration was run.

- **Older:** 1.20.6, Mojmap. `mojmap.ready.json` identifies version ID `1.20.6`, mapping `mojmap`, and client SHA-256 `02dfd345ac1ad55692d5dbc8486ac7e4fea72cd54ac494a79cd48963048e56b2`. Its `artifacts.sha256` identifies `1.20.6/client_mappings.txt` as `27f4ef3a9362e9874e2c33edac981b6f485ad97f92c4e35b497525b646aae745` and `1.20.6/client-mojmap.jar` as `2add08d295773a0615ca595877f5fd055fa454d7c68fac56d6a4894a1e055284`. SHA-256 of `mojmap.ready.json`: `1ec1dc773ea2a8a3ef48a9fc868893bd2f83d6b13b057d75b151b711788c3d41`; source-list SHA-256: `56aae10684471d7abb1c366bd5dd431ab976112a87e6cc5c687a68f1eff06311`; artifact-list SHA-256: `e5882622bcf22b3e3c2c96c73843a1308e11e3096f4feefd945ea5808933ce31`
- **Newer:** initial 1.21, Mojmap. Local cache `version.json` says release ID `1.21` (release timestamp 2024-06-13T10:24:03Z); its SHA-256 is `cdd1a44db7b5e8787723c1f6c7c7dc3a4638d96a31ddc84aeb27d3f30efa802a`. Cached raw `client.jar` SHA-256 is `4b56bc9b44de7e88f9fbb52e716c18240da34469fcbafa5ed2e75b04d60617e8`; cached Mojmap `client_mappings.txt` SHA-256 is `127485d79248d4ae2955a7d061c3626d38853ecb054c4d008707732264b0aac1`. These actual hashes match the URL/hash input entries in the 1.21 legacy provenance file. SHA-256 of that full legacy `.decompile-provenance.json`: `90246f277aefd2350e42e17245faa51928a949c8db4427f3be04a732527deb7c`.

For both versions, the actual `LocalPlayer.java`, `Player.java`, and `LivingEntity.java` hashes match the corresponding local source manifest entries:

| Relative source | 1.20.6 SHA-256 | 1.21 SHA-256 |
|---|---|---|
| `net/minecraft/client/player/LocalPlayer.java` | `6b429dfa6e0681251ec985dda1627f808652a7bbe5b70dc85c8fa0fe0ed46ffa` | `c555e68ac3c63ab9b4f9a9e31933e263b96350a2bc599d11a0de5928bc24b583` |
| `net/minecraft/world/entity/player/Player.java` | `785d93ccc94e1f912e545b2b0c355edeb352b44ee8e83a69364daec35266dbe2` | `ed32b88c3c7c8418b83db41823520f2b6b0b49a98610306ef26e9681dc925c71` |
| `net/minecraft/world/entity/LivingEntity.java` | `c66ec8dc3b1856e490e5834a46185589030d9fbc411e3e2ce64c73203cd753b2` | `324a3eee8496caab57cfaf5101ef576f1ae3c60c40e3857e96f35f3af9a3a0d8` |

**Evidence-readiness note:** 1.21 has no current ready marker or separate source/artifact checksum lists beside its source tree. Its single legacy provenance file is at `decompiled_minecraft/1.21/mojmap/.decompile-provenance.json`; it records source hashes and input URL/hash entries. Contrary to a possible missing-input concern, the corresponding pinned raw 1.21 client, mappings, and version metadata are present in `.minecraft-decompile-cache/1.21/`, and their computed hashes match that legacy file. Treat this as legacy-layout provenance, not as a freshly published ready marker. The 1.20.6 raw client/mapping input artifacts are represented by the version-local artifact manifest; the old campaign cache path recorded in its wrapper metadata is absent from this checkout, so those inputs were not independently rehashed from that old cache.

## Paired slices and references

Line numbers below are for the exact local files identified above.

- **Local ability path:** 1.20.6 `LocalPlayer.java:720–749` and 1.21 `LocalPlayer.java:717–745`. `mayfly` gates creative flight handling; a second jump toggles `abilities.flying` and calls `onUpdateAbilities()` without testing the current Elytra flag. Elytra start is a distinct later condition requiring `!abilities.flying`; it does not preclude an already-live flag from overlapping a later toggle.
- **Client update / ordinary reach:** 1.20.6 `LocalPlayer.java:648–751,807` and 1.21 `LocalPlayer.java:644–748,804`; then `Player.aiStep` calls `super.aiStep()` (1.20.6 `Player.java:515–533`; 1.21 `Player.java:513–535`). The normal `LivingEntity.aiStep` calls `updateFallFlying()` and then virtual `this.travel(...)` (1.20.6 `LivingEntity.java:2673–2684`; 1.21 `LivingEntity.java:2668–2679`). The named order is unchanged in the compared caller slices.
- **Already-live flag validation / side ownership:** 1.20.6 `LivingEntity.java:2721–2745`; 1.21 `LivingEntity.java:2716–2740`. Both retain shared flag 7 only while airborne, not a passenger, without levitation and wearing an enabled Elytra; they update the shared flag only on `!level().isClientSide`. The client therefore does not have this pre-travel updater clear an otherwise-valid already-live flag.
- **Elytra displacement and own server-gated clear:** 1.20.6 `LivingEntity.java:2168–2207`; 1.21 `LivingEntity.java:2151–2190`. The Elytra branch reaches `move(MoverType.SELF, ...)`; its own flag clear is after movement and gated by `onGround() && !level().isClientSide`.
- **Compared Player wrapper:** 1.20.6 `Player.java:1473–1495`; 1.21 `Player.java:1456–1478`. Each has identical branch gates and operation order: save `getDeltaMovement().y`; `super.travel($$0)`; read resultant vector; set resultant x/z with saved y times `0.6`; reset fall distance; `setSharedFlag(7, false)`; otherwise call `super.travel` alone.

## Coverage boundary

Complete for the assigned overlap question: paired LocalPlayer toggle path, update-before-travel reachability, relevant side gates, Elytra displacement-before-wrapper-return, and direct post-travel flag clear; no difference found in the compared Player operation. Not a full 1.20.6→1.21 player-tick or movement-inventory audit, not independent review, and not evidence of runtime parity. No implementation, test, game/server/TAS/Gym launch, or network activity was performed.

