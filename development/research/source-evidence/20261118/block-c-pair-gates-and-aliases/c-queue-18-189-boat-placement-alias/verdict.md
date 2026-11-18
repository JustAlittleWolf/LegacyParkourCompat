# Independent blind audit verdict: boat placement aliases, 1.8–1.8.9

**Terminal: COMPLETED.** The bounded movement claim is confirmed. The audited boat callback places an attached player by XYZ only; its only relevant change across the interval is the mount-height constant's Java type/value between 1.8 and 1.8.1. The input report's source-provenance limitation is corrected below.

## Immutable input and bounds

- Audited report: `C:\Users\Wolfi\.codex\visualizations\2026\11\17\01a1eb7b-b9ff-7a20-8686-0295dde20f26\c-queue-18-189-boat-placement-alias\report.md`
- Audited report SHA-256: `984e133434562f5b3c7f063c0684dfe9bee022b112313dc02b7762af59799314`.
- Exact source bounds: canonical local `decompiled_minecraft/1.8/ornithe-feather/` and each intervening mapping root `1.8.1` through `1.8.8`, plus outer `decompiled_minecraft/1.8.9/` manifests and its `ornithe-feather/` sources. No other release, behavior area, product/Git material, wiki, or backlog was examined.
- Relevant source members: `entity/vehicle/BoatEntity.getMountHeight()` and `.updateRiderPositon()`; `entity/living/player/PlayerEntity.getRideHeight()`; `entity/Entity.rideTick()` and `setPosition(double,double,double)`.

## Movement verdict

`BoatEntity.updateRiderPositon()` on every checked version gates on `this.rider != null`, computes the two horizontal offsets as `cos(yaw * PI / 180.0) * 0.4` and `sin(yaw * PI / 180.0) * 0.4`, then makes one `rider.setPosition(boat.x + dx, boat.y + getMountHeight() + rider.getRideHeight(), boat.z + dz)` call. There is no yaw, head-yaw, body-yaw, or old-angle write in this method. The rider tick invokes `tick()`, then calls the vehicle's rider-position callback when still mounted, before the shown rider/vehicle yaw and pitch delta bookkeeping. Player `getRideHeight()` returns `-0.35` at both endpoints.

At 1.8, `getMountHeight()` returns `this.height * 0.0 - 0.3F`. The expression is double arithmetic: `height * 0.0` is double, and the float literal is rounded to float `-0.30000001192092896` before promotion for subtraction. At every checked version from 1.8.1 through 1.8.9, it returns `-0.3` as a double literal. For an ordinary player staying mounted, boat `(10,20,30)`, yaw `0.0F`, the callback writes X/Z `(10.4,30.0)` at both; Y is `19.34999998807907` at 1.8 versus `19.35` from 1.8.1. Thus the source confirms a position-only Y delta of about `1.1920929e-8` for this witness, and confirms no orientation write as an identical contract. It does not establish any behavior outside rider placement.

## Provenance correction and source pins

The input report's statement that canonical `1.8`–`1.8.8` has no per-release provenance sidecars is incorrect. Each mapping root contains `.decompile-provenance.json`; its `inputs` records that release's Feather mapping artifact and SHA-256, and `sources` records the SHA-256 for `net/minecraft/entity/vehicle/BoatEntity.java`. In order 1.8 through 1.8.8, the source hashes are:

- 1.8: `d6f9f8ac97f5f337db499f0fe0b34a6e7b154b9fa79db270ef687ae0b412ec55`
- 1.8.1: `5bafd066ccccdb2931fb26f35f32d80006d9c2e174680beb5e2586dcc64ea366`
- 1.8.2–1.8.8: `77ef72c28024b754ff76f2c67e14dd61832394bac2bd7b62b29708560e588da2` in each release
- 1.8.9: `77ef72c28024b754ff76f2c67e14dd61832394bac2bd7b62b29708560e588da2`, recorded by `decompiled_minecraft/1.8.9/artifacts.sha256` and matching the file bytes.

Per-release mapping coordinates are `feather-gen2-<version>+build.2-mergedv2.jar`, with each exact input SHA recorded in its hidden mapping-root sidecar. The outer 1.8.9 directory has `ornithe-feather.provenance.json` (SHA-256 `ba066fc8f1c7ceb9819d2106338d9e9f2c2b96eb5a8329eb40e171b1f9889185`), naming mapping artifact files `feather-gen2-1.8.9+build.2-mergedv2.jar` and `feather-gen2-1.8.9+build.2.tiny`; it does not have a root-level `.decompile-provenance.json`. The per-release hidden source sidecars do exist for 1.8–1.8.8, so the cited limitation is not present. This correction changes provenance description only, not the movement conclusion.

## Final identity

Verdict path: `C:\Users\Wolfi\.codex\visualizations\2026\11\17\01a1eb7b-b9ff-7a20-8686-0295dde20f26\c-queue-18-189-boat-placement-alias-audit\verdict.md`

The immutable input report SHA-256 is `984e133434562f5b3c7f063c0684dfe9bee022b112313dc02b7762af59799314`. Verdict SHA-256 is recorded with terminal completion in the handoff.
