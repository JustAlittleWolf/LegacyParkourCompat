# 1.21.1 / 1.21.2 / 1.21.3 source sweep: player bubble and flight/climb consumers

Scope: exact Mojmap decompiled sources for player direct bubble-column callbacks and the specified ability-flight/climbability consumers. This is a bounded three-release comparison, not full pair coverage, pair freeze, or an independent audit. No wiki, product implementation, Git history, other frontier output, or generated product change was used.

## Source identities and hash checks

Canonical read-only tree: `D:\Javastuff\LegacyParkourCompat\decompiled_minecraft\<version>\mojmap`.

- **1.21.1 / Mojmap:** release ID is 1.21.1 in `1.21.1/mojmap.ready.json` and `mojmap.provenance.json`. Ready snapshot source manifest SHA-256 `900f956e00f6fc1300bb3d689ea49df2b1a57bcaa54617344ef456d95d47cb48`, artifact manifest `09ced418cbc7530a1d6d8802ee10c05cd576b217a2129655a71f30a2ae38f486`, diagnostics `44c184cd1385e5b23991b2698ff0ae9f1e62a9491de8dcac27e5a3f9af88cd4f`; all three sidecar hashes match the ready marker. Pinned client jar SHA-256 `499f6897d1837516680f3114072d8106e11c9adcd933fe5cf051b551089b0c99`; Mojang `client_mappings.txt` SHA-256 `140c47931cccc8fc9e4c22d7603e2d714d1a953a146f51ea7397d95c955536ec`. The five relevant source SHA-256 values below match `mojmap.sources.sha256`.
- **1.21.2 / Mojmap:** hidden `1.21.2/mojmap/.decompile-provenance.json` identifies the exact source tree and pins the client jar SHA-256 `613091da0f62426ae8dcf9afb30eb639485f493b81cda3a2b96b1fb8a6b9976c` and official `client.txt` SHA-256 `e37560e4889168916a73dbc4922c563002eff70fbecfe1cf5c50a7b222f451b2`. The five source hashes below match the manifest's `sources` map.
- **1.21.3 / Mojmap:** release ID is 1.21.3 in `1.21.3/mojmap.ready.json` and `mojmap.provenance.json`. Ready snapshot source manifest SHA-256 `d673bb5464853e3a2a92ed9b1ffe1789d4e62c097bb884f67c336fcbb3b178ce`, artifact manifest `0587668e5c70bb06dacf3496a4442f66dc9cdcab23f2350b844f14259cc40dcf`, diagnostics `065c0644d14a4ba2001713ba9a72470b414f01ab327aec772924709f3118f08f`; all three sidecar hashes match the ready marker. Pinned client jar SHA-256 `25190c556bc56d11070638ccab3b3c6efd76bb9684c92a2680a4de01c3c7138b`; Mojang `client_mappings.txt` SHA-256 `e92a9cb01c233075b51a6233d0c22f56dcda3fcab46438d3f7a1781d104dcd41`. The five relevant source SHA-256 values below match `mojmap.sources.sha256`.

