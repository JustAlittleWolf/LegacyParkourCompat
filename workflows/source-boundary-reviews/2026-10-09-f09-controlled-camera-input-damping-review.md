# D9 independent review: F-09 controlled-camera input damping

- Exact-snapshot verdict: **REQUEST CHANGES**.
- Adjudication: the original “B skips the `0.98F` damping” claim is unsupported and must be withdrawn. A narrower, source-supported float-order difference remains: A applies the factor after item/sneak scaling; B applies it before those scales and square correction. A replacement finding may assert only that order-dependent delta and its bounded applicability.
- Pair status: `1.21.4--1.21.5` remains partial. This review does not freeze the pair.
- Runtime validation: not performed.

## Immutable snapshot and checkpoint binding

The exact original F-09 bytes were read from author commit `95061345541a2b5a8c56e57a7fe71a6007e9d684`:

- Path: `workflows/source-campaign-2026-10-07/1.21.4--1.21.5/findings/F-09-local-player-input-damping.md`
- Git blob: `8a9c64a35705195d39d300f7fa0ea4f25cf049aa`
- File SHA-256: `3a1680c5e724a935cbc576705088f03e3a9d4c6a8e29e5375d4ddb8e93b1e328`

I compared only the relevant pair-run input evidence at the assigned owner checkpoints `b8c502b89d08df1436b16e5d98e156440a7bad40`, `ce2cb45d14311769c4cde28013c7fe9fd87dce8b`, and `930e4ed490ef41a98b0527183e9b3b4ad9e0d339`, plus the immutable original F-09 and the exact vanilla sources below. The first two checkpoints state that the controlled-camera override lacks the factor; the fresh D7 follow-up recorded at `930e4ed` finds it in B `LocalPlayer#modifyInput()` and leaves D9 open for this independent review. I preserve the original snapshot and all prior review events unchanged; this verdict applies only to the cited original bytes.

## Source publication and hashes

The live markers identify exact Mojmap 1.21.4 and 1.21.5 sources. I recomputed both marker hashes, source-manifest hashes, artifact-manifest hashes, and every Java source-file hash below; each source hash matches its exact version's manifest entry.

| Side | Ready-marker SHA-256 | Source-manifest SHA-256 | Artifact-manifest SHA-256 | Client jar SHA-256 |
|---|---|---|---|---|
| A: 1.21.4 Mojmap | `1c6617b7acf8bb357f77c6c6c276881c73d882bda1f93f6dd217bb54eb8aa4be` | `f90b61197928632e061ea877955a19055c92ae6f357c2daf1bc646172c6f51f0` | `1a0929ca8c88cfe7874f323918dfa3b044964007d3ff0d3943317bdb35caf841` | `c17c450c6e72cc51297daa57ce38f800aa01cf022b743daa21a0512d326d894e` |
| B: 1.21.5 Mojmap | `5f65fb143209abcc6e28e77a7150aecefdbc9de00b6ef3fc924e53bc0e1a667e` | `365cc2d22446ceba0e36f46aa1c95ce4cc31514dbd657505680fb660f3adefd9` | `d35b1b9389d8958e33893faa63d94c86f22af30385233ca947fd410aa5c36656` | `522672ad20b460c02c2e39b6c5035ef6a849af28eb11ab2ac7eb293d395a8c11` |

