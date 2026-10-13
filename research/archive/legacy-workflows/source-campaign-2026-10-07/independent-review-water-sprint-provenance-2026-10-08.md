# Independent source review: water sprint snapshot provenance

## Verdict

**ACCEPT, bounded to the local-player sprint state rules described below.** The 1.12.2 and 1.13.2 source bodies support a real difference in double-tap and held-key sprint eligibility, with a reachable grounded, dry, surface-water, or eye-submerged (`isSubmergedInWater`) input state as applicable. This is an endpoint source comparison, not full-pair closure, an intermediate-release boundary, or runtime validation.

The two snapshots receive the same bounded source verdict but remain distinct immutable records. The integrated 1.12.2–1.13.2 finding at `main` is **not** the revised snapshot's bytes, even though their sprint/source-claim sections are identical. The revised Feather-derived artifact's equivalence to the unavailable original derived artifact remains **unproven**.

## Frozen snapshot identities and provenance comparison

| Record | Commit | Git blob | Raw SHA-256 |
|---|---|---|---|
| Integrated snapshot | `3a60fe735560e478bf0aa0d05f5e306c74800f6a` | `ec3f21a967cb1b71e775295de3e5d1be060cd95f` | `8698af8d4474a11725abdcfb1aabcea8a84e6fb44c31a8254bf6a40810b5f114` |
| Revised Feather snapshot | `2dae7b235bde47c7837fd4092ec67ccb014352f2` | `2595ba0c83a20f5543235759e789121bb1b0aa68` | `ebc678ad4522a14a10549bc38a1f7958dcedcabd6de290a262c81d6c726d46f1` |

Both raw digests were computed from the respective Git blob bytes. A direct diff shows exactly one added line in the revised snapshot: `Evidence artifact records: EA-FEATHER-R1-A and EA-FEATHER-R1-B (see run.md, Artifact evidence identities).` The integrated snapshot already contains the revised-artifact SHA values, source-manifest hashes, original-artifact-manifest hashes, and the warning that original derived jars are unavailable and equivalence is unproven. It lacks only that evidence-record pointer. This report keeps the identities and verdicts separate so the integrated old bytes cannot be represented as the revised bytes.

The canonical ready markers declare `1.12.2/ornithe-feather` and `1.13.2/ornithe-feather`. Their source-manifest SHA-256 values match the ready markers: A `b8a37ccfccd2aac5f40f5e34fec85873dbdfa93a266043c103e592e4a8c949da`; B `2c8cfb646bf622fb26ac0ca0cb5e02a354fb512e29f8b1010f1aaacd509e1211`. Their artifact-manifest SHA-256 values also match: A `8171a095a5ee74cebbd12b3d76983fc01796c0c23c962076d1e235cd0f80fa8c`; B `fdcacd9150f98ea70acafc5cab754027ab1a09828ff0eaa8e542dd7890d9ce1e`. The original A and B client jars at the artifact-cache paths match ready-marker and artifact-manifest hashes `8ada07da5ee77dad3527bd7278fbd05ee1fc8a597813b216a871a2d7d64cc64f` and `3410887ba652f25792c7675bfaf9140e73b60e93cfbf113a803f8a98cb05c0f9`. B's targeted original-jar resource `data/minecraft/tags/fluids/water.json` has SHA-256 `698e1662335b6241879b380a58d478ee019a1e789362b004050b5ccac421ad18` and includes `minecraft:water` and `minecraft:flowing_water`. The finding's cited `LocalClientPlayerEntity.java` file hashes match both source manifests. Other source files named below were checked against their side's source manifest; no full-tree rehash was performed.

The two revised source trees are therefore identified and their targeted source bodies are verified against the canonical ready manifests. That does **not** establish that either revised decompile is byte-identical or behaviorally equivalent to an unavailable original derived JAR. Keep this limitation attached to the source claim.

## Frozen version verdicts and bounded source claim

### A — 1.12.2

`net/minecraft/client/entity/living/player/LocalClientPlayerEntity.java::mobTick`, lines 694–745 (SHA-256 `01a58e94d8c6ff98a8e3794227cdc76a5fcbdbad795c70c9cf28854aff9823cc`), saves prior `movementForward >= 0.8F` to `bl3`, then calls `input.tick()` before reading the current forward value and checking sprint conditions. `KeyboardInput.tick()` samples the current forward key to `movementForward`; the current sprint-key state is read directly from `options.sprintKey.isPressed()`.

The double-tap start branch requires `onGround`, a previous/current forward-input edge, no previously sampled sneak, sufficient food or flight, and no item/blindness blocker. The held-sprint branch requires forward at least `0.8F`, sufficient food or flight, no item/blindness blocker, and a pressed sprint key; it has no water predicate. The stop branch clears sprint for low forward input, horizontal collision, or insufficient food; it has no water-specific cancellation.

### B — 1.13.2