| Relative source | 1.21.1 SHA-256 | 1.21.2 SHA-256 | 1.21.3 SHA-256 |
| --- | --- | --- | --- |
| `net/minecraft/world/entity/Entity.java` | `b81905c7879e2cc5c5063a41d865c4164ad919f156306705be791d1017b99850` | `a93719c302a0381a972af75ea360465e2e3551708dd07c34d4d40b7e5173c2b9` | `a93719c302a0381a972af75ea360465e2e3551708dd07c34d4d40b7e5173c2b9` |
| `net/minecraft/world/entity/player/Player.java` | `ed32b88c3c7c8418b83db41823520f2b6b0b49a98610306ef26e9681dc925c71` | `a803203e92aa4729d5f5c9b16085b6a43ce51d9907d309eb96736e9c7c1340de` | `a803203e92aa4729d5f5c9b16085b6a43ce51d9907d309eb96736e9c7c1340de` |
| `net/minecraft/world/entity/LivingEntity.java` | `324a3eee8496caab57cfaf5101ef576f1ae3c60c40e3857e96f35f3af9a3a0d8` | `087390495f4fdfd14b9e12230e7aea4fdc50913bb4d1d91119e72892881cfc52` | `087390495f4fdfd14b9e12230e7aea4fdc50913bb4d1d91119e72892881cfc52` |
| `net/minecraft/client/player/LocalPlayer.java` | `c555e68ac3c63ab9b4f9a9e31933e263b96350a2bc599d11a0de5928bc24b583` | `fbd40f1f47adfa66dda9b15188e5dce82af3e8e8d7c3dd0543e602a354ad3fe0` | `fbd40f1f47adfa66dda9b15188e5dce82af3e8e8d7c3dd0543e602a354ad3fe0` |
| `net/minecraft/world/level/block/BubbleColumnBlock.java` | `39c4da51460f4c2c06927f15de65a9ee2ae2f8bee0a2c9289cea375ad5d53a7c` | `9427b3e617753f5b3adc7a724fd80c87d4aa0e2f5d0eb38cd2ca34092e449b47` | `9427b3e617753f5b3adc7a724fd80c87d4aa0e2f5d0eb38cd2ca34092e449b47` |

## Findings and checked no-change slices

### Player bubble-column callbacks gain a creative-flight guard

- **1.21.1:** `world/entity/Entity.java`, `onAboveBubbleCol` at 2357 and `onInsideBubbleColumn` at 2369 provide the inherited vertical response. `Player.java` has no override of either method. The upward response sets Y to `min(1.8, y + 0.1)`; the drag-down response sets Y to `max(-0.9, y - 0.03)`. Inside-column Y is `min(0.7, y + 0.06)` upward or `max(-0.3, y - 0.03)` downward, then resets fall distance.
- **1.21.2 and 1.21.3:** `world/entity/player/Player.java`, `onAboveBubbleCol` at 355 and `onInsideBubbleColumn` at 362, call the inherited callback only under `!this.getAbilities().flying`. Their inherited `Entity` formulas remain the same (1.21.2 lines 2455–2478; same source hash and lines in 1.21.3).
- **Reachability and movement meaning:** `BubbleColumnBlock.entityInside` at 49 (1.21.1), 50 (1.21.2/1.21.3) dispatches `onAboveBubbleCol` when the block above is air and `onInsideBubbleColumn` otherwise. A local client player is a `Player` subclass and reaches these overrides through the block-inside callback. With `abilities.flying == true`, 1.21.1 applies bubble Y-velocity changes and the inside callback's fall-distance reset; 1.21.2 and 1.21.3 suppress both callback bodies. This is a source-confirmed player movement-response difference. Among examined releases, the behavior is absent in 1.21.1 and present by 1.21.2, then persists in 1.21.3; no earlier/later exact boundary is claimed.

### Creative-flight Player.travel side effects move/change

- **1.21.1:** `world/entity/player/Player.java`, `travel(Vec3)` at 1456 gates its flying branch on `abilities.flying && !isPassenger()`, stores the original Y velocity, calls `super.travel`, sets Y to the stored value times `0.6`, then calls `resetFallDistance()` and `setSharedFlag(7, false)`.
- **1.21.2 and 1.21.3:** `Player.java`, `travel(Vec3)` at 1445 keeps passenger travel in an outer branch and, for a non-passenger with flight ability, restores only Y velocity times `0.6` using `with(Direction.Axis.Y, ...)`; it does not clear shared flag 7 there. The old travel-local fall-distance reset is relocated to the beginning of `Player.aiStep()` (1.21.2 line 543; 1.21.3 line 543), guarded by `abilities.flying && !isPassenger()`, before `super.aiStep()`.
- **Reachability and movement meaning:** LocalPlayer uses inherited Player travel for its ordinary non-passenger tick. Compared with 1.21.1, the 1.21.2+ flight branch no longer clears the fall-flying shared flag at that location and moves the fall-distance reset to Player.aiStep. Do not model this as total removal of the flying reset: the source shows it remains at the start of aiStep. The timing can matter to intervening movement callbacks/state writers in the super.aiStep call; this sweep did not audit every ordering dependency. Among examined releases, the changed arrangement is present by 1.21.2 and unchanged in 1.21.3.