| Paired source file | A SHA-256 | B SHA-256 |
|---|---|---|
| `net/minecraft/client/player/KeyboardInput.java` | `c885bcc709e445c3ff91f03bf37acf9320e17fc9daf1bd2ba46ba20487f1d9ed` | `d5cb0e93df7f66755172d74e028225012ee33c6e4f0d510a1d8e25ba5497c0a3` |
| `net/minecraft/client/player/ClientInput.java` | `0f2e4d03749ab7495e386af05aa77a0414b5d1c3ef76256db2b514f42cc6e3c4` | `597a44339a99f1bce1b081614c7c2984ca03e255b40e9d247675a31b6f810d78` |
| `net/minecraft/client/player/LocalPlayer.java` | `145686ebdc7f0d12a64070309073665eb8695b7e09911a723e86113a77d04611` | `f1fcfed4a938732361e7ad951f93e9b73b02320ee56f0b219e2b3c7acdbfa2ef` |
| `net/minecraft/world/entity/LivingEntity.java` | `e62ce650af0a5ac97e4d0a2ba7cad4ba68a8d098525609f8b5b6976a8ab0ae30` | `a8aed863d4fdc515c751dd2878a8bbc13179228cb8dbb50edf1d19cd5404271` |
| `net/minecraft/world/entity/Entity.java` | `05f18ef2ec0413fc010230407c812a11553eb5123b68d21b5d7b2c0c175698ad` | `fc177733e9cc4b2d5cf2562e5529d0f4e0b9c3690f081fbd58aa9119eb173dc4` |
| `net/minecraft/world/phys/Vec2.java` | `06ddeb256f748d326f79fa941c852db14a65abdc1036f31550f0d95eb0e05c56` | `b637ef6899de5d8892e19273b114ffaf0397761e509170a56d52dd068f54bffe` |
| `net/minecraft/world/phys/Vec3.java` | `62581d3005cc32401545f9229a9c293dfd61c52e7d30f88456c49d2c2867e68d` | `62581d3005cc32401545f9229a9c293dfd61c52e7d30f88456c49d2c2867e68d` |
| `net/minecraft/world/entity/player/Player.java` | `c45f41b9784ce50a88a498e84a13edcef0a23e175233d726ed7f90940f19ebc1` | `8fc187f33999db9dfc49251e95e92a17645a50adab16ca0f93f4948209f62036` |
| `net/minecraft/world/entity/ai/attributes/Attributes.java` | `14bcc5821d0f67f41fdc3e512556181823cff5c43f84fc1007e51d25f413a3ea` | `14bcc5821d0f67f41fdc3e512556181823cff5c43f84fc1007e51d25f413a3ea` |
| `net/minecraft/world/entity/ai/attributes/AttributeInstance.java` | `2a5ed44096b45b3b050a8df947cd44c788e6a35d51eb81d4c023f331f6f62870` | `6e557daca10617e6a8f4af17d6436a8d1165cd175048e98ea795629a274c44cb` |
| `net/minecraft/world/item/enchantment/Enchantments.java` | `01bdf94faa8089e50a282ec0b50c2b9956ddfb4260ae37dd9ec23e460e9d1bd1` | `ee37699b95dc530fdd91dadef27d19a54bc7c4febe79bca40cc1ee438a626356` |
| `net/minecraft/world/item/enchantment/LevelBasedValue.java` | `f9a0bed1d7693606f8657fadd4a59d95eea9955fcc036687e7a9dd08a8b360e3` | `f9a0bed1d7693606f8657fadd4a59d95eea9955fcc036687e7a9dd08a8b360e3` |
| `net/minecraft/world/item/enchantment/effects/EnchantmentAttributeEffect.java` | `c1e2edd5c870e1ecba364cbee07ae2f12720e47681dd1dbf16ac081e392f4270` | `c1e2edd5c870e1ecba364cbee07ae2f12720e47681dd1dbf16ac081e392f4270` |

## Paired controlled-camera path

**Producer.** In both versions, `KeyboardInput#tick()` samples the forward key and uses `calculateImpulse(true,false) == 1.0F` for forward-only input (A lines 13–18, 22–34; B lines 14–19, 23–36). A stores raw `leftImpulse`/`forwardImpulse`; its `ClientInput#getMoveVector()` returns those fields as a `Vec2` (A `ClientInput.java:14–16`). B stores a `Vec2` and returns it from `getMoveVector()` (B `ClientInput.java:8–15`); keyboard normalization of `(0,1)` leaves this cardinal input unchanged.

**A scaling and phase.** `LocalPlayer#aiStep()` samples input, then for a non-passenger using an item multiplies both input axes by `0.2F`, and for `isMovingSlowly()` multiplies them by `(float)getAttributeValue(SNEAKING_SPEED)` (A `LocalPlayer.java:662–678`). It then calls `super.aiStep()` at line 809. A `LocalPlayer#isEffectiveAi()` returns true (lines 482–485); in the non-immobile branch `LivingEntity#aiStep()` invokes virtual `serverAiStep()` (A `LivingEntity.java:2727–2735`). The controlled-camera `LocalPlayer#serverAiStep()` copies those already-scaled input axes to `xxa`/`zza` (A `LocalPlayer.java:607–617`, camera check 620–622). The caller then multiplies `xxa` and `zza` by `0.98F` at A `LivingEntity.java:2766–2768`, before forming the travel vector at 2773–2783.

