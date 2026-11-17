# Independent bounded source audit: pose and collision fit

Audited report SHA-256: `CF0D101CD23F2C44D3D2038051AF59058C2ADB79F228D9A5A8FD60AA397AB6EA` (`build/later-source-20261116/1.20.6--1.21.11/pose_collision/pose-collision-no-diff.md`).

## Verdict

**NO_DIFF corroborated for the report's bounded ordinary local-player standing/crouching and ceiling-forced pose-fit claims.** This verdict applies only to those claims and dependencies; it does not infer pair freeze or broader acceptance.

In both exact endpoint sources, `Player.updatePlayerPose` gates the decision on fit for `Pose.SWIMMING`, then selects the desired pose for spectator/passenger or if its box fits, otherwise falls back to crouching if that box fits and swimming otherwise. The ordinary desired-pose branch is crouching exactly when shift is down and the player is not flying; 1.21.11's `getDesiredPose` preserves that branch. The local-player crouching predicate matches across endpoints: not flying, swimming, or passenger; crouching box fits; and shift is down or the player is not sleeping and the standing box does not fit. The input representation changes, but the keyboard tick populates shift before `Player.tick` reaches `updatePlayerPose` in the local-player tick path.

The cited Player, Avatar, LivingEntity, LocalPlayer, Input/KeyboardInput/ClientInput, Entity, and CollisionGetter Java file SHA-256 values all match the report. The viewed methods support its dimensions/eye heights, pose-triggered dimension refresh, player exclusion from size-change position fudging, `1.0E-7` fit-box deflation, and no-collision short-circuit equivalence (with 1.21.11's default liquid flag false).

## Identity evidence limitation

The report's `mojmap.ready.json` and `mojmap.provenance.json` SHA-256 values match the current root-level files for both versions, and the ready markers identify versions `1.20.6` / `1.21.11` with `mojmap`. Their referenced `mojmap.sources.sha256` and `artifacts.sha256` files are not present at the ready markers' recorded output paths in the current checkout, so I could not independently re-check those two manifest digests or file membership against the manifests. All cited decompiled Java files are present and match the report's per-file hashes, which permits verification of the bounded source claims.

## Boundaries

No audit was performed of the report's expressly excluded sleeping/vehicle/bed-entry paths, predicate-only climbing, collision-shape production or unrelated input/vector movement. No intermediate patch boundary, runtime parity, full-pair coverage, pair freeze, or acceptance beyond this bounded slice is asserted.
