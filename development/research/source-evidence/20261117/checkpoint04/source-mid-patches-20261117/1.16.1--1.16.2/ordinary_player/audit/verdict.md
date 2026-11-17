# Independent audit — 1.16.1 → 1.16.2 ordinary-player packet

Verdict: ACCEPT the bounded source-comparison claims and PARTIAL coverage summary. This verdict does not freeze the pair and does not establish implementation applicability or runtime parity.

## Immutable inputs and source identity

Audited report SHA-256: `A542F16666AED2E489296B8AF886D7717092A4809EADC516DAEC79F11D57967A`.

Audited candidate drafts:

- `player-airborne-edge-backoff.md` — SHA-256 `5107A28034E7CA52881B83D9D7DC809A11CFAC51A3BEE76CDF7D5572BA4495B3`.
- `local-player-suffocation-escape.md` — SHA-256 `868DF34DD179E59546793F38DCE241395C8B8B3A3CA676A51EB012C2E56866B5`.
- `soul-speed-block-position.md` — SHA-256 `DE361025678422E62CF65B7633377364FB6FD673986F4B98BBA6E11F4571D5AB`.

The exact source roots are Mojmap `decompiled_minecraft/1.16.1/mojmap/` and `decompiled_minecraft/1.16.2/mojmap/`. The source manifest and provenance hashes match the assigned identities for both endpoints; ready markers identify `versionId` 1.16.1 and 1.16.2 with mapping `mojmap`. I rehashed the four relevant Java files per endpoint (`LocalPlayer.java`, `Player.java`, `LivingEntity.java`, and `Entity.java`); every disk hash matches its entry in that endpoint's source manifest. The input locator records complete inventories and exact mapping identity.

I independently parsed the two source manifests: 3,572 and 3,516 paths; 899 same-path hash changes, 96 additions, and 152 removals. Those counts match the report. They are navigation counts only and do not prove complete movement coverage.

## Bounded source claims

1. **Player crouch edge backoff — source claim accepted.** In 1.16.1 `Player.maybeBackOffFromEdge` requires `onGround`; in 1.16.2 the gate adds `!abilities.flying` and delegates ground proximity to new `isAboveGround()`. The helper is `onGround || (fallDistance < maxUpStep && !level.noCollision(this, boundingBox.move(0, fallDistance - maxUpStep, 0)))`. The subsequent 0.05 horizontal trimming loops are present in both methods. The stated consequence is conditional on this gate being true while the player is airborne. A concrete geometry witness and applicability disposition remain open.

2. **Local-player suffocation escape — code-path claim accepted.** Both versions call the response four times in the same order behind `!noPhysics` in `LocalPlayer.aiStep`, before later input/travel work. In 1.16.1 `checkInBlock` checks the probed block's `isSuffocating` state through `Player.freeAt` across integer Y slices spanning the player box, then chooses the closest free horizontal neighbor and writes ±0.1 to one horizontal velocity component. In 1.16.2 `moveTowardsClosestSpace` uses `suffocatesAt`, which checks a deflated block-column AABB over the player box's vertical interval with `noBlockCollision` and an `isSuffocating` predicate; direction selection and the ±0.1 component write match the described method. The actual outcomes depend on the exact state geometry and collision semantics; the draft correctly leaves a concrete vanilla-state witness open.

3. **Soul Speed block lookup — code-path claim accepted.** The `getBlockSpeedFactor()` override still requires a positive Soul Speed level and the `SOUL_SPEED_BLOCKS` tag. The tag lookup changes from `getBlockStateOn()` in 1.16.1 to the state at `getBlockPosBelowThatAffectsMyMovement()` in 1.16.2. The cited `getOnPos()` and movement-affecting-position formulas are present as described. Tag/resource membership and a reachable ordinary-state witness separating the selected positions remain open, as the draft states.

## Disposition and remaining gates

The report accurately labels itself PARTIAL and does not overstate resource membership, full tick closure, dependency closure, or pair completion. The three drafts accurately describe bounded source-level changes and state the important limitations. This audit accepts those source-level descriptions only; it does not independently accept an eligible, applicable movement finding for implementation. In particular, the suffocation and Soul Speed claims still need concrete vanilla-state witnesses and their cited dependencies need closure. The pair remains PARTIAL. Resources, the remaining tick/callback/provider inventory, applicability, runtime parity, and full-pair independent coverage audit remain open.