### Climbability and ladder behavior under flight: no source difference found

- `LivingEntity.onClimbable()` is structurally identical in all three: spectators return false; otherwise it reads the in-block state and returns true for `BlockTags.CLIMBABLE` or an open trapdoor directly above a ladder with matching facing (1.21.1 line 1517; 1.21.2/1.21.3 line 1561). It does not test `abilities.flying`.
- `LivingEntity.handleOnClimbable(Vec3)` is structurally identical (1.21.1 line 2280; 1.21.2/1.21.3 line 2379): reset fall distance, clamp X/Z to `[-0.15F, 0.15F]`, floor Y at `-0.15F`, then set negative Y to zero only when not scaffolding and `isSuppressingSlidingDownLadder()` is true for a Player.
- Base `LivingEntity.isSuppressingSlidingDownLadder()` returns shift state (1.21.1 line 3163; 1.21.2/1.21.3 line 3262). `LocalPlayer` overrides it in all three versions with `!this.getAbilities().flying && super.isSuppressingSlidingDownLadder()` (1.21.1 line 354; 1.21.2/1.21.3 line 352). Thus flight does not make climbable blocks stop being climbable; while flying, LocalPlayer's shift state does not suppress the ladder slide-to-zero clamp. This gate and its effect are unchanged across the examined releases.
- `LivingEntity.travel` still reaches climb handling through `handleRelativeFrictionAndCalculateMovement`; the water travel branch's `horizontalCollision && onClimbable()` step-up remains 0.2 Y in all three. The travel implementation was reorganized after 1.21.1, but the examined climb formulas/gates above are identical.

### Other flight entry / movement-gate checks

- LocalPlayer's jump-to-glide call site changes form: 1.21.1 checks ability flight, passenger status and a chest Elytra item/`ElytraItem.isFlyEnabled` before `tryToStartFallFlying`; 1.21.2/1.21.3 call `tryToStartFallFlying()` after jump and climbable checks. That helper now delegates to `canGlide`; Player's override returns false while abilities are flying, and LivingEntity's helper checks airborne/non-passenger/non-levitating state and the item's glider equipment slot. This appears to relocate the equivalent vanilla Elytra gates into shared helpers, alongside the new data-component route; no additional vanilla-player movement difference is accepted from this sweep.
- `Player.isSwimming()` continues to return false while abilities flight or spectator applies. `Player.updateSwimming()` continues to clear swimming when flight applies. In 1.21.1 and 1.21.2/1.21.3 these are the same gates.

## Ordered coverage and remaining gaps

1. Exact identity/provenance and per-class SHA checks: complete for the named versions and five sources listed above.
2. Bubble callback producer (`BubbleColumnBlock.entityInside`), player overrides/base math (`Player`/`Entity`): inspected for all three releases; flight guard difference found from 1.21.1 to 1.21.2 and present in 1.21.3.
3. Climb predicate, ladder suppression and climb velocity handling (`LivingEntity`, `LocalPlayer`): inspected at all three releases; no difference found.
4. Player swimming/flying and glide-start predicates plus Player/LivingEntity travel gates: inspected at the referenced slices; travel flight side-effect arrangement differs by 1.21.2; the glide-start rewrite appears behavior-preserving for vanilla Elytra but was not treated as a separately proven equivalence for non-vanilla/data-component equipment.
5. Full player tick order across all adjacent calls, all travel branches, other direct responses, and release-by-release intermediate patch boundaries: unexamined. The three-point comparison does not close the full tick/inventory coverage or pair freeze.
