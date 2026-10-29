# 1.21.4 to 1.21.5 client local-tick response coverage

## Evidence identity

Pair: Minecraft 1.21.4 → 1.21.5, Mojmap on both sides. Canonical reference root: `D:\Javastuff\LegacyParkourCompat\decompiled_minecraft` (read-only).

| Release | Ready marker | Client artifact SHA-256 | Source manifest SHA-256 | Artifact manifest SHA-256 | Parent provenance |
|---|---|---|---|---|---|
| 1.21.4 | `1.21.4/mojmap.ready.json`, versionId `1.21.4`, mapping `mojmap`, 5,744 source files | `c17c450c6e72cc51297daa57ce38f800aa01cf022b743daa21a0512d326d894e` | `f90b61197928632e061ea877955a19055c92ae6f357c2daf1bc646172c6f51f0` | `1a0929ca8c88cfe7874f323918dfa3b044964007d3ff0d3943317bdb35caf841` | `1.21.4/mojmap.provenance.json`, namespace `mojmap`, batch exact run 1.21.1/1.21.3/1.21.4/1.21.5, Vineflower 1.12.0, Java 25.0.3+9-LTS |
| 1.21.5 | `1.21.5/mojmap.ready.json`, versionId `1.21.5`, mapping `mojmap`, 5,921 source files | `522672ad20b460c02c2e39b6c5035ef6a849af28eb11ab2ac7eb293d395a8c11` | `365cc2d22446ceba0e36f46aa1c95ce4cc31514dbd657505680fb660f3adefd9` | `d35b1b9389d8958e33893faa63d94c86f22af30385233ca947fd410aa5c36656` | `1.21.5/mojmap.provenance.json`, namespace `mojmap`, same batch exact run and toolchain |

For both releases, the actual source and artifact manifest file hashes match their ready-marker declarations. Each selected source file below also matches its entry in the corresponding source manifest. I inspected the mapping-root contents and searched both mapping trees recursively for embedded provenance: neither contains a provenance file. Identity is therefore bound by the release-parent provenance plus the release-parent ready marker and verified source manifest; no mapping-root embedded provenance claim is made. The parent provenance records that sources were published from batch staging output, and the ready marker identifies the pinned client artifact hash.

## Hash-pruned slice inventory

Selected source file hashes (`1.21.4` → `1.21.5`):

- `net/minecraft/client/player/LocalPlayer.java`: `145686ebdc7f0d12a64070309073665eb8695b7e09911a723e86113a77d04611` → `f1fcfed4a938732361e7ad951f93e9b73b02320ee56f0b219e2b3c7acdbfa2ef`
- `net/minecraft/client/player/ClientInput.java`: `0f2e4d03749ab7495e386af05aa77a0414b5d1c3ef76256db2b514f42cc6e3c4` → `597a44339a99f1bce1b081614c7c2984ca03e255b40e9d247675a31b6f810d78`
- `net/minecraft/client/player/KeyboardInput.java`: `c885bcc709e445c3ff91f03bf37acf9320e17fc9daf1bd2ba46ba20487f1d9ed` → `d5cb0e93df7f66755172d74e028225012ee33c6e4f0d510a1d8e25ba5497c0a3`
- `net/minecraft/world/entity/player/Player.java`: `c45f41b9784ce50a88a498e84a13edcef0a23e175233d726ed7f90940f19ebc1` → `8fc187f33999db9dfc49251e95e92a17645a50adab16ca0f93f4948209f62036`
- `net/minecraft/world/entity/LivingEntity.java`: `e62ce650af0a5ac97e4d0a2ba7cad4ba68a8d098525609f8b5b6976a8ab0ae30` → `a8aed863d4fdc515c751dd2878a8bb9c13179228cb8dbb50edf1d19cd5404271`
- `net/minecraft/client/multiplayer/ClientPacketListener.java`: `eef170dd22b5711e7d9527601592093a0456590f192b54fefef170b5791165b4` → `284f34159a1baf30b6b540751ad2fa62344c179d5ed6a7bbcb5c888665779617`
- `net/minecraft/world/entity/PositionMoveRotation.java`: `74b39fb0907043885e7cfeb6376c8d39eba7131ee6befa2df57a08ea53571436` → `75baa0b6f99d1b9d18f157cbbf2c6d209b13af6719e960c7da1c451587433cc3`
- `net/minecraft/world/entity/Entity.java`: `05f18ef2ec0413fc010230407c812a11553eb5123b68d21b5d7b2c0c175698ad` → `fc177733e9cc4b2d5cf2562e5529d0f4e0b9c3690f081fbd58aa9119eb173dc4`

Ordered examined slices:

1. `LocalPlayer.tick` and its pre-travel `aiStep` work: checked local state updates, `input.tick()`, sprint transitions, abilities, jumping, mounted-jump, flight input, and call to superclass. No additional accepted delta recorded here. The sprint trigger/stop branches are excluded by assignment; input vector normalization, slow-item scaling, and tiny-input/deadzone math are also excluded as already closed.
2. `LocalPlayer.serverAiStep` (1.21.4) versus `LocalPlayer.applyInput` and `modifyInput` (1.21.5), with `ClientInput.getMoveVector` / `hasForwardImpulse`, `KeyboardInput.tick`, and `LivingEntity.aiStep` call order. The move vector representation changes to normalized `Vec2`; the superclass calls `applyInput()` before immobility/jump processing, while travel no longer multiplies `xxa` / `zza` by `0.98F`. These input arithmetic/order candidates are excluded by the assignment's closed sprint/deadzone boundary and are retained as coverage only.
3. `Player.updatePlayerPose` / new `getDesiredPose` and `canPlayerFitWithinBlocksAndEntitiesWhen`; pose preference order was extracted and compared. The preference order changes: 1.21.4 tests fall-flying, sleeping, then swimming; 1.21.5 tests sleeping, swimming, then fall-flying. The unchanged outer fit gate and fallback pose selection remain the same. Reachability of overlapping pose predicates was not established, so this remains a disposition rather than an accepted movement finding.
4. `ClientPacketListener.handleMovePlayer` and the removed-vehicle fallback in `handleTeleportEntity` → `setValuesFromPositionPacket` → `PositionMoveRotation.calculateAbsolute`; compared all relative position/rotation/delta inputs and caller gates. One independent packet-response difference is written in `relative-pitch-velocity-rotation.md`.
5. `ClientPacketListener.handleEntityPositionSync`, `handleTeleportEntity`, `handleMoveEntity`, `handleSetEntityMotion`, `handleRespawn`, and effect/ability consumers were scanned. Changed interpolation/authority branches are vehicle or other-entity handling; no separate local walking-player response finding established. Rendering consumers and server packet producers were kept separate and not traced. Vehicle physics remain outside this bounded slice.

This is bounded source coverage for the assigned slices, not a full-pair inventory or closure claim. Collision-provider internals and block callback/traversal behavior were not examined. No tests, builds, launches, or source generation were run.
