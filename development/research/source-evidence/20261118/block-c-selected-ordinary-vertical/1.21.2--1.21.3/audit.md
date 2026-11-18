# Independent source audit: ordinary vertical movement, 1.21.2 → 1.21.3

## Verdict

**No source difference found in the assigned bounded path.** The exact compared source files for the local-player tick entry, living-player vertical travel, movement resolution/fall accumulation, and gravity defaults have byte-identical SHA-256 values in both releases. This supports only the ordinary, local, dry, nonvehicle, non-gliding/non-flying vertical path and common fall state named in the assignment. It does not freeze complete-pair coverage or establish runtime parity.

## Evidence identity

- Host/workflow: Windows PowerShell; read root `AGENTS.md`, `README.md`, `development/workflows/discovery/WORKFLOW.md`, `development/workflows/discovery/source/WORKFLOW.md`, `development/workflows/implementation/WORKFLOW.md`, and `buildSrc/README.md`. Source discovery remained blind to wiki hints, product implementations, indexes, and Git history.
- **1.21.2 Mojmap:** `decompiled_minecraft/1.21.2/mojmap/.decompile-provenance.json`, SHA-256 `00f399407dc447737ea83e3e46562418f8c712f66e3f51cb85d46afa2ecac575` (matches assigned identity). Its pinned inputs include version metadata URL ending `/1.21.2.json` (SHA-256 `793e3fd0451354a26aaa00253dcb6600add11f9d2a90f7bdef75ddf5b38c2b45`), client artifact SHA-256 `613091da0f62426ae8dcf9afb30eb639485f493b81cda3a2b96b1fb8a6b9976c`, and official `client.txt` mappings SHA-256 `e37560e4889168916a73dbc4922c563002eff70fbecfe1cf5c50a7b222f451b2`.
- **1.21.3 Mojmap:** release-level `decompiled_minecraft/1.21.3/mojmap.ready.json` SHA-256 `72385aa99d249a56d742b9c07823c1155c74c39dda1fbc6eacc12c980cd12e02`; `mojmap.provenance.json` SHA-256 `24093437c0cd41d807ab0829068ef97828b65bbb95b38dfdc4f706883a8483d7`; `mojmap.sources.sha256` SHA-256 `d673bb5464853e3a2a92ed9b1ffe1789d4e62c097bb884f67c336fcbb3b178ce`. The ready marker identifies `versionId=1.21.3`, `mapping=mojmap`; provenance identifies `versionId=1.21.3`, `namespace=mojmap`, and exact run `mappingOption=mojmap`. Its client artifact SHA-256 is `25190c556bc56d11070638ccab3b3c6efd76bb9684c92a2680a4de01c3c7138b`.
- Every compared source file below matched its version manifest's recorded file digest, and the 1.21.2 and 1.21.3 digest values are identical.

## Compared exact source files

SHA-256 values (same for both releases):

| Mojmap source | SHA-256 |
|---|---|
| `net/minecraft/client/player/LocalPlayer.java` | `fbd40f1f47adfa66dda9b15188e5dce82af3e8e8d7c3dd0543e602a354ad3fe0` |
| `net/minecraft/world/entity/player/Player.java` | `a803203e92aa4729d5f5c9b16085b6a43ce51d9907d309eb96736e9c7c1340de` |
| `net/minecraft/world/entity/LivingEntity.java` | `087390495f4fdfd14b9e12230e7aea4fdc50913bb4d1d91119e72892881cfc52` |
| `net/minecraft/world/entity/Entity.java` | `a93719c302a0381a972af75ea360465e2e3551708dd07c34d4d40b7e5173c2b9` |
| `net/minecraft/world/entity/ai/attributes/Attributes.java` | `14bcc5821d0f67f41fdc3e512556181823cff5c43f84fc1007e51d25f413a3ea` |
| `net/minecraft/world/entity/ai/attributes/DefaultAttributes.java` | `0bf443a98b148dd4ce505d1827be9125f972fa2de64823d2e727e09852374baa` |

## Bounded trace and checked ranges

Line numbers below are in both exact files; byte identity means the cited bodies are identical across the pair.

