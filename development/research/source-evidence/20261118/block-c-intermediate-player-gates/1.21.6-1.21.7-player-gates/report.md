# 1.21.6 → 1.21.7: player-state and locomotion gates

## Disposition

**No source difference found in the assigned 1.21.6→1.21.7 slice.** The examined LocalPlayer input-consumption order, sprint entry and continuation gates, item-use slowdown, player crouch/pose selection, and effect-backed movement/jump predicates are identical. This is source coverage for this bounded slice, not a full-pair audit and not runtime parity evidence. No finding is proposed for this pair.

## Evidence identity

Both sides are the exact `mojmap` sources in `decompiled_minecraft/1.21.6/mojmap/` and `decompiled_minecraft/1.21.7/mojmap/`. Their hidden `.decompile-provenance.json` files are present; SHA-256: 1.21.6 `56b036c61094d1d936882bdf5b2b59044512189cb33adbb075880c727da9b19e`; 1.21.7 `45bf296d921b2239fd0aedb2684e2c1cb9b7d7532b4106b8a2fd9b09309ca82d`.

The manifests identify release metadata `1.21.6.json` / `1.21.7.json`, their respective client JARs, and official client mappings (`client.txt`). Pinned client JAR SHA-256 values are `9592df8587d6d3ec0334c873ad432dc76006d6129afdce9a7eac52638339a24a` (1.21.6) and `6d0aa64c6a0aee3270253317f51e9e816f94cd3b16b44f5d605615c39fc62fb9` (1.21.7); the corresponding mapping-file hashes are `2ff82b34ce5f849aaafdff51e23b9448c4263881238b780a441fe1061a6fc261` and `328f2c5849c7c5fc4f2b1eae4f74a7cfe6bb337c7938242ac5c4f9eac89b1e50` as recorded in the manifests. The manifest source hashes match the on-disk SHA-256 for each compared source below. There is no adjacent `mojmap.ready.json` marker for 1.21.6 or 1.21.7; the hidden per-tree provenance is the available canonical identity.

Compared source SHA-256 values are identical across 1.21.6 and 1.21.7:

- `net/minecraft/client/player/LocalPlayer.java` — `53f2a71a886b9c71853afe36f2df857f80a9bf1cd6ec4ca8604ccd7e238d89ee`
- `net/minecraft/client/player/ClientInput.java` — `597a44339a99f1bce1b081614c7c2984ca03e255b40e9d247675a31b6f810d78`
- `net/minecraft/client/player/KeyboardInput.java` — `d5cb0e93df7f66755172d74e028225012ee33c6e4f0d510a1d8e25ba5497c0a3`
- `net/minecraft/world/entity/player/Input.java` — `1c0c12af07da1f90651c3a7c4eb19bab6486b6391dcbfdd2926f06e3f36c2f06`
- `net/minecraft/world/entity/player/Player.java` — `8dc5514fe44311f39268692f5188840b9f65cb71143228fa8b84242585ea7987`
- `net/minecraft/world/entity/LivingEntity.java` — `609f0197a0b4551ab42279e452c11cdd256135b1d467c2a95950b0e9fd7ddef8`
- `net/minecraft/client/multiplayer/ClientPacketListener.java` — `a66556678a93c63c0ceb8953f25261b82bf3bb1971048fbc15b177c6dffea8cd`
- `net/minecraft/client/multiplayer/MultiPlayerGameMode.java` — `9a3377ac813724545f484cdbf132a5d8ff263540fcbdb2de2fddeffcd5edd8e5`

## Checked behavior and source locations

All paths below are relative to `decompiled_minecraft/<version>/mojmap/`; the cited 1.21.6 line ranges also apply verbatim to 1.21.7 because the source files are byte-identical.