`LocalClientPlayerEntity.java::mobTick`, lines 698–758 (SHA-256 `2583495f3a02b4791aa036e6a8d354d7596c4984761969a0f29b29d8d9bf42bf`), retains the same prior-input capture and current keyboard sampling order. Its double-tap branch changes the support test to `(onGround || isSubmergedInWater())`. Its held-key branch requires `(!isInWater() || isSubmergedInWater())`; thus the current gate allows dry and fully submerged water states, while it rejects water contact without submersion. Other forward/food/item/blindness conditions remain in the gate.

Sprint stopping is conditional on the swimming state at that point in the tick. For a non-swimming player, `bl7` includes `isInWater() && !isSubmergedInWater()`, so a surface-water state cancels sprint. For a player already swimming, the branch instead clears sprint when `(!onGround && !sneaking && (forward < 0.8F || !bl5)) || !isInWater()`. It does not cancel solely because the player is no longer submerged while still in water. The finding's statement about surface-water cancellation is accurate for the non-swimming branch; do not generalize it to every already-swimming state.

The existing local-player input path makes both gates reachable: `Minecraft` installs `KeyboardInput` for the local player; `KeyboardInput.tick()` reads the forward key, and the sprint key is sampled through `isPressed()`. `doubleTapSprintTime` starts at Java's default integer zero, is set to seven on the first eligible forward edge when the sprint key is not held, then counts down; after a release and a second forward edge inside the window the branch can set sprint. For the bounded witness, use the same ordinary forward/food/item/blindness preconditions on both sides and vary only the cited support/water state or sprint-key input.

### Exact state and consumer order

The water predicates are reachable from current world water states before the local sprint decision. In A, `Entity.baseTick` invokes `checkWaterCollisions` before `LivingEntity.tick` reaches `mobTick`; `checkWaterCollisions` sets `inWater` using the player's contracted/grown shape against water-material block states (`Entity.java:328–382, 940–963`; `World.applyLiquidDrag`, `World.java:1579–1618`). A's `isSubmergedIn(Material)` samples the eye position's block material and liquid height (`Entity.java:1027–1043`), though the sprint gate itself does not read this predicate.

In B, `PlayerEntity.tick` calls `updateWaterSubmersion()` before `super.tick()` (`PlayerEntity.java:175–208,257–259`). The local player override stores the result from `isSubmergedIn(FluidTags.WATER)` and returns that field to the sprint gate (`LocalClientPlayerEntity.java:998–1020`). During `Entity.baseTick`, `m_03231680()` refreshes `inWater`, the entity submersion flag, and swimming before `LivingEntity.tick` reaches `mobTick` (`Entity.java:332–395,943–981`; `LivingEntity.java:1689–1752`). `isSubmergedIn` reads the fluid state at eye position and compares against its height (`Entity.java:1042–1051`); `m_69693160` tests water fluid states intersecting the contracted entity box (`Entity.java:2509–2535`). A water `LiquidBlock` resolves its state to a `FluidState`; chunk-section reads resolve the stored block state's fluid state (`LiquidBlock.java:68–70`; `WorldChunkSection.java:30–50`). The hash-bound original B client-jar water tag lists both source and flowing water, so ordinary water block fluid states satisfy the tag predicate. Thus a dry state, a water-contact/surface state with the eye above the fluid surface, and an eye-submerged state are distinct source-reachable inputs.

Input and water sampling precede sprint evaluation. The local sprint decision occurs before `super.mobTick()`, whose `LivingEntity.mobTick()` reaches travel/movement. B's `updateSwimming` runs in the earlier base-tick water update, so the stop branch and same-tick movement use the swimming state established before the current sprint decision; a newly started sprint can change swimming on the next entity tick. `PlayerEntity.moveRelative()` then has a swimming-specific movement path (`PlayerEntity.java:1442–1465`). This ordering bounds the immediate claim to sprint-state writes and their subsequent movement consumers; it does not justify treating the swimming flag as synchronously recomputed at `setSprinting(true)`.

`bl5` reads `getHungerManager().getFoodLevel() > 6.0F || abilities.canFly` at the sprint gate; the source expression does not mutate food. Both `HungerManager` sources initialize food level to `20`. `PlayerEntity.mobTick()` has a separate Peaceful-mode food update after the local sprint decision, so “read-only” applies to the sprint predicate, not to all vanilla food state production. Food simulation remains excluded. The accepted comparison assumes the same eligible food state (for example, default `20`) on both endpoints and does not claim behavior for arbitrary food histories.

## Remaining dependencies and limits

- First changed release remains `unknown within (1.12.2, 1.13.2]`; only these endpoints were reviewed.
- Full-pair movement, input, fluid, and consumer inventories remain open. In particular, this bounded check does not compare every fluid current/drag path or all reasons swimming can start/stop; the sprint/swimming ordering described above is the dependency closed for this finding.
- Food-level production is outside scope. Only the `foodLevel > 6` movement predicate and the stated equal-food precondition are accepted here.
- The Feather-derived source/JAR revision is traceable to the cited manifests, but equivalence to the unavailable original derived artifact is unproven. Do not upgrade that provenance claim based on this review.
- No implementation changes, tests, builds, decompiles, clients, servers, TAS runs, or gameplay validation were performed. Pair discovery and runtime validation remain partial/not performed.
