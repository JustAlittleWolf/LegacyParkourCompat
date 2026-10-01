# F001: Pose-aware sneak input slowdown gate

- Older version A: 1.13.2
- Newer version B: 1.14.4
- Mechanic / coverage slice IDs: stage 1, `INPUT-SLOWDOWN`
- Classification: changed behavior
- Confidence: source-confirmed
- Applicability: historical player behavior
- First changed release: 1.14 (verified at the exact group base)
- Runtime validation: not performed

## Paired evidence

- A manifest: `../../../../build/stable-shared-minecraft/ready/1.13.2--feather.json`; source `net/minecraft/client/entity/living/player/KeyboardInput.java`, `KeyboardInput.tick()`, lines 46–49, SHA-256 `7BE11425906BE051C83E275F359816546E4677B16D212156380E8D2E9258654A`.
- A player path: `net/minecraft/client/entity/living/player/LocalClientPlayerEntity.java`, `mobTick()` line 702 calls `input.tick()`; lines 638–639 copy sampled input to movement speeds; lines 797–800 reverse the `0.3` sneak factor when flying. SHA-256 `2583495F3A02B4791AA036E6A8D354D7596C4984761969A0F29B29D8D9BF42BF`.
- B manifest: `../../../../build/stable-shared-minecraft/ready/1.14.4--feather.json`; source `net/minecraft/client/entity/living/player/KeyboardInput.java`, `KeyboardInput.tick(boolean, boolean)`, lines 13–25, SHA-256 `5932453A9E48E7A798AE1BE1CD3A4BF660B6B3BE43BB7E5DC22686B3C4A82526`.
- B player path: `LocalClientPlayerEntity.java`, `m_63723874()` lines 596–598, `mobTick()` lines 629–630, movement-speed copy lines 604–605, flying compensation lines 729–732; SHA-256 `708AF6A3880FB58B67BF4604A5509B351719A9C2A0C06A8EC261586435BF00CE`.
- B outside-water swim predicate: `net/minecraft/entity/Entity.java`, `m_00306336()` / `m_99544176()`, lines 1818–1823, SHA-256 `7315A496C195DA767DE9D4936D3ADB6EFC3C419DC0F0E95D6F32781B0DA1BA55`.
- Exact group-base check: manifest `../../../../build/stable-shared-minecraft/ready/1.14--feather.json`; `LocalClientPlayerEntity.java` SHA-256 `2B1AD3B3416949A9DA2607A3EC2251AA69B2D9EEBD76638C92E20749D625338F` confirms the pose gate at release 1.14.
- Requested latest-patch endpoint check: manifest `../../../../build/stable-shared-minecraft/ready/1.14.4--feather.json`; the endpoint adds `!isSwimming()` to the pose subgate. Its within-group boundary is deferred to the minor-version pass (F002).

## Source-level difference

In 1.13.2, `KeyboardInput.tick()` multiplies sideways input and then forward input by `(float)(value * 0.3)` whenever the sneak key is held. In 1.14, `tick(boolean bl, boolean bl2)` uses the same two operations when `!bl2 && (this.sneaking || bl)`. The local player computes `bl` from pose-fit state before calling the input tick and supplies `isSpectator()` as `bl2`. The exact 1.14 group base confirms the pose-aware gate first appears at 1.14.

At the 1.14.4 endpoint, `KeyboardInput.tick` applies slowdown when the player is not a spectator and either the sneak key is held or the local pose-based predicate is true. That predicate requires the player not to be flying or swimming, the sneaking pose to fit, and either the sneak key to be held or the standing pose not to fit. An outside-water swimming pose is a separate alternative. F002 records that the swimming exclusion differs from the 1.14 base and 1.14.1; its exact first patch is deferred to the minor-version pass.

When the local player is flying with sneak held, the direct sneak-key branch still applies the `0.3` factor in 1.14.4. The later flight compensation divides the sampled input fields after the local horizontal movement speeds have already been copied, so it does not undo the current tick's slowdown. The compatibility gate therefore keeps slowdown active for flying sneak input; the later field compensation is recorded as source context and is not applied to the modern movement vector.

## Reachability and dependencies

Local client player `mobTick()` → `KeyboardInput.tick(...)` → sideways/forward input fields → local player movement-speed fields. In 1.14, the gate reads spectator state, flight state, sneak-key state, pose dimensions, collision availability, swimming pose, and water state. `PlayerEntity.updatePlayerPose()` selects a crouching or swimming pose when the requested pose does not fit; collision checks use the entity's pose dimensions and block/entity collision shapes. Both endpoint player paths feed the sampled values into player movement. The claim is limited to the client player path.

## Consequence and uncertainty

Source proves the changed predicate and the ordering of the later flight compensation. It predicts additional slowdown while a non-flying player is forced into a fitting sneak pose without holding sneak, no slowdown for spectators, and slowdown for flying sneak input through the direct key branch. F002 records the swimming-state refinement; its exact first patch is unresolved and deferred. Other movement slices in this run remain pending. No trajectory has been runtime-validated.

## Handoff

Implement the 1.14.4 pose/spectator slowdown gate through the existing `V1_14` group, which represents the requested latest patch. Preserve the direct sneak-key branch, including while flying. The exact within-group boundary for the swimming-state refinement is tracked by F002 for the later minor-version pass.