**B scaling and phase.** `LivingEntity#aiStep()` invokes virtual `applyInput()` before its immobility check (B `LivingEntity.java:2712–2721`). For the controlled camera, `LocalPlayer#applyInput()` calls `modifyInput(input.getMoveVector())` and assigns its result to `xxa`/`zza` (B `LocalPlayer.java:608–621`, camera predicate 660–662). `modifyInput()` applies `0.98F` first, then item-use `0.2F`, then the sneaking-speed float, then square-movement correction (B `LocalPlayer.java:623–658`). The base `LivingEntity#applyInput()` also contains `0.98F` (`2806–2809`), but the controlled-camera override does not call it; the override's own helper supplies the factor. The non-controlled-camera branch calls `super.applyInput()` and is outside the finding.

**Consumer.** The paired `LivingEntity#travelInAir()` paths call `moveRelative()` (A `LivingEntity.java:2248`; B `2270`). The paired `Entity#moveRelative()` then call `getInputVector()` before adding the result to delta movement (A `Entity.java:1424–1427`; B `1453–1456`). The paired `getInputVector()` methods retain the same `1.0E-7` zero cutoff, normalize only when squared length is greater than `1.0`, then scale and rotate (A `Entity.java:1429–1439`; B `1458–1468`). Thus B does not omit the factor: both paths execute one `0.98F` multiplication, at different positions in their float-operation sequences.

## Narrower source-supported difference

The order change can alter the controlled-camera axes after float rounding. A concrete cardinal-input precondition isolates it from the diagonal-normalization and square-correction difference recorded elsewhere in the pair:

- Hold forward only (`forwardImpulse = 1.0F`), remain the controlled camera, non-immobile and unmounted; crouch while using an item that invokes the `0.2F` slowdown.
- Use a `SNEAKING_SPEED` value whose cast to float is `0.45000001788139343F`. This is source-reachable with Swift Sneak I: both `Attributes` define base `SNEAKING_SPEED` as `0.3`; the Swift Sneak definition assigns `LevelBasedValue.perLevel(0.15F)` with `ADD_VALUE` to that attribute (both `Enchantments.java:556–578`); level 1 calculates the `0.15F` base value (`LevelBasedValue.java:126–138`), and `AttributeInstance` adds `ADD_VALUE` modifiers to the base before `LocalPlayer` casts the result to float. The exact double before the cast is `0.45000000596046447`; the float used by both LocalPlayer paths is `0.45000001788139343F`.
- A's order is `1.0F * 0.2F`, then `* 0.45000001788139343F`, then `* 0.98F`, yielding forward axis `0.08820000290870667F`.
- B's order is `1.0F * 0.98F`, then `* 0.2F`, then `* 0.45000001788139343F`, yielding `0.08820001035928726F`. For this one-axis vector, the square-movement helper computes unit direction `(0,1)`, distance `1`, and returns the same B component; it does not erase the rounding difference.

The resulting values differ by `7.450580596923828E-9` (one float ULP at this magnitude). Both are nonzero, have squared length between `1.0E-7` and `1.0`, and therefore pass the shared consumer's zero cutoff without normalization. At yaw zero, the shared input-vector consumer receives the distinct forward components and applies its common nonzero speed scale. This establishes a bounded source-level input-vector difference, not a measured trajectory.

This witness does not rely on diagonal keyboard normalization: A's raw forward axis and B's normalized cardinal `Vec2` are both exactly `1.0F`. B's square-movement correction is identity for the one-axis case. The narrower delta is the relative placement of `0.98F` with respect to the item and sneaking multipliers. The original “B has no damping” statement must not be carried into a replacement snapshot.

## Disposition and next dependencies

The immutable F-09 snapshot cannot be accepted as written because its defining B-side absence claim is contradicted by B `LocalPlayer#modifyInput()`. Preserve it unchanged with this **REQUEST CHANGES** verdict. If the source owner keeps an F-09 finding, the next snapshot should narrow its claim to the source-proven order-dependent float result above, retain exact source hashes and guards, and leave the original bytes and review history intact. The owner/integration ledger can then record this verdict; I made no pair-run edits.

This adjudication closes only the independent D9 review question. Broader `D1–D6` inventories remain open; `D8` is bounded only for the keyboard key-state path, and `D10` remains open for the recorded key lifecycle routes. The pair remains partial and its broader input, tick, modifier, travel and coverage audit remain open. No implementation, wiki, mixed coordinator, build, test, client/server, TAS, Gym, Docker, push or main-branch merge was used.
