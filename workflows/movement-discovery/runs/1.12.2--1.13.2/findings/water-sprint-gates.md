# 1.13.2 changes sprint start and cancellation conditions in water

- Older version A: 1.12.2
- Newer version B: 1.13.2
- Mechanic / coverage slice IDs: 1.2 (local sprint gates); 2.x / 3.x swim-state dependencies remain open
- Classification: changed behavior
- Confidence: source-confirmed
- Applicability: historical player behavior in water present in both versions
- First changed release: unknown within (1.12.2, 1.13.2]
- Runtime validation: not performed

## Paired evidence

- A: `decompiled_minecraft/1.12.2/ornithe-feather/net/minecraft/client/entity/living/player/LocalClientPlayerEntity.java`, `LocalClientPlayerEntity.mobTick()V`, lines 719-745; SHA-256 `01a58e94d8c6ff98a8e3794227cdc76a5fcbdabad795c70c9cf28854aff9823cc`. Double-tap initiation is guarded by `onGround`; the separate held-sprint-key initiation checks forward input, hunger/flight eligibility, item use, and blindness, without a water/submersion predicate. Sprint cancellation checks forward input, horizontal collision, and hunger/flight eligibility.
- B: corresponding file/member, lines 723-758; SHA-256 `2583495f3a02b4791aa036e6a8d354d7596c4984761969a0f29b29d8d9bf42bf`. Double-tap initiation accepts `onGround || isSubmergedInWater()`. Held-sprint-key initiation adds `(!isInWater() || isSubmergedInWater())`. Cancellation now has separate swimming logic; non-swimming cancellation also treats partial submersion as a stop condition.
- B water/swim state: `decompiled_minecraft/1.13.2/ornithe-feather/net/minecraft/entity/Entity.java`, `updateSwimming()V`, lines 953-959, `isSubmergedInWater()Z`, lines 943-945, and `checkWaterState()Z`, lines 961-981; SHA-256 `1d6ec8b80f74635401745c2c027bf36555c85348ca5693764f2668363b17d269`. `submergedInWater` is assigned from a water-tag query and the exposed predicate also requires `isInWater()`. Base swimming state depends on sprint state, water/submersion state, and not riding; PlayerEntity disables swimming while flying and exposes swimming only when not flying or spectating (B PlayerEntity lines 1471-1477, 1819-1821; SHA-256 `4ed22f6c5a3c55d67eed782070ac722201df4d624adbc90779f1fd29c2876633`). The 1.12.2 Entity and PlayerEntity sources have no corresponding swimming-state path.

## Source-level difference

The client sprint state responds differently to water. In B, a submerged player can start sprinting through the double-tap path while not on ground; a held sprint key cannot start sprint while in water unless the player is submerged. Existing sprint cancellation also depends on swimming state and water contact. A has neither the submerged double-tap option nor the held-key water gate or swimming-specific cancellation branch. Applicability within mixed/partial submersion depends on the computed water state and swimming flag; other sprint eligibility conditions still apply.

## Reachability and dependencies

`LocalClientPlayerEntity.mobTick()` samples input and changes the sprint flag. In B, `Entity` refreshes the water flags and swimming state from fluid contact; `PlayerEntity.isSwimming()` filters for flying and spectator state. These client player paths are reachable during ordinary water movement. Exact collision/fluid-state production and timing interactions continue in stages 2-5. This source difference does not imply altered block states.

## Consequence and uncertainty

Source proves the predicates and writes to sprint/swimming flags differ. The predicted consequence is a different sprint flag, which feeds later movement-speed selection and underwater swim movement. This finding does not quantify displacement. The server may synchronize abilities and movement state; server behavior is outside the client-only conclusion.

## Handoff

Water-dependent sprint initiation/cancellation. Related to swimming movement integration, but separate from the water-jump dispatch and sneaking-water downward impulse. Release of introduction is unknown within the endpoint interval.