1. **Reachable client player tick and Y deadzone.** `LocalPlayer.tick` calls `super.tick` at `LocalPlayer.java:188–212`; `Player.tick` calls `super.tick` at `Player.java:253–280`; `LivingEntity.tick` calls `aiStep()` at `LivingEntity.java:2417–2459`. The local override reaches `super.aiStep()` at `LocalPlayer.java:638–800` (call at line 795), and `Player.aiStep` reaches `super.aiStep()` at `Player.java:543–556`. In `LivingEntity.aiStep`, client/non-effective-AI velocity is first scaled by `0.98` (`2685–2690`), then `abs(deltaMovement.y) < 0.003` changes Y to `0.0` (`2697–2713`). This is the Y velocity later presented to travel; equality of the complete file shows no changed ordering or threshold.
2. **Ordinary dry, non-gliding travel.** `LivingEntity.aiStep` calls `travel($$10)` at `2754–2772` outside its excluded riding/gliding cases. `travel` selects `travelInAir` for the bounded dry, non-fall-flying branch at `2178–2188`. `travelInAir` first calls `handleRelativeFrictionAndCalculateMovement` (`2191–2196`); that helper moves with current delta movement and returns post-move velocity (`2351–2361`). It then uses that post-move Y, subtracts effective gravity when the levitation branch does not apply and the client has the affecting chunk (`2196–2206`), and applies the ordinary vertical multiplier `0.98F` after gravity when friction is not discarded (`2208–2213`). The `discardFriction` field defaults to false (`LivingEntity.java:197`) and the accessor returns it (`649–655`). This audit excludes effects and missing-chunk fallback; the code confirms they are gates, not claims about their behavior.
3. **Ordinary gravity default and gate.** `LivingEntity.getDefaultGravity` reads `Attributes.GRAVITY` (`LivingEntity.java:2168–2171`); `getEffectiveGravity`'s slow-falling adjustment is effect-gated (`2173–2176`) and excluded. The `GRAVITY` ranged attribute has default `0.08` (`Attributes.java:41–43`); the living attribute builder includes it (`LivingEntity.java:318–330`), and player defaults extend that builder (`Player.java:226–239`, registered by `DefaultAttributes.java:138`). `Entity.getGravity` returns zero only under `isNoGravity`, otherwise default gravity (`Entity.java:1185–1205`); no-gravity synchronized data defaults false (`Entity.java:223, 267`).
4. **Y movement request and common fall state.** `LivingEntity.handleRelativeFrictionAndCalculateMovement` passes current velocity to `Entity.move` (`LivingEntity.java:2351–2361`). In `Entity.move`, the resolved displacement updates position (`Entity.java:619–652`); vertical collision and below-ground flags are computed from requested versus resolved Y (`656–667`), then ordinary locally controlled client movement reaches `checkFallDamage` (`667–672`). The collision resolver and support/block callbacks themselves are boundary providers and were not audited. Base `Entity.checkFallDamage` sends the already-accumulated landing event then resets on ground; otherwise only a negative resolved Y displacement accumulates, using float conversion (`1216–1232`). `LivingEntity.checkFallDamage` forwards to the base path (`341–373`). `fallDistance` defaults to Java's zero-initialized field (`Entity.java:192`) and `resetFallDistance` assigns `0.0F` (`2490–2492`). Damage calculation and block-specific landing effects are out of scope.
5. **Network threshold excluded.** `LocalPlayer.sendPosition` has a position reminder threshold that includes Y (`LocalPlayer.java:224–260`); this is network reporting, not a movement writer, so it was examined only to avoid conflating it with the assigned vertical velocity deadzone. It is outside the verdict scope.

## Explicit boundaries and unexamined areas

Excluded: fluids/swimming, effects, equipment, powder snow or block effects, collision/support/shape providers and callbacks, climbing, jumping and other Y writers, rotation, horizontal velocity/acceleration, vehicles, gliding/flying, spectators/no-physics, missing client chunks, damage, and visual/network behavior. The checked comparison is not a complete reachable-tick inventory for the whole game or pair.

## Independent audit conclusion

For the assigned exact pair and bounded operation, source comparison yields **no 1.21.2 → 1.21.3 movement delta**: every selected source file has the same complete byte hash in both exact Mojmap trees, and the trace establishes the path and its default ordinary gates. Intermediate patch applicability and behavior outside these bounds remain undetermined.

## Phase 2 independent verdict addendum

**Signed independent verdict — /root/c_vertical_117_1194_scheduler/audit_1212_1213, 2026-11-18.** The author source file was read only after its whole-file SHA-256 matched the assigned digest `33513a2a39d414858149d962e7326e67bc170159850f4d84cf7c0560a93b6ce7`. The frozen blind audit prefix was rehashed first and matched `15164c09c026bbe7960349ce219b168923d8ecffe56f22370435d55dbb94dc65`.

**Agreement:** I independently verified the exact release/mapping identities, all four movement-source whole-file hashes cited in the author comparison, and the six source identities recorded in the frozen audit. The local-player → player → living-entity tick/AI-step dispatch, `0.003` Y deadzone, ordinary dry travel gates, `0.08` gravity default, `0.98F` vertical drag order, `SELF` move request, and common fall-distance update/reset are byte-identical across 1.21.2 and 1.21.3. The no-delta verdict is supported within the assigned path. The author's `Player.travel(Vec3)` range 1445–1467 is present; its passenger/swimming/flying branches are outside the stated case and its ordinary branch delegates to `LivingEntity.travel`.

**Material trace disagreement:** The author's arithmetic witness conflates the Y velocity written at the end of `travelInAir` with the Y displacement requested earlier in that same call. `travelInAir` first calls `handleRelativeFrictionAndCalculateMovement`, which calls `move(SELF, currentDeltaMovement)` (LivingEntity 2191–2196, 2351–2355); only after that move returns does it subtract gravity and apply `0.98F` (2196–2213). Thus, after the `abs(y) < 0.003` deadzone writes Y to zero, the same tick's movement request is Y=0 and its position delta is zero absent other writers/collision effects. The arithmetic result `(0.0 - 0.08) * 0.98F ≈ -0.0784000015258789` is the Y velocity written after that move and is available as a movement request on a later tick. For the ordinary client player, `LivingEntity.aiStep` scales velocity by `0.98` before applying the deadzone on the next tick (`2685–2713`); if no intervening writer or collision changes it, that next request is therefore about `-0.07683200149536133`, not `-0.0784000015258789`. The source ordering itself is unchanged in both releases; this disagreement is in the predicted tick/position consequence, not in the source-difference verdict.

**Boundary:** I reviewed no wider player movement inventory, blocks/collision providers, effects, jumps, horizontal movement, networking behavior, runtime parity, or intermediate patch boundary. This addendum audits only the frozen author's bounded claim and preserves the original audit bytes as the exact prefix.
