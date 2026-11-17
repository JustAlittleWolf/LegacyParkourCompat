# Blind source coverage: 1.21.5 → 1.21.8 Mojmap (bounded state/predicate queue)

## Evidence identity

Both canonical trees report `status=ready`, exact `versionId`, `mapping=mojmap`, Vineflower, Java 25. `1.21.5` source manifest SHA-256: `365cc2d22446ceba0e36f46aa1c95ce4cc31514dbd657505680fb660f3adefd9`; client SHA-256 `522672ad20b460c02c2e39b6c5035ef6a849af28eb11ab2ac7eb293d395a8c11`. `1.21.8` source manifest SHA-256: `8ffb76cea647a2ba4fe58e000678f751bea2c40ea6358bae492e962ed1d9d008`; client SHA-256 `ea74e9c5e92d01f95f3d39196ddb734e19a077a58b4c433c34109487d600276f`. Evidence is the corresponding local `decompiled_minecraft/<version>/mojmap` tree; no online mappings or non-source lanes consulted.

## Examined slices

- `client/player/LocalPlayer.java`: fields/constructor and `tick()` around input state, shift/sprint remembered state and packet-facing input changes. 1.21.5 stores `wasShiftKeyDown`, calls `sendShiftKeyState()` after `super.tick()`, and emits press/release shift commands on transition. 1.21.8 removes that field/helper/call, stores `Input lastSentInput`, and sends `ServerboundPlayerInputPacket` when input differs. This is a client input/network representation change; the server-side consumption/protocol path was not traced and no client movement predicate delta is claimed. Flag for separate client/server boundary review if it affects the movement contract.
- `world/entity/player/Player.java`: `isSwimming()` remains `!abilities.flying && !isSpectator() && super.isSwimming()`; `canGlide()` remains `!abilities.flying && super.canGlide()`; `isSleepingLongEnough()` remains `isSleeping() && sleepCounter >= 100`; `canSprint()` and nearby movement-speed selection inspected. No behavioral delta found in these selected predicates. The Player diff also includes serialization/API churn; `Abilities` changes NBT save/load helpers to a packed codec record, retaining the same per-field defaults and assignments. No changed effective movement default identified.
- `world/entity/player/Abilities.java`: fields/default speeds and packed/apply operations. Defaults remain invulnerable/flying/mayfly/instabuild false, mayBuild true, flyingSpeed `0.05F`, walkingSpeed `0.1F`; `apply(pack())` preserves these values. No runtime movement behavior delta identified in this slice.
- `world/entity/LivingEntity.java`: inspected inheritance/context for shared predicates and state; broad diff has many unrelated persistence, waypoint and entity changes. This was not a full LivingEntity movement method inventory.

## Findings

No independent source finding established by this bounded comparison. The LocalPlayer shift-input transport change is not asserted as historical local-player movement behavior without tracing its full receiver/default-state path.

## Bounds and residual work

This is a partial report, not full pair coverage or a freeze. The assigned independent local predicates/default-state consumers were sampled in Player/Abilities and relevant LocalPlayer state; caller/consumer closure is incomplete. Not exhaustively inventoried: all `LivingEntity`/`Entity` player-inherited predicates, all direct player eligibility consumers and writers, client packet receiver/default effective state, every LocalPlayer pre-travel/tick-carried flag (including full swimming/fall-flying/sleeping/sprint transition call graph), and non-Player helper predicates. Excluded by assignment: rider synchronization, respawn carry, palette/provider/collision, fluid physics, outside correction, generic tick, axes, sprint/jump timer, pose dimensions, flight already audited, item-use eligibility, rendering, server movement simulation. Exact introduction boundary within 1.21.5–1.21.8 is uninspected.
