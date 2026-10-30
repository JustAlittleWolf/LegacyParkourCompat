# Scope disposition and final source-coverage verdict

## Bound audit inputs

This supplement is tied to the independent full-pair audit report and support-state production closure by exact SHA-256:

| Artifact | SHA-256 |
|---|---|
| `full-pair-audit-r3/report.md` | `68040F4BF78407D074311ED1B1EB2030F3659D5EB811285E664EFE5FA8E99974` |
| `support-state-production/closure.md` | `64082AFC69F868552166D3C127EFB2A92866CEAE99B0F0FC0346543E0DDEA14F` |

Both digests were recomputed from the current files before this supplement was written. The source identity and matrix identity are those recorded in the bound artifacts: exact Feather Gen2 `ornithe-feather` sources for 1.11.2 and 1.12.2; Java matrix SHA-256 `f41e7591f77d44268dc58d7b42a496b7385ffeb79992568c2dac50785d0f948c`.

## Fixed-state Ladder, Vine, and SnowLayer consumer closure

I independently checked the exact source files in both canonical decompilations. SHA-256 values below were recomputed directly from the source files and match the source manifests and the bound support closure:

| Source | 1.11.2 SHA-256 | 1.12.2 SHA-256 |
|---|---|---|
| `net/minecraft/block/LadderBlock.java` | `3ceafedeb39e0bc3862bed2cc784f490340d832563764adb225ecea40ee83ee7` | `c9d59cd266af92a731710fda0e2a8e60c61fb07131fecdd3b04759277ae24d94` |
| `net/minecraft/block/VineBlock.java` | `b06bafd1f06a7a75ef0239e87f190afe9709dff05ccbeee72144222c2c1c558e` | `3ee4302208ba5820e5fa7f3b166988b673ba691dc78823d1ccfd5e62712368ac` |
| `net/minecraft/block/SnowLayerBlock.java` | `74bc424529548cdd37abc67cd7bffb5a1ebc7b82589c08862c5ea553da2ad172` | `6ed1d83d9230cd3c1434b4ef5055963048763eea4dbdeaa6a58dd3b8591b915b` |
| `net/minecraft/entity/living/LivingEntity.java` | `bb7dc6c9e423a9568d6433d51bba12e7aee4555fbf3fb3e2b87f618382279f2f` | `190e9ac551538e015d9e4d6c42856e5ba32b593131cf6d93895e7b29533f1ee6` |

The complete fixed-state consumer set relevant to the reachable player movement chain closes as follows:

| Block | Fixed-state collision/climb consumer | Paired result |
|---|---|---|
| Ladder | Collision shape selects a box solely from stored `FACING`. The player climb predicate checks the raw Ladder block identity. | Shape selection is unchanged; climb predicate is unchanged. No FaceShape lookup occurs in either movement consumer. |
| Vine | Collision-shape provider returns the empty shape directly. Its separate outline `getShape` resolves virtual `UP`, but the movement collision provider does not call that method. The player climb predicate checks raw Vine identity. | Collision and climb results are unchanged for the same raw state/world block identity. Virtual `UP` is not a movement input. |
| SnowLayer | Collision shape derives from stored `LAYERS` and the same height formula; support state is not consulted by that movement query. | Shape and collision-height formula are unchanged for the same state. No FaceShape lookup occurs in the movement consumer. |


The related `LivingEntity.isPushable` use of `isClimbing` also receives the same unchanged predicate result. `SnowLayerBlock.canWalkThrough` is used by path-navigation/village queries, not by the local-player collision or movement terminal path; it is outside this player movement slice. The reachable path is `Entity.move` → `World.getCollisions` → state/block collision-shape dispatch, plus `LivingEntity.isClimbing` and its existing ladder-response gates. The latter is paired: it identifies Ladder/Vine (or the existing open-trapdoor/ladder combination) from the feet state and has no FaceShape branch. The checked local-player collision adjustment uses the same collision-shape consumers. None of the three block classes adds a changed direct velocity/contact callback on this path. The bound support closure's source-line references identify these dispatch and predicate sites; this supplement confirms the exact fixed-state conclusions against the canonical files and hashes above.

## Bounded disposition

The coordinator's scope ruling excludes historical placement, client-predicted placement, attachment/support validation, neighbor-derived updates, growth, and other vanilla block-state production/evolution. Accordingly, the Ladder/Vine/SnowLayer state-production differences are closed as **out of scope for this source-coverage pair**, not as server-only and not as equivalent behavior. The fixed-raw-state movement consumers remain in scope and are independently closed above.

**Freeze verdict: source-coverage freeze is defensible for 1.11.2 → 1.12.2 under the fixed-vanilla-state movement scope.** The bound audit's ordered reachable tick/terminal inventory and 2,066-path Java matrix reconciliation are complete; the remaining support-state production gate is explicitly excluded by the coordinator ruling. This is a discovery/source-coverage verdict only; it does not claim acceptance of implementation, build status, or runtime parity.

The movement-relevant source findings to carry forward are the bed landing velocity response, level-8 water current face-predicate input, and query-time Fence/Pane/Wall virtual connection collision arms. The latter remain in scope because collision collection dynamically resolves their properties from raw neighboring states at query time. No movement delta was established for the reviewed relative-movement, modifier/effect, or fixed-state Ladder/Vine/SnowLayer consumer paths.

Remaining exclusions are the ruled-out state-production/evolution branches above; server-only dismount/farmland relocation and other server-only movement handling; rendering-only, vehicle/non-player physics, health/food production, damage calculation, bed-entry eligibility, and modern-only behavior. These exclusions do not leave an unresolved fixed-state movement consumer dependency in the covered local-player tick/terminal graph.