- `net/minecraft/client/player/LocalPlayer.java:197-205`: local tick calls `super.tick()` first, then sends `ServerboundPlayerInputPacket` only when the current `input.keyPresses` differs from `lastSentInput`, and updates the snapshot after sending.
- `LocalPlayer.java:683-743, 837-852, 1065-1075`: `aiStep` snapshots jump, shift, and forward impulse before `input.tick()` at line 702; tutorial and auto-jump processing follow. The captured shift, active item use while not a passenger, or backward input clears the sprint trigger. Sprint start requires a fresh forward impulse, food eligibility, no active item use, no blindness, vehicle eligibility, and allowed fall-flying/slow/water state. A later sprint-key press can start sprint while those gates pass. Continuation uses the separate run or swim stop predicates; run stop checks blindness, vehicle eligibility, forward impulse, food, hard horizontal collision and water/submersion, while swim stop checks blindness, vehicle eligibility, water, the forward/ground/shift combination and food. Item use is a start/trigger gate, not a continuation stop test.
- `LocalPlayer.java:608-639`: applying input uses the client input move vector and jump key; nonzero movement is scaled by `0.98F`, by `0.2F` while using an item outside a vehicle, then by the sneaking-speed attribute when crouching or visually crawling. The keyboard input producer is `net/minecraft/client/player/KeyboardInput.java:22-36`; it samples keys and normalizes the move vector. Input record fields and packet codec are `net/minecraft/world/entity/player/Input.java:6-39`. Device plumbing itself is out of scope; these callers establish the freshness/order boundary.
- `LocalPlayer.java:693-702` and `593-605`: local crouching is computed before `input.tick()` from the current shift state and pose-fit checks; `isCrouching()` returns the stored crouching flag and slow movement includes visual crawling.
- `net/minecraft/world/entity/player/Player.java:459-491`: pose selection prefers swimming when it fits, otherwise desired pose, then crouching, then swimming; desired pose prioritizes sleeping, swimming, fall flying and spin attack, then crouching when shift is down and not flying, else standing. Existing shape/collision implementation is not audited here.
- `net/minecraft/world/entity/LivingEntity.java:2192-2217`: jump power adds `getJumpBoostPower()` to jump-strength attribute × multiplier × block jump factor; Jump Boost contributes `0.1F * (amplifier + 1.0F)`. `jumpFromGround` preserves the epsilon gate, maxes vertical velocity, adds the sprint impulse when sprinting, then sets `hasImpulse`. The code is identical in both releases.

## Intermediate boundary for the 1.21.5→1.21.8 endpoint

The only relevant input-freshness transition found in the requested slice lands at **1.21.6**. In 1.21.5, `LocalPlayer.java:193-200, 269-278` sends shift press/release as a separate player-command packet after `super.tick()`, while `lastSentInput` starts as `Input.EMPTY`. In 1.21.6, `LocalPlayer.java:145-154, 197-205` instead accepts the last `Input` snapshot in its constructor, compares and sends the full input record after `super.tick()`, and no longer has `sendShiftKeyState`. Respawn preservation follows that transition: `net/minecraft/client/multiplayer/ClientPacketListener.java:1180-1185` passes `isShiftKeyDown()` in 1.21.5; at `:1228-1236` in 1.21.6 it passes `getLastSentInput()`. `net/minecraft/client/multiplayer/MultiPlayerGameMode.java:384-390` changes the corresponding constructor argument from a boolean to `Input`, with the default overload supplying `Input.EMPTY`.

This places the input-packet/snapshot change in the 1.21.5→1.21.6 interval for the inspected operation. The 1.21.6, 1.21.7 and 1.21.8 LocalPlayer, Input, ClientInput, KeyboardInput, Player and LivingEntity files have matching source hashes; thus this narrow input, sprint, pose and jump slice shows no further boundary through 1.21.8. It does not classify other changes in the full 1.21.5→1.21.8 player tick.

The 1.21.5 and 1.21.8 neighboring source sets have adjacent `mojmap.ready.json` markers with `status: ready`; their source-manifest SHA-256 values are respectively `365cc2d22446ceba0e36f46aa1c95ce4cc31514dbd657505680fb660f3adefd9` and `8ffb76cea647a2ba4fe58e000678f751bea2c40ea6358bae492e962ed1d9d008`. The six source entries relevant to this boundary match those manifests. The ready records identify versions 1.21.5 and 1.21.8, `mojmap`, and their pinned client JAR hashes. No 1.21.6/1.21.7 ready marker exists alongside the hidden provenance manifests.

## Bounds

Included: local player input snapshot/consumption order, sprint initiation and continuation gates, local item-use movement slowdown and sprint gate, crouch state/desired pose, and jump-effect predicates plus only the input/respawn dependencies needed to place the boundary.

Excluded: rendering/cosmetic state, remote-player behavior, input-device behavior beyond confirming sampling order, collision/AutoJump geometry, fluid/contact behavior, server-side input packet consumption, and unrelated changes in the 1.21.5→1.21.8 endpoint. No tests, launches, source generation, network, product files, or Git history were used.